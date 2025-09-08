# Dynamic Web Project

Una applicazione web Spring Boot per la gestione di una rubrica contatti con configurazione dinamica del database e autenticazione sicura.

## Funzionalità

- Configurazione dinamica del database MySQL
- Autenticazione sicura con variabili di ambiente  
- Gestione CRUD completa della rubrica contatti
- Interfaccia web responsiva con JSP e Bootstrap 5

## Setup e Avvio

1. **Avviare il database MySQL con Docker:**
   ```bash
   docker run --name mysql-rubrica \
     -e MYSQL_ROOT_PASSWORD=root \
     -e MYSQL_DATABASE=rubrica \
     -e MYSQL_USER=rubrica \
     -e MYSQL_PASSWORD=rubrica \
     -p 3306:3306 \
     -d mysql:8.0
   ```

2. **Configurare variabili di ambiente (opzionale):**
   ```bash
   export AUTH_USERNAME=your_admin_username
   export AUTH_PASSWORD=your_secure_password
   ```

3. **Avviare l'applicazione:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Aprire il browser su:** `http://localhost:8080`
   
   **Credenziali di accesso (default se variabili di ambiente non impostate):**
   - Username: `admin`
   - Password: `admin123`

## Packaging WAR

```bash
./mvnw clean package
```

Il file WAR viene generato in `target/dynamic-web-app-0.0.1-SNAPSHOT.war`

### Esecuzione standalone:
```bash
java -jar target/dynamic-web-app-0.0.1-SNAPSHOT.war
```

### Deploy su Tomcat esterno:
```bash
# Copia il WAR nella directory webapps di Tomcat
cp target/dynamic-web-app-0.0.1-SNAPSHOT.war /path/to/tomcat/webapps/rubrica.war

# Avvia Tomcat
/path/to/tomcat/bin/startup.sh

# Accedi su: http://localhost:8080/rubrica
```

## Tecnologie

- Spring Boot 3.5.5
- MySQL 8.0 + Docker
- JSP + Bootstrap 5
- Maven
- Lombok (ottimizzato)
- HikariCP per connection pooling

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