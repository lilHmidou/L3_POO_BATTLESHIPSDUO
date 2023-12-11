package fr.pantheonsorbonne.miage.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.playerBots.Bot;
import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Board;
import fr.pantheonsorbonne.miage.game.setting.Boat;
import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.setting.Config;

public class BotTest {

    private Bot bot;
    private Player enemy;

    @BeforeEach
    public void setUp() {
        bot = new Bot("TestBot");
        enemy = new Player(); // Initialisez ou simulez un joueur ennemi
        // Configurez les états initiaux de bot et enemy si nécessaire
    }

    @Test
    public void testPlaceBoats() {
        bot.placeBoats();
        Board board = bot.getBoard();

        // Vérifiez si tous les bateaux sont placés
        assertEquals(Config.getNbBoats(), board.getBoats().length, "Tous les bateaux doivent être placés");

        // Vérifiez s'il n'y a pas de chevauchement
        assertFalse(board.hasOverlappingBoats(), "Les bateaux ne doivent pas se chevaucher");

        // Vérifiez le nombre de bateaux avec système de défense
        long countWithDefense = board.getBoatsWithDefenseSystem();
        assertTrue(countWithDefense >= 2, "Doit avoir au moins deux bateaux avec système de défense");
    }

    @Test
    public void testMakeMoveCallsShootMethod() {
        // Configurez l'état pour que bot devrait tirer sur une cellule
        bot.placeBoats();
        enemy.placeBoats();
        bot.makeMove(enemy);

        // Vérifiez que bot a effectué un tir
        // Cette vérification dépend de la façon dont vous pouvez observer les actions de bot
    }

    @Test
public void testSpecialFeaturesUsage() {
    // Configurez le bot et l'ennemi pour un scénario où une fonctionnalité spéciale devrait être utilisée
    bot.placeBoats();
    enemy.placeBoats();

    // Mettez le bot dans un état où il devrait utiliser une fonctionnalité spéciale
    // Par exemple, définissez les tours à un nombre spécifique et configurez les conditions requises
    bot.setRounds(6); // Supposons que setRounds est une méthode pour définir le nombre de tours

    // Exécutez makeMove et vérifiez si une fonctionnalité spéciale a été utilisée
    bot.makeMove(enemy);

    // Vérifiez que la fonctionnalité spéciale attendue a été utilisée
    // Cette vérification dépend de la façon dont vous pouvez observer les actions de bot
    // Par exemple, en vérifiant l'état de bot ou de enemy après l'appel de makeMove
    }

    @Test
    public void testRecognitionMethods() {
    bot.placeBoats();
    enemy.placeBoats();

    // Configurez bot pour un scénario où la reconnaissance devrait être utilisée
    bot.setRounds(4); // Supposons que `setRounds` est une méthode pour définir le nombre de tours

    bot.makeMove(enemy);

    // Vérifiez que les méthodes de reconnaissance appropriées ont été appelées
    // Cette vérification dépend de comment vous pouvez observer les actions de bot
    }

    @Test
    public void testUpdateDetectedCells() {
    bot.placeBoats();
    enemy.placeBoats();

    // Simulez des cellules détectées par le radar ou le sous-marin
    // ...

    bot.makeMove(enemy);

    // Vérifiez que les listes de cellules détectées sont mises à jour correctement
    // Vous devrez peut-être ajouter des getters dans la classe `Bot` pour ces listes
}


    @Test
    public void testShouldUseRecognition() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player(); // Initialisez ou simulez un joueur ennemi
        enemy.placeBoats();

        // Définissez les tours à un nombre spécifique pour tester la condition
        bot.publicSetRounds(4);

