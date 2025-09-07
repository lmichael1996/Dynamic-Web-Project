package com.dynamicweb.rubrica.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Configurazione delle proprietà di connessione al database MySQL.
 * Definisce i bean DataSource e JdbcTemplate per l'applicazione.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Configuration
public class DatabaseConfig {
    /**
     * Bean principale DataSource per la connessione MySQL.
     */
    @Bean
    @Primary
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // Configurazione di default, verrà aggiornata dal DataSourceService
        dataSource.setUrl("jdbc:mysql://localhost:3306/rubrica");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    /**
     * Bean principale JdbcTemplate per operazioni SQL.
     */
    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
