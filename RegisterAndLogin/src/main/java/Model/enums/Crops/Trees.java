package Model.enums.Crops;

import Model.enums.Crops.PlantAble;

import java.util.*;

public enum Trees implements PlantAble {
    APRICOT("Apricot Tree", "Apricot Sapling", "7-7-7-7", 28, "Apricot", 1, 59, true, 38, 17, "Spring"),
    CHERRY("Cherry Tree", "Cherry Sapling", "7-7-7-7", 28, "Cherry", 1, 80, true, 38, 17, "Spring"),
    BANANA("Banana Tree", "Banana Sapling", "7-7-7-7", 28, "Banana", 1, 150, true, 75, 33, "Summer"),
    MANGO("Mango Tree", "Mango Sapling", "7-7-7-7", 28, "Mango", 1, 130, true, 100, 45, "Summer"),
    ORANGE("Orange Tree", "Orange Sapling", "7-7-7-7", 28, "Orange", 1, 100, true, 38, 17, "Summer"),
    PEACH("Peach Tree", "Peach Sapling", "7-7-7-7", 28, "Peach", 1, 140, true, 38, 17, "Summer"),
    APPLE("Apple Tree", "Apple Sapling", "7-7-7-7", 28, "Apple", 1, 100, true, 38, 17, "Fall"),
    POMEGRANATE("Pomegranate Tree", "Pomegranate Sapling", "7-7-7-7", 28, "Pomegranate", 1, 140, true, 38, 17, "Fall"),
    OAK("Oak Tree", "Acorns", "7-7-7-7", 28, "Oak Resin", 7, 150, false, 0, 0, "Special"),
    MAPLE("Maple Tree", "Maple Seeds", "7-7-7-7", 28, "Maple Syrup", 9, 200, false, 0, 0, "Special"),
    PINE("Pine Tree", "Pine Cones", "7-7-7-7", 28, "Pine Tar", 5, 100, false, 0, 0, "Special"),
    MAHOGANY("Mahogany Tree", "Mahogany Seeds", "7-7-7-7", 28, "Sap", 1, 2, true, -2, 0, "Special"),
    MUSHROOM("Mushroom Tree", "Mushroom Tree Seeds", "7-7-7-7", 28, "Common Mushroom", 1, 40, true, 38, 17, "Special"),
    MYSTIC("Mystic Tree", "Mystic Tree Seeds", "7-7-7-7", 28, "Mystic Syrup", 7, 1000, true, 500, 225, "Special");

    private final String name;
    private final String source;
    private final String stages;
    private final int totalHarvestTime;
    private final String fruitName;
    private final int fruitCycle;
    private final int fruitBaseSellPrice;
    private final boolean isFruitEdible;
    private final int fruitEnergy;
    private final int fruitHealth;
    private final String season;

    Trees(String name, String source, String stages, int totalHarvestTime, String fruitName, int fruitCycle,
         int fruitBaseSellPrice, boolean isFruitEdible, int fruitEnergy, int fruitHealth, String season) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruitName = fruitName;
        this.fruitCycle = fruitCycle;
        this.fruitBaseSellPrice = fruitBaseSellPrice;
        this.isFruitEdible = isFruitEdible;
        this.fruitEnergy = fruitEnergy;
        this.fruitHealth = fruitHealth;
        this.season = season;
    }

    // Getters
    public String getName() { return name; }
    public String getSource() { return source; }
    public String getStages() { return stages; }
    public int getTotalHarvestTime() { return totalHarvestTime; }
    public String getFruitName() { return fruitName; }
    public int getFruitCycle() { return fruitCycle; }
    public int getFruitBaseSellPrice() { return fruitBaseSellPrice; }
    public boolean isFruitEdible() { return isFruitEdible; }
    public int getFruitEnergy() { return fruitEnergy; }
    public int getFruitHealth() { return fruitHealth; }
    public String getSeason() { return season; }
}
