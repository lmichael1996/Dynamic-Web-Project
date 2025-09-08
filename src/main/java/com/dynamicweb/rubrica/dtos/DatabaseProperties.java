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
     * Metodo helper unificato per la validazione di lunghezza e pattern regex.
     * 
     * <p>Valida contemporaneamente la lunghezza massima e il formato di una stringa
     * utilizzando un pattern regex. Utilizzato per semplificare la validazione
     * dei vari campi della configurazione database.</p>
     * 
     * @param value la stringa da validare
     * @param fieldName il nome del campo per i messaggi di errore
     * @param maxLength la lunghezza massima consentita
     * @param regex il pattern regex da utilizzare per la validazione del formato
     * @throws IllegalArgumentException se la validazione fallisce
     */
    private void validateFieldFormat(String value, String fieldName, int maxLength, String regex) {
        // Controllo lunghezza
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " troppo lungo: massimo " + maxLength + " caratteri");
        }
        
        // Controllo pattern
        if (!Pattern.compile(regex).matcher(value.trim()).matches()) {
            throw new IllegalArgumentException(fieldName + " non valido: " + value);
        }
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
        // Validazione porta
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Porta non valida: " + port);
        }

        // Validazioni unificate con controllo lunghezza e pattern
        
        // Host (hostname/IP)
        String hostPattern = "^(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)*)?" +
                "[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$|" +
                "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        validateFieldFormat(host, "Host", 255, hostPattern);

        // Nome database
        String dbPattern = "^[a-zA-Z_][a-zA-Z0-9_]{0,63}$";
        validateFieldFormat(dbName, "Nome database", 64, dbPattern);

        // Username
        String usernamePattern = "^[a-zA-Z0-9_@.-]+$";
        validateFieldFormat(username, "Username", 32, usernamePattern);

        // Password (solo controllo lunghezza)
        if (password.length() > 128) {
            throw new IllegalArgumentException("Password troppo lunga: massimo 128 caratteri");
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
