package fr.pantheonsorbonne.miage.game.playerBots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.setting.Cell;


public class Bot extends Player {

    protected int rounds = 0;
    protected boolean isSubmarineLineScan;
    protected Cell targetCellRandomForShoot;
    

    public Bot(String name){
        super();
        super.playerName = name ;
    }

    @Override
    public void makeMove(Player enemy) {
        rounds ++ ;
        // Vérifier si des fonctionnalités spéciales peuvent être utilisées
        // Déclencher des fonctionnalités spéciales sous certaines conditions
        if (shouldUseSpecialFeatures(enemy)) {
            checkAndUseSpecialFeature(enemy);
            return; // Si une fonctionnalité spéciale a été utilisée, terminer le tour
        }

        if (shouldUseRecognition(enemy)) {
            requestForRecognition(enemy);
            return; // Si une reconnaissance a été demandée, terminer le tour
        }

        updateDetectedCells(enemy);
        if (!super.subMarineDetectedBoat.isEmpty()) {
            handleSubmarineDetected(enemy);
            return;
        } else if (!super.radarDetectedBoats.isEmpty()) {
            handleRadarDetected(enemy);
            return;
        }


        // Choix de la cellule cible en fonction de la stratégie
        updateStrategicLists(enemy);
        Cell targetCell = chooseTargetCell(enemy);

        // Effectuer le tir sur la cellule cible
        if (targetCell != null) {
            super.shoot(enemy, targetCell);
        } else {
            // Si aucune cellule cible n'est déterminée, tirer au hasard
            shootRandomly(enemy);
        }
    }


    public Cell chooseTargetCell(Player enemy) {
        // Stratégie de suivi basée sur les cellules connues et potentielles
        if (super.currentBoat != 0 && 
        !super.potentialCells.isEmpty() && 
        !isInBoard(super.subMarineDetectedMine, super.potentialCells.get(0)) &&
        !isInBoard(super.radarDetectedMines, super.potentialCells.get(0))) {
            return super.potentialCells.get(0);

        } else if (!super.knownBoatCells.isEmpty()) {
            // Tirer sur une cellule connue non touchée
            for (Cell cell : super.knownBoatCells) {
                if (!cell.isShot() && 
                (!isInBoard(super.radarDetectedMines, cell) && 
                !isInBoard(super.subMarineDetectedMine, cell))) {
                    return cell;
                }
            }
        }
        // Retourne null si aucune cellule stratégique n'est trouvée
        return null;
    }



    protected boolean isInBoard(List<Cell> cells, Cell targetCell){
        for(Cell cell : cells){
            if(cell.getX() == targetCell.getX() && cell.getY() == targetCell.getY()){
                return true;
            }
        }
    return false;
    }

    
    protected void updateStrategicLists(Player enemy) {
        super.knownBoatCells = updateListWithAvailableCells(super.knownBoatCells, enemy);
        super.potentialCells = updateListWithAvailableCells(super.potentialCells, enemy);
    }
    

    protected List<Cell> updateListWithAvailableCells(List<Cell> list, Player enemy) {
        List<Cell> updatedList = new ArrayList<Cell>();
        for (Cell cell : list) {
            if (!enemy.getBoard().getCell(cell.getX(), cell.getY()).isShot()) {
                updatedList.add(cell);
            }
        }
    return updatedList;
    } 

    
    protected void shootRandomly(Player enemy) {
            // Tirer sur une cellule aléatoire disponible
            List<int[]> availableCoordinates = enemy.getBoard().getAvailableCoordinates();
            Cell targetCellRandom ;
            boolean validShot = true ;
            do{
                Random random = new Random();
                int[] coords = availableCoordinates.get(random.nextInt(availableCoordinates.size()));
                targetCellRandom = enemy.getBoard().getCell(coords[0], coords[1]);
                if (!isInBoard(super.radarDetectedMines, targetCellRandom) || !isInBoard(super.subMarineDetectedMine, targetCellRandom)){
                    validShot = false ;
                }
                
            }while(validShot);
        
            this.targetCellRandomForShoot = targetCellRandom ;
            super.shoot(enemy, targetCellRandom);
        }

    
    protected boolean shouldUseSpecialFeatures(Player enemy) {
        // Exemple de condition : Utiliser une fonctionnalité spéciale si plus de la moitié du tableau est intacte
        if (rounds > 5 && (super.radarDetectedBoats.isEmpty() && 
        super.subMarineDetectedBoat.isEmpty()) && 
        (!super.hasUsedAirStrike || !super.hasUsedBurstFire || !super.hasUsedNuclearBomb)) {
            return true;
        } else{
            return false;
        }
    }

    
    protected void checkAndUseSpecialFeature(Player enemy) {
        // Obtenez une cellule aléatoire valide
        Cell targetCell = getRandomValidCell(enemy);
        
        // Choisissez une fonctionnalité spéciale à utiliser
        if (!super.hasUsedBurstFire) {
            // Préparer les cellules pour le tir en rafale
            List<Cell> targetCells = prepareBurstFireCells(targetCell, enemy);
            super.useBurstFire(enemy, targetCells);
        } else if (!super.hasUsedNuclearBomb) {
            super.useNuclearBomb(enemy, targetCell.getX(), targetCell.getY());
        } else if (!super.hasUsedAirStrike) {
            boolean isLine = new Random().nextBoolean();
            super.useAirStrike(enemy, isLine, isLine ? targetCell.getX() : targetCell.getY());
        }
    }
    
    
    protected Cell getRandomValidCell(Player enemy) {
        List<int[]> availableCoordinates = enemy.getBoard().getAvailableCoordinates();
        Random random = new Random();
        while (true) {
            int[] coords = availableCoordinates.get(random.nextInt(availableCoordinates.size()));
            Cell cell = enemy.getBoard().getCell(coords[0], coords[1]);
            if (!isInBoard(super.radarDetectedMines, cell) && !isInBoard(super.subMarineDetectedMine, cell)) {
                return cell;
            }
        }
    }
    
