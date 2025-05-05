package Model.enums.Crops;

import Model.Item;
import Model.ItemInterface;

public enum Fruit implements ItemInterface {
    APRICOT("Apricot", 1, 59, true, 38, 17, "Spring"),
    CHERRY("Cherry", 1, 80, true, 38, 17, "Spring"),
    BANANA("Banana", 1, 150, true, 75, 33, "Summer"),
    MANGO("Mango", 1, 130, true, 100, 45, "Summer"),
    ORANGE("Orange", 1, 100, true, 38, 17, "Summer"),
    PEACH("Peach", 1, 140, true, 38, 17, "Summer"),
    APPLE("Apple", 1, 100, true, 38, 17, "Fall"),
    POMEGRANATE("Pomegranate", 1, 140, true, 38, 17, "Fall"),
    OAK_RESIN("Oak Resin", 7, 150, false, -1, -1, "Special"),
    MAPLE_SYRUP("Maple Syrup", 9, 200, false, -1, -1, "Special"),
    PINE_TAR("Pine Tar", 5, 100, false, -1, -1, "Special"),
    SAP("Sap", 1, 2, true, -2, 0, "Special"),
    COMMON_MUSHROOM("Common Mushroom", 1, 40, true, 38, 17, "Special"),
    MYSTIC_SYRUP("Mystic Syrup", 7, 1000, true, 500, 225, "Special");

    private final String name;
    private final int harvestCycle;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final int baseHealth;
    private final String season;

    Fruit(String name, int harvestCycle, int baseSellPrice, boolean isEdible, int energy, int baseHealth, String season) {
        this.name = name;
        this.harvestCycle = harvestCycle;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.baseHealth = baseHealth;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public int getHarvestCycle() {
        return harvestCycle;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public String getSeason() {
        return season;
    }
}

