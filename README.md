# L3_POO_BATTLESHIPS - "Bataille Navale 2.0"

## Présentation du Projet

**Bataille Navale 2.0** renouvelle le classique jeu de stratégie maritime en introduisant des bots intelligents et des fonctionnalités de jeu avancées. Ce projet, développé dans le cadre de la matière Programmation Orientée Objet (POO), propose une expérience de jeu automatisée, stratégique et riche en actions.

## Règles du Jeu

### Mise en Place
1. **Plateau de Jeu** : Chaque joueur (bot) dispose d'un plateau 10x10 pour placer ses navires et deviner les positions ennemies.
2. **Navires** : La flotte de chaque joueur comprend :
   - 1 porte-avions (5 cases)
   - 1 croiseur (4 cases)
   - 2 contre-torpilleurs (3 cases chacun)
   - 1 torpilleur (2 cases)
3. **Tour de Jeu** : Les bots jouent à tour de rôle, bombardant une case à la fois. Les réponses aux tirs (touché, manqué, coulé) sont communiquées après chaque tir.
4. **Fin de la Partie** : Le jeu se termine lorsque tous les navires de l'un des bots sont coulés.

### Règles Supplémentaires
- **Mines** : Des mines sont placées secrètement. Si un bot tire sur une mine, il perd son prochain tour.
- **Radar** : Une fois par partie, un bot peut scanner une zone 3x3 pour détecter la présence de navires sans connaître leurs positions exactes.
- **Brouillard de Guerre** : Après quelques tours, une zone 5x5 devient cachée temporairement.
- **Tir en Rafale** : Une fois par partie, un bot peut effectuer un tir sur 3 cases consécutives.
- **Frappe Aérienne** : Chaque bot dispose d'une frappe aérienne pour bombarder une ligne ou colonne entière. Utilisable une seule fois.
- **Système de Défense** : Deux navires par flotte sont équipés d'un système de défense, les protégeant du premier tir.
- **Sous-marin de Reconnaissance** : Permet de détecter la présence de navires ennemis sur une ligne ou une colonne donnée.

## Fonctionnalités Clés

- **Jeu Autonome** : Les parties se jouent exclusivement entre bots.
- **Fonctionnalités Stratégiques Avancées** : Mines, radars, frappes aériennes, et plus.
- **Bots Intelligents** : Chaque bot dispose de stratégies uniques et utilise les nouvelles fonctionnalités de jeu.

## Architecture du Projet

Le projet est structuré autour de plusieurs classes Java essentielles à la mécanique et à la logique du jeu.

- **`Board`** : Représente le plateau de jeu.
- **`Cell`** : Incarne une cellule du plateau, pouvant contenir une partie d'un navire.
- **`Boat`** : Modélise un navire avec ses cellules et son état.
- **`CoordinateHelper`** : Fournit des outils pour la gestion des coordonnées.
- **`Config`** : Contient la configuration du jeu.
- **`Game`** : Encapsule la logique principale du jeu.
- **`GameLauncher`** : Point d'entrée pour démarrer le jeu.
- **`Player`/`Bot`** : Définit un joueur (bot) avec des stratégies de jeu avancées.

## Lancement du Jeu

Pour démarrer une partie de Bataille Navale 2.0, exécutez la classe `GameLauncher`. La partie se déroulera automatiquement, illustrant la compétition stratégique entre les bots.

---

**Bataille Navale 2.0** vous invite à découvrir une version automatisée et stratégiquement enrichie d'un jeu de guerre maritime classique. Préparez-vous à une expérience où la tactique, l'intelligence artificielle et l'innovation règnent en maître!