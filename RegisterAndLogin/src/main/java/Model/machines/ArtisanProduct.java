package Model.machines;

import Model.ItemInterface;

public class ArtisanProduct implements ItemInterface {
    private String name;
    private int processingTime; //hourBased
    private int energy;
    private int sellingPrice;
    private ItemInterface[] ingredients;


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

    public ItemInterface[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(ItemInterface[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return sellingPrice;
    }
}
