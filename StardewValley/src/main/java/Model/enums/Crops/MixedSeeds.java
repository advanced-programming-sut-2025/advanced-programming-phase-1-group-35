package Model.enums.Crops;

import Model.ItemInterface;
import Model.enums.ItemConstant;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum MixedSeeds implements PlantAble, ItemInterface, ItemConstant {
    SPRING(new CropEnum[]{CropEnum.CAULIFLOWER, CropEnum.PARSNIP, CropEnum.POTATO, CropEnum.BLUE_JAZZ, CropEnum.TULIP}),
    SUMMER(new CropEnum[]{CropEnum.CORN, CropEnum.HOT_PEPPER, CropEnum.RADISH, CropEnum.WHEAT, CropEnum.POPPY, CropEnum.SUNFLOWER, CropEnum.SUMMER_SPANGLE}),
    FALL(new CropEnum[]{CropEnum.ARTICHOKE, CropEnum.CORN, CropEnum.EGGPLANT, CropEnum.PUMPKIN, CropEnum.SUNFLOWER, CropEnum.FAIRY_ROSE}),
    WINTER(new CropEnum[]{CropEnum.POWDERMELON});

    private final CropEnum[] possibleCropEnums;

    MixedSeeds (CropEnum[] possibleCropEnums) {
        this.possibleCropEnums = possibleCropEnums;
    }

    public CropEnum[] getPossibleCrops() {
        return possibleCropEnums;
    }

    @Override
    public ItemInterface getItem() {
        return null;
        //TODO
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }
}
