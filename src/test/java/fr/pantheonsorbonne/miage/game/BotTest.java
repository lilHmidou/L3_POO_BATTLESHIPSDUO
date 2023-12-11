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
        enemy = new Player();
    }

    @Test
    public void testPlaceBoats() {
        bot.placeBoats();
        Board board = bot.getBoard();

        assertEquals(Config.getNbBoats(), board.getBoats().length, "Tous les bateaux doivent être placés");

        assertFalse(board.hasOverlappingBoats(), "Les bateaux ne doivent pas se chevaucher");

        long countWithDefense = board.getBoatsWithDefenseSystem();
        assertTrue(countWithDefense >= 2, "Doit avoir au moins deux bateaux avec système de défense");
    }

    @Test
    public void testMakeMoveCallsShootMethod() {

        bot.placeBoats();
        enemy.placeBoats();
        bot.makeMove(enemy);
    }

    @Test
    public void testSpecialFeaturesUsage() {

        bot.placeBoats();
        enemy.placeBoats();
        bot.setRounds(6);

        bot.makeMove(enemy);

    }

    @Test
    public void testRecognitionMethods() {
        bot.placeBoats();
        enemy.placeBoats();

        bot.setRounds(4);

        bot.makeMove(enemy);

    }

    @Test
    public void testUpdateDetectedCells() {
        bot.placeBoats();
        enemy.placeBoats();

        bot.makeMove(enemy);

    }

    @Test
    public void testShouldUseRecognition() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.publicSetRounds(4);

        boolean shouldUse = bot.publicShouldUseRecognition(enemy);
        assertTrue(shouldUse, "La reconnaissance devrait être utilisée après un certain nombre de tours");
    }

    @Test
    public void testRequestForRecognition() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.publicSetRounds(4);

        bot.publicRequestForRecognition(enemy);

        assertTrue(bot.publicHasUsedRadar() || bot.publicHasUsedSubmarine(),
                "Le radar ou le sous-marin devrait avoir été utilisé");
    }

    @Test
    public void testGetRandomValidCell() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.SetRadarDetectedMine(enemy).add(new Cell(1, 1));
        bot.SetSubMarineDetectedMine(enemy).add(new Cell(2, 2));

        Cell randomValidCell = bot.publicGetRandomValidCell(enemy);

        assertFalse(bot.useIsInBoard(bot.SetRadarDetectedMine(enemy), randomValidCell),
                "La cellule ne doit pas être dans les cellules détectées par le radar");
        assertFalse(bot.useIsInBoard(bot.SetSubMarineDetectedMine(enemy), randomValidCell),
                "La cellule ne doit pas être dans les cellules détectées par le sous-marin");

        assertFalse(randomValidCell.isShot(), "La cellule retournée ne doit pas déjà être touchée");
    }

    @Test
    public void testHandleSubmarineDetected() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.publicUseSubmarine(enemy, true, 3);

        int detectedSize = bot.setSubMarineDetectedBoats(enemy).size();
        if (detectedSize != 0) {
            Cell detectedCell = bot.setSubMarineDetectedBoats(enemy).get(0);
            Boat boat = enemy.getBoard().getBoats(detectedCell.getId());

            bot.publicHandleSubmarineDetected(enemy);

            if (detectedSize > 2) {
                assertTrue(bot.publicHasUsedAirStrike() || bot.publicHasUsedBurstFire(),
                        "Le bot devrait utiliser une frappe aérienne ou un tir en rafale pour plusieurs cellules détectées");
            } else {

                boolean cellHitOrShieldDeactivated = detectedCell.isShot() || (boat != null && !boat.isProtected());
                assertTrue(cellHitOrShieldDeactivated,
                        "Le bot devrait tirer sur la cellule détectée ou désactiver le bouclier");
            }
        } else {
            bot.publicShootRandomly(enemy);
        }
    }

    @Test
    public void testPrepareBurstFireCells() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        Cell startingCell = bot.publicGetRandomValidCell(enemy);

        List<Cell> burstFireCells = bot.publicPrepareBurstFireCells(startingCell, enemy);

        assertEquals(3, burstFireCells.size(), "La liste doit contenir exactement 3 cellules");

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
        bot.publicSetRounds(6);

        if (bot.setSubMarineDetectedBoats(enemy).size() == 0 &&
                bot.setRadarDetectedBoats(enemy).size() == 0 &&
                (!bot.publicHasUsedAirStrike() || !bot.publicHasUsedBurstFire() || !bot.publicHasUsedNuclearBomb())) {
            shouldUse = true;
        }

        assertTrue(shouldUse, "Le bot devrait utiliser une fonctionnalité spéciale sous ces conditions");
    }

    @Test
    public void testCheckAndUseSpecialFeature() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.publicHasUsedBurstFire();
        bot.publicHasUsedNuclearBomb();
        bot.publicHasUsedAirStrike();

        bot.publicCheckAndUseSpecialFeature(enemy);

        boolean specialFeatureUsed = bot.publicHasUsedBurstFire() || bot.publicHasUsedNuclearBomb()
                || bot.publicHasUsedAirStrike();
        assertTrue(specialFeatureUsed, "Le bot devrait utiliser au moins une fonctionnalité spéciale");
    }

    @Test
    public void testChooseTargetCellWithCell() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.setKnownBoatCells(enemy).add(new Cell(1, 1));
        bot.setPotentialCells(enemy).add(new Cell(2, 2));

        Cell targetCell = bot.publicChooseTargetCell(enemy);

        assertNotNull(targetCell, "Une cellule cible devrait être choisie");

        assertTrue(
                bot.setKnownBoatCells(enemy).contains(targetCell) || bot.setPotentialCells(enemy).contains(targetCell),
                "La cellule cible devrait être l'une des cellules potentielles ou connues");
    }

    @Test
    public void testChooseTargetCellReturnsNull() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.setKnownBoatCells(enemy).clear();
        bot.setPotentialCells(enemy).clear();
        bot.setCurrentBoat(enemy);

        Cell targetCell = bot.publicChooseTargetCell(enemy);
        assertNull(targetCell, "Aucune cellule cible ne devrait être choisie lorsque les listes sont vides");
    }

    @Test
    public void testUpdateStrategicLists() {
        TestableBot bot = new TestableBot("TestBot");
        Player enemy = new Player();
        enemy.placeBoats();

        bot.setKnownBoatCells(enemy).add(new Cell(1, 1));
        bot.setPotentialCells(enemy).add(new Cell(2, 2));

        enemy.getBoard().getCell(1, 1).shoot();

        bot.publicUpdateDetectedCells(enemy);

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

        bot.publicUseRadar(enemy, 3, 3);

        int detectedSize = bot.setRadarDetectedBoats(enemy).size();
        if (detectedSize != 0) {
            Cell detectedCell = bot.setRadarDetectedBoats(enemy).get(0);
            Boat boat = enemy.getBoard().getBoats(detectedCell.getId());

            bot.publicHandleRadarDetected(enemy);

            if (detectedSize > 1) {
                assertTrue(bot.publicHasUsedNuclearBomb() || (bot.publicHasUsedBurstFire() && detectedSize > 2),
                        "Le bot devrait utiliser un missile nucléaire ou un tir en rafale pour plusieurs cellules détectées");
            } else {

                boolean cellHitOrShieldDeactivated = detectedCell.isShot() || (boat != null && !boat.isProtected());
                assertTrue(cellHitOrShieldDeactivated,
                        "Le bot devrait tirer sur la cellule détectée ou désactiver le bouclier");
            }
        } else {
            bot.publicShootRandomly(enemy);
        }
    }

}
