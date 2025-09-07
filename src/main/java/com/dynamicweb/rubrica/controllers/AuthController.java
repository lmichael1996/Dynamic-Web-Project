package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

/**
 * Controller per la gestione dell'autenticazione utenti.
 * Gestisce login e sessione tramite AuthService.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Controller
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Costruttore del controller di autenticazione.
     * 
     * @param authService il servizio per le operazioni di autenticazione
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * Visualizza la pagina di login.
     * 
     * <p>URL: http://localhost:8080/login</p>
     * <p>Restituisce la vista "login" che contiene il form per l'inserimento
     * delle credenziali di accesso.</p>
     * 
     * @return il nome della vista login
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    /**
     * Elabora il submit del form di login e autentica l'utente.
     * 
     * <p>Verifica le credenziali fornite dall'utente utilizzando {@link AuthService}.
     * In caso di successo, registra l'utente nella sessione e reindirizza alla
     * lista delle persone. In caso di errore, torna alla pagina di login con
     * un messaggio di errore.</p>
     * 
     * @param username il nome utente inserito
     * @param password la password inserita
     * @param session la sessione HTTP corrente
     * @param redirectAttributes attributi per i messaggi di redirect
     * @return redirect alla lista persone se successo, altrimenti al login
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (authService.authenticate(username, password)) {
            authService.login(session, username);
            return "redirect:/lista";
        } else {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Username o password errati"
            );
            return "redirect:/login";
        }
    }
}
