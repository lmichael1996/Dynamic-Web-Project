# Dynamic Web Project

Una applicazione web Spring Boot per la gestione di una rubrica contatti con configurazione dinamica del database e autenticazione sicura.

## Funzionalità

- Configurazione dinamica del database MySQL
- Autenticazione sicura con variabili di ambiente  
- Gestione CRUD completa della rubrica contatti
- Interfaccia web responsiva con JSP e Bootstrap 5

## Setup e Avvio

1. **Avviare il database MySQL:**
   ```bash
   docker run --name mysql-rubrica \
     -e MYSQL_ROOT_PASSWORD=root \
     -e MYSQL_DATABASE=rubrica \
     -e MYSQL_USER=rubrica \
     -e MYSQL_PASSWORD=rubrica \
     -p 3306:3306 \
     -d mysql:8.0
   ```

2. **Avviare l'applicazione:**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Aprire il browser su:** `http://localhost:8080`
   - Username: `admin`
   - Password: `admin123`

## Packaging WAR

```bash
./mvnw clean package
java -jar target/Dynamic-Web-Project-0.0.1-SNAPSHOT.war
```

## Tecnologie

- Spring Boot 3.5.5
- MySQL 8.0 + Docker
- JSP + Bootstrap 5
- Maven

## Autore

**Michael Leanza**