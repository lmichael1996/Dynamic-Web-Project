package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.services.AuthService;
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
    
    /**
     * Costruttore per l'injection del servizio di autenticazione.
     * 
     * @param authService servizio per le operazioni di autenticazione
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * Mostra la pagina di login.
     * 
     * @return nome della vista JSP per il login
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    /**
     * Processa il form di login e autentica l'utente.
     * 
     * <p>Valida le credenziali tramite {@link AuthService}. Se l'autenticazione
     * ha successo, crea una sessione utente e reindirizza alla lista persone.
     * In caso di errore, torna al login con messaggio di errore.</p>
     * 
     * @param username nome utente inserito nel form
     * @param password password inserita nel form
     * @param session sessione HTTP per memorizzare i dati utente
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect a /lista se successo, altrimenti a /login
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
