package Model.enums.Crops;


//to do: change stages and seasons into Lists//


public enum Crop {
    BLUE_JAZZ("Blue Jazz", "Jazz Seeds", "1-2-2-2", 7, true, -1, 50, true, 45, "Spring", false),
    CARROT("Carrot", "Carrot Seeds", "1-1-1", 3, true, -1, 35, true, 75, "Spring", false),
    CAULIFLOWER("Cauliflower", "Cauliflower Seeds", "1-2-4-4-1", 12, true, -1, 175, true, 75, "Spring", true),
    COFFEE_BEAN("Coffee Bean", "Coffee Bean", "1-2-2-3-2", 10, false, 2, 15, false, -1, "Spring & Summer", false),
    GARLIC("Garlic", "Garlic Seeds", "1-1-1-1", 4, true, -1, 60, true, 20, "Spring", false),
    GREEN_BEAN("Green Bean", "Bean Starter", "1-1-1-3-4", 10, false, 3, 40, true, 25, "Spring", false),
    KALE("Kale", "Kale Seeds", "1-2-2-1", 6, true, -1, 110, true, 50, "Spring", false),
    PARSNIP("Parsnip", "Parsnip Seeds", "1-1-1-1", 4, true, -1, 35, true, 25, "Spring", false),
    POTATO("Potato", "Potato Seeds", "1-1-1-2-1", 6, true, -1, 80, true, 25, "Spring", false),
    RHUBARB("Rhubarb", "Rhubarb Seeds", "2-2-2-3-4", 13, true, -1, 220, false, -1, "Spring", false),
    STRAWBERRY("Strawberry", "Strawberry Seeds", "1-1-2-2-2", 8, false, 4, 120, true, 50, "Spring", false),
    TULIP("Tulip", "Tulip Bulb", "1-1-2-2", 6, true, -1, 30, true, 45, "Spring", false),
    UNMILLED_RICE("Unmilled Rice", "Rice Shoot", "1-2-2-3", 8, true, -1, 30, true, 3, "Spring", false),
    BLUEBERRY("Blueberry", "Blueberry Seeds", "1-3-3-4-2", 13, false, 4, 50, true, 25, "Summer", false),
    CORN("Corn", "Corn Seeds", "2-3-3-3-3", 14, false, 4, 50, true, 25, "Summer & Fall", false),
    HOPS("Hops", "Hops Starter", "1-1-2-3-4", 11, false, 1, 25, true, 45, "Summer", false),
    HOT_PEPPER("Hot Pepper", "Pepper Seeds", "1-1-1-1-1", 5, false, 3, 40, true, 13, "Summer", false),
    MELON("Melon", "Melon Seeds", "1-2-3-3-3", 12, true, -1, 250, true, 113, "Summer", true),
    POPPY("Poppy", "Poppy Seeds", "1-2-2-2", 7, true, -1, 140, true, 45, "Summer", false),
    RADISH("Radish", "Radish Seeds", "2-1-2-1", 6, true, -1, 90, true, 45, "Summer", false),
    RED_CABBAGE("Red Cabbage", "Red Cabbage Seeds", "2-1-2-2-2", 9, true, -1, 260, true, 75, "Summer", false),
    STARFRUIT("Starfruit", "Starfruit Seeds", "2-3-2-3-3", 13, true, -1, 750, true, 125, "Summer", false),
    SUMMER_SPANGLE("Summer Spangle", "Spangle Seeds", "1-2-3-1", 8, true, -1, 90, true, 45, "Summer", false),
    SUMMER_SQUASH("Summer Squash", "Summer Squash Seeds", "1-1-1-2-1", 6, false, 3, 45, true, 63, "Summer", false),
    SUNFLOWER("Sunflower", "Sunflower Seeds", "1-2-3-2", 8, true, -1, 80, true, 45, "Summer & Fall", false),
    TOMATO("Tomato", "Tomato Seeds", "2-2-2-2-3", 11, false, 4, 60, true, 20, "Summer", false),
    WHEAT("Wheat", "Wheat Seeds", "1-1-1-1", 4, true, -1, 25, false, -1, "Summer & Fall", false),
    AMARANTH("Amaranth", "Amaranth Seeds", "1-2-2-2", 7, true, -1, 150, true, 50, "Fall", false),
    ARTICHOKE("Artichoke", "Artichoke Seeds", "2-2-1-2-1", 8, true, -1, 160, true, 30, "Fall", false),
    BEET("Beet", "Beet Seeds", "1-1-2-2", 6, true, -1, 100, true, 30, "Fall", false),
    BOK_CHOY("Bok Choy", "Bok Choy Seeds", "1-1-1-1", 4, true, -1, 80, true, 25, "Fall", false),
    BROCCOLI("Broccoli", "Broccoli Seeds", "2-2-2-2", 8, false, 4, 70, true, 63, "Fall", false),
    CRANBERRIES("Cranberries", "Cranberry Seeds", "1-2-1-1-2", 7, false, 5, 75, true, 38, "Fall", false),
    EGGPLANT("Eggplant", "Eggplant Seeds", "1-1-1-1", 5, false, 5, 60, true, 20, "Fall", false),
    FAIRY_ROSE("Fairy Rose", "Fairy Seeds", "1-4-4-3", 12, true, -1, 290, true, 45, "Fall", false),
    GRAPE("Grape", "Grape Starter", "1-1-2-3-3", 10, false, 3, 80, true, 38, "Fall", false),
    PUMPKIN("Pumpkin", "Pumpkin Seeds", "1-2-3-4-3", 13, true, -1, 320, false, -1, "Fall", true),
    YAM("Yam", "Yam Seeds", "1-3-3-3", 10, true, -1, 160, true, 45, "Fall", false),
    SWEET_GEM_BERRY("Sweet Gem Berry", "Rare Seed", "2-4-6-6-6", 24, true, -1, 3000, false, -1, "Fall", false),
    POWDERMELON("Powdermelon", "Powdermelon Seeds", "1-2-1-2-1", 7, true, -1, 60, true, 63, "Winter", true),
    ANCIENT_FRUIT("Ancient Fruit", "Ancient Seeds", "2-7-7-7-5", 28, false, 7, 550, false, -1, "Spring & summer & Fall ", false);
    private final String name;
    private final String source;
    private final String stages;
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final String season;
    private final boolean canBecomeGiant;
    private boolean isForaging;
    Crop(String name , String source, String stages, int totalHarvestTime, boolean oneTime, int regrowthTime,
         int baseSellPrice, boolean isEdible, int energy, String season, boolean canBecomeGiant) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.season = season;
        this.canBecomeGiant = canBecomeGiant;
    }

    public String getName() { return name; }
    public String getSource() { return source; }
    public String getStages() { return stages; }
    public int getTotalHarvestTime() { return totalHarvestTime; }
    public boolean isOneTime() { return oneTime; }
    public int getRegrowthTime() { return regrowthTime; }
    public int getBaseSellPrice() { return baseSellPrice; }
    public boolean isEdible() { return isEdible; }
    public int getEnergy() { return energy; }
    public String getSeasons() { return season; }
    public boolean canBecomeGiant() { return canBecomeGiant; }
}
