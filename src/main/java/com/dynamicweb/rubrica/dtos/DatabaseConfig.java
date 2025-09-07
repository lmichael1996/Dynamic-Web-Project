package com.dynamicweb.rubrica.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.regex.Pattern;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Data
@AllArgsConstructor
public class DatabaseConfig {
    
    private String host;
    private int port;
    private String dbName;
    private String username;
    private String password;

    /**
     * Costruisce l'URL JDBC completo per MySQL
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
     * Valida la configurazione del database
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
     * Valida l'host
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
     * Valida la porta
     */
    private boolean isValidPort(Integer port) {
        return port >= 1 && port <= 65535;
    }
    
    /**
     * Valida il nome del database
     */
    private boolean isValidDatabaseName(String dbName) {
        // MySQL database name rules: può contenere lettere, numeri, underscore
        // Non può iniziare con un numero, lunghezza massima 64 caratteri
        String dbPattern = "^[a-zA-Z_][a-zA-Z0-9_]{0,63}$";
        return Pattern.compile(dbPattern).matcher(dbName.trim()).matches();
    }
    
    /**
     * Valida l'username
     */
    private boolean isValidUsername(String username) {
        // MySQL username rules: massimo 32 caratteri, non può contenere caratteri speciali
        return username.trim().length() <= 32 && 
               Pattern.compile("^[a-zA-Z0-9_@.-]+$").matcher(username.trim()).matches();
    }
    
    /**
     * Valida la password
     */
    private boolean isValidPassword(String password) {
        // Password può essere vuota ma non null, massimo 128 caratteri
        return password.length() <= 128;
    }
    
    /**
     * Testa la connessione con una configurazione specifica senza applicarla
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
