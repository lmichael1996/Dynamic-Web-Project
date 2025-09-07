package com.dynamicweb.rubrica.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Classe di configurazione per le proprietà di autenticazione dell'applicazione.
 * Mappa automaticamente le proprietà con prefisso "app.auth" dal file application.properties.
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

    /**
     * Verifica se le credenziali corrispondono a quelle configurate.
     * @param username nome utente da verificare
     * @param password password da verificare
     * @return true se le credenziali sono corrette, false altrimenti
     */
    public boolean checkCredentials(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
