package fr.pantheonsorbonne.miage.game.playerBots;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.setting.Boat;
import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.setting.Config;
import fr.pantheonsorbonne.miage.game.helpers.ConsoleHelper;
import fr.pantheonsorbonne.miage.game.helpers.CoordinateHelper;

public class Player extends AbstractPlayer{

    protected List<Cell> knownBoatCells = new ArrayList<>();
    protected List<Cell> potentialCells = new ArrayList<>();
    protected int currentBoat = 0;
    protected String name;

    protected List<Cell> subMarineDetectedMine = new ArrayList<>();
    protected List<Cell> subMarineDetectedBoat  = new ArrayList<>();
    protected List<Cell> radarDetectedBoats = new ArrayList<>();
    protected List<Cell> radarDetectedMines = new ArrayList<>();


    public Player() {
        super();
        super.hasUsedRadar = false;
        super.hasUsedBurstFire = false;
        super.hasUsedAirStrike = false;
        super.skipNextTurn = false;
        super.hasUsedSubmarine = false;
        super.hasUsedNuclearBomb = false;
        setPlayerName();
    }

    @Override
    protected void setPlayerName() {
        playerName = name ; 
    }

    @Override
    public void makeMove(Player enemy){
    }

    
    @Override
    protected void shoot(Player enemy, Cell targetCell) {
            
        handleCellHit(targetCell, enemy);
        super.setLastCellShot(targetCell.getX(), targetCell.getY());
        
    }

    @Override
    protected void useBurstFire(Player enemy, List<Cell> targetCells) {
        if (hasUsedBurstFire) {
            throw new IllegalStateException("Tir en rafale déjà utilisé");
        }
        
        for (int i = 0; i < 3; i++) {
            shoot(enemy, targetCells.get(i));
            if(enemy.shouldSkipNextTurn()) break; 
        }
        hasUsedBurstFire = true;
    }



    @Override
    protected void useAirStrike(Player enemy, boolean isLine, int target) {
        if (hasUsedAirStrike) {
            throw new IllegalStateException("Frappe aérienne déjà utilisée");
        }

        for (int i = 0; i < 10; i++) {
            Cell targetCell = isLine ? enemy.getBoard().getCells()[target][i] : enemy.getBoard().getCells()[i][target];
            handleCellHit(targetCell, enemy);
            super.setLastCellShot(targetCell.getX(), targetCell.getY());
        }           
        ConsoleHelper.sleep(1000);
        hasUsedAirStrike = true;
    }