    protected List<Cell> prepareBurstFireCells(Cell startingCell, Player enemy) {
        List<Cell> targetCells = new ArrayList<>();
        targetCells.add(startingCell);
        while (targetCells.size() < 3) {
            Cell targetCellRandom = getRandomValidCell(enemy);
            // Ajouter la nouvelle cellule à la liste si elle est différente des autres
            if (!targetCells.contains(targetCellRandom)) {
                targetCells.add(targetCellRandom);
            }
        }

        return targetCells;
    }

    protected boolean shouldUseRecognition(Player enemy) {    
        return  (rounds > 3 && (!super.hasUsedRadar || !super.hasUsedSubmarine)) ;
    }

    protected void requestForRecognition(Player enemy){
        // Logique pour demander une reconnaissance au hasard car début de partie
        List<int[]> availableCoordinates = enemy.getBoard().getAvailableCoordinates();
        Random random = new Random();
        this.isSubmarineLineScan = new Random().nextBoolean();
        int[] coords = availableCoordinates.get(random.nextInt(availableCoordinates.size()));
        Cell cell = enemy.getBoard().getCell(coords[0], coords[1]);

        // Utilisez le radar ou le sous-marin si approprié
        if(!super.hasUsedSubmarine){
            super.useSubmarine(enemy, isSubmarineLineScan, isSubmarineLineScan ? cell.getX() : cell.getY());
        }else if(!super.hasUsedRadar){
            super.useRadar(enemy, cell.getX(), cell.getY());
        }else{
            shootRandomly(enemy);}
    }


    protected void updateDetectedCells(Player enemy) {
        List<int[]> availableCoordinates = enemy.getBoard().getAvailableCoordinates();
    
        // Mise à jour de la liste des bateaux détectés par le sous-marin
        updateListWithAvailableCoordinates(super.subMarineDetectedBoat, availableCoordinates);
    
        // Mise à jour de la liste des bateaux détectés par le radar
        updateListWithAvailableCoordinates(super.radarDetectedBoats, availableCoordinates);
    }
    

    protected void updateListWithAvailableCoordinates(List<Cell> detectedList, List<int[]> availableCoordinates) {
        List<Cell> updatedList = new ArrayList<>();
    
        for (Cell detectedCell : detectedList) {
            for (int[] coords : availableCoordinates) {
                if (detectedCell.getX() == coords[0] && detectedCell.getY() == coords[1]) {
                    updatedList.add(detectedCell);
                    break;
                }
            }
        }
    
        detectedList.clear();
        detectedList.addAll(updatedList);
    }


    protected void handleSubmarineDetected(Player enemy) {
        // Logique pour gérer les cibles détectées par le sous-marin
        int detectedSize = super.subMarineDetectedBoat.size();

        if (detectedSize >= 2) {
            if (detectedSize > 2  && !super.hasUsedAirStrike) {
                super.useAirStrike(enemy, isSubmarineLineScan, 
                isSubmarineLineScan ? super.subMarineDetectedBoat.get(0).getX() : super.subMarineDetectedBoat.get(0).getY());
            }else if (!super.hasUsedBurstFire && detectedSize > 2) {
                super.useBurstFire(enemy, super.subMarineDetectedBoat);
            }else{
                super.shoot(enemy, super.subMarineDetectedBoat.get(0));
            }
        } else if(detectedSize > 0){
                super.shoot(enemy, super.subMarineDetectedBoat.get(0));
        }else{
                shootRandomly(enemy);
            }
        }
    

        protected void handleRadarDetected(Player enemy) {
            // Logique pour gérer les cibles détectées par le radar
            int detectedSize = super.radarDetectedBoats.size();
    
            // Utilisez le missile nucléaire ou le tir en rafale si approprié
            if (detectedSize > 1) {
                if (!super.hasUsedNuclearBomb) {
                    super.useNuclearBomb(enemy, super.radarDetectedBoats.get(0).getX(), super.radarDetectedBoats.get(0).getY());
                } else if (!super.hasUsedBurstFire && detectedSize > 2) {
                    super.useBurstFire(enemy, super.radarDetectedBoats);
                }else {
    super.shoot(enemy, super.radarDetectedBoats.get(0));
    }
            } else if(detectedSize > 0){
                    super.shoot(enemy, super.radarDetectedBoats.get(0));
            }else{
                    shootRandomly(enemy);
                }
            }

        public void setRounds(int i) {
        }
}
