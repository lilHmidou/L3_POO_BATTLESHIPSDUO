package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.game.playerBots.Bot;
import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.game.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLauncherTest {

    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player1 = new Bot("Thanos");
        player2 = new Bot("Jon Snow");

        game.init(player1, player2);
    }

    @Test
    public void testGameInitialization() {
        Player bot1 = new Bot("Thanos");
        Player bot2 = new Bot("Jon Snow");

        game.init(bot1, bot2);

        assertEquals(bot1, game.getPlayer1(), "Le joueur 1 doit être initialisé correctement");
        assertEquals(bot2, game.getPlayer2(), "Le joueur 2 doit être initialisé correctement");

    }

}
