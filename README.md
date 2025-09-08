# Dynamic Web Project

Una applicazione web Spring Boot per la gestione di una rubrica contatti con configurazione dinamica del database e autenticazione sicura basata su variabili di ambiente.

## Funzionalità

- **Configurazione dinamica del database MySQL**
- **Autenticazione sicura con variabili di sistema**
- **Gestione CRUD completa della rubrica contatti**
- **Interfaccia web responsiva con JSP e Bootstrap 5**
- **Validazione avanzata dei dati di input**
- **Gestione sessioni utente**

## Configurazione Sicurezza

L'applicazione utilizza variabili di sistema per le credenziali di accesso:

```bash
# Impostare le variabili di ambiente (opzionale)
export AUTH_USERNAME=your_admin_username
export AUTH_PASSWORD=your_secure_password

# Se non impostate, usa i valori di default:
# Username: admin
# Password: admin123
```

## Setup Database con Docker

Per avviare il database MySQL utilizzando Docker:

```bash
sudo docker run --name mysql-rubrica \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=rubrica \
  -e MYSQL_USER=rubrica \
  -e MYSQL_PASSWORD=rubrica \
  -p 3306:3306 \
  -d mysql:8.0
```

## Struttura del Progetto

```
Dynamic-Web-Project/
├── src/
│   ├── main/
│   │   ├── java/com/dynamicweb/rubrica/
│   │   │   ├── DynamicWebProjectApplication.java  # Classe principale
│   │   │   ├── components/       # Componenti di configurazione
│   │   │   ├── controllers/      # Controller REST e Web
│   │   │   ├── dtos/            # Data Transfer Objects
│   │   │   ├── entities/        # Entità del dominio
│   │   │   ├── repositories/    # Repository per l'accesso ai dati
│   │   │   └── services/        # Logica di business
│   │   ├── resources/           # File di configurazione e risorse
│   │   └── webapp/              # Pagine JSP e risorse web
│   └── test/                    # Test dell'applicazione
├── target/                      # File compilati
├── pom.xml                      # Configurazione Maven
└── README.md                    # Documentazione
```

## Tecnologie Utilizzate

- **Spring Boot 3.5.5** - Framework principale
- **Spring MVC** - Pattern architetturale web
- **JdbcTemplate** - Accesso ai dati senza ORM
- **JSP + Bootstrap 5.3.0** - Interfaccia utente responsiva
- **MySQL 8.0** - Database relazionale
- **Maven** - Gestione dipendenze e build
- **Lombok** - Riduzione boilerplate code
- **Docker** - Containerizzazione del database

## Avvio dell'Applicazione

1. **Avviare il database MySQL:**
   ```bash
   sudo docker run --name mysql-rubrica \
     -e MYSQL_ROOT_PASSWORD=root \
     -e MYSQL_DATABASE=rubrica \
     -e MYSQL_USER=rubrica \
     -e MYSQL_PASSWORD=rubrica \
     -p 3306:3306 \
     -d mysql:8.0
   ```

2. **Configurare le credenziali di accesso (opzionale):**
   ```bash
   export AUTH_USERNAME=myadmin
   export AUTH_PASSWORD=mysecurepassword
   ```
   Se non configurate, vengono utilizzate le credenziali di default:
   - **Username:** `admin`
   - **Password:** `admin123`

3. **Compilare e avviare l'applicazione:**
   ```bash
   ./mvnw clean compile
   ./mvnw spring-boot:run
   ```

4. **Aprire il browser su:** `http://localhost:8080`

## Flusso dell'Applicazione

1. **Configurazione Database** - Inserire parametri di connessione MySQL
2. **Test Connessione** - Validazione automatica dei parametri
3. **Autenticazione** - Login sicuro con credenziali da variabili di ambiente
4. **Gestione Rubrica** - CRUD completo dei contatti con validazione

## Caratteristiche Tecniche

### Sicurezza
- Autenticazione basata su variabili di sistema
- Sessioni utente sicure
- Validazione robusta dei dati di input

### Database
- Configurazione dinamica runtime
- Test automatico delle connessioni
- Gestione transazioni ottimizzata

### Interfaccia
- Design responsivo con Bootstrap 5
- Validazione client-side e server-side
- Messaggi di feedback per l'utente

## Autore

**Michael Leanza** - Sviluppo completo dell'applicazione