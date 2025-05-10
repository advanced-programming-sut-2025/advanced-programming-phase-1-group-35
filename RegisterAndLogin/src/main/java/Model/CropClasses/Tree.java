package Model.CropClasses;

import Model.Item;
import Model.ItemInterface;
import Model.Tile;
import Model.enums.Crops.Fruit;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.TreeEnum;
import Model.enums.Seasons;

import java.util.List;

public class Tree implements PlantAble,ItemInterface {
    private final String name;
    private final String source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final Fruit fruit;
    private int price;
    private final List<Seasons> seasons;
    private Tile tile;
    private int daysSinceLastGrowth;
    private int currentState;
    public Tree(TreeEnum Tree){
        this.name = Tree.getName();
        this.source = Tree.getSource();
        this.stages = Tree.getStages();
        this.totalHarvestTime = Tree.getTotalHarvestTime();
        this.fruit = Tree.getFruit();
        this.seasons = Tree.getSeasons();
        this.daysSinceLastGrowth = 0;
        this.currentState = 1;
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

    public List<Seasons> getSeasons() {
        return seasons;
    }
    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void grow(){
        if(this.currentState != this.stages.size() && this.daysSinceLastGrowth >= this.stages.get(this.currentState)) {
            this.currentState++;
            this.daysSinceLastGrowth = 0;
            return;
        }
        this.daysSinceLastGrowth++;
    }



    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
