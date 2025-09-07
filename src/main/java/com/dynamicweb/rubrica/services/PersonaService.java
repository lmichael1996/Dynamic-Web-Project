package com.dynamicweb.rubrica.services;

import com.dynamicweb.rubrica.entities.Persona;
import com.dynamicweb.rubrica.repositories.PersonaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servizio per la gestione delle operazioni business relative alle persone.
 * 
 * <p>Questo servizio fornisce un'interfaccia di alto livello per tutte le
 * operazioni CRUD (Create, Read, Update, Delete) sulle entità Persona,
 * fungendo da strato intermedio tra i controller e il repository.</p>
 * 
 * <p>Incapsula la logica business e coordina le chiamate al
 * {@link PersonaRepository} per l'accesso ai dati.</p>
 * 
 * @author Michael Leanza
 * @since 1.0
 * @see Persona
 * @see PersonaRepository
 */
@Service
public class PersonaService {
    
    private final PersonaRepository personaRepository;

    /**
     * Costruttore del servizio persona.
     * 
     * @param personaRepository il repository per l'accesso ai dati delle persone
     */
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Recupera tutte le persone presenti nel database.
     * 
     * @return lista di tutte le persone, vuota se non ce ne sono
     */
    public List<Persona> getAllPersons() {
        return personaRepository.findAll();
    }
    
    /**
     * Recupera una persona specifica tramite il suo ID.
     * 
     * @param id l'identificativo univoco della persona
     * @return la persona trovata, o {@code null} se non esiste
     */
    public Persona getPersonById(Long id) {
        return personaRepository.findById(id);
    }
    
    /**
     * Salva una nuova persona nel database.
     * 
     * @param persona la persona da salvare (senza ID)
     * @return {@code true} se il salvataggio è avvenuto con successo, {@code false} altrimenti
     */
    public boolean savePerson(Persona persona) {
        return personaRepository.insert(persona);
    }
    
    /**
     * Aggiorna una persona esistente nel database.
     * 
     * @param persona la persona con i dati aggiornati (deve contenere l'ID)
     * @return {@code true} se l'aggiornamento è avvenuto con successo, {@code false} altrimenti
     */
    public boolean updatePerson(Persona persona) {
        return personaRepository.update(persona);
    }
    
    /**
     * Elimina una persona dal database tramite il suo ID.
     * 
     * @param id l'identificativo della persona da eliminare
     * @return {@code true} se l'eliminazione è avvenuta con successo, {@code false} altrimenti
     */
    public boolean deletePerson(Long id) {
        return personaRepository.deleteById(id);
    }
}
