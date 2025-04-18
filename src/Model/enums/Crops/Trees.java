package Model.enums.Crops;

public enum Trees {
    APRICOT_TREE("Apricot Tree", "Apricot Sapling", "7-7-7-7", 28, "Apricot",
            "1", "59", "TRUE", "38", "17", "Spring"),
    CHERRY_TREE("Cherry Tree", "Cherry Sapling", "7-7-7-7", 28, "Cherry",
            "1", "80", "TRUE", "38", "17", "Spring"),
    BANANA_TREE("Banana Tree", "Banana Sapling", "7-7-7-7", 28, "Banana",
            "1", "150", "TRUE", "75", "33", "Summer"),
    MANGO_TREE("Mango Tree", "Mango Sapling", "7-7-7-7", 28, "Mango",
            "1", "130", "TRUE", "100", "45", "Summer"),
    ORANGE_TREE("Orange Tree", "Orange Sapling", "7-7-7-7", 28, "Orange",
            "1", "100", "TRUE", "38", "17", "Summer"),
    PEACH_TREE("Peach Tree", "Peach Sapling", "7-7-7-7", 28, "Peach",
            "1", "140", "TRUE", "38", "17", "Summer"),
    APPLE_TREE("Apple Tree", "Apple Sapling", "7-7-7-7", 28, "Apple",
            "1", "100", "TRUE", "38", "17", "Fall"),
    POMEGRANATE_TREE("Pomegranate Tree", "Pomegranate Sapling", "7-7-7-7", 28,
            "Pomegranate", "1", "140", "TRUE", "38", "17", "Fall"),
    OAK_TREE("Oak Tree", "Acorns", "7-7-7-7", 28, "Oak Resin",
            "7", "150", "FALSE", "-", "-", "Special"),
    MAPLE_TREE("Maple Tree", "Maple Seeds", "7-7-7-7", 28, "Maple Syrup",
            "9", "200", "FALSE", "-", "-", "Special"),
    PINE_TREE("Pine Tree", "Pine Cones", "7-7-7-7", 28, "Pine Tar",
            "5", "100", "FALSE", "-", "-", "Special"),
    MAHOGANY_TREE("Mahogany Tree", "Mahogany Seeds", "7-7-7-7", 28, "Sap",
            "1", "2", "TRUE", "-2", "0", "Special"),
    MUSHROOM_TREE("Mushroom Tree", "Mushroom Tree Seeds", "7-7-7-7", 28,
            "Common Mushroom", "1", "40", "TRUE", "38", "17", "Special"),
    MYSTIC_TREE("Mystic Tree", "Mystic Tree Seeds", "7-7-7-7", 28, "Mystic Syrup",
            "7", "1000", "TRUE", "500", "225", "Special");

    private final String Name;
    private final String Source;
    private final String Stages;
    private final int TotalHarvestTime;
    private final String Fruit;
    private final String FruitHarvestCycle;
    private final String FruitBaseSellPrice;
    private final String IsFruitEdible;
    private final String FruitEnergy;
    private final String FruitBaseHealth;
    private final String Season;

    Trees(String Name, String Source, String Stages, int totalHarvestTime, String Fruit, String FruitHarvestCycle,
          String FruitBaseSellPrice, String IsFruitEdible, String FruitEnergy, String FruitBaseHealth, String Season) {
        this.Name = Name;
        this.Source = Source;
        this.Stages = Stages;
        this.TotalHarvestTime = totalHarvestTime;
        this.Fruit = Fruit;
        this.FruitHarvestCycle = FruitHarvestCycle;
        this.FruitBaseSellPrice = FruitBaseSellPrice;
        this.IsFruitEdible = IsFruitEdible;
        this.FruitEnergy = FruitEnergy;
        this.FruitBaseHealth = FruitBaseHealth;
        this.Season = Season;
    }

    // Getters can be added here if needed
}
