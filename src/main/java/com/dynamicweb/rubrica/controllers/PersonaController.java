package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.entities.Persona;
import com.dynamicweb.rubrica.services.PersonaService;
import com.dynamicweb.rubrica.services.AuthService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

/**
 * Controller per la gestione delle operazioni CRUD sulle persone.
 * 
 * <p>Fornisce endpoint per visualizzazione, creazione, modifica ed eliminazione
 * delle persone con autenticazione obbligatoria per l'accesso alla lista.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Controller
public class PersonaController {
    
    private final PersonaService personaService;
    private final AuthService authService;

    /**
     * Costruttore per l'injection dei servizi necessari.
     * 
     * @param personaService servizio per operazioni CRUD sulle persone
     * @param authService servizio per la gestione dell'autenticazione
     */
    public PersonaController(PersonaService personaService, AuthService authService) {
        this.personaService = personaService;
        this.authService = authService;
    }
    
    /**
     * Mostra la lista delle persone registrate.
     * 
     * <p>Richiede autenticazione obbligatoria. Se l'utente non è autenticato,
     * viene reindirizzato alla pagina di login.</p>
     * 
     * @param model model per passare dati alla vista
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @param session sessione HTTP per verifica autenticazione
     * @return vista "lista" se autenticato, altrimenti redirect a /login
     */
    @GetMapping("/lista")
    public String listPersons(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        // Verifica autenticazione - OBBLIGATORIA per accedere alla lista
        if (!authService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Devi effettuare il login per accedere alla lista persone"
            );
            return "redirect:/login";
        }
        
        try {
            model.addAttribute("listPerson", personaService.getAllPersons());
            return "lista";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Errore di connessione al database: " + e.getMessage()
            );
            return "redirect:/index";
        }
    }
    
    /**
     * Mostra il form per creare una nuova persona.
     * 
     * @param model model per passare oggetto persona vuoto alla vista
     * @param redirectAttributes attributi per messaggi flash (non utilizzato)
     * @return vista "editor" con form vuoto
     */
    @GetMapping("/editor")
    public String newPerson(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("person", new Persona());
        return "editor";
    }

    /**
     * Mostra il form per modificare una persona esistente.
     * 
     * @param id ID della persona da modificare
     * @param model model per passare dati alla vista
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return vista "editor" con dati persona, altrimenti redirect a /lista
     */
    @GetMapping("/editor/{id}")
    public String editPerson(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("person", personaService.getPersonById(id));
            return "editor";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Errore nel recupero della persona: " + e.getMessage()
            );
            return "redirect:/lista";
        }
    }

    /**
     * Salva o aggiorna una persona (insert/update).
     * 
     * <p>Se la persona ha un ID, viene aggiornata; altrimenti viene creata una nuova.</p>
     * 
     * @param person oggetto persona dal form
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect a /lista con messaggio di successo o errore
     */
    @PostMapping("/salva")
    public String savePerson(@ModelAttribute Persona person, RedirectAttributes redirectAttributes) {
        try {
            if (person.getId() != null) {
                // Update existing person
                if (personaService.updatePerson(person)) {
                    redirectAttributes.addFlashAttribute(
                        "successMessage", 
                        "Persona aggiornata con successo!"
                    );
                } else {
                    redirectAttributes.addFlashAttribute(
                        "errorMessage", 
                        "Errore durante l'aggiornamento della persona!"
                    );
                }
            } else {
                // Insert new person
                if (personaService.savePerson(person)) {
                    redirectAttributes.addFlashAttribute(
                        "successMessage", 
                        "Persona salvata con successo!"
                    );
                } else {
                    redirectAttributes.addFlashAttribute(
                        "errorMessage", 
                        "Errore durante il salvataggio della persona!"
                    );
                }
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Errore di connessione durante il salvataggio: " + e.getMessage()
            );
        }
        return "redirect:/lista";
    }
    
    /**
     * Elimina una persona dal database.
     * 
     * @param id ID della persona da eliminare
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect a /lista con messaggio di successo o errore
     */
    @GetMapping("/elimina/{id}")
    public String deletePerson(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (personaService.deletePerson(id)) {
                redirectAttributes.addFlashAttribute(
                    "successMessage", 
                    "Persona eliminata con successo!"
                );
            } else {
                redirectAttributes.addFlashAttribute(
                    "errorMessage", 
                    "Errore durante l'eliminazione della persona!"
                );
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Errore di connessione durante l'eliminazione: " + e.getMessage()
            );
        }
        return "redirect:/lista";
    }
}
