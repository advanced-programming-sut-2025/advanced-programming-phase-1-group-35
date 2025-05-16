package Model.Buildings;

import Model.animal.Animal;

import java.util.ArrayList;


public class AnimalHouse extends Building {
    private String type ;
    private int level;

    public AnimalHouse(String type, int level) {
        this.type = type;
        this.level = level;
    }

    public ArrayList<Animal> thisHouseAnimals = new ArrayList<>();

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
