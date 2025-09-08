package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.dtos.DatabaseProperties;
import com.dynamicweb.rubrica.services.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

/**
 * Controller per la configurazione iniziale del database.
 * Fornisce l'interfaccia di setup per configurare i parametri di connessione MySQL.
 *
 * <p>Flusso: configurazione database → autenticazione → gestione persone</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Controller
public class MainController {

    private final DatabaseConnectionManager databaseConnectionManager;

    /**
     * Costruttore per l'injection del servizio di connessione database.
     * 
     * @param databaseConnectionManager servizio per la gestione delle connessioni database
     */
    public MainController(DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }
    
    /**
     * Mostra la pagina di configurazione del database.
     * 
     * <p>Presenta un form per inserire i parametri di connessione MySQL:
     * host, porta, nome database, username e password. Aggiunge un oggetto
     * DatabaseProperties vuoto al modello per il binding del form.</p>
     * 
     * <p>Mappato su: "/", "" e "/index"</p>
     * 
     * @param model modello Spring per passare dati alla vista
     * @return nome della vista JSP per la configurazione
     */
    @GetMapping({"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("config", new DatabaseProperties());
        return "index";
    }
    
    /**
     * Processa la configurazione del database e valida la connessione.
     * 
     * <p>Riceve un {@link DatabaseProperties} direttamente dal form, valida la
     * configurazione e testa la connettività. Se tutto è corretto, aggiorna
     * il DataSource e procede al login.</p>
     * 
     * @param config oggetto DatabaseProperties popolato automaticamente dal form
     * @param session sessione HTTP (non utilizzata attualmente)
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect a /login se successo, altrimenti a /index con errore
     */
    @PostMapping("/configure")
    public String configureDatabase(
        @ModelAttribute DatabaseProperties config,
        HttpSession session,
        RedirectAttributes redirectAttributes) {
        try {
            // Valida e applica la nuova configurazione al DataSource
            databaseConnectionManager.updateDataSource(config);

            // Reindirizza al login dopo configurazione completata
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Errore di configurazione: " + e.getMessage()
            );
            return "redirect:/index";
        }
    }
}
