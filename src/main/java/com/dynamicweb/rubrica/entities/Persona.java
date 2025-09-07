package com.dynamicweb.rubrica.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Entità che rappresenta una persona nella rubrica.
 * 
 * <p>Questa classe modella i dati di una persona con tutte le informazioni
 * necessarie per la gestione di una rubrica contatti. Utilizza le annotazioni
 * Lombok per generare automaticamente i metodi boilerplate.</p>
 * 
 * <p>Supporta il pattern Builder per una creazione fluente degli oggetti
 * e fornisce costruttori sia vuoti che con tutti i parametri.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    
    private Long id;
    
    private String nome;
    
    private String cognome;
    
    private String indirizzo;
    
    private String telefono;
    
    private int eta;
}