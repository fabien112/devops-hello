# Base de données PostgreSQL

Ce dossier contient la configuration et les scripts d'initialisation de PostgreSQL.

## Contenu

- `init/01-schema.sql` : crée la table `clients` au **premier** démarrage du volume

## Connexion (docker-compose)

| Paramètre | Valeur |
|-----------|--------|
| Host | `localhost` (depuis la machine) / `postgres` (depuis Docker) |
| Port | `5432` |
| Database | `devops` |
| User | `devops` |
| Password | `devops` |

## Notes

- Les scripts dans `init/` ne sont exécutés que si le volume PostgreSQL est vide.
- Pour réinitialiser complètement : `docker compose down -v` puis `docker compose up --build`.
