package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.entities.Persona;
import com.dynamicweb.rubrica.services.PersonaService;
import com.dynamicweb.rubrica.services.AuthService;
import com.dynamicweb.rubrica.services.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

/**
 * Controller per la gestione delle operazioni CRUD sulle persone.
 * Fornisce endpoint per visualizzazione, creazione, modifica ed eliminazione
 * delle persone con autenticazione obbligatoria per l'accesso alla lista.
 *
 * @author Michael Leanza
 * @since 1.0
 */
@Controller
public class PersonaController {
    
    private final PersonaService personaService;
    private final DatabaseConnectionManager databaseConnectionManager;
    private final AuthService authService;

    /**
     * Costruttore per l'injection dei servizi necessari.
     * 
     * @param personaService servizio per operazioni CRUD sulle persone
     * @param databaseConnectionManager servizio per verificare la configurazione database
     * @param authService servizio per la gestione dell'autenticazione
     */
    public PersonaController(
        PersonaService personaService, 
        DatabaseConnectionManager databaseConnectionManager, 
        AuthService authService) {
        this.personaService = personaService;
        this.databaseConnectionManager = databaseConnectionManager;
        this.authService = authService;
        }
    
    /**
     * Verifica i prerequisiti per l'accesso alle funzionalità del controller.
     * Controlla che il database sia configurato e l'utente sia autenticato.
     * 
     * @param session sessione HTTP per verifica autenticazione
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect string se controlli falliscono, null se tutto ok
     */
    private String checkAccessPrerequisites(HttpSession session, RedirectAttributes redirectAttributes) {
        // Verifica che il database sia configurato
        if (!databaseConnectionManager.isDatabaseConfigured()) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Database non configurato. Configura prima la connessione al database."
            );
            return "redirect:/index";
        }

        // Verifica autenticazione - OBBLIGATORIA per accedere alle funzionalità
        if (!authService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Devi effettuare il login per accedere alla lista persone"
            );
            return "redirect:/login";
        }
        
        return null; // Tutti i controlli superati
    }
    
    /**
     * Mostra la lista delle persone registrate.
     * 
     * <p>Richiede database configurato e autenticazione obbligatoria. 
     * Se i prerequisiti non sono soddisfatti, reindirizza alla configurazione o login.</p>
     * 
     * @param model model per passare dati alla vista
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @param session sessione HTTP per verifica autenticazione
     * @return vista "lista" se autenticato, altrimenti redirect appropriato
     */
    @GetMapping("/lista")
    public String listPersons(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        // Verifica prerequisiti di accesso
        String accessCheck = checkAccessPrerequisites(session, redirectAttributes);
        if (accessCheck != null) {
            return accessCheck;
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
     * Richiede database configurato e autenticazione valida.
     * 
     * @param model model per passare oggetto persona vuoto alla vista
     * @param session sessione HTTP per verifica autenticazione
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return vista "editor" con form vuoto o redirect se prerequisiti non soddisfatti
     */
    @GetMapping("/editor")
    public String newPerson(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Verifica prerequisiti di accesso
        String accessCheck = checkAccessPrerequisites(session, redirectAttributes);
        if (accessCheck != null) {
            return accessCheck;
        }
        // Aggiunge un oggetto Persona vuoto per il form
        model.addAttribute("person", new Persona());
        return "editor";
    }

    /**
     * Mostra il form per modificare una persona esistente.
     * Richiede database configurato e autenticazione valida.
     * 
     * @param id ID della persona da modificare
     * @param model model per passare dati alla vista
     * @param session sessione HTTP per verifica autenticazione
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return vista "editor" con dati persona o redirect appropriato
     */
    @GetMapping("/editor/{id}")
    public String editPerson(
        @PathVariable Long id, 
        Model model, 
        HttpSession session, 
        RedirectAttributes redirectAttributes) {
        // Verifica prerequisiti di accesso
        String accessCheck = checkAccessPrerequisites(session, redirectAttributes);
        if (accessCheck != null) {
            return accessCheck;
        }
        
        try {
            // Recupera la persona e la passa al modello
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
     * Richiede database configurato e autenticazione valida.
     * 
     * <p>Se la persona ha un ID, viene aggiornata; altrimenti viene creata una nuova.
     * In caso di successo reindirizza alla lista, in caso di errore rimane nell'editor.</p>
     * 
     * @param person oggetto persona dal form
     * @param session sessione HTTP per verifica autenticazione
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @param model model per passare dati alla vista in caso di errore
     * @return redirect a /lista se successo, vista "editor" se errore o redirect se prerequisiti non soddisfatti
     */
    @PostMapping("/salva")
    public String savePerson(
        @ModelAttribute Persona person, 
        HttpSession session, 
        RedirectAttributes redirectAttributes, 
        Model model) {
        // Verifica prerequisiti di accesso
        String accessCheck = checkAccessPrerequisites(session, redirectAttributes);
        if (accessCheck != null) {
            return accessCheck;
        }
        
        try {
            if (person.getId() != null) {
                // Aggiorna persona esistente
                if (personaService.updatePerson(person)) {
                    redirectAttributes.addFlashAttribute(
                        "successMessage", 
                        "Persona aggiornata con successo!"
                    );
                    return "redirect:/lista";
                } else {
                    model.addAttribute(
                        "errorMessage", 
                        "Errore durante l'aggiornamento della persona!"
                    );
                    model.addAttribute("person", person);
                    return "editor";
                }
            } else {
                // Inserisci nuova persona
                if (personaService.savePerson(person)) {
                    redirectAttributes.addFlashAttribute(
                        "successMessage", 
                        "Persona salvata con successo!"
                    );
                    return "redirect:/lista";
                } else {
                    model.addAttribute(
                        "errorMessage", 
                        "Errore durante il salvataggio della persona!"
                    );
                    model.addAttribute("person", person);
                    return "editor";
                }
            }
            
        } catch (Exception e) {
            model.addAttribute(
                "errorMessage", 
                "Errore di connessione durante il salvataggio: " + e.getMessage()
            );
            model.addAttribute("person", person);
            return "editor";
        }
    }
    
    /**
     * Elimina una persona dal database.
     * Richiede database configurato e autenticazione valida.
     * 
     * @param id ID della persona da eliminare
     * @param session sessione HTTP per verifica autenticazione
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect a /lista con messaggio di esito o redirect se prerequisiti non soddisfatti
     */
    @GetMapping("/elimina/{id}")
    public String deletePerson(
        @PathVariable Long id, 
        HttpSession session, 
        RedirectAttributes redirectAttributes) {
        // Verifica prerequisiti di accesso
        String accessCheck = checkAccessPrerequisites(session, redirectAttributes);
        if (accessCheck != null) {
            return accessCheck;
        }
        
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
