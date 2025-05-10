package Model.Buildings;

import Model.animal.Animal;

import java.util.ArrayList;


public class AnimalHouse extends Building {
    private String type ;
    private String level;
    public ArrayList<Animal> thisHouseAnimals = new ArrayList<>();

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }
}
