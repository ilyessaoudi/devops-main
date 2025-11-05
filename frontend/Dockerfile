# Étape 1 : Build Angular
FROM node:18-alpine AS builder

WORKDIR /app

# Copier les fichiers package.json et package-lock.json
COPY package*.json ./

# Installer les dépendances avec legacy-peer-deps pour éviter les erreurs
RUN npm install --legacy-peer-deps --save-dev http-server --force

# Définir la variable d'environnement pour éviter les erreurs OpenSSL
ENV NODE_OPTIONS=--openssl-legacy-provider

# Copier le code source
COPY . .

# Build du projet Angular en mode production
RUN npm run build --prod

# Étape 2 : Nginx pour servir l'app Angular
FROM nginx:alpine

# Copier le build généré dans le dossier de Nginx
COPY --from=builder /app/dist/achat-frontend /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

# Lancer Nginx
CMD ["nginx", "-g", "daemon off;"]
