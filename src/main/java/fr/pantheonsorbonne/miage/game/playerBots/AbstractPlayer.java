package fr.pantheonsorbonne.miage.game.playerBots;

import java.util.List;

import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.setting.Config;
import fr.pantheonsorbonne.miage.game.setting.Board;

public abstract class AbstractPlayer {

    private int statNbTotalShot;
    private int statNbSuccessfullShot;
    private int statNbBoatShot;

    protected String playerName;
    protected Board board = new Board();
    protected boolean hasUsedBurstFire;
    protected boolean hasUsedRadar;
    protected boolean hasUsedAirStrike;
    protected boolean skipNextTurn;
    protected boolean hasUsedSubmarine;
    protected boolean hasUsedNuclearBomb;
    protected Cell lastCellShot = new Cell(-1, -1);

    protected abstract void shoot(Player enemy, Cell targetCell);

    protected abstract void useBurstFire(Player enemy, List<Cell> targetCells);

    protected abstract void useAirStrike(Player enemy, boolean isLine, int target);

    protected abstract void useNuclearBomb(Player enemy, int targetX, int targetY);

    protected abstract void handleCellHit(Cell targetCell, Player enemy);

    protected abstract void useSubmarine(Player enemy, boolean isLine, int target);

    protected abstract void useRadar(Player enemy, int x, int y);

    protected abstract void setPlayerName();

    public abstract boolean setHasUsedRadar();

    public abstract boolean setHasUsedAirStrike();

    public abstract boolean setHasUsedNuclearBomb();

    public abstract boolean setHasUsedSubmarine();

    public abstract boolean setHasUsedBurstFire();

    public abstract void placeBoats();

    public abstract void makeMove(Player enemy);

    protected Cell getLastCellShot() {
        return lastCellShot;
    }

    protected void setLastCellShot(int x, int y) {
        lastCellShot = new Cell(x, y);
    }

    protected void incrementStatNbTotalShot() {
        statNbTotalShot++;
    }

    protected void incrementStatNbSuccessfullShot() {
        statNbSuccessfullShot++;
    }

    protected void incrementStatNbBoatShot() {
        statNbBoatShot++;
    }

    public void setSkipNextTurn(boolean skip) {
        this.skipNextTurn = skip;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public boolean shouldSkipNextTurn() {
        return this.skipNextTurn;
    }

    public void printStats() {
        System.out.println("Statistiques de " + this.playerName);
        System.out.println("Nombre de tirs total : " + statNbTotalShot); //Le nombre de tirs total peut être différent de l'adversaire si le bot tire sur une case déjà touchée via les armes spéciales.
        System.out.println("Nombre de tirs réussis : " + statNbSuccessfullShot);
        System.out.println("Precision : "
                + (double) Math.round((double) statNbSuccessfullShot * 100 / (double) statNbTotalShot) + "%");
        System.out.println("Utilisation de l'air strike : " + (hasUsedAirStrike ? "Oui" : "Non"));
        System.out.println("Utilisation de la bombe nucléaire : " + (hasUsedNuclearBomb ? "Oui" : "Non"));
        System.out.println("Utilisation du sous-marin : " + (hasUsedSubmarine ? "Oui" : "Non"));
        System.out.println("Utilisation du radar : " + (hasUsedRadar ? "Oui" : "Non"));
        System.out.println("Utilisation du tir groupé : " + (hasUsedBurstFire ? "Oui" : "Non"));
        System.out.println("Nombre de bateaux touchés : " + statNbBoatShot + "/" + (Config.getNbBoats() - 2));
    }

}
