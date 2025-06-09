package Model.machines;

import Model.ItemInterface;
import Model.enums.machines.ArtisanProductDetails;

public class ArtisanProduct implements ItemInterface {
    private String name;
    private int processingTime; //hourBased
    private int energy;
    private int sellingPrice;

    public ArtisanProduct(ArtisanProductDetails details) {
        this.name = details.getName();
        this.processingTime = details.processingTime;
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
}
