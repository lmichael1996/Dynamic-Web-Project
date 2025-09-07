# Dynamic Web Project

Una applicazione web Spring Boot per la gestione di una rubrica contatti con configurazione dinamica del database.

## 🚀 Funzionalità

- **Configurazione dinamica del database MySQL**
- **Autenticazione basata su sessione**
- **Gestione CRUD della rubrica contatti**
- **Interfaccia web con JSP e Bootstrap**

## 🛠️ Setup Database con Docker

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

## 📁 Struttura del Progetto

```
Dynamic-Web-Project/
├── src/
│   ├── main/
│   │   ├── java/com/michaelleanza/rubrica/
│   │   │   ├── DynamicWebProjectApplication.java  # Classe principale
│   │   │   ├── configs/          # Configurazioni dell'applicazione
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

## 🔧 Tecnologie Utilizzate

- **Spring Boot** - Framework principale
- **Spring MVC** - Pattern architetturale
- **JdbcTemplate** - Accesso ai dati
- **JSP + Bootstrap** - Interfaccia utente
- **MySQL** - Database
- **Maven** - Gestione dipendenze

## 🚀 Avvio dell'Applicazione

1. Assicurarsi che MySQL sia in esecuzione
2. Compilare il progetto: `./mvnw clean compile`
3. Avviare l'applicazione: `./mvnw spring-boot:run`
4. Aprire il browser su: `http://localhost:8080`

## 📝 Flusso dell'Applicazione

1. **Configurazione Database** - Inserire parametri di connessione MySQL
2. **Autenticazione** - Login con credenziali configurate
3. **Gestione Rubrica** - CRUD completo dei contatti

## 👨‍💻 Autore

**Michael Leanza** - Sviluppo completo dell'applicazione