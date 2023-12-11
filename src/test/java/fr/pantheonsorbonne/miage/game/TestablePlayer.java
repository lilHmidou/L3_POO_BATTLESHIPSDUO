package fr.pantheonsorbonne.miage.game;

import java.util.List;

import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Cell;

public class TestablePlayer extends Player {
    public TestablePlayer() {
        super();
    }

    public void publicShoot(Player enemy, Cell targetCell) {
        super.shoot(enemy, targetCell);
    }

    public void publicUseBurstFire(Player enemy, List<Cell> targetCells) {
        super.useBurstFire(enemy, targetCells);
    }

    public void publicUseAirStrike(Player enemy, boolean isLine, int target) {
        super.useAirStrike(enemy, isLine, target);
    }

    public void publicUseNuclearBomb(Player enemy, int targetX, int targetY) {
        super.useNuclearBomb(enemy, targetX, targetY);
    }

    public boolean hasUsedBurstFire() {
        return super.hasUsedBurstFire;
    }

    public boolean hasUsedAirStrike() {
        return super.hasUsedAirStrike;
    }

    public boolean hasUsedNuclearBomb() {
        return super.hasUsedNuclearBomb;
    }

    public void publicUseSubmarine(Player enemy, boolean isLine, int target) {
        super.useSubmarine(enemy, isLine, target);
    }

    public boolean hasUsedSubmarine() {
        return super.hasUsedSubmarine;
    }

    public void publicUseRadar(Player enemy, int centerX, int centerY) {
        super.useRadar(enemy, centerX, centerY);
    }

    public boolean hasUsedRadar() {
        return super.hasUsedRadar;
    }

    public void publicUpdateNonPotentialCells(List<Cell> list, Player player) {
        super.updateNonPotentialCells(list, player);
    }
}