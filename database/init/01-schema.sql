-- Schéma initial PostgreSQL (exécuté au premier démarrage du conteneur)
CREATE TABLE IF NOT EXISTS clients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_clients_email ON clients (email);
