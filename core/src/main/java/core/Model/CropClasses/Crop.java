package core.Model.CropClasses;

import core.Controller.Controller;
import core.GameUpdater;
import core.Model.App;
import core.Model.ItemInterface;
import core.Model.Tile;
import core.Model.enums.Crops.CropEnum;
import core.Model.enums.Crops.PlantAble;
import core.Model.enums.Crops.SeedEnum;
import core.Model.enums.Seasons;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Crop implements PlantAble, ItemInterface {
    private char symbol = '&';
    private final CropEnum cropEnum;
    private final String name;
    private final SeedEnum source;
    private List<Integer> stages = new ArrayList<>();
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private List<Seasons> season = new ArrayList<>();
    private final boolean canBecomeGiant;
    private boolean isForaging;
    private int currentState;
    private int daysSinceLastGrowth;
    private Tile cropTile;
    private boolean isGiant;
    private int daysSincePlanted = 0;
    private int price ;
    private int daysSinceWatered;
    private boolean isFertilized;
    private ItemInterface fertilizer;
    private Texture texture;

    public ItemInterface getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(ItemInterface fertilizer) {
        this.fertilizer = fertilizer;
    }

    public boolean isFertilized() {
        return isFertilized;
    }
    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }
    public void updateDaysSincePlanted() {
        daysSincePlanted++;
    }
    public int getDaysSincePlanted() {
        return daysSincePlanted;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public Crop(CropEnum cropEnum, Tile tile) {
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
        this.isForaging = cropEnum.isForaging();
        this.daysSinceLastGrowth = 0;
        this.currentState = 1;
        this.daysSincePlanted = 0;
        this.daysSinceWatered = 0;
        this.cropEnum = cropEnum;
        this.cropTile = tile;
        this.texture = new Texture(cropEnum.state1Path());
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

    public CropEnum getCropEnum() {
        return cropEnum;
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

    public List<Seasons> getSeasons() {return season;}

    public boolean canBecomeGiant() {
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

    public String getIconPath(){
        return "crops/" + getName() + ".png";
    }

    public String getStatePath(){
        return "crops/" + Controller.formatUpperSnakeCase(getName())+ "_Stage_" + currentState + ".png";
    }




    //TODO:use this at the end of the day(if current tile is fertilized do it twice)
    //actually this is pretty complicated because i need to keep in mind how many days have passed and then use this;
    //my current idea is this
    public boolean grow() {
        boolean result;
        if (daysSinceWatered <= 1) {
            if (this.currentState != this.stages.size() && this.daysSinceLastGrowth >= this.stages.get(this.currentState)) {
                this.currentState++;
                this.daysSinceLastGrowth = 0;
                this.texture = new Texture(getStatePath());
            } else {
                daysSinceLastGrowth++;
            }
            daysSinceWatered++;
            daysSincePlanted++;
            this.getCropTile().setWatered(false);
            result = true;
        }
        else{
            cropTile.setPlanted(null);
            cropTile.getContents().remove(this);
            cropTile.setContentSymbol('.');
            cropTile.setSymbol('.');
            cropTile.changeTileContents(null);
            App.getCurrentGame().getMap().getCrops().remove(this);
            App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(this);
            result =  false;
        }
        GameUpdater.sendTileUpdate(cropTile);
        return result;
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
        Seed seed = new Seed(this.source, null);
        if (!this.isOneTime()) {
            return seed.getSeedInAmount(1);
        }
        return null;
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
