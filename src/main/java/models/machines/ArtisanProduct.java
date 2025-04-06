package models.machines;

import models.Item;

import java.util.ArrayList;

public class ArtisanProduct extends Item {
    private int processingTime; //hourBased
    private int energy;
    private ArrayList<Item> ingredients;

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public ArrayList<Item> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Item> ingredients) {
        this.ingredients = ingredients;
    }
}
