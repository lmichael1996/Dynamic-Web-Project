package com.dynamicweb.rubrica.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.regex.Pattern;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * DTO per le proprietà di configurazione del database MySQL.
 * 
 * <p>Contiene i parametri necessari per stabilire una connessione al database
 * e fornisce metodi per validazione e test della connettività.</p>
 * 
 * <p>Include validazione per hostname/IP, porta, nome database, username e password
 * secondo le regole di MySQL.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Data
@AllArgsConstructor
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
        validateConfiguration();
        return String.format(
            "jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", 
            host, 
            port, 
            dbName
        );
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
        if (!isValidHost(host)) {
            throw new IllegalArgumentException("Host non valido: " + host);
        }
        
        if (!isValidPort(port)) {
            throw new IllegalArgumentException("Porta non valida: " + port);
        }
        
        if (!isValidDatabaseName(dbName)) {
            throw new IllegalArgumentException("Nome database non valido: " + dbName);
        }
        
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username non valido: " + username);
        }
        
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password non valida");
        }
    }

    /**
     * Valida l'hostname o indirizzo IP del server.
     * 
     * <p>Accetta hostname validi (RFC 952/1123) e indirizzi IPv4.
     * Esempi validi: localhost, example.com, 192.168.1.1</p>
     * 
     * @param host hostname o IP da validare
     * @return true se l'host è valido, false altrimenti
     */
    private boolean isValidHost(String host) {
        // Pattern per validare hostname o IP
        String hostPattern = "^(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)*)?" +
                           "[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$|" +
                           "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                           "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        
        return Pattern.compile(hostPattern).matcher(host.trim()).matches();
    }
    
    /**
     * Valida la porta del server MySQL.
     * 
     * <p>La porta deve essere nell'intervallo valido 1-65535.
     * La porta standard di MySQL è 3306.</p>
     * 
     * @param port numero di porta da validare
     * @return true se la porta è valida, false altrimenti
     */
    private boolean isValidPort(Integer port) {
        return port >= 1 && port <= 65535;
    }
    
    /**
     * Valida il nome del database MySQL.
     * 
     * <p>Regole MySQL: deve iniziare con lettera o underscore,
     * può contenere lettere, numeri e underscore, massimo 64 caratteri.</p>
     * 
     * @param dbName nome del database da validare
     * @return true se il nome è valido, false altrimenti
     */
    private boolean isValidDatabaseName(String dbName) {
        // MySQL database name rules: può contenere lettere, numeri, underscore
        // Non può iniziare con un numero, lunghezza massima 64 caratteri
        String dbPattern = "^[a-zA-Z_][a-zA-Z0-9_]{0,63}$";
        return Pattern.compile(dbPattern).matcher(dbName.trim()).matches();
    }
    
    /**
     * Valida l'username per la connessione MySQL.
     * 
     * <p>Regole MySQL: massimo 32 caratteri, può contenere lettere, numeri,
     * underscore, chiocciola, punto e trattino.</p>
     * 
     * @param username nome utente da validare
     * @return true se l'username è valido, false altrimenti
     */
    private boolean isValidUsername(String username) {
        // MySQL username rules: massimo 32 caratteri, non può contenere caratteri speciali
        String usernamePattern = "^[a-zA-Z0-9_@.-]+$";
        return username.trim().length() <= 32 && 
               Pattern.compile(usernamePattern).matcher(username.trim()).matches();
    }
    
    /**
     * Valida la password per la connessione MySQL.
     * 
     * <p>La password può essere vuota ma non null, con lunghezza massima di 128 caratteri.
     * Non vengono applicate altre restrizioni sui caratteri.</p>
     * 
     * @param password password da validare
     * @return true se la password è valida, false altrimenti
     */
    private boolean isValidPassword(String password) {
        // Password può essere vuota ma non null, massimo 128 caratteri
        return password.length() <= 128;
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
