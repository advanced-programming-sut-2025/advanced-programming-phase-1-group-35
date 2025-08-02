package core.Model.machines;

import core.Model.ItemInterface;
import core.Model.enums.machines.ArtisanProductDetails;

import java.util.HashMap;

public class ArtisanProduct implements ItemInterface {
    private String name;
    private int processingTime; //hourBased
    private int energy;
    private int sellingPrice;
    private String description; //Hours
    private HashMap<ItemInterface[],Integer> ingredients; // to be set manually in logic

    public ArtisanProduct(ArtisanProductDetails details) {
        this.name = details.getName();
        this.processingTime = details.processingTime;
        this.description = details.description;
        this.energy = details.energy;
        this.sellingPrice = details.sellPrice;
    }

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

    public String getName() {
        return name;
    }

    public int getPrice() {
        return sellingPrice;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<ItemInterface[], Integer> getIngredients() {
        return ingredients;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }
}
