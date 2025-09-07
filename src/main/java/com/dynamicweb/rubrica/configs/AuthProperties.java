package com.dynamicweb.rubrica.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configurazione delle proprietà di autenticazione dell'applicazione.
 * Utilizzato per mappare le proprietà con prefisso "app.auth" da application.properties.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {

    private String username;
    
    private String password;
    
}
