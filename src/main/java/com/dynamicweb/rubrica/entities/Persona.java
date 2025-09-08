package com.dynamicweb.rubrica.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entità che rappresenta una persona nella rubrica.
 * Questa classe modella i dati di una persona con le informazioni essenziali
 * per la gestione di una rubrica contatti: identificativo, nome, cognome, 
 * indirizzo, telefono ed età.
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
    
    private Integer eta;
}