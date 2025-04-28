package Model.CropClasses;

import Model.enums.Crops.Fruit;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.TreeEnum;

import java.util.List;

public class Tree implements PlantAble {
    private final String name;
    private final String source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final Fruit fruit;

    public Tree(TreeEnum Tree){
        this.name = Tree.getName();
        this.source = Tree.getSource();
        this.stages = Tree.getStages();
        this.totalHarvestTime = Tree.getTotalHarvestTime();
        this.fruit = Tree.getFruit();
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
}
