package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.dtos.DatabaseProperties;
import com.dynamicweb.rubrica.services.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

/**
 * Controller per la configurazione iniziale del database.
 * 
 * <p>Fornisce l'interfaccia di setup per configurare i parametri
 * di connessione al database MySQL prima dell'accesso all'applicazione.</p>
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
     * Costruttore per l'injection del manager di connessioni database.
     * 
     * @param databaseConnectionManager manager per le connessioni al database
     */
    public MainController(DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }
    
    /**
     * Endpoint root che reindirizza alla pagina di configurazione.
     * 
     * <p>Mappato su: "/" e ""</p>
     * 
     * @return redirect verso /index
     */
    @GetMapping({"", "/"})
    public String root() {
        return "redirect:/index";
    }
    
    /**
     * Mostra la pagina di configurazione del database.
     * 
     * <p>Presenta un form per inserire i parametri di connessione MySQL:
     * host, porta, nome database, username e password.</p>
     * 
     * @return nome della vista JSP per la configurazione
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
    /**
     * Processa la configurazione del database e valida la connessione.
     * 
     * <p>Crea un {@link DatabaseProperties} dai parametri del form, valida la
     * configurazione e testa la connettività. Se tutto è corretto, aggiorna
     * il DataSource tramite {@link DatabaseConnectionManager} e procede al login.</p>
     * 
     * @param host hostname del server MySQL
     * @param port porta del server MySQL  
     * @param dbName nome del database
     * @param username username per la connessione
     * @param password password per la connessione
     * @param session sessione HTTP (non utilizzata attualmente)
     * @param redirectAttributes attributi per messaggi flash tra redirect
     * @return redirect a /login se successo, altrimenti a /index con errore
     */
    @PostMapping("/configure")
    public String configureDatabase(@RequestParam String host,
                                  @RequestParam Integer port,
                                  @RequestParam String dbName,
                                  @RequestParam String username,
                                  @RequestParam String password,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        
        try {
            // Crea configurazione database dai parametri del form
            DatabaseProperties config = new DatabaseProperties(host, port, dbName, username, password);

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
