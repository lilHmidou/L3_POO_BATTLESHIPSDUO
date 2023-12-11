package fr.pantheonsorbonne.miage.game;

import java.util.List;

import fr.pantheonsorbonne.miage.game.playerBots.Bot;
import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Cell;

public class TestableBot extends Bot {

    public TestableBot(String name) {
        super(name);
    }

    // Accéder aux méthodes protégées de Bot
    public Cell publicGetRandomValidCell(Player enemy) {
        return super.getRandomValidCell(enemy);
    }

    public void publicHandleSubmarineDetected(Player enemy) {
        super.handleSubmarineDetected(enemy);
    }

    public void publicRequestForRecognition(Player enemy) {
        super.requestForRecognition(enemy);
    }

    public void publicHandleRadarDetected(Player enemy) {
        super.handleRadarDetected(enemy);
    }

    public Cell publicChooseTargetCell(Player enemy) {
        return super.chooseTargetCell(enemy);
    }

    public void publicCheckAndUseSpecialFeature(Player enemy) {
        super.checkAndUseSpecialFeature(enemy);
    }

    public List<Cell> publicPrepareBurstFireCells(Cell startingCell, Player enemy) {
        return super.prepareBurstFireCells(startingCell, enemy);
    }

    public void publicShootRandomly(Player enemy) {
        super.shootRandomly(enemy);
    }

    public boolean publicShouldUseSpecialFeatures(Player enemy) {
        return super.shouldUseSpecialFeatures(enemy);
    }

    public boolean publicShouldUseRecognition(Player enemy) {
        return super.shouldUseRecognition(enemy);
    }

    public void publicUpdateDetectedCells(Player enemy) {
        super.updateDetectedCells(enemy);
    }

    public void publicUpdateListWithAvailableCoordinates(List<Cell> detectedList, List<int[]> availableCoordinates) {
        super.updateListWithAvailableCoordinates(detectedList, availableCoordinates);
    }

    // Accéder aux méthodes publiques de Bot
    public void publicMakeMove(Player enemy) {
        super.makeMove(enemy);
    }

    public void publicSetRounds(int rounds) {
        super.rounds = rounds;
    }

    public boolean publicHasUsedRadar() {
        return super.hasUsedRadar;
    }

    public boolean publicHasUsedSubmarine() {
        return super.hasUsedSubmarine;
    }

    public boolean publicHasUsedBurstFire() {
        return super.hasUsedBurstFire;
    }

    public boolean publicHasUsedAirStrike() {
        return super.hasUsedAirStrike;
    }

    public boolean publicHasUsedNuclearBomb() {
        return super.hasUsedNuclearBomb;
    }

    public void publicUseSubmarine(Player enemy, boolean isLine, int target) {
        super.useSubmarine(enemy, isLine, target);
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

    public boolean hasUsedSubmarine() {
        return super.hasUsedSubmarine;
    }

    public void publicUseRadar(Player enemy, int centerX, int centerY) {
        super.useRadar(enemy, centerX, centerY);
    }

    public boolean hasUsedRadar() {
        return super.hasUsedRadar;
    }

    public List<Cell> SetSubMarineDetectedMine(Player enemy) {
        return super.subMarineDetectedMine;
    }

    public List<Cell> SetRadarDetectedMine(Player enemy) {
        return super.radarDetectedMines;
    }

    public List<Cell> setRadarDetectedBoats(Player enemy) {
        return super.radarDetectedBoats;
    }

    public List<Cell> setSubMarineDetectedBoats(Player enemy) {
        return super.subMarineDetectedBoat;
    }

    public boolean useIsInBoard(List<Cell> cells, Cell cell) {
        return super.isInBoard(cells, cell);
    }

    public Cell getRandomCell() {
        return super.targetCellRandomForShoot;
    }

    public List<Cell> setKnownBoatCells(Player enemy) {
        return super.knownBoatCells;
    }

    public List<Cell> setPotentialCells(Player enemy) {
        return super.potentialCells;
    }

    public int setCurrentBoat(Player enemy) {
        return super.currentBoat;
    }
}
