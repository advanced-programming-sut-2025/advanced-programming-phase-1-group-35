package Model.enums.Crops;

import java.util.List;

public enum TreeEnum {
    APRICOT_TREE("Apricot Tree", "Apricot Sapling", 28, Fruit.APRICOT),
    CHERRY_TREE("Cherry Tree", "Cherry Sapling", 28, Fruit.CHERRY),
    BANANA_TREE("Banana Tree", "Banana Sapling", 28, Fruit.BANANA),
    MANGO_TREE("Mango Tree", "Mango Sapling", 28, Fruit.MANGO),
    ORANGE_TREE("Orange Tree", "Orange Sapling", 28, Fruit.ORANGE),
    PEACH_TREE("Peach Tree", "Peach Sapling", 28, Fruit.PEACH),
    APPLE_TREE("Apple Tree", "Apple Sapling", 28, Fruit.APPLE),
    POMEGRANATE_TREE("Pomegranate Tree", "Pomegranate Sapling", 28, Fruit.POMEGRANATE),
    OAK_TREE("Oak Tree", "Acorns", 28, Fruit.OAK_RESIN),
    MAPLE_TREE("Maple Tree", "Maple Seeds", 28, Fruit.MAPLE_SYRUP),
    PINE_TREE("Pine Tree", "Pine Cones", 28, Fruit.PINE_TAR),
    MAHOGANY_TREE("Mahogany Tree", "Mahogany Seeds", 28, Fruit.SAP),
    MUSHROOM_TREE("Mushroom Tree", "Mushroom Tree Seeds", 28, Fruit.COMMON_MUSHROOM),
    MYSTIC_TREE("Mystic Tree", "Mystic Tree Seeds", 28, Fruit.MYSTIC_SYRUP);

    private final String name;
    private final String source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final Fruit fruit;

    TreeEnum(String name, String source, int totalHarvestTime, Fruit fruit) {
        this.name = name;
        this.source = source;
        this.stages = List.of(7, 7, 7, 7);
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public String getSeason() {
        return fruit.getSeason();
    }

    public boolean isFruitEdible() {
        return fruit.isEdible();
    }

    public int getFruitEnergy() {
        return fruit.getEnergy();
    }

    public int getFruitBaseHealth() {
        return fruit.getBaseHealth();
    }

    public int getFruitellPrice() {
        return fruit.getBaseSellPrice();
    }

    public int getFruitHarvestCycle() {
        return fruit.getHarvestCycle();
    }
}