    @Override
    protected void useNuclearBomb(Player enemy, int targetX, int targetY) {
        if (hasUsedNuclearBomb) {
            throw new IllegalStateException("Missile nucléaire déjà utilisé");
        }
        int radius = 1;
    
        int startX = Math.max(0, targetX - radius);
        int startY = Math.max(0, targetY - radius);
        int endX = Math.min(enemy.getBoard().getCells().length - 1, targetX + radius);
        int endY = Math.min(enemy.getBoard().getCells()[0].length - 1, targetY + radius);
    
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                Cell targetCell = enemy.getBoard().getCells()[i][j];
                handleCellHit(targetCell, enemy);
                super.setLastCellShot(targetCell.getX(), targetCell.getY());
            }
        }
        ConsoleHelper.sleep(1000);
        hasUsedNuclearBomb = true;
    }


    @Override
    protected void handleCellHit(Cell targetCell, Player enemy) {
        if (enemy.getBoard().getBoats(targetCell.getId()).isProtected()) {
            enemy.getBoard().getBoats(targetCell.getId()).desactivateDefenseSystem();

        } else {
            if (!targetCell.isShot()) {
                targetCell.shoot();
                enemy.getBoard().updateAvailableCoordinates(targetCell.getX(), targetCell.getY());
                incrementStatNbTotalShot();
                if (targetCell.getId() > 0) {
                    // Gestion des mines
                    if (targetCell.getId() == 99) {
                        enemy.setSkipNextTurn(true);
                        
                    } else { // Gestion des bateaux
                        Boat boat = enemy.getBoard().getBoats(targetCell.getId());
                        boat.getCells(targetCell.getX(), targetCell.getY()).shoot();
                        incrementStatNbSuccessfullShot();

                        currentBoat = targetCell.getId();

                        // Ajouter la cellule touchée à la liste des cellules connues
                        cellListAdd(knownBoatCells, targetCell);

                        // Mise à jour des cellules potentielles autour de la cellule touchée
                        potentialCells = updatePotentialCells(knownBoatCells, enemy);

                        if (boat.isSunk()) {
                            incrementStatNbBoatShot();

                            // Mise à jour des cellules non potentielles autour du bateau coulé
                            updateNonPotentialCells(knownBoatCells, enemy);

                            // Réinitialiser les listes connues et potentielles après avoir coulé un bateau
                            knownBoatCells = new ArrayList<>();
                            potentialCells = new ArrayList<>();
                            currentBoat = 0; // Réinitialiser l'ID du bateau courant
                        } else {
                            // Si le bateau n'est pas encore coulé, mettre à jour l'ID du bateau courant
                            currentBoat = targetCell.getId();
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void useSubmarine(Player enemy, boolean isLine, int target) {
        if (hasUsedSubmarine) {
            throw new IllegalStateException("Sous-marin déjà utilisé");
        }
        if (isLine) {
            for (int i = 0; i < 10; i++) {
                Cell cell = enemy.getBoard().getCells()[target][i];
                if (cell.getId() > 0) {
                    if(cell.getId() == 99){
                         subMarineDetectedMine.add(cell);
                    }else{
                        subMarineDetectedBoat.add(cell);
                    }
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                Cell cell = enemy.getBoard().getCells()[i][target];
                if (cell.getId() > 0) { // Exclure les mines
                    if(cell.getId() == 99){
                         subMarineDetectedMine.add(cell);
                    }else{
                        subMarineDetectedBoat.add(cell);
                    }
                }
            }
        }
        hasUsedSubmarine = true;
    }

    @Override
    protected void useRadar(Player enemy, int centerX, int centerY) {
        if (hasUsedRadar) {
            throw new IllegalStateException("Radar déjà utilisé");
        }
        int startX = Math.max(0, centerX - 1);
        int startY = Math.max(0, centerY - 1);
        int endX = Math.min(enemy.getBoard().getCells().length - 1, centerX + 1);
        int endY = Math.min(enemy.getBoard().getCells()[0].length - 1, centerY + 1);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                Cell cell = enemy.getBoard().getCells()[i][j];
                if (cell.getId() > 0 ) { 
                    if(cell.getId() == 99){
                        radarDetectedMines.add(cell);
                    }
                    else{
                        radarDetectedBoats.add(cell);
                    }
                }
            }
        }
        hasUsedRadar = true;
    }

   
    @Override
    public void placeBoats() {
        
        Random rand = new Random();
        int firstProtectedBoat = rand.nextInt(Config.getNbBoats() - 2);
        int secondProtectedBoat;
        do {
            secondProtectedBoat = rand.nextInt(Config.getNbBoats() - 2);
        } while (secondProtectedBoat == firstProtectedBoat);

        for (int i = 0; i < Config.getNbBoats(); i++) {
            String direction;
            int x, y;
            int boatSize = Integer.valueOf(Config.getBoatsConfig(i)[2]);
            boolean hasDefenseSystem = (i == firstProtectedBoat || i == secondProtectedBoat) && !Config.getBoatsConfig(i)[0].equals("99");

            boolean error;
            do {
                int randomDirection = rand.nextInt(2);
                direction = randomDirection == 1 ? "H" : "V";
                x = rand.nextInt(randomDirection == 1 ? 10 - boatSize : 10);
                y = rand.nextInt(randomDirection == 1 ? 10 : 10 - boatSize);

                Cell[] boatCoordinates = board.generateBoatCoordinates(x, y, direction, boatSize, Integer.valueOf(Config.getBoatsConfig(i)[0]), hasDefenseSystem);
                if (board.isInBoard(boatCoordinates) && !board.existsOverlap(boatCoordinates) && !board.existsNeighbors(boatCoordinates)) {
                    board.addBoat(new Boat(boatCoordinates, Integer.valueOf(Config.getBoatsConfig(i)[0]), Config.getBoatsConfig(i)[1], hasDefenseSystem)); // Ajout du système de défense
                    error = false;
                } else {
                    error = true;
                }
            } while (error);
        }
    }

    protected void cellListAdd(List<Cell> list, Cell cell) {
        list.add(cell);
    }
    
    protected void freezeCells(List<Cell> list, Player player) {
        for (Cell c : list) {
            player.getBoard().getCell(c.getX(), c.getY()).updatePotential(false);
        }
    }

    protected List<Cell> updatePotentialCells(List<Cell> knownBoatCells, Player player) {
        List<Cell> result = new ArrayList<>();
        int[][] testingCoordinates = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
    
        for (Cell c : knownBoatCells) {
            for (int[] coordinates : testingCoordinates) {
                int newX = c.getX() + coordinates[0];
                int newY = c.getY() + coordinates[1];
                if (CoordinateHelper.isValid(newX, newY)) {
                    Cell potentialCell = player.getBoard().getCell(newX, newY);
                    if (!result.contains(potentialCell)) {
                        result.add(potentialCell);
                    }
                }
            }
        }
        return result;
    }

    protected void updateNonPotentialCells(List<Cell> list, Player player) {
        if (list.size() < 2) {
            return; // Sortir de la méthode si la liste ne contient pas au moins deux éléments
        }
    
        boolean vertical = list.get(0).getX() == list.get(1).getX();
        int[] tempCoordinates = { -1, 1 };
    
        for (Cell c : list) {
            for (int j : tempCoordinates) {
                int x = c.getX() + (vertical ? 0 : j);
                int y = c.getY() + (vertical ? j : 0);
                if (CoordinateHelper.isValid(x, y)) {
                    player.getBoard().getCell(x, y).updatePotential(false);
                }
            }
        }
    }
    

}
