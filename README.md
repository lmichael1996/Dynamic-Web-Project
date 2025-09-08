# Dynamic Web Project

Applicazione web per la gestione di una rubrica contatti sviluppata con Spring Boot. Include configurazione database dinamica, autenticazione sicura e interfaccia Bootstrap 5 per operazioni CRUD complete.

## Caratteristiche

- **CRUD completo:** Gestione completa della rubrica contatti
- **Autenticazione:** Variabili di ambiente (AUTH_USERNAME, AUTH_PASSWORD)
- **Database:** MySQL con configurazione dinamica runtime
- **Validazione:** Unificata server-side con controlli null, lunghezza e regex
- **UI:** Bootstrap 5 responsiva con feedback utente
- **Deploy:** WAR per Tomcat esterno o standalone

## Pagine dell'applicazione

- **Index (/):** Configurazione database e test connessione
- **Login (/login):** Autenticazione sicura con credenziali
- **Lista (/lista):** Visualizzazione e gestione contatti esistenti
- **Editor (/editor):** Inserimento e modifica contatti
- **Operazioni:** Salvataggio, aggiornamento ed eliminazione contatti dalla rubrica

## Configurazione

- Database MySQL richiesto (configurabile via Docker)
- Porta default: 8080
- Credenziali default: admin/admin123 (se variabili non impostate)
- Campi opzionali: indirizzo e età

## Autore

**Michael Leanza**