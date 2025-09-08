# Dynamic Web Project

Applicazione web per la gestione di una rubrica contatti sviluppata con Spring Boot. Offre configurazione flessibile del database, autenticazione sicura e interfaccia responsiva.

## Funzionalità

- Configurazione dinamica del database MySQL
- Autenticazione sicura con variabili di ambiente  
- Gestione CRUD completa della rubrica contatti
- Interfaccia web responsiva con JSP e Bootstrap 5

## Caratteristiche

- **Autenticazione:** Variabili di ambiente (AUTH_USERNAME, AUTH_PASSWORD)
- **Database:** Configurazione dinamica runtime con test connessione
- **Validazione:** Robusta validazione server-side con regex
- **UI:** Interfaccia responsiva Bootstrap 5 con feedback utente
- **Packaging:** WAR deployabile su Tomcat esterno o standalone

## Note

- Database MySQL richiesto (configurabile via Docker)
- Porta default: 8080
- Credenziali default: admin/admin123 (se variabili di ambiente non impostate)
- Campo indirizzo opzionale nella gestione contatti

## Autore

**Michael Leanza**