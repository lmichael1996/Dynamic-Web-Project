package com.dynamicweb.rubrica.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Connection;
import java.util.regex.Pattern;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * DTO per le proprietà di configurazione del database MySQL.
 * Contiene i parametri necessari per stabilire una connessione al database
 * e fornisce metodi per validazione e test della connettività.
 * 
 * <p>Include validazione per hostname/IP, porta, nome database, username e password
 * secondo le regole di MySQL.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseProperties {
    
    /** Hostname o indirizzo IP del server MySQL */
    private String host;
    
    /** Porta del server MySQL (default: 3306) */
    private int port;
    
    /** Nome del database MySQL */
    private String dbName;
    
    /** Username per la connessione al database */
    private String username;
    
    /** Password per la connessione al database */
    private String password;

    /**
     * Costruisce l'URL JDBC completo per la connessione MySQL.
     * 
     * <p>Genera una stringa di connessione nel formato:
     * jdbc:mysql://host:porta/database?parametri</p>
     * 
     * @return URL JDBC formattato per MySQL
     * @throws IllegalArgumentException se la configurazione non è valida
     */
    public String buildJdbcUrl() {
        return String.format(
            "jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", 
            host, 
            port, 
            dbName
        );
    }

    /**
     * Metodo helper per la validazione di pattern regex.
     * 
     * <p>Compila il pattern regex e verifica se la stringa fornita corrisponde.
     * Utilizzato internamente per validare i vari campi della configurazione database.</p>
     * 
     * @param regex il pattern regex da utilizzare per la validazione
     * @param value la stringa da validare contro il pattern
     * @return {@code true} se la stringa corrisponde al pattern, {@code false} altrimenti
     */
    private boolean pattern(String regex, String value) {
        return Pattern.compile(regex).matcher(value).matches();
    }
    
    /**
     * Valida tutti i parametri di configurazione del database.
     * 
     * <p>Controlla che host, porta, nome database, username e password
     * rispettino le regole di validità per MySQL.</p>
     * 
     * @throws IllegalArgumentException se uno o più parametri non sono validi
     */
    public void validateConfiguration() {
        // 1. Controlli lunghezza stringhe
        if (host.length() > 255) {
            throw new IllegalArgumentException("Host troppo lungo: massimo 255 caratteri");
        }
        if (dbName.length() > 64) {
            throw new IllegalArgumentException("Nome database troppo lungo: massimo 64 caratteri");
        }
        if (username.length() > 32) {
            throw new IllegalArgumentException("Username troppo lungo: massimo 32 caratteri");
        }
        if (password.length() > 128) {
            throw new IllegalArgumentException("Password troppo lunga: massimo 128 caratteri");
        }

        // 2. Validazione porta
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Porta non valida: " + port);
        }

        // 3. Validazione formato host (hostname/IP)
        String hostPattern = "^(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)*)?" +
                "[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$|" +
                "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        if (!pattern(hostPattern, host.trim())) {
            throw new IllegalArgumentException("Host non valido: " + host);
        }

        // 4. Validazione formato nome database
        String dbPattern = "^[a-zA-Z_][a-zA-Z0-9_]{0,63}$";
        if (!pattern(dbPattern, dbName.trim())) {
            throw new IllegalArgumentException("Nome database non valido: " + dbName);
        }

        // 5. Validazione formato username
        String usernamePattern = "^[a-zA-Z0-9_@.-]+$";
        if (!pattern(usernamePattern, username.trim())) {
            throw new IllegalArgumentException("Username non valido: " + username);
        }
    }    
    
    /**
     * Testa la connettività al database con i parametri configurati.
     * 
     * <p>Crea una connessione temporanea per verificare che i parametri
     * siano corretti e che il database sia raggiungibile. La connessione
     * viene chiusa automaticamente dopo il test.</p>
     * 
     * @throws RuntimeException se la connessione fallisce o non è valida
     */
    public void testConnection() {    
        try {
            DriverManagerDataSource testDataSource = new DriverManagerDataSource();
            testDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            testDataSource.setUrl(buildJdbcUrl());
            testDataSource.setUsername(getUsername());
            testDataSource.setPassword(getPassword());

            try (Connection connection = testDataSource.getConnection()) {
                if (!connection.isValid(5)) {
                    throw new RuntimeException("Connessione al database non valida");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore di connessione al database: " + e.getMessage(), e);
        }
    }
}
