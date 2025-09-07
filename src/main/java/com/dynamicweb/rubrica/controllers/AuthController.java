package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.services.AuthService;
import com.dynamicweb.rubrica.services.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

/**
 * Controller per la gestione dell'autenticazione degli utenti.
 * Fornisce endpoint per login e gestione delle sessioni utente.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Controller
public class AuthController {
    
    private final AuthService authService;
    private final DatabaseConnectionManager databaseConnectionManager;
    
    /**
     * Costruttore per l'injection dei servizi di autenticazione e database.
     * 
     * @param authService servizio per le operazioni di autenticazione
     * @param databaseConnectionManager manager per verificare la configurazione database
     */
    public AuthController(AuthService authService, DatabaseConnectionManager databaseConnectionManager) {
        this.authService = authService;
        this.databaseConnectionManager = databaseConnectionManager;
    }
    
    /**
     * Mostra la pagina di login solo se il database è configurato.
     * 
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return nome della vista JSP per il login o redirect alla configurazione
     */
    @GetMapping("/login")
    public String loginPage(RedirectAttributes redirectAttributes) {
        // Verifica che il database sia configurato
        if (!databaseConnectionManager.isDatabaseConfigured()) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Database non configurato. Configura prima la connessione al database."
            );
            return "redirect:/index";
        }
        
        return "login";
    }
    
    /**
     * Processa il login dell'utente solo se il database è configurato.
     * 
     * @param username nome utente inserito
     * @param password password inserita
     * @param session sessione HTTP per mantenere lo stato di login
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect alla lista persone se autenticato, altrimenti al login o configurazione
     */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username, 
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        // Verifica che il database sia configurato prima del login
        if (!databaseConnectionManager.isDatabaseConfigured()) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Database non configurato. Configura prima la connessione al database."
            );
            return "redirect:/index";
        }
        
        // Procede con l'autenticazione
        if (authService.authenticate(username, password)) {
            // Imposta l'utente come autenticato nella sessione
            authService.login(session, username);
            return "redirect:/lista";
        } else {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Credenziali non valide"
            );
            return "redirect:/login";
        }
    }
}
