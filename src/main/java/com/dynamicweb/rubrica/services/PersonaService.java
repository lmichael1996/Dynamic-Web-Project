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
     * Valida un campo controllando sia lunghezza che formato.
     * 
     * @param value il valore da validare
     * @param fieldName il nome del campo per i messaggi di errore
     * @param maxLength la lunghezza massima consentita
     * @param pattern il pattern regex da utilizzare
     * @throws IllegalArgumentException se il valore non è valido
     */
    private void validateField(String value, String fieldName, int maxLength, String pattern) {
        // Controllo lunghezza
        if (value.length() > maxLength) {
            throw new IllegalArgumentException("Il " + fieldName + " non può superare i " + maxLength + " caratteri");
        }
        
        // Controllo pattern
        if (!value.matches(pattern)) {
            throw new IllegalArgumentException("Il " + fieldName + " contiene caratteri non validi o formato errato");
        }
    }

    /**
     * Valida tutti i campi della persona.
     * 
     * @param persona la persona da validare
     * @throws IllegalArgumentException se uno o più campi non sono validi
     */
    private void validatePersona(Persona persona) {
        // Validazioni complete per ogni campo
        validateField(
            persona.getNome(), 
            "nome", 
            100, 
            "^[a-zA-ZÀ-ÿ\\s'.-]+$");
        validateField(
            persona.getCognome(), 
            "cognome", 
            100, 
            "^[a-zA-ZÀ-ÿ\\s'.-]+$");
        validateField(
            persona.getTelefono(), 
            "telefono", 
            20, 
            "^(\\+39\\s?)?((3[0-9]{2}|0[0-9]{1,3})\\s?)?[0-9]{6,8}$");

        if (persona.getIndirizzo() != null) {
            validateField(
                persona.getIndirizzo(), 
                "indirizzo", 
                255, 
                "^[a-zA-ZÀ-ÿ0-9\\s,.'-]+$");
        }
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
}
