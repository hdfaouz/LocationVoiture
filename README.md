# **🚗 CarRental - Plateforme de Location de Voitures en Ligne**

CarRental est une application web full stack qui permet aux utilisateurs de réserver des voitures à louer en ligne, et aux administrateurs de gérer les véhicules et les réservations. La plateforme offre une expérience simple, sécurisée et intuitive pour tous les types d’utilisateurs.

### _**🎯 Objectif**_

Faciliter la réservation de voitures pour les particuliers

Offrir aux propriétaires/gestionnaires un outil de gestion efficace

Mettre en place une application moderne et sécurisée, avec une bonne séparation des rôles

**_🧑‍🤝‍🧑 Utilisateurs et Rôles_**
Rôle	Capacités
Anonyme	Consulter les voitures, voir les détails, rechercher et filtrer
Client Authentifié	Réserver une voiture, consulter et annuler ses réservations
Administrateur	Gérer les voitures, valider/refuser des réservations, consulter les stats

### ⚙**_️ Fonctionnalités Principales_**

**_**_🔐 Authentification & Sécurité_**_**
Authentification avec JWT

Hashage des mots de passe avec BCrypt

AuthGuard pour la protection des routes sensibles

Contrôle des accès selon les rôles (client/admin)

Restriction des modifications aux propriétaires des données

**_🚘 Pour les Utilisateurs_**
**Anonymes**
Accès à la liste des voitures

**Détails complets** : marque, modèle, prix, image, disponibilité

Recherche par nom / marque / modèle

Filtrage par prix, disponibilité, catégorie (SUV, citadine, etc.)

**Clients Authentifiés**
Création et connexion à un compte

Réservation de voiture pour une période choisie

Historique personnel des réservations

Annulation possible avant le début de la location

**🧑‍💼 Pour les Administrateurs**
Ajout de nouvelles voitures (image, description, prix, disponibilité)

Modification/Suppression de voitures existantes

Accès à toutes les réservations

Validation ou refus des demandes de réservation

#### **_🧱 Stack Technique_**

**🖥️ Frontend**
**Framework** : Angular

**UI** : Angular Material

**State Management** : RxJS / Services

**Sécurité** : AuthGuard pour les routes privées

**🖥️ Backend**
**Langage** : Java

**Framework** : Spring Boot

**Sécurité** : Spring Security + JWT

**Base de Données** : MySQL

**ORM** : JPA (Hibernate)

**_🚀 Déploiement_**
**Conteneurs** : Docker
**CI/CD** : GitHub Actions / GitLab CI
**Serveur** : Apache

----------------------------------------------------------------------------------------------------------------------

#### **UMl Diagrams**

**_Diagramme de cas d'utilisation_**
![Diagrammes de cas d'utilisation](Diagrammes/diagramme%20de%20cas%20d'utilisation.png)
**_Diagramme de Classe_**
![Diagrammes de classe](Diagrammes/diagramme%20de%20classe.png)
**_Diagramme de sequence**_ 
![Diagrammes de séquence](Diagrammes/diagramme%20de%20sequence.png)
![Diagrammes de séquence](Diagrammes/diagramme%20de%20sequence%202.png)