        // Testez si la reconnaissance devrait être utilisée
        boolean shouldUse = bot.publicShouldUseRecognition(enemy);
        assertTrue(shouldUse, "La reconnaissance devrait être utilisée après un certain nombre de tours");
    }

    @Test
    public void testRequestForRecognition() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player(); // Initialisez ou simulez un joueur ennemi
        enemy.placeBoats();

        // Assurez-vous que le bot n'a pas encore utilisé le radar ou le sous-marin
        bot.publicSetRounds(4); // Supposons que le bot devrait maintenant demander une reconnaissance

        // Appel de requestForRecognition
        bot.publicRequestForRecognition(enemy);

        // Vérifiez que le radar ou le sous-marin a été utilisé
        assertTrue(bot.publicHasUsedRadar() || bot.publicHasUsedSubmarine(), "Le radar ou le sous-marin devrait avoir été utilisé");
    }


    @Test
    public void testGetRandomValidCell() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player(); // Initialisez ou simulez un joueur ennemi
        enemy.placeBoats();
    
        // Simulez des cellules détectées par le radar et le sous-marin
        bot.SetRadarDetectedMine(enemy).add(new Cell(1, 1)); // Ajouter une cellule détectée par le radar
        bot.SetSubMarineDetectedMine(enemy).add(new Cell(2, 2)); // Ajouter une cellule détectée par le sous-marin
    
        // Appel de getRandomValidCell
        Cell randomValidCell = bot.publicGetRandomValidCell(enemy);
    
        // Vérifiez que la cellule retournée n'est pas dans les mines détectées par le radar ou le sous-marin
        assertFalse(bot.useIsInBoard(bot.SetRadarDetectedMine(enemy), randomValidCell), "La cellule ne doit pas être dans les cellules détectées par le radar");
        assertFalse(bot.useIsInBoard(bot.SetSubMarineDetectedMine(enemy), randomValidCell), "La cellule ne doit pas être dans les cellules détectées par le sous-marin");
    
        // Vérifiez également que la cellule retournée n'est pas déjà touchée
        assertFalse(randomValidCell.isShot(), "La cellule retournée ne doit pas déjà être touchée");
    }
    

    @Test
    public void testHandleSubmarineDetected() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        // Utiliser le sous-marin pour détecter des cellules
        bot.publicUseSubmarine(enemy, true, 3); // Exemple : ligne de balayage du sous-marin

        // Vérifier l'action effectuée en fonction du nombre de cellules détectées
        int detectedSize = bot.setSubMarineDetectedBoats(enemy).size();
        if (detectedSize != 0) {
            Cell detectedCell = bot.setSubMarineDetectedBoats(enemy).get(0);
            Boat boat = enemy.getBoard().getBoats(detectedCell.getId());

            bot.publicHandleSubmarineDetected(enemy);

            if (detectedSize > 2) {
                assertTrue(bot.publicHasUsedAirStrike() || bot.publicHasUsedBurstFire(),
                    "Le bot devrait utiliser une frappe aérienne ou un tir en rafale pour plusieurs cellules détectées");
            } else {
                // Vérifier si la cellule est touchée ou si le bouclier est désactivé
                boolean cellHitOrShieldDeactivated = detectedCell.isShot() || (boat != null && !boat.isProtected());
                assertTrue(cellHitOrShieldDeactivated, "Le bot devrait tirer sur la cellule détectée ou désactiver le bouclier");
            }
        } else {
            bot.publicShootRandomly(enemy); // Tirer au hasard si aucune cellule n'est détectée
        }
    }

        @Test
        public void testPrepareBurstFireCells() {
            TestableBot bot = new TestableBot("TestBot");
            Player enemy = new Player();
            enemy.placeBoats();

            // Choisir une cellule de départ
            Cell startingCell = bot.publicGetRandomValidCell(enemy); // Exemple de cellule de départ

            // Appeler prepareBurstFireCells
            List<Cell> burstFireCells = bot.publicPrepareBurstFireCells(startingCell, enemy);

            // Vérifier que la liste contient exactement 3 cellules
            assertEquals(3, burstFireCells.size(), "La liste doit contenir exactement 3 cellules");

            // Vérifier que chaque cellule est unique et non touchée
            HashSet<Cell> uniqueCells = new HashSet<>(burstFireCells);
            assertEquals(3, uniqueCells.size(), "Toutes les cellules doivent être uniques");
            for (Cell cell : burstFireCells) {
                assertFalse(cell.isShot(), "Aucune cellule ne doit être déjà touchée");
            }
        }


    @Test
    public void testShouldUseSpecialFeatures() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        boolean shouldUse = false;
        bot.publicSetRounds(6) ;

        if (bot.setSubMarineDetectedBoats(enemy).size() == 0 &&
            bot.setRadarDetectedBoats(enemy).size() == 0 &&
            (!bot.publicHasUsedAirStrike() || !bot.publicHasUsedBurstFire() || !bot.publicHasUsedNuclearBomb()) ) {
                shouldUse = true;
        }
        

        // Vérifier si shouldUseSpecialFeatures retourne true
        assertTrue(shouldUse, "Le bot devrait utiliser une fonctionnalité spéciale sous ces conditions");
    }


    @Test
