# devops-hello

Projet fil rouge DevOps : **3 projets séparés** + orchestration Docker.

**Git → GitHub → Maven → Tests → Docker → Docker Registry → GitHub Actions → Kubernetes**

## Structure

```text
DevOpps/
├── docker-compose.yml          # Orchestre les 3 services
├── README.md
├── backend/                    # API Spring Boot
│   ├── Dockerfile
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd
│   └── src/
├── frontend/                   # UI Vue 3
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   └── src/
└── database/                   # PostgreSQL (scripts d'init)
    ├── README.md
    └── init/
        └── 01-schema.sql
```

## Architecture

| Dossier | Service Docker | Image | Port |
|---------|----------------|-------|------|
| `database/` | `postgres` | `postgres:16-alpine` | 5432 |
| `backend/` | `backend` | `devops-backend:1.0.0` | 8080 |
| `frontend/` | `frontend` | `devops-frontend:1.0.0` | 3000 |

```text
Navigateur → frontend:3000 → /api → backend:8080 → postgres:5432
```

## Lancer toute la stack

Prérequis : Docker Desktop démarré.

```bash
docker compose up --build
```

- Frontend : [http://localhost:3000](http://localhost:3000)
- API : [http://localhost:8080/api/hello](http://localhost:8080/api/hello)
- PostgreSQL : `localhost:5432` (user / password / db = `devops`)

Arrêter :

```bash
docker compose down
```

Réinitialiser la BDD (supprime le volume) :

```bash
docker compose down -v
```

## Tests backend

Depuis le dossier `backend/` (H2 en mémoire, pas besoin de Postgres) :

```bash
cd backend
.\mvnw.cmd clean verify
```

## Développement local

1. Postgres seulement :

```bash
docker compose up -d postgres
```

2. Backend :

```bash
cd backend
.\mvnw.cmd spring-boot:run
```

3. Frontend :

```bash
cd frontend
npm install
npm run dev
```

→ [http://localhost:5173](http://localhost:5173)

## Endpoints API

### Hello

- `GET /api/hello`
- `GET /api/hello/{name}`

### CRUD Clients

| Méthode | Endpoint |
|---------|----------|
| GET | `/api/clients` |
| GET | `/api/clients/{id}` |
| POST | `/api/clients` |
| PUT | `/api/clients/{id}` |
| DELETE | `/api/clients/{id}` |

## Prochaine étape

Voir le guide pas à pas : [`docs/CI-CD-PAS-A-PAS.md`](docs/CI-CD-PAS-A-PAS.md)

**Étape 1 (en cours)** : CI GitHub Actions (tests backend + build frontend) → pousser sur GitHub et vérifier l’onglet Actions.
