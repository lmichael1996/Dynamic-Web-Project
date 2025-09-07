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
 * Controller per la gestione delle persone (CRUD operations).
 * 
 * <p>Questo controller gestisce tutte le operazioni relative alle persone:
 * visualizzazione lista, creazione, modifica ed eliminazione.</p>
 * 
 * <p>L'accesso alla lista persone richiede autenticazione obbligatoria.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Controller
public class PersonaController {
    
    private final PersonaService personaService;
    private final AuthService authService;

    public PersonaController(PersonaService personaService, AuthService authService) {
        this.personaService = personaService;
        this.authService = authService;
    }
    
    /**
     * Visualizza la lista delle persone.
     * 
     * <p>URL: http://localhost:8080/lista</p>
     * <p>Richiede autenticazione obbligatoria.</p>
     * 
     * @param model il model per passare dati alla vista
     * @param redirectAttributes attributi per i messaggi di redirect
     * @param session la sessione HTTP per verificare l'autenticazione
     * @return la vista "lista" o redirect al login se non autenticato
     */
    @GetMapping("/lista")
    public String listPersons(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        // Verifica autenticazione - OBBLIGATORIA per accedere alla lista
        if (!authService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Devi effettuare il login per accedere alla lista persone");
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
    
    // Show form for new person
    @GetMapping("/editor")
    public String newPerson(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("person", new Persona());
        return "editor";
    }

    // Show form to edit person
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

    // Save person (insert or update)
    @PostMapping("/salva")
    public String savePerson(@ModelAttribute Persona person, RedirectAttributes redirectAttributes) {
        try {
            boolean success;
            String operation;
            
            if (person.getId() != null) {
                // Update existing person
                success = personaService.updatePerson(person);
                operation = "aggiornata";
            } else {
                // Insert new person
                success = personaService.savePerson(person);
                operation = "salvata";
            }
            
            String message = success ? 
                "Persona " + operation + " con successo!" :
                "Errore durante l'operazione sulla persona!";
            
            String messageType = success ? "successMessage" : "errorMessage";
            redirectAttributes.addFlashAttribute(messageType, message);
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Errore di connessione durante il salvataggio: " + e.getMessage()
            );
        }
        return "redirect:/lista";
    }
    
    // Delete person
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
