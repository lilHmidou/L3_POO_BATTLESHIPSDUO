package fr.pantheonsorbonne.miage.game.game;
import fr.pantheonsorbonne.miage.game.playerBots.Bot;
import fr.pantheonsorbonne.miage.game.playerBots.Player;

public class GameLauncher {

    public static void main(String[] args) {
        Game game = new Game();

        Player bot1 = new Bot("Thanos");
        Player bot2 = new Bot("Jon Snow");

        game.init(bot1, bot2);

        game.play();

        game.end();

    }
}
