package core.Model;

import core.Model.CropClasses.Tree;
import core.Model.enums.Crops.Fruit;
import core.Model.enums.Seasons;

import java.io.Serializable;
import java.util.List;

public class SerializableTree implements Serializable {
    public char symbol = '7';
    public String name;
    public String source;
    public List<Integer> stages;
    public int totalHarvestTime;
    public Fruit fruit;
    public int price;
    public List<Seasons> seasons;
    public Point location;
    public int daysSinceLastGrowth;
    public int daysSincePlanted;
    public int daysSinceWatered;
    public boolean fertilized;
    public int currentState;
    public boolean isChopped;

    public SerializableTree(Tree tree) {
        this.name = tree.getName();
        this.source = tree.getSource();
        this.stages = tree.getStages();
        this.totalHarvestTime = tree.getTotalHarvestTime();
        this.fruit = tree.getFruit();
        this.price = tree.getPrice();
        this.seasons = tree.getSeasons();
        this.daysSinceLastGrowth = tree.getDaysSinceLastGrowth();
        this.daysSincePlanted = tree.getDaysSincePlanted();
        this.daysSinceWatered = tree.getDaysSinceWatered();
        this.fertilized = tree.isFertilized();
        this.currentState = tree.getCurrentState();
        this.isChopped = tree.isChopped();
        this.location = tree.getTile().coordination;
    }
    public SerializableTree() {

    }
}
