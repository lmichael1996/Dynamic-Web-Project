package com.michaelleanza.rubrica.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.michaelleanza.rubrica.entities.Persona;

import java.util.List;

@Repository
public class PersonaRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public PersonaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // Stampa tutte le persone
    public List<Persona> findAll() {
        String sql = "SELECT * FROM lista_contatti";
        List<Persona> listPersona = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Persona.class));
        return listPersona != null ? listPersona : List.of(); // Restituisce lista vuota se null
    }

    public Persona findById(Long id) {
        String sql = "SELECT * FROM lista_contatti WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Persona.class), id);
    }
    
    // Inserisci persona
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
    
    // Aggiorna persona
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
    
    // Elimina persona per ID
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM lista_contatti WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
