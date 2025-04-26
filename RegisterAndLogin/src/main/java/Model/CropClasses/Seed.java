package Model.CropClasses;

import Model.enums.Crops.CropEnum;
import Model.enums.Crops.PlantAble;
import Model.enums.Crops.SeedEnum;

public class Seed implements PlantAble {
    private final String seedName;
    private final boolean isMixed;
    private final boolean isForaging;
    private final CropEnum cropEnum;
    private int seedAmount;

    public Seed(SeedEnum seedEnum){
        this.seedName = seedEnum.getSeedName();
        this.isMixed = seedEnum.isMixed();
        this.isForaging = seedEnum.isForaging();
        this.cropEnum = seedEnum.getCrop();
    }

    public Seed getSeed(int seedAmount) {
        this.seedAmount = seedAmount;
        return this;
    }
}
