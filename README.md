# ToDoList - Application de gestion des tâches

## Contexte
Ce projet est une application de gestion de tâches (To-Do List). L'application permet de gérer des tâches via une API REST en Spring Boot et une interface utilisateur simple avec Angular.

## Technologies utilisées
- **Backend** : Spring Boot (Java) - API REST
- **Frontend** : Angular
- **Base de données** : MySQL/PostgreSQL
- **Tests unitaires** : JUnit, Mockito (Backend) et Karma (Frontend)
- **Authentification** : JWT (JSON Web Token) pour la sécurité
- **Outils** : Postman (pour tester l'API), MySQL/PostgreSQL, et Swagger

## Fonctionnalités de l'application

### Backend (API REST)
L'API permet de gérer des tâches avec les fonctionnalités suivantes :
- **GET /tasks** : Récupérer la liste des tâches
- **POST /tasks** : Créer une nouvelle tâche
- **GET /tasks/{id}** : Récupérer une tâche par son ID
- **PUT /tasks/{id}** : Modifier une tâche
- **DELETE /tasks/{id}** : Supprimer une tâche
- **PUT /tasks/{id}/complete** : Marquer une tâche comme terminée (fonctionnalité ajoutée pour marquer une tâche comme "terminée").

### Modèle de la tâche
Chaque tâche contient :
- `id` (UUID ou entier, auto-incrémenté)
- `title` (Texte, obligatoire)
- `description` (Texte, facultatif)
- `status` (Enum : pending, in_progress, completed)
- `created_at` (Date de création, auto-rempli)
- `updated_at` (Date de mise à jour, auto-rempli)

### Frontend
- Affichage de la liste des tâches
- Formulaire pour ajouter une tâche
- Boutons pour modifier et supprimer une tâche
- Gestion du statut de la tâche (bouton "Marquer comme terminé")

### Authentification
L'application implémente un système d'authentification sécurisé via JWT (JSON Web Token) pour protéger les routes sensibles.

## Installation

### Prérequis
- Java 17 ou version supérieure
- Node.js et npm
- MySQL/PostgreSQL installé et configuré

### Backend (Spring Boot)
1. Clonez le dépôt :
    ```bash
    git clone https://github.com/imane-el-mazouz/ToDoList.git
    ```
2. Accédez au dossier du backend :
    ```bash
    cd todoList-backend
    ```
3. Compilez et démarrez l'application :
    ```bash
    ./mvnw spring-boot:run
    ```
4. L'API sera accessible sur `http://localhost:8080`.

### Frontend (Angular)
1. Accédez au dossier du frontend :
    ```bash
    cd todoList-frontend
    ```
2. Installez les dépendances :
    ```bash
    npm install
    ```
3. Démarrez l'application Angular :
    ```bash
    ng serve
    ```
4. L'interface utilisateur sera accessible sur `http://localhost:4200`.

### Base de données
1. Importez le script SQL `todo.sql` pour créer la table `tasks` dans votre base de données MySQL/PostgreSQL.
2. Assurez-vous que les contraintes sont définies (NOT NULL, DEFAULT, ENUM).

## Tests unitaires
Les tests unitaires ont été réalisés avec :
- **Backend** : JUnit et Mockito pour tester les services et les contrôleurs.
- **Frontend** : Karma pour tester les composants Angular.

Pour exécuter les tests unitaires :
```bash
ng test
