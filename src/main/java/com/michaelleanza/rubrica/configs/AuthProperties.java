package com.michaelleanza.rubrica.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Proprietà di configurazione per l'autenticazione dell'applicazione.
 * 
 * <p>Questa classe mappa automaticamente le proprietà con prefisso "app.auth" 
 * dal file application.properties utilizzando Spring Boot Configuration Properties.</p>
 * 
 * <p>Esempio di configurazione:</p>
 * <pre>
 * app.auth.username=admin
 * app.auth.password=secure123
 * </pre>
 * 
 * @author Michael Leanza
 * @since 1.0
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {
    /**
     * Nome utente per l'accesso all'applicazione.
     * Mappato dalla proprietà {@code app.auth.username}.
     */
    private String username;
    
    /**
     * Password per l'accesso all'applicazione.
     * Mappato dalla proprietà {@code app.auth.password}.
     */
    private String password;
}
