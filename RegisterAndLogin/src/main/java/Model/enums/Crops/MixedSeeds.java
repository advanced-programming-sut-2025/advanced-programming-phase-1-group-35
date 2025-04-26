package Model.enums.Crops;

public enum MixedSeeds  implements PlantAble {
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
}
