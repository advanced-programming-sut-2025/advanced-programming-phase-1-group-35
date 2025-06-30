package Model.CropClasses;

import Model.App;
import Model.ItemInterface;
import Model.Tile;
import Model.enums.Crops.Fruit;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.TreeEnum;
import Model.enums.Seasons;

import java.util.List;

public class Tree implements PlantAble,ItemInterface {
    private char symbol = '7';
    private final String name;
    private final String source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final Fruit fruit;
    private int price;
    private final List<Seasons> seasons;
    private Tile tile;
    private int daysSinceLastGrowth;
    private int daysSincePlanted;
    private int daysSinceWatered;
    private boolean fertilized;
    private int currentState;
    private ItemInterface fertilizer;
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

    public int getDaysSinceWatered() {
        return daysSinceWatered;
    }

    public void setDaysSinceWatered(int daysSinceWatered) {
        this.daysSinceWatered = daysSinceWatered;
    }

    public ItemInterface getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(ItemInterface fertilizer) {
        this.fertilizer = fertilizer;
    }

    public int getDaysSinceLastGrowth() {
        return daysSinceLastGrowth;
    }

    public void setDaysSinceLastGrowth(int daysSinceLastGrowth) {
        this.daysSinceLastGrowth = daysSinceLastGrowth;
    }

    public int getDaysSincePlanted() {
        return daysSincePlanted;
    }

    public void setDaysSincePlanted(int daysSincePlanted) {
        this.daysSincePlanted = daysSincePlanted;
    }

    public boolean isFertilized() {
        return fertilized;
    }

    public void setFertilized(boolean fertilized) {
        this.fertilized = fertilized;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
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

    public boolean grow() {
        if (daysSinceWatered <= 1) {
            if (this.currentState != this.stages.size() && this.daysSinceLastGrowth >= this.stages.get(this.currentState)) {
                this.currentState++;
                this.daysSinceLastGrowth = 0;
            } else {
                daysSinceLastGrowth++;
            }
            daysSinceWatered++;
            daysSincePlanted++;
            this.getTile().setWatered(false);
            return true;
        }
        else{
            tile.setPlanted(null);
            tile.getContents().remove(this);
            tile.setContentSymbol('.');
            tile.setSymbol('.');
            tile.changeTileContents(null);
            App.getCurrentGame().getMap().getCrops().remove(this);
            App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(this);
            return false;
        }
    }


    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
