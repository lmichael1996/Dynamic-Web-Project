package com.dynamicweb.rubrica.repositories;

import com.dynamicweb.rubrica.entities.Persona;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'accesso ai dati delle persone nel database.
 * Fornisce operazioni CRUD (Create, Read, Update, Delete) per la gestione
 * delle persone nella tabella 'lista_contatti' utilizzando JdbcTemplate.
 * 
 * Tutti i metodi gestiscono automaticamente le connessioni al database
 * e la mappatura dei risultati verso l'entità Persona.
 * 
 * @author Michael Leanza
 * @since 1.0
 */
@Repository
public class PersonaRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    /**
     * Costruttore per l'injection del JdbcTemplate.
     * 
     * @param jdbcTemplate template per operazioni JDBC
     */
    public PersonaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * Recupera tutte le persone dal database.
     * 
     * <p>Esegue una query per ottenere tutti i record dalla tabella 'lista_contatti'
     * e li mappa automaticamente in oggetti Persona.</p>
     * 
     * @return lista di tutte le persone, lista vuota se nessun risultato
     */
    public List<Persona> findAll() {
        String sql = "SELECT * FROM lista_contatti";
        List<Persona> listPersona = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Persona.class));
        return listPersona != null ? listPersona : List.of(); // Restituisce lista vuota se null
    }

    /**
     * Cerca una persona specifica per ID.
     * 
     * @param id identificativo univoco della persona
     * @return oggetto Persona corrispondente all'ID
     * @throws org.springframework.dao.EmptyResultDataAccessException se non trovata
     */
    public Persona findById(Long id) {
        String sql = "SELECT * FROM lista_contatti WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Persona.class), id);
    }
    
    /**
     * Inserisce una nuova persona nel database.
     * 
     * <p>Aggiunge un nuovo record nella tabella 'lista_contatti' con tutti
     * i dati della persona fornita. L'ID viene generato automaticamente.</p>
     * 
     * @param persona oggetto Persona da inserire
     * @return true se l'inserimento è riuscito, false altrimenti
     */
    public boolean insert(Persona persona) {
        String sql = """
            INSERT INTO lista_contatti (nome, cognome, indirizzo, telefono, eta) 
            VALUES (?, ?, ?, ?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(sql,
            persona.getNome(),
            persona.getCognome(),
            persona.getIndirizzo(),
            persona.getTelefono(),
            persona.getEta());
            
        return rowsAffected > 0;
    }
    
    /**
     * Aggiorna i dati di una persona esistente.
     * 
     * <p>Modifica tutti i campi della persona identificata dall'ID.
     * Tutti i campi vengono aggiornati con i nuovi valori forniti.</p>
     * 
     * @param persona oggetto Persona con i nuovi dati e ID esistente
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    public boolean update(Persona persona) {
        String sql = """
            UPDATE lista_contatti 
            SET nome = ?, cognome = ?, indirizzo = ?, telefono = ?, eta = ? 
            WHERE id = ?
            """;
        
        int rowsAffected = jdbcTemplate.update(sql,
            persona.getNome(),
            persona.getCognome(), 
            persona.getIndirizzo(),
            persona.getTelefono(),
            persona.getEta(),
            persona.getId());
            
        return rowsAffected > 0;
    }
    
    /**
     * Elimina una persona dal database tramite ID.
     * 
     * <p>Rimuove definitivamente il record della persona dalla tabella
     * 'lista_contatti' usando l'identificativo fornito.</p>
     * 
     * @param id identificativo della persona da eliminare
     * @return true se l'eliminazione è riuscita, false altrimenti
     */
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM lista_contatti WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