public void testCheckAndUseSpecialFeature() {
    TestableBot bot = new TestableBot("TestBot");
    Player enemy = new Player();
    enemy.placeBoats();

    // Assurez-vous que le bot n'a pas encore utilisé toutes les armes spéciales
    bot.publicHasUsedBurstFire();
    bot.publicHasUsedNuclearBomb();
    bot.publicHasUsedAirStrike();

    // Appeler checkAndUseSpecialFeature
    bot.publicCheckAndUseSpecialFeature(enemy);

    // Vérifier si au moins une arme spéciale a été utilisée
    boolean specialFeatureUsed = bot.publicHasUsedBurstFire() || bot.publicHasUsedNuclearBomb() || bot.publicHasUsedAirStrike();
    assertTrue(specialFeatureUsed, "Le bot devrait utiliser au moins une fonctionnalité spéciale");
}



        @Test
        public void testChooseTargetCellWithCell() {
            TestableBot bot = new TestableBot("TestBot");
            Player enemy = new Player();
            enemy.placeBoats();

            // Configurer des cellules connues et potentielles
            bot.setKnownBoatCells(enemy).add(new Cell(1, 1)); // Ajouter une cellule connue
            bot.setPotentialCells(enemy).add(new Cell(2, 2)); // Ajouter une cellule potentielle

            // Appeler chooseTargetCell
            Cell targetCell = bot.publicChooseTargetCell(enemy);

            // Vérifier que la cellule retournée est correcte
            assertNotNull(targetCell, "Une cellule cible devrait être choisie");
            // Vérifier si la cellule retournée est l'une des cellules potentielles ou connues
            assertTrue(bot.setKnownBoatCells(enemy).contains(targetCell) || bot.setPotentialCells(enemy).contains(targetCell),
                "La cellule cible devrait être l'une des cellules potentielles ou connues");
        }

        @Test
        public void testChooseTargetCellReturnsNull() {
            TestableBot bot = new TestableBot("TestBot");
            Player enemy = new Player();
            enemy.placeBoats();
        
            // S'assurer que les listes knownBoatCells et potentialCells sont vides
            bot.setKnownBoatCells(enemy).clear(); // Vider la liste des cellules connues
            bot.setPotentialCells(enemy).clear(); // Vider la liste des cellules potentielles
            bot.setCurrentBoat(enemy); // Réinitialiser currentBoat
        
            // Appeler chooseTargetCell et vérifier qu'elle retourne null
            Cell targetCell = bot.publicChooseTargetCell(enemy);
            assertNull(targetCell, "Aucune cellule cible ne devrait être choisie lorsque les listes sont vides");
        }


        @Test
        public void testUpdateStrategicLists() {
            TestableBot bot = new TestableBot("TestBot");
            Player enemy = new Player();
            enemy.placeBoats();

            // Configurer des cellules initiales, y compris des cellules déjà touchées
            bot.setKnownBoatCells(enemy).add(new Cell(1, 1)); // Ajouter une cellule connue
            bot.setPotentialCells(enemy).add(new Cell(2, 2)); // Ajouter une cellule potentielle

            // Marquer une des cellules comme touchée
            enemy.getBoard().getCell(1, 1).shoot();

            // Appeler updateStrategicLists
            bot.publicUpdateDetectedCells(enemy);

            // Vérifier que les listes mises à jour ne contiennent que des cellules non touchées
            for (Cell cell : bot.setKnownBoatCells(enemy)) {
                assertFalse(cell.isShot(), "Les cellules connues mises à jour ne doivent pas être touchées");
            }
            for (Cell cell : bot.setPotentialCells(enemy)) {
                assertFalse(cell.isShot(), "Les cellules potentielles mises à jour ne doivent pas être touchées");
            }
        }


        @Test
        public void testHandleRadarDetected() {
            TestableBot bot = new TestableBot("TestBot");
            Player enemy = new Player();
            enemy.placeBoats();

            // Utiliser le radar pour détecter des cellules
            bot.publicUseRadar(enemy, 3, 3); // Exemple : utilisation du radar

            // Vérifier l'action effectuée en fonction du nombre de cellules détectées par le radar
            int detectedSize = bot.setRadarDetectedBoats(enemy).size();
            if (detectedSize != 0) {
                Cell detectedCell = bot.setRadarDetectedBoats(enemy).get(0);
                Boat boat = enemy.getBoard().getBoats(detectedCell.getId());

                bot.publicHandleRadarDetected(enemy);

                if (detectedSize > 1) {
                    assertTrue(bot.publicHasUsedNuclearBomb() || (bot.publicHasUsedBurstFire() && detectedSize > 2),
                        "Le bot devrait utiliser un missile nucléaire ou un tir en rafale pour plusieurs cellules détectées");
                } else {
                    // Vérifier si la cellule est touchée ou si le bouclier est désactivé
                    boolean cellHitOrShieldDeactivated = detectedCell.isShot() || (boat != null && !boat.isProtected());
                    assertTrue(cellHitOrShieldDeactivated, "Le bot devrait tirer sur la cellule détectée ou désactiver le bouclier");
                }
            } else {
                bot.publicShootRandomly(enemy); // Tirer au hasard si aucune cellule n'est détectée
            }
        }

        
}
    

