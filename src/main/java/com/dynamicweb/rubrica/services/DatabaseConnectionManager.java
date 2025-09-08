package com.dynamicweb.rubrica.services;

import com.dynamicweb.rubrica.dtos.DatabaseProperties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

/**
 * Manager per la gestione dinamica delle connessioni al database.
 * Permette di aggiornare la connessione al database a runtime.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Service
public class DatabaseConnectionManager {

    private final DriverManagerDataSource dataSource;

    /**
     * Costruttore con injection del DataSource.
     */
    public DatabaseConnectionManager(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Aggiorna la connessione del JdbcTemplate con una nuova configurazione.
     * Valida e testa la configurazione prima di applicarla.
     * 
     * @param newProperties nuova configurazione database da applicare
     * @throws IllegalArgumentException se la configurazione non è valida
     * @throws RuntimeException se la connessione non è valida
     */
    public void updateDataSource(DatabaseProperties newProperties) {
        try {
            newProperties.validateConfiguration();
            newProperties.testConnection();
            // Applica la nuova configurazione al DataSource
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(newProperties.buildJdbcUrl());
            dataSource.setUsername(newProperties.getUsername());
            dataSource.setPassword(newProperties.getPassword());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Configurazione database non valida: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Connessione al database fallita: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se il DataSource è configurato con valori validi e non di default.
     * Controlla che non siano null, vuoti o i valori placeholder iniziali.
     * 
     * @return {@code true} se il database è configurato con valori validi, {@code false} altrimenti
     */
    public boolean isDatabaseConfigured() {
        String url = dataSource.getUrl();
        String username = dataSource.getUsername();
        String password = dataSource.getPassword();
        
        // Verifica che i valori essenziali siano presenti e non vuoti
        return url != null && !url.isEmpty() &&
               username != null && !username.isEmpty() &&
               password != null && !password.isEmpty();
    }
}