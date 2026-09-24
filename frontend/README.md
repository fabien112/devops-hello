# Frontend — devops-frontend

Interface Vue 3 pour tester le CRUD Clients.

## Commandes

```bash
npm install
npm run dev      # http://localhost:5173
npm run build

# Image Docker
docker build -t devops-frontend:1.0.0 .
```

En Docker, Nginx sert l'UI et proxifie `/api` vers le service `backend`.
