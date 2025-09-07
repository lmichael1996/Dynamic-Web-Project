
package com.dynamicweb.rubrica.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.dynamicweb.rubrica.dtos.DatabaseProperties;

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
     * Bean DataSource di default per l'inizializzazione dell'applicazione.
     * Usa valori placeholder che verranno aggiornati dinamicamente.
     */
    @Bean
    public DriverManagerDataSource dataSource() {
        return new DriverManagerDataSource();
    }

    /**
     * Bean per JdbcTemplate che usa il DataSource di default.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }   
}
