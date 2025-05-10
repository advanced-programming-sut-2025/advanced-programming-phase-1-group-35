package Model.enums.Crops;

import Model.ItemInterface;

public enum SeedEnum implements ItemInterface {
    JAZZ("Jazz Seeds", true, CropEnum.BLUE_JAZZ),
    CARROT("Carrot Seeds", true, CropEnum.CARROT),
    CAULIFLOWER("Cauliflower Seeds", true, CropEnum.CAULIFLOWER),
    COFFEE("Coffee Bean", false, CropEnum.COFFEE_BEAN),
    GARLIC("Garlic Seeds", true, CropEnum.GARLIC),
    BEAN("Bean Starter", false, CropEnum.GREEN_BEAN),
    KALE("Kale Seeds", true, CropEnum.KALE),
    PARSNIP("Parsnip Seeds", true, CropEnum.PARSNIP),
    POTATO("Potato Seeds", true, CropEnum.POTATO),
    RHUBARB("Rhubarb Seeds", true, CropEnum.RHUBARB),
    STRAWBERRY("Strawberry Seeds", true, CropEnum.STRAWBERRY),
    TULIP("Tulip Bulb", false, CropEnum.TULIP),
    RICE("Rice Shoot", false, CropEnum.UNMILLED_RICE),
    BLUEBERRY("Blueberry Seeds", true, CropEnum.BLUEBERRY),
    CORN("Corn Seeds", true, CropEnum.CORN),
    HOPS("Hops Starter", false, CropEnum.HOPS),
    PEPPER("Pepper Seeds", true, CropEnum.HOT_PEPPER),
    MELON("Melon Seeds", true, CropEnum.MELON),
    POPPY("Poppy Seeds", true, CropEnum.POPPY),
    RADISH("Radish Seeds", true, CropEnum.RADISH),
    RED_CABBAGE("Red Cabbage Seeds", true, CropEnum.RED_CABBAGE),
    STARFRUIT("Starfruit Seeds", true, CropEnum.STARFRUIT),
    SPANGLE("Spangle Seeds", false, CropEnum.SUMMER_SPANGLE),
    SUMMER_SQUASH("Summer Squash Seeds", true, CropEnum.SUMMER_SQUASH),
    SUNFLOWERSEED("Sunflower Seeds", true, CropEnum.SUNFLOWER),
    TOMATO("Tomato Seeds", true, CropEnum.TOMATO),
    WHEAT("Wheat Seeds", true, CropEnum.WHEAT),
    AMARANTH("Amaranth Seeds", true, CropEnum.AMARANTH),
    ARTICHOKE("Artichoke Seeds", true, CropEnum.ARTICHOKE),
    BEET("Beet Seeds", true, CropEnum.BEET),
    BOK_CHO("Bok Choy Seeds", true, CropEnum.BOK_CHOY),
    BROCCOLI("Broccoli Seeds", true, CropEnum.BROCCOLI),
    CRANBERRY("Cranberry Seeds", true, CropEnum.CRANBERRIES),
    EGGPLANT("Eggplant Seeds", true, CropEnum.EGGPLANT),
    FAIRY("Fairy Seeds", false, CropEnum.FAIRY_ROSE),
    GRAPE("Grape Starter", false, CropEnum.GRAPE),
    PUMPKIN("Pumpkin Seeds", true, CropEnum.PUMPKIN),
    YAM("Yam Seeds", true, CropEnum.YAM),
    SWEET_GEM_BERRY("Rare Seed", false, CropEnum.SWEET_GEM_BERRY),
    POWDERMELON("Powdermelon Seeds", false, CropEnum.POWDERMELON),
    ANCIENT("Ancient Seeds", false, null),
    MIXED("Mixed Seeds", false, null);

    private final String seedName;
    private final boolean isMixed;
    private final CropEnum cropEnum;
    private int seedAmount;

    SeedEnum(String seedName, boolean isMixed , CropEnum cropEnum) {
        this.seedName = seedName;
        this.isMixed = isMixed;;
        this.cropEnum = cropEnum;
    }

    public String getSeedName() {
        return seedName;
    }

    public boolean isMixed() {
        return isMixed;
    }


    public CropEnum getCrop() {
        return cropEnum;
    }


    @Override
    public int getPrice() {
        return this.cropEnum.getPrice();
    }

    @Override
    public String getName() {
        return this.seedName;
    }
}
