package com.dynamicweb.rubrica.components;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Configurazione per le proprietà di autenticazione dell'applicazione.
 * Legge username e password dalle variabili di sistema per maggiore sicurezza.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Getter
@Component
public class AuthProperties {

    private final String username;
    private final String password;

    /**
     * Costruttore che inizializza le credenziali dalle variabili di sistema.
     * Se le variabili non sono impostate, usa valori di default.
     */
    public AuthProperties() {
        this.username = System.getenv().getOrDefault("AUTH_USERNAME", "admin");
        this.password = System.getenv().getOrDefault("AUTH_PASSWORD", "admin123");
    }

    /**
     * Verifica se le credenziali corrispondono a quelle configurate.
     * 
     * @param username nome utente da verificare
     * @param password password da verificare
     * @return true se le credenziali sono corrette, false altrimenti
     */
    public boolean checkCredentials(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
