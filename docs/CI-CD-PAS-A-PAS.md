# CI/CD — guide pas à pas (GHCR)

Ce document suit la formation étape par étape.

## Étape 1 — CI GitHub Actions ✅

Workflow : tests Maven (backend) + build Vue (frontend).

Résultat attendu : jobs verts dans l’onglet **Actions**.

## Étape 2 — Push images Docker vers GHCR (en cours)

Fichier : `.github/workflows/ci-cd.yml`

Après les tests réussis (push sur `main` uniquement) :

1. login sur `ghcr.io` avec `GITHUB_TOKEN`
2. build & push `devops-backend` (`latest` + SHA)
3. build & push `devops-frontend` (`latest` + SHA)

Postgres reste l’image officielle `postgres:16-alpine` (pas de push).

### Ce que tu dois faire maintenant

1. Commit + push du nouveau workflow :

```powershell
git add .
git commit -m "ci: ajouter build et push des images Docker vers GHCR"
git push
```

2. Onglet **Actions** → workflow **CI/CD** → le job **Docker — Build & Push GHCR** doit être vert

3. Vérifier les packages :
   - GitHub → ton profil → **Packages**
   - ou : `https://github.com/fabien112?tab=packages`
   - tu dois voir `devops-backend` et `devops-frontend`

4. (Recommandé) Rendre les packages **Public** :
   - Package → **Package settings** → **Change visibility** → Public  
   Sinon `docker pull` demandera une authentification.

### Tirer une image en local (après push)

```powershell
docker pull ghcr.io/fabien112/devops-backend:latest
docker pull ghcr.io/fabien112/devops-frontend:latest
```

Quand c’est OK, dis-le : on passe à l’**Étape 3** (déploiement avec ces images GHCR).

## Étape 3 — Déploiement (à venir)

- adapter `docker-compose` pour tirer les images GHCR
- lancer postgres + backend + frontend
