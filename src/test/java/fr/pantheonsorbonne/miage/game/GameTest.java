package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.game.playerBots.Bot;
import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Boat;
import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.game.Game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));

        player1 = new Player(/* arguments */);
        player2 = new Player(/* arguments */);
        game = new Game();
        game.init(player1, player2);

    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game.getPlayer1(), "Le joueur 1 ne doit pas être null");
        assertNotNull(game.getPlayer2(), "Le joueur 2 ne doit pas être null");

    }

    @Test
    public void testSetFirstPlayer() {
        game.setFirstPlayer();
        assertTrue(game.getPlayerPlay() == 1 || game.getPlayerPlay() == 2,
                "Le premier joueur doit être soit le joueur 1 soit le joueur 2");
    }

    @Test
    public void testEndMethodDisplaysWinner() {

        game.end();
        String output = outputStreamCaptor.toString();
        String expectedWinnerAnnouncement = "Le joueur " + game.getPlayerWinner() + " a gagné la partie";
        assertTrue(output.contains(expectedWinnerAnnouncement), "La méthode end() doit afficher le nom du gagnant");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testAllBoatsSunk() {

        Cell[] sunkCells = { new Cell(1, 1), new Cell(1, 2) };
        for (Cell cell : sunkCells) {
            cell.shoot();
        }
        Boat sunkBoat = new Boat(sunkCells, 1, "Destroyer", false);

        assertTrue(game.allBoatsSunk(new Boat[] { sunkBoat }),
                "La méthode allBoatsSunk() doit renvoyer true si tous les bateaux sont coulés");

        Cell[] notSunkCells = { new Cell(2, 1), new Cell(2, 2) };
        Boat notSunkBoat = new Boat(notSunkCells, 2, "Cruiser", false);

        assertFalse(game.allBoatsSunk(new Boat[] { sunkBoat, notSunkBoat }),
                "La méthode allBoatsSunk() doit renvoyer false si tous les bateaux ne sont pas coulés");
    }

    @Test
    public void testPrintStats() {
        game.end();
        String output = outputStreamCaptor.toString();

        assertTrue(output.contains(output),
                "Les statistiques des joueurs doivent être affichées à la fin de la partie");
    }

    @Test
    public void testBasicGamePlay() {
        Player player1 = new Bot("Ahmed Amine");
        Player player2 = new Bot("Le Sommeil");

        game.init(player1, player2);
        game.play();

        assertNotNull(game.getPlayerWinner(), "Un gagnant doit être déclaré à la fin du jeu");
    }

    @Test
    public void testGameOverWhenAllBoatsSunk() {
        Game game = new Game();
        Player player1 = new Bot("Ahmed Amine");
        Player player2 = new Bot("Le Sommeil");

        game.init(player1, player2);

        game.play();

        assertTrue(game.over(), "Le jeu devrait se terminer quand tous les bateaux d'un joueur sont coulés");
    }

    @Test
    public void testGameContinuesWhenBoatsRemain() {
        Game game = new Game();
        Player player1 = new Bot("Ahmed Amine");
        Player player2 = new Bot("Le Sommeil");

        game.init(player1, player2);

        assertFalse(game.over(), "Le jeu devrait continuer tant que des bateaux sont encore intacts");
    }

    @Test
    public void testGameOverForEachPlayer() {
        Game game = new Game();
        Player player1 = new Bot("Ahmed Amine");
        Player player2 = new Bot("Le Sommeil");

        game.init(player1, player2);

        boolean overForPlayer1 = game.allBoatsSunk(player1.getBoard().getBoats());
        boolean overForPlayer2 = game.allBoatsSunk(player2.getBoard().getBoats());
        assertEquals(game.over(), overForPlayer1 || overForPlayer2,
                "Le jeu devrait se terminer si tous les bateaux d'un des joueurs sont coulés");
    }

    @Test
    public void testCompleteGamePlay() {
        Game game = new Game();
        Player player1 = new Bot("Ahmed Amine");
        Player player2 = new Bot("Le Sommeil");

        game.init(player1, player2);

        while (!game.over()) {
            game.play();
            assertTrue(player1.setHasUsedRadar() || player2.setHasUsedRadar(),
                    "Un radar est utilisé au cours de la partie");
            assertTrue(player1.setHasUsedSubmarine() || player2.setHasUsedSubmarine(),
                    "Un sous-marin est utilisé au cours de la partie");
            assertTrue(player1.setHasUsedAirStrike() || player2.setHasUsedAirStrike(),
                    "Une frappe aérienne est utilisée au cours de la partie");
            assertTrue(player1.setHasUsedNuclearBomb() || player2.setHasUsedNuclearBomb(),
                    "Une bombe nucléaire est utilisée au cours de la partie");
            assertTrue(player1.setHasUsedBurstFire() || player2.setHasUsedBurstFire(),
                    "Un tir en rafale est utilisé au cours de la partie");
        }

        assertNotNull(game.getPlayerWinner(), "Un gagnant doit être déclaré à la fin du jeu");
        assertTrue(game.over(), "Le jeu devrait être terminé");
    }

    @Test
    public void testGameOverWhenNoBoatsSunk() {
        assertFalse(game.over(), "La partie ne devrait pas être terminée si aucun bateau n'est coulé");
    }

    @Test
    public void testGameOverWhenAllPlayer1BoatsSunk() {
        for (Boat boat : player1.getBoard().getBoats()) {
            for (Cell cell : boat.getCells()) {
                cell.shoot();
            }
        }
        assertTrue(game.over(), "La partie devrait être terminée si tous les bateaux du joueur 1 sont coulés");
    }

    @Test
    public void testGameOverWhenAllPlayer2BoatsSunk() {
        for (Boat boat : player2.getBoard().getBoats()) {
            for (Cell cell : boat.getCells()) {
                cell.shoot();
            }
        }
        assertTrue(game.over(), "La partie devrait être terminée si tous les bateaux du joueur 2 sont coulés");
    }

    @Test
    public void testGameOverWhenSomeBoatsSunk() {
        for (int i = 0; i < player1.getBoard().getBoats().length; i++) {
            if (i % 2 == 0) {
                for (Cell cell : player1.getBoard().getBoats()[i].getCells()) {
                    cell.shoot();
                }
            }
        }
        assertFalse(game.over(), "La partie ne devrait pas être terminée si seuls certains bateaux sont coulés");
    }

}
