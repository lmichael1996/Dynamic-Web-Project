-- Creazione tabella lista_contatti per MySQL
CREATE TABLE IF NOT EXISTS lista_contatti (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL UNIQUE,
    indirizzo VARCHAR(255),
    eta INT NOT NULL
);
