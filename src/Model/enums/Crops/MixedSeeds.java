package Model.enums.Crops;

public enum MixedSeeds  implements PlantAble {
    SPRING(new Crop[]{Crop.CAULIFLOWER, Crop.PARSNIP, Crop.POTATO, Crop.BLUE_JAZZ, Crop.TULIP}),
    SUMMER(new Crop[]{Crop.CORN, Crop.HOT_PEPPER, Crop.RADISH, Crop.WHEAT, Crop.POPPY, Crop.SUNFLOWER, Crop.SUMMER_SPANGLE}),
    FALL(new Crop[]{Crop.ARTICHOKE, Crop.CORN, Crop.EGGPLANT, Crop.PUMPKIN, Crop.SUNFLOWER, Crop.FAIRY_ROSE}),
    WINTER(new Crop[]{Crop.POWDERMELON});

    private final Crop[] possibleCrops;

    MixedSeeds (Crop[] possibleCrops) {
        this.possibleCrops = possibleCrops;
    }

    public Crop[] getPossibleCrops() {
        return possibleCrops;
    }
}
