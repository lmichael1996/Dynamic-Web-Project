package com.dynamicweb.rubrica.services;

import com.dynamicweb.rubrica.dtos.DatabaseConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

    private final DriverManagerDataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public DataSourceService(DriverManagerDataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateDataSource(DatabaseConfig config) {
        // Valida la configurazione  
        config.validateConfiguration();

        // Testa la connessione
        config.testConnection();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(config.buildJdbcUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());

        // Aggiorna anche il JdbcTemplate con il nuovo DataSource
        jdbcTemplate.setDataSource(dataSource);
    }
    
    /**
     * Verifica se il DataSource è configurato
     */
    public boolean isConfigured() {
        try {
            String url = dataSource.getUrl();
            String username = dataSource.getUsername();
            return url != null && !url.isEmpty() && 
                   username != null && !username.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    /**
     * Restituisce le informazioni attuali della configurazione (senza password)
     */
    public String getCurrentConfigInfo() {
        try {
            String url = dataSource.getUrl();
            String username = dataSource.getUsername();
            return String.format("URL: %s, Username: %s", 
                                url != null ? url : "Non configurato",
                                username != null ? username : "Non configurato");
        } catch (Exception e) {
            return "Configurazione non disponibile: " + e.getMessage();
        }
    }
}
