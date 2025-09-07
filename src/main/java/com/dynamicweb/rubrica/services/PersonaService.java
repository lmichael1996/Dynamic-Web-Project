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
     * Salva una nuova persona nel database dopo aver validato i dati.
     * 
     * @param persona la persona da salvare (senza ID)
     * @return {@code true} se il salvataggio è avvenuto con successo, {@code false} altrimenti
     * @throws IllegalArgumentException se i dati della persona non sono validi
     */
    public boolean savePerson(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException("La persona non può essere null");
        }
        
        // Per l'inserimento, l'ID deve essere null
        if (persona.getId() != null) {
            throw new IllegalArgumentException("L'ID deve essere null per una nuova persona");
        }
        
        // Valida tutti i dati
        validatePersona(persona);
        
        return personaRepository.insert(persona);
    }
    
    /**
     * Aggiorna una persona esistente nel database dopo aver validato i dati.
     * 
     * @param persona la persona con i dati aggiornati (deve contenere l'ID)
     * @return {@code true} se l'aggiornamento è avvenuto con successo, {@code false} altrimenti
     * @throws IllegalArgumentException se i dati della persona non sono validi
     */
    public boolean updatePerson(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException("La persona non può essere null");
        }
        
        // Per l'aggiornamento, l'ID è obbligatorio
        if (persona.getId() == null || persona.getId() <= 0) {
            throw new IllegalArgumentException("ID persona obbligatorio per l'aggiornamento: " + persona.getId());
        }
        
        // Valida tutti i dati
        validatePersona(persona);
        
        return personaRepository.update(persona);
    }
    
    /**
     * Elimina una persona dal database tramite il suo ID.
     * 
     * @param id l'identificativo della persona da eliminare
     * @return {@code true} se l'eliminazione è avvenuta con successo, {@code false} altrimenti
     * @throws IllegalArgumentException se l'ID non è valido
     */
    public boolean deletePerson(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID persona non valido: " + id);
        }
        return personaRepository.deleteById(id);
    }
    
    /**
     * Valida tutti i campi della persona.
     * 
     * @param persona la persona da validare
     * @throws IllegalArgumentException se uno o più campi non sono validi
     */
    private void validatePersona(Persona persona) {
        validateNameField(persona.getNome(), "nome");
        validateNameField(persona.getCognome(), "cognome");
        validateTelefono(persona.getTelefono());
    }
    
    /**
     * Valida un campo nome/cognome generico.
     * 
     * @param value il valore da validare
     * @param fieldName il nome del campo per i messaggi di errore
     * @throws IllegalArgumentException se il valore non è valido
     */
    private void validateNameField(String value, String fieldName) {
        if (value.trim().length() > 100) {
            throw new IllegalArgumentException("Il " + fieldName + " non può superare i 100 caratteri");
        }
        if (!value.trim().matches("^[a-zA-ZÀ-ÿ\\s'.-]+$")) {
            throw new IllegalArgumentException("Il " + fieldName + " contiene caratteri non validi");
        }
    }
    
    /**
     * Valida il telefono della persona.
     * 
     * @param telefono il telefono da validare
     * @throws IllegalArgumentException se il telefono non è valido
     */
    private void validateTelefono(String telefono) {
        if (telefono.trim().length() > 20) {
            throw new IllegalArgumentException("Il telefono non può superare i 20 caratteri");
        }
        // Pattern per telefono italiano (con o senza prefisso internazionale)
        String phonePattern = "^(\\+39\\s?)?((3[0-9]{2}|0[0-9]{1,3})\\s?)?[0-9]{6,8}$";
        if (!telefono.trim().replaceAll("\\s+", "").matches(phonePattern)) {
            throw new IllegalArgumentException("Formato telefono non valido");
        }
    }
}
