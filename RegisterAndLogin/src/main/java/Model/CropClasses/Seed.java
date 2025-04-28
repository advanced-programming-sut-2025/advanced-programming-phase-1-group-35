package Model.CropClasses;

import Model.Tile;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.SeedEnum;

public class Seed implements PlantAble {
    private final String seedName;
    private final boolean isMixed;
    private final CropEnum cropEnum;
    private int seedAmount;
    private Tile tile;

    public Seed(SeedEnum seedEnum, Tile tile) {
        this.seedName = seedEnum.getSeedName();
        this.isMixed = seedEnum.isMixed();
        this.cropEnum = seedEnum.getCrop();
        this.tile = tile;
    }

    public Seed getSeed(int seedAmount) {
        this.seedAmount = seedAmount;
        return this;
    }

    public Tile getTile() {
        return tile;
    }

    public String getSeedName() {
        return seedName;
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
}
