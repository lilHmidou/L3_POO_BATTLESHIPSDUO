package fr.pantheonsorbonne.miage.game.setting;

import java.util.ArrayList;
import java.util.List;


import fr.pantheonsorbonne.miage.game.helpers.CoordinateHelper;



public class Board{

    private Cell[][] board ;
    private Boat[] boats ; 
    private int nbBoats; 
    public List<int[]> availableCoordinates ;

    
    public Board(){
        this.board = generateBlankBoard(10, 10) ;
        this.boats = new Boat[Config.getNbBoats()] ; 
        this.availableCoordinates = new ArrayList<>();
        initializeAvailableCoordinates();
    }

    private Cell[][] generateBlankBoard(int l, int L){
        Cell[][] cells = new Cell[l][L];
        for(int i = 0; i < l; i++){
            for(int j = 0; j < L; j++){
                cells[i][j] = new Cell(i, j);
            }
        }
        return cells;
    }

    private void initializeAvailableCoordinates() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                availableCoordinates.add(new int[]{i, j});
            }
        }
    }

    public void updateAvailableCoordinates(int x, int y) {
        availableCoordinates.removeIf(coords -> coords[0] == x && coords[1] == y);
    }

    public List<int[]> getAvailableCoordinates() {
        return availableCoordinates;
    }    

    public Cell getCell(int x, int y){
        return board[x][y] ;
    }

    public void addBoat(Boat boat){
        int ind = 0 ;
        while (this.boats[ind] != null) {
            ind++ ;
        }
        this.boats[ind] = boat ;
        for (int i = 0; i < boat.getCells().length; i++) {
            this.board[boat.getCells()[i].getX()][boat.getCells()[i].getY()].addBoat(boat.getId());
        }
        nbBoats++ ;
    }

    public boolean existsNeighbors(Cell[] cells){

        int minX = cells[0].getX();
        int minY = cells[0].getY();
        int maxX = cells[cells.length-1].getX();
        int maxY = cells[cells.length-1].getY();

        int [][] voisins = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

        for (int x = minX; x <= maxX; x++){
            for(int y = minY ; y <=maxY; y++){
                for (int[] voisin : voisins) {
                    int voisinX = x + voisin[0];
                    int voisinY = y + voisin[1];
                    if (voisinX >= 0 && voisinX < 10 && voisinY >= 0 && voisinY < 10) {
                        if (CoordinateHelper.isValid(voisinX, voisinY) && this.board[voisinX][voisinY].getId() > 0) {
                        return true;
                    }
                    }
                }
            }
        }
        return false;
    }

    public boolean isInBoard(Cell [] cells){
        for (Cell cell : cells) {
            if (!CoordinateHelper.isValid(cell.getX(), cell.getY())) {
                return false;
            }
        }
        return true;
    }

    public boolean existsOverlap(Cell[] cells){
        for (int i = 0 ; i < cells.length ; i++) {
            if (this.board[cells[i].getX()][cells[i].getY()].getId() != 0) {
                return true;
            }
        }
        return false;
    }

    public Cell[] generateBoatCoordinates(int x, int y, String direction, int boatSize, int boatId, boolean isProtected){
        Cell[] cells = new Cell[boatSize];
        for(int i = 0; i < boatSize; i++){
            if(direction.equals("H")){
                cells[i] = new Cell( x + i, y);
            }else if (direction.equals("V")){
                cells[i] = new Cell(x, y + i);
            }
            cells[i].addBoat(boatId);
        }
        return cells;
    }

    public Boat [] getBoats() {
        return boats;
    }

    public Boat getBoats(int boatId) {
        for(int i = 0 ; i < boats.length ; i++){
            if(boats[i].getId() == boatId){
                return boats[i];
            }
        }
        return boats[0];
    }

    public Cell[][] getCells() {
        return board;
    }

    public int getNbBoats() {
        return nbBoats;
    }

    
}