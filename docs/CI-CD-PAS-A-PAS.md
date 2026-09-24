# CI/CD — guide pas à pas (GHCR)

Ce document suit la formation étape par étape.

## Étape 1 — CI GitHub Actions (fait)

Fichier : `.github/workflows/ci.yml`

À chaque push / PR sur `main` ou `master` :

1. lance les tests Maven du **backend**
2. build le **frontend** Vue

Pas encore de build/push d’images Docker.

### Ce que tu dois faire maintenant

1. Créer un dépôt vide sur GitHub (ex. `devops-hello`)
2. Dans PowerShell, à la racine du projet :

```powershell
git add .
git commit -m "chore: initialisation projet DevOps (backend, frontend, database, CI)"
git branch -M main
git remote add origin https://github.com/<TON_USER>/devops-hello.git
git push -u origin main
```

3. Onglet **Actions** du dépôt GitHub → vérifier que le workflow **CI** est vert

Quand c’est OK, dis-le : on passe à l’**Étape 2** (build + push images vers GHCR).

## Étape 2 — Push images Docker vers GHCR (à venir)

- login `ghcr.io` avec `GITHUB_TOKEN`
- build `devops-backend` et `devops-frontend`
- push avec tags `latest` + SHA du commit

## Étape 3 — Déploiement (plus tard)

- tirer les images GHCR
- lancer postgres + backend + frontend (Compose ou Kubernetes)
