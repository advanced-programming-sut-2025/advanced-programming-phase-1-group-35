package Model.CropClasses;

import Controller.InGameMenu.FarmingController;
import Model.App;
import Model.ItemInterface;
import Model.Tile;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.MixedSeeds;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.SeedEnum;

public class Seed implements PlantAble, ItemInterface {
    private char symbol = '^';
    private final String name;
    private final boolean isMixed;
    private final CropEnum cropEnum;
    private int seedAmount;
    private Tile tile;
    private int price;

    public Seed(SeedEnum seedEnum, Tile tile) {
        if(seedEnum == null ) seedEnum = SeedEnum.SUNFLOWERSEED;
        this.name = seedEnum.getSeedName();
        this.isMixed = seedEnum.isMixed();
        this.cropEnum = seedEnum.getCrop();
        this.tile = tile;
    }
    FarmingController farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
    public Seed(SeedEnum seedEnum, Tile tile, boolean isMixed) {
        this.name = "mixed seed";
        this.isMixed = true;
        this.cropEnum = farmingController.MixedSeedCrop(App.getCurrentGame().getSeason());
        this.tile = tile;
        this.symbol = '?';
    }

    public Seed(SeedEnum seedEnum) {
        this.name = seedEnum.getSeedName();
        this.isMixed = seedEnum.isMixed();
        this.cropEnum = seedEnum.getCrop();
        this.tile = null;
    }

    public Seed getSeed(int seedAmount) {
        this.seedAmount = seedAmount;
        return this;
    }

    public Tile getTile() {
        return tile;
    }

    public String getName() {
        return name;
    }

    public boolean isMixed() {
        return isMixed;
    }

    public CropEnum getCropEnum() {
        return cropEnum;
    }

    public int getSeedAmount() {
        return seedAmount;
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
