package com.michaelleanza.rubrica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale dell'applicazione Spring Boot Dynamic Web Project.
 * 
 * <p>Dynamic Web Project è un'applicazione web per la gestione di una rubrica che fornisce:</p>
 * <ul>
 *   <li>Configurazione dinamica della connessione al database MySQL</li>
 *   <li>Autenticazione semplice basata su sessione</li>
 *   <li>Gestione CRUD di una rubrica contatti</li>
 *   <li>Interfaccia web con pagine JSP e Bootstrap</li>
 * </ul>
 * 
 * <p>L'applicazione segue il pattern MVC con Spring Boot e utilizza
 * JdbcTemplate per l'accesso ai dati.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@SpringBootApplication
public class DynamicWebProjectApplication {

	/**
	 * Metodo principale che avvia l'applicazione Spring Boot.
	 * 
	 * @param args argomenti da linea di comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(DynamicWebProjectApplication.class, args);
	}

}
