package Model.enums.Crops;

public enum SeedEnum{
    JAZZ("Jazz Seeds", true, false, CropEnum.BLUE_JAZZ),
    CARROT("Carrot Seeds", true, false, CropEnum.CARROT),
    CAULIFLOWER("Cauliflower Seeds", true, false, CropEnum.CAULIFLOWER),
    COFFEE("Coffee Bean", false, false, CropEnum.COFFEE_BEAN),
    GARLIC("Garlic Seeds", true, false, CropEnum.GARLIC),
    BEAN("Bean Starter", false, false, CropEnum.GREEN_BEAN),
    KALE("Kale Seeds", true, false, CropEnum.KALE),
    PARSNIP("Parsnip Seeds", true, false, CropEnum.PARSNIP),
    POTATO("Potato Seeds", true, false, CropEnum.POTATO),
    RHUBARB("Rhubarb Seeds", true, false, CropEnum.RHUBARB),
    STRAWBERRY("Strawberry Seeds", true, false, CropEnum.STRAWBERRY),
    TULIP("Tulip Bulb", false, false, CropEnum.TULIP),
    RICE("Rice Shoot", false, false, CropEnum.UNMILLED_RICE),
    BLUEBERRY("Blueberry Seeds", true, false, CropEnum.BLUEBERRY),
    CORN("Corn Seeds", true, false, CropEnum.CORN),
    HOPS("Hops Starter", false, false, CropEnum.HOPS),
    PEPPER("Pepper Seeds", true, false, CropEnum.HOT_PEPPER),
    MELON("Melon Seeds", true, false, CropEnum.MELON),
    POPPY("Poppy Seeds", true, false, CropEnum.POPPY),
    RADISH("Radish Seeds", true, false, CropEnum.RADISH),
    RED_CABBAGE("Red Cabbage Seeds", true, false, CropEnum.RED_CABBAGE),
    STARFRUIT("Starfruit Seeds", true, false, CropEnum.STARFRUIT),
    SPANGLE("Spangle Seeds", false, false, CropEnum.SUMMER_SPANGLE),
    SUMMER_SQUASH("Summer Squash Seeds", true, false, CropEnum.SUMMER_SQUASH),
    SUNFLOWER("Sunflower Seeds", true, false, CropEnum.SUNFLOWER),
    TOMATO("Tomato Seeds", true, false, CropEnum.TOMATO),
    WHEAT("Wheat Seeds", true, false, CropEnum.WHEAT),
    AMARANTH("Amaranth Seeds", true, false, CropEnum.AMARANTH),
    ARTICHOKE("Artichoke Seeds", true, false, CropEnum.ARTICHOKE),
    BEET("Beet Seeds", true, false, CropEnum.BEET),
    BOK_CHO("Bok Choy Seeds", true, false, CropEnum.BOK_CHOY),
    BROCCOLI("Broccoli Seeds", true, false, CropEnum.BROCCOLI),
    CRANBERRY("Cranberry Seeds", true, false, CropEnum.CRANBERRIES),
    EGGPLANT("Eggplant Seeds", true, false, CropEnum.EGGPLANT),
    FAIRY("Fairy Seeds", false, false, CropEnum.FAIRY_ROSE),
    GRAPE("Grape Starter", false, false, CropEnum.GRAPE),
    PUMPKIN("Pumpkin Seeds", true, false, CropEnum.PUMPKIN),
    YAM("Yam Seeds", true, false, CropEnum.YAM),
    SWEET_GEM_BERRY("Rare Seed", false, false, CropEnum.SWEET_GEM_BERRY),
    POWDERMELON("Powdermelon Seeds", false, false, CropEnum.POWDERMELON),
    ANCIENT("Ancient Seeds", false, false, null),
    MIXED("Mixed Seeds", false, true, null);

    private final String seedName;
    private final boolean isMixed;
    private final boolean isForaging;
    private final CropEnum cropEnum;
    private int seedAmount;

    SeedEnum(String seedName, boolean isMixed, boolean isForaging, CropEnum cropEnum) {
        this.seedName = seedName;
        this.isMixed = isMixed;
        this.isForaging = isForaging;
        this.cropEnum = cropEnum;
    }

    public String getSeedName() {
        return seedName;
    }

    public boolean isMixed() {
        return isMixed;
    }

    public boolean isForaging() {
        return isForaging;
    }

    public CropEnum getCrop() {
        return cropEnum;
    }


}
