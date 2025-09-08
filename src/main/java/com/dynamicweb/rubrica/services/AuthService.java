package com.dynamicweb.rubrica.services;

import com.dynamicweb.rubrica.components.AuthProperties;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * Servizio per la gestione dell'autenticazione dell'applicazione.
 * 
 * <p>Questo servizio fornisce metodi per autenticare gli utenti utilizzando
 * le credenziali configurate tramite {@link AuthProperties} e per gestire
 * lo stato di login attraverso le sessioni HTTP.</p>
 * 
 * <p>L'autenticazione è basata su credenziali statiche configurate nel file
 * application.properties e utilizza la sessione HTTP per mantenere lo stato
 * di login dell'utente.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Service
public class AuthService {
    
    private final AuthProperties authProperties;
    
    /**
     * Costruttore del servizio di autenticazione.
     * 
     * @param authProperties le proprietà di configurazione per l'autenticazione
     */
    public AuthService(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }
    
    /**
     * Autentica un utente verificando username e password.
     * 
     * <p>Confronta le credenziali fornite con quelle configurate nelle
     * proprietà dell'applicazione.</p>
     * 
     * @param username il nome utente da verificare
     * @param password la password da verificare
     * @return {@code true} se le credenziali sono corrette, {@code false} altrimenti
     */
    public boolean authenticate(String username, String password) {
        return authProperties.checkCredentials(username, password);
    }
    
    /**
     * Verifica se l'utente è attualmente autenticato.
     * 
     * <p>Controlla la presenza dell'attributo "loggedUser" nella sessione HTTP
     * per determinare lo stato di autenticazione.</p>
     * 
     * @param session la sessione HTTP corrente
     * @return {@code true} se l'utente è autenticato, {@code false} altrimenti
     */
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedUser") != null;
    }
    
    /**
     * Registra l'utente come autenticato nella sessione.
     * 
     * <p>Salva l'username nella sessione HTTP per mantenere lo stato
     * di autenticazione durante la navigazione.</p>
     * 
     * @param session la sessione HTTP corrente
     * @param username il nome utente da registrare come autenticato
     */
    public void login(HttpSession session, String username) {
        session.setAttribute("loggedUser", username);
    }
}