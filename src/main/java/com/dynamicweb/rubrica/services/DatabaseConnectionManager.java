
package com.dynamicweb.rubrica.services;

import com.dynamicweb.rubrica.dtos.DatabaseProperties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

/**
 * Manager per la gestione dinamica delle connessioni al database.
 * Permette di aggiornare la connessione al database a runtime.
 * Si consiglia di usare questa funzionalità solo per casi particolari,
 * poiché la configurazione del DataSource dovrebbe essere gestita da Spring.
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
            setDataSource(newProperties);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Configurazione database non valida: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Connessione al database fallita: " + e.getMessage(), e);
        }
    }

    /**
     * Configura e imposta il DataSource nel JdbcTemplate.
     * 
     * @param properties configurazione database da applicare
     */
    private void setDataSource(DatabaseProperties properties) {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(properties.buildJdbcUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
    }

    /**
     * Verifica se il DataSource è configurato con valori validi e non di default.
     * Controlla che non siano null, vuoti o i valori placeholder iniziali.
     * 
     * @return {@code true} se il database è configurato con valori validi, {@code false} altrimenti
     */
    public boolean isDatabaseConfigured() {
        // Verifica che i valori non siano null, vuoti o i placeholder di default
        return !(dataSource.getUrl() == null &&
               dataSource.getUsername() == null &&
               dataSource.getPassword() == null);   
    }
}
