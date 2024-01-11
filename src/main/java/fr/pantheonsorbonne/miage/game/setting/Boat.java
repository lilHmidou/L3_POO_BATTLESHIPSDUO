package fr.pantheonsorbonne.miage.game.setting;

public class Boat {
    private int id;
    private String name;
    private Cell[] cells;
    private String orientation;
    private boolean hasDefenseSystem; // should be on a BoatSubclass that implements a Defendable interface

    public Boat(Cell[] cells, int id, String name, boolean hasDefenseSystem) {
        this.cells = cells;
        this.id = id;
        this.name = name;
        this.hasDefenseSystem = hasDefenseSystem;
    }

    public Cell getCells(int cellPosition) {
        return this.cells[cellPosition];
    }

    public Cell getCells(int x, int y) {
        for (int i = 0; i < this.cells.length; i++) {
            if (cells[i].getX() == x && cells[i].getY() == y) {
                return cells[i];
            }
        }
        return cells[0];
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public int getId() {
        return this.id;
    }

    public int getSize() {
        return cells.length;
    }

    public String getName() {
        return name;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public boolean isSunk() {
        for (int i = 0; i < this.cells.length; i++) {
            if (!this.cells[i].isShot()) {
                return false;
            }
        }
        return true;
    }

    public boolean isProtected() {
        return this.hasDefenseSystem;
    }

    public void desactivateDefenseSystem() {
        this.hasDefenseSystem = false;
    }

}