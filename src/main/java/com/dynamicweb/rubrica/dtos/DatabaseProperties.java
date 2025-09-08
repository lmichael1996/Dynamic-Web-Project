package com.dynamicweb.rubrica.dtos;

import lombok.Data;
import lombok.Getter;
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
 * secondo le regole di MySQL. Supporta campi opzionali nullable come età e indirizzo.</p>
 * 
 * <p>La classe centralizza la costante del driver MySQL per un uso consistente
 * in tutta l'applicazione ed evita duplicazione di codice.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseProperties {
    
    /** 
     * Driver JDBC per MySQL - costante centralizzata utilizzata in tutta l'applicazione.
     */
    public static final String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    
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
     * <p>Include parametri ottimizzati per MySQL 8.0:
     * - useSSL=false per connessioni locali
     * - allowPublicKeyRetrieval=true per autenticazione
     * - serverTimezone=UTC per gestione fuso orario</p>
     * 
     * @return URL JDBC formattato per MySQL
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
    private void validateFieldFormat(
        String value, 
        String fieldName, 
        int maxLength, 
        String regex) {
        // Controllo lunghezza
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " troppo lungo: massimo " + maxLength + " caratteri");
        }
        
        // Controllo pattern
        if (!Pattern.compile(regex).matcher(value).matches()) {
            throw new IllegalArgumentException(fieldName + " non valido: " + value);
        }
    }
    
    /**
     * Valida tutti i parametri di configurazione del database.
     * 
     * <p>Controlla che host, porta, nome database, username e password
     * rispettino le regole di validità per MySQL 8.0. La validazione
     * include controlli di formato, lunghezza e caratteri consentiti.</p>
     * 
     * <p>Supporta configurazioni per database con campi nullable come
     * età e indirizzo nella tabella lista_contatti.</p>
     * 
     * @throws IllegalArgumentException se uno o più parametri non sono validi
     */
    public void validateConfiguration() {
        // Host (hostname/IP)
        String hostPattern = "^(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)*)?" +
                "[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$|" +
                "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        validateFieldFormat(
            host, 
            "Host", 
            255, 
            hostPattern);

        // Porta (solo controllo range)
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Porta non valida: " + port);
        }

        // Nome database
        validateFieldFormat(
            dbName, 
            "Nome database", 
            64, 
            "^[a-zA-Z_][a-zA-Z0-9_]{0,63}$"
        );

        // Username
        validateFieldFormat(
            username, 
            "Username", 
            32, 
            "^[a-zA-Z0-9_@.-]+$"
        );

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
     * <p>Utilizza un timeout di 5 secondi per verificare la validità
     * della connessione, ottimale per connessioni locali Docker.</p>
     * 
     * @throws Exception se la connessione fallisce o non è valida
     */
    public void testConnection() {    
        try {
            DriverManagerDataSource testDataSource = new DriverManagerDataSource();
            testDataSource.setDriverClassName(MYSQL_DRIVER_CLASS);
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
