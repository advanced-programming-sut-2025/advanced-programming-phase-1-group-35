package Model.enums.Crops;

public enum MixedSeeds {
    SPRING(new String[]{"Cauliflower", "Parsnip", "Potato", "Blue Jazz", "Tulip"}),
    SUMMER(new String[]{"Corn", "Hot Pepper", "Radish", "Wheat", "Poppy", "Sunflower", "Summer Spangle"}),
    FALL(new String[]{"Artichoke", "Corn", "Eggplant", "Pumpkin", "Sunflower", "Fairy Rose"}),
    WINTER(new String[]{"Powdermelon"});

    private final String[] possibleCrops;

    MixedSeeds (String[] possibleCrops) {
        this.possibleCrops = possibleCrops;
    }

    public String[] getPossibleCrops() {
        return possibleCrops;
    }
}
