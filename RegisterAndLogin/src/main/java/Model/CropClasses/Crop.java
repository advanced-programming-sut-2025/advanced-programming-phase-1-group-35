package Model.CropClasses;

import Model.Item;
import Model.ItemInterface;
import Model.Tile;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.SeedEnum;
import Model.enums.Seasons;

import java.util.List;

public class Crop implements PlantAble, ItemInterface {
    private char symbol = '&';
    private final String name;
    private final SeedEnum source;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final List<Seasons> season;
    private final boolean canBecomeGiant;
    private boolean isForaging;
    private int currentState;
    private int daysSinceLastGrowth;
    private Tile cropTile;
    private boolean isGiant;
    private int daysSincePlanted = 0;
    private int price ;
    private int daysSinceWatered;

    public void updateDaysSincePlanted() {
        daysSincePlanted++;
    }
    public int getDaysSincePlanted() {
        return daysSincePlanted;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public Crop(CropEnum cropEnum) {
        this.name = cropEnum.getName();
        this.source = cropEnum.getSource();
        this.stages = cropEnum.getStages();
        this.totalHarvestTime = cropEnum.getTotalHarvestTime();
        this.oneTime = cropEnum.isOneTime();
        this.regrowthTime = cropEnum.getRegrowthTime();
        this.baseSellPrice = cropEnum.getBaseSellPrice();
        this.isEdible = cropEnum.isEdible();
        this.energy = cropEnum.getEnergy();
        this.season = cropEnum.getSeasons();
        this.canBecomeGiant = cropEnum.canBecomeGiant();
        this.daysSinceLastGrowth = 0;
        this.currentState = 1;
        this.daysSincePlanted = 0;
        this.daysSinceWatered = 0;
    }

    public void EmptyTile() {
        this.cropTile = null;
        this.cropTile.changeTileContents(null);
    }
    public Tile getcropTile() {
        try{
            return this.cropTile;
        }catch (NullPointerException e){
            System.out.println("Crop Tile Not Set!");
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public SeedEnum getSource() {
        return source;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
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

    public List<Seasons> getSeason() {
        return season;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public boolean isForaging() {
        return isForaging;
    }

    public void setForaging(boolean foraging) {
        isForaging = foraging;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void setDaysSinceLastGrowth(int daysSinceLastGrowth) {
        this.daysSinceLastGrowth = daysSinceLastGrowth;
    }

    public int getDaysSinceLastGrowth() {
        return daysSinceLastGrowth;
    }

    public void addDaysSinceLastGrowth() {
        this.daysSinceLastGrowth ++;
    }

    public Tile getCropTile() {
        return cropTile;
    }

    public void setCropTile(Tile cropTile) {
        this.cropTile = cropTile;
    }
    //TODO:use this at the end of the day(if current tile is fertilized do it twice)
    //actually this is pretty complicated because i need to keep in mind how many days have passed and then use this;
    //my current idea is this


    public void grow(){
        if(this.currentState != this.stages.size() && this.daysSinceLastGrowth >= this.stages.get(this.currentState)) {
            this.currentState++;
            this.daysSinceLastGrowth = 0;
        }
        else{
            daysSinceLastGrowth++;
            daysSinceWatered++;
        }

    }

    public void addDaysSincePlanted() {
        this.daysSincePlanted++;
    }

    public void setDaysSincePlanted(int daysSincePlanted) {
        this.daysSincePlanted = daysSincePlanted;
    }

    public int getDaysSinceWatered() {
        return daysSinceWatered;
    }

    public void setDaysSinceWatered(int daysSinceWatered) {
        this.daysSinceWatered = daysSinceWatered;
    }

    public Seed HarvestAndDropSeed() {
        Seed seed = new Seed(this.source,null);
        //TODO:how many seeds should i return?
        return seed.getSeed(1);
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
