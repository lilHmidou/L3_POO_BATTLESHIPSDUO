# BATTLESHIP_DUO POUR "Bataille Navale 2.0"
Dans le cadre de la matière POO, le projet consiste à concevoir un jeu de société connu en Java. Ce dépôt est celui de mon binôme.

# Bataille Navale 2.0

## Description
Bataille Navale 2.0 est une version automatisée et enrichie du jeu classique de bataille navale. Ce projet se concentre sur des affrontements entre bots intelligents, avec de nouvelles fonctionnalités stratégiques et des options de gameplay.

## Classes 

### Board

**Description** : Représente le plateau de jeu avec un tableau 2D de `Cell`.
**Fonction** : Utilisée pour représenter le plateau pour chaque bot.

### Cell
**Description** : Représente une cellule sur le plateau, pouvant contenir une partie d'un bateau.
**Fonction** : Utilisée pour suivre l'état du jeu, notamment les tirs et les bateaux touchés.

### Boat
**Description** : Représente un bateau avec ses cellules et son état (coulé ou non).
**Fonction** : Intégrée dans la logique des bots pour gérer les flottes.

### CoordinateHelper
**Description** : Utilitaire pour la conversion et la validation des coordonnées.
**Fonction** : Essentielle pour les algorithmes de décision des bots.

### ConsoleHelper
**Description** : Fournit des méthodes d'affichage pour la console.
**Fonction** : Utilisée pour un affichage minimaliste des actions et résultats.

### Config
**Description** : Configuration du jeu, incluant les types de bateaux.
**Fonction** : Mise à jour pour les nouvelles règles et types de navires.

### Game
**Description** : Logique principale du jeu, gérant le déroulement de la partie.
**Fonction** : Simplifiée pour automatiser le jeu avec des bots, suppression des interactions utilisateur.

### GameLauncher
**Description** : Logique du jeu, gérant l'ordre des tâches du jeu.
**Fonction** : Permet de lancer le jeu.

### Player
**Description** : Représente une abstraction d'un joueur.
**Fonction** : Permet de créer plusieurs instance de bots.

### Bots
**Description** : Représente un joueur contrôlé par ordinateur.
**Fonction** : Enrichie avec de nouvelles stratégies de jeu et intégration des fonctionnalités supplémentaires (mines, radar, etc.).

