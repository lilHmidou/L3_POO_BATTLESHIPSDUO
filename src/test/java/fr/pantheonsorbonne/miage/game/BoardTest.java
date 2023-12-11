package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Board;
import fr.pantheonsorbonne.miage.game.setting.Boat;
import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.setting.Config;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testBoardInitialization() {
        Board board = new Board();

        assertNotNull(board.getCells(), "Le tableau de cellules ne doit pas être null");
        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells()[i].length; j++) {
                assertNotNull(board.getCells()[i][j], "Chaque cellule du tableau doit être initialisée");
            }
        }

        assertNotNull(board.getBoats(), "Le tableau des bateaux ne doit pas être null");
        assertEquals(0, board.getNbBoats(), "Le nombre de bateaux devrait être 0 à l'initialisation");

        assertNotNull(board.getAvailableCoordinates(), "La liste des coordonnées disponibles ne doit pas être null");
        assertEquals(100, board.getAvailableCoordinates().size(),
                "Il devrait y avoir 100 coordonnées disponibles à l'initialisation");
    }

    @Test
    public void testAddBoat() {
        Board board = new Board();
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        Boat boat = new Boat(cells, 1, "Destroyer", true);
        board.addBoat(boat);
        assertEquals(1, board.getNbBoats());
    }

    @Test
    public void testIsInBoard() {
        Board board = new Board();
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        assertTrue(board.isInBoard(cells));
        Cell[] outOfBoardCells = { new Cell(100, 100), new Cell(101, 101) };
        assertFalse(board.isInBoard(outOfBoardCells));
    }

    @Test
    public void testExistsOverlap() {
        Board board = new Board();
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        Boat boat = new Boat(cells, 1, "Destroyer", true);
        board.addBoat(boat);
        assertTrue(board.existsOverlap(cells));
        Cell[] nonOverlapCells = { new Cell(2, 2), new Cell(2, 3) };
        assertFalse(board.existsOverlap(nonOverlapCells));
    }

    @Test
    void testGetBoatsConfigConsistency() {
        String[][] boatsConfig = Config.getBoatsConfig();
        for (int i = 0; i < boatsConfig.length; i++) {
            assertNotNull(boatsConfig[i]);
            assertTrue(boatsConfig[i].length >= 3);
        }
    }

    @Test
    public void testBoardWithConfigBoats() {
        Player player = new Player();
        player.placeBoats();
        Board board = player.getBoard();

        Boat[] boats = board.getBoats();
        assertNotNull(boats, "Les bateaux ne doivent pas être null");
        assertEquals(Config.getNbBoats(), boats.length,
                "Le nombre de bateaux sur le plateau doit correspondre à la configuration");

        for (int i = 0; i < boats.length; i++) {
            Boat boat = boats[i];
            String[] boatConfig = Config.getBoatsConfig(i);
            assertEquals(Integer.parseInt(boatConfig[0]), boat.getId(),
                    "L'ID du bateau doit correspondre à la configuration");
            assertEquals(boatConfig[1], boat.getName(), "Le nom du bateau doit correspondre à la configuration");

        }
    }
}
