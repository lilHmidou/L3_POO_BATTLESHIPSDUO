package fr.pantheonsorbonne.miage.game.game;

import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Boat;

public class Game {

    private Player player1;
    private Player player2;
    private int playerPlay;
    private String playerWinner;

    private void printGameLauncher() {
        System.out.println("Bienvenue dans le jeu de bataille navale 2.0");
    }

    public void setFirstPlayer() {
        this.playerPlay = (int) Math.round(Math.random() * 2 + 1);
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public String getPlayerWinner() {
        return this.playerWinner;
    }

    public int getPlayerPlay() {
        return this.playerPlay;
    }

    public void init(Player player1, Player player2) {
        printGameLauncher();

        this.player1 = player1;
        this.player2 = player2;

        System.out.println(
                "la partie opposant " + player1.getPlayerName() + " à " + player2.getPlayerName() + " va commencer");
        System.out.println("");
        player1.placeBoats();
        player2.placeBoats();

        setFirstPlayer();
    }

    public void play() {
        do {
            if (playerPlay == 1) {
                player1.makeMove(player2);
                playerWinner = player1.getPlayerName();
                playerPlay = 2;
            } else {
                player2.makeMove(player1);
                playerWinner = player2.getPlayerName();
                playerPlay = 1;
            }
            if (player1.shouldSkipNextTurn()) {
                playerPlay = 2;
                player1.setSkipNextTurn(false);
            } else if (player2.shouldSkipNextTurn()) {
                playerPlay = 1;
                player2.setSkipNextTurn(false);
            }
        } while (!over());
    }

    public void end() {
        System.out.println("Le joueur " + playerWinner + " a gagné la partie");
        player1.printStats();
        System.out.println("");
        player2.printStats();
    }

    public boolean over() {
        return allBoatsSunk(player1.getBoard().getBoats()) || allBoatsSunk(player2.getBoard().getBoats());
    }

    public boolean allBoatsSunk(Boat[] boats) {
        for (Boat boat : boats) {
            if (!boat.isSunk() && (boat.getId() != 99)) {
                return false;
            }
        }
        return true;
    }

}
