package Model.enums.Crops;

import Model.enums.Seasons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum TreeEnum {
    APRICOT_TREE("Apricot Tree", "Apricot Sapling", 28, Fruit.APRICOT,true,List.of(Seasons.Spring)),
    CHERRY_TREE("Cherry Tree", "Cherry Sapling", 28, Fruit.CHERRY,false,List.of(Seasons.Spring)),
    BANANA_TREE("Banana Tree", "Banana Sapling", 28, Fruit.BANANA,false,List.of(Seasons.Summer)),
    MANGO_TREE("Mango Tree", "Mango Sapling", 28, Fruit.MANGO,false,List.of(Seasons.Summer)),
    ORANGE_TREE("Orange Tree", "Orange Sapling", 28, Fruit.ORANGE,false,List.of(Seasons.Summer)),
    PEACH_TREE("Peach Tree", "Peach Sapling", 28, Fruit.PEACH,false,List.of(Seasons.Summer)),
    APPLE_TREE("Apple Tree", "Apple Sapling", 28, Fruit.APPLE,false,List.of(Seasons.Fall)),
    POMEGRANATE_TREE("Pomegranate Tree", "Pomegranate Sapling", 28, Fruit.POMEGRANATE, false,List.of(Seasons.Fall)),
    OAK_TREE("Oak Tree", "Acorns", 28, Fruit.OAK_RESIN,false,List.of(Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter)),
    MAPLE_TREE("Maple Tree", "Maple Seeds", 28, Fruit.MAPLE_SYRUP,true,List.of(Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter)),
    PINE_TREE("Pine Tree", "Pine Cones", 28, Fruit.PINE_TAR,false,List.of(Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter)),
    MAHOGANY_TREE("Mahogany Tree", "Mahogany Seeds", 28, Fruit.SAP,true,List.of(Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter)),
    MUSHROOM_TREE("Mushroom Tree", "Mushroom Tree Seeds", 28, Fruit.COMMON_MUSHROOM,true,List.of(Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter)),
    MYSTIC_TREE("Mystic Tree", "Mystic Tree Seeds", 28, Fruit.MYSTIC_SYRUP,false,List.of(Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter)),;

    private final String name;
    private final String source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final Fruit fruit;
    private boolean isForaging;
    private List<Seasons> seasons;

    TreeEnum(String name, String source, int totalHarvestTime, Fruit fruit, boolean isForaging, List<Seasons> seasons) {
        this.name = name;
        this.source = source;
        this.stages = List.of(7, 7, 7, 7);
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.isForaging = isForaging;
        this.seasons = seasons;
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

    public boolean isForaging() {
        return isForaging;
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

    public int getFruitSellPrice() {
        return fruit.getPrice();
    }

    public int getFruitHarvestCycle() {
        return fruit.getHarvestCycle();
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public static TreeEnum getRandomForagingTree() {
        List<TreeEnum> foragingTrees = List.of(TreeEnum.values()).stream()
                .filter(TreeEnum::isForaging)
                .collect(Collectors.toList());

        if (foragingTrees.isEmpty()) {
            return null; // or throw an exception if you prefer
        }

        Random random = new Random();
        return foragingTrees.get(random.nextInt(foragingTrees.size()));
    }

}
