package Model.CropClasses;

import Model.ItemInterface;
import Model.Tile;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.SeedEnum;

public class Seed implements PlantAble, ItemInterface {
    private final String name;
    private final boolean isMixed;
    private final CropEnum cropEnum;
    private int seedAmount;
    private Tile tile;
    private int price;

    public Seed(SeedEnum seedEnum, Tile tile) {
        this.name = seedEnum.getSeedName();
        this.isMixed = seedEnum.isMixed();
        this.cropEnum = seedEnum.getCrop();
        this.tile = tile;
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
}
