
package com.dynamicweb.rubrica.controllers;

import com.dynamicweb.rubrica.dtos.DatabaseProperties;
import com.dynamicweb.rubrica.services.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

/**
 * Controller principale per la gestione della configurazione database dell'applicazione.
 * 
 * <p>Questo controller gestisce la pagina iniziale dell'applicazione dove gli utenti
 * possono configurare la connessione al database MySQL. Fornisce l'interfaccia per
 * inserire i parametri di connessione e testa la connettività prima di procedere
 * con l'autenticazione.</p>
 * 
 * <p>Il flusso dell'applicazione prevede: configurazione database → autenticazione → 
 * gestione persone.</p>
 * 
 * <p><strong>URL dell'applicazione:</strong> http://localhost:8080/</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 * @see DatabaseConnectionManager
 * @see DatabaseProperties
 */
@Controller
public class MainController {

    private final DatabaseConnectionManager databaseConnectionManager;

    /**
     * Costruttore del controller principale.
     * 
     * @param databaseConnectionManager il manager per la gestione delle connessioni al database
     */
    public MainController(DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }
    
    /**
     * Endpoint root dell'applicazione che reindirizza alla pagina di configurazione.
     * 
     * <p>URL: http://localhost:8080/</p>
     * 
     * @return redirect alla pagina index
     */
    @GetMapping({"", "/"})
    public String root() {
        return "redirect:/index";
    }
    
    /**
     * Visualizza la pagina di configurazione del database.
     * 
     * <p>Mostra un form per l'inserimento dei parametri di connessione
     * al database MySQL (host, porta, nome database, username, password).</p>
     * 
     * @return il nome della vista index
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
    /**
     * Elabora la configurazione del database e testa la connessione.
     * 
     * <p>Riceve i parametri di connessione dal form, crea un oggetto {@link DatabaseProperties},
     * valida la configurazione, testa la connessione al database e, se tutto va a buon fine,
     * aggiorna il DataSource dell'applicazione tramite {@link DatabaseConnectionManager}.</p>
     * 
     * <p>In caso di successo reindirizza alla pagina di login, altrimenti torna alla
     * pagina di configurazione con un messaggio di errore.</p>
     * 
     * @param host l'hostname del server MySQL
     * @param port la porta del server MySQL
     * @param dbName il nome del database
     * @param username l'username per la connessione al database
     * @param password la password per la connessione al database
     * @param session la sessione HTTP corrente
     * @param redirectAttributes attributi per i messaggi di redirect
     * @return redirect al login se successo, altrimenti alla configurazione
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
            // Crea la configurazione
            DatabaseProperties config = new DatabaseProperties(host, port, dbName, username, password);

            // Aggiorna il DataSource e JdbcTemplate tramite il DatabaseConnectionManager
            databaseConnectionManager.updateDataSource(config);

            // Dopo la configurazione, vai al login
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Errore di configurazione: " + e.getMessage());
            return "redirect:/index";
        }
    }
}
