package fr.pantheonsorbonne.miage.game.setting;


public class Cell {
    private int x ; 
    private int y ;
    private int id ;
    private boolean shot ;
    private boolean potential ;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.id = 0 ;
        this.shot = false;
        this.potential = true;
    }

    public void addBoat(int id){
        this.id = id;
    }

    public void updatePotential(boolean value){ 
        potential = value;
    }

    public boolean isPotential(){
        return potential;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void shoot(){
        this.shot = true;
    }

    public boolean isShot(){
        return this.shot;
    }

    public int getId(){
        return this.id;
    }
}