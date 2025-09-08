# Dynamic Web Project

Applicazione web per la gestione di una rubrica contatti sviluppata con Spring Boot. Offre configurazione flessibile del database, autenticazione sicura e interfaccia responsiva.

## Caratteristiche

- **CRUD completo:** Gestione completa della rubrica contatti
- **Autenticazione:** Variabili di ambiente (AUTH_USERNAME, AUTH_PASSWORD)
- **Database:** MySQL con configurazione dinamica runtime
- **Validazione:** Server-side robusta con regex
- **UI:** Bootstrap 5 responsiva con feedback utente
- **Deploy:** WAR per Tomcat esterno o standalone

## Configurazione

- Database MySQL richiesto (configurabile via Docker)
- Porta default: 8080
- Credenziali default: admin/admin123 (se variabili non impostate)
- Campi opzionali: indirizzo e età

## Autore

**Michael Leanza**