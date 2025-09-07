
package com.dynamicweb.rubrica.services;

import com.dynamicweb.rubrica.dtos.DatabaseProperties;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore con injection del JdbcTemplate.
     */
    public DatabaseConnectionManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Aggiorna la connessione del JdbcTemplate con una nuova configurazione.
     * Valida e testa la configurazione prima di applicarla.
     * @param config configurazione database
     * @throws IllegalArgumentException se la configurazione non è valida
     * @throws RuntimeException se la connessione non è valida
     */
    public void updateDataSource(DatabaseProperties config) {
        try {
            config.validateConfiguration();
            config.testConnection();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Configurazione database non valida: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Connessione al database fallita: " + e.getMessage(), e);
        }
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(config.buildJdbcUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        jdbcTemplate.setDataSource(dataSource);
    }

    /**
     * Restituisce il JdbcTemplate attuale.
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
