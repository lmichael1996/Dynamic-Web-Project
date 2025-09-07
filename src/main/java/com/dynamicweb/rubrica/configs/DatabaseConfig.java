package com.dynamicweb.rubrica.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Configurazione Spring per i bean DataSource e JdbcTemplate.
 * Gestisce la connessione al database MySQL con supporto per configurazione dinamica.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Configuration
public class DatabaseConfig {

    /**
     * Bean DataSource vuoto per l'inizializzazione dell'applicazione.
     * Verrà configurato dinamicamente dal DatabaseConnectionManager.
     * 
     * @return istanza vuota di DriverManagerDataSource
     */
    @Bean
    @Primary
    public DriverManagerDataSource dataSource() {
        return new DriverManagerDataSource();
    }

    /**
     * Bean per JdbcTemplate che usa il DataSource configurato.
     * Fornisce un template per le operazioni SQL con gestione automatica delle connessioni.
     * 
     * @param dataSource il DataSource da utilizzare per le connessioni database
     * @return istanza configurata di JdbcTemplate
     */
    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }   
}
