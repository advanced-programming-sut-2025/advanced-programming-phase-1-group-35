package models;

public class Energy {
    private int energy;
    public Energy(){
        this.energy = 100;
    }
    public void energyCost(int energy){
        this.energy -= energy;
    }
    public void addEnergy(int energy){
        this.energy += energy;
    }
}
