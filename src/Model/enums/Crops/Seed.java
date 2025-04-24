package Model.enums.Crops;

public enum Seed {
    JAZZ("Jazz Seeds", true, false, Crop.BLUE_JAZZ),
    CARROT("Carrot Seeds", true, false, Crop.CARROT),
    CAULIFLOWER("Cauliflower Seeds", true, false, Crop.CAULIFLOWER),
    COFFEE("Coffee Bean", false, false, Crop.COFFEE_BEAN),
    GARLIC("Garlic Seeds", true, false, Crop.GARLIC),
    BEAN("Bean Starter", false, false, Crop.GREEN_BEAN),
    KALE("Kale Seeds", true, false, Crop.KALE),
    PARSNIP("Parsnip Seeds", true, false, Crop.PARSNIP),
    POTATO("Potato Seeds", true, false, Crop.POTATO),
    RHUBARB("Rhubarb Seeds", true, false, Crop.RHUBARB),
    STRAWBERRY("Strawberry Seeds", true, false, Crop.STRAWBERRY),
    TULIP("Tulip Bulb", false, false, Crop.TULIP),
    RICE("Rice Shoot", false, false, Crop.RICE),
    BLUEBERRY("Blueberry Seeds", true, false, Crop.BLUEBERRY),
    CORN("Corn Seeds", true, false, Crop.CORN),
    HOPS("Hops Starter", false, false, Crop.HOPS),
    PEPPER("Pepper Seeds", true, false, Crop.PEPPER),
    MELON("Melon Seeds", true, false, Crop.MELON),
    POPPY("Poppy Seeds", true, false, Crop.POPPY),
    RADISH("Radish Seeds", true, false, Crop.RADISH),
    RED_CABBAGE("Red Cabbage Seeds", true, false, Crop.RED_CABBAGE),
    STARFRUIT("Starfruit Seeds", true, false, Crop.STARFRUIT),
    SPANGLE("Spangle Seeds", false, false, Crop.SPANGLE),
    SUMMER_SQUASH("Summer Squash Seeds", true, false, Crop.SUMMER_SQUASH),
    SUNFLOWER("Sunflower Seeds", true, false, Crop.SUNFLOWER),
    TOMATO("Tomato Seeds", true, false, Crop.TOMATO),
    WHEAT("Wheat Seeds", true, false, Crop.WHEAT),
    AMARANTH("Amaranth Seeds", true, false, Crop.AMARANTH),
    ARTICHOKE("Artichoke Seeds", true, false, Crop.ARTICHOKE),
    BEET("Beet Seeds", true, false, Crop.BEET),
    BOK_CHO("Bok Choy Seeds", true, false, Crop.BOK_CHO),
    BROCCOLI("Broccoli Seeds", true, false, Crop.BROCCOLI),
    CRANBERRY("Cranberry Seeds", true, false, Crop.CRANBERRY),
    EGGPLANT("Eggplant Seeds", true, false, Crop.EGGPLANT),
    FAIRY("Fairy Seeds", false, false, Crop.FAIRY),
    GRAPE("Grape Starter", false, false, Crop.GRAPE),
    PUMPKIN("Pumpkin Seeds", true, false, Crop.PUMPKIN),
    YAM("Yam Seeds", true, false, Crop.YAM),
    SWEET_GEM_BERRY("Rare Seed", false, false, Crop.SWEET_GEM_BERRY),
    POWDERMELON("Powdermelon Seeds", false, false, Crop.POWDERMELON),
    ANCIENT("Ancient Seeds", false, false, Crop.ANCIENT),
    MIXED("Mixed Seeds", false, true, Crop.MIXED);

    private final String seedName;
    private final boolean isMixed;
    private final boolean isForaging;
    private final Crop crop;

    Seed(String seedName, boolean isMixed, boolean isForaging, Crop crop) {
        this.seedName = seedName;
        this.isMixed = isMixed;
        this.isForaging = isForaging;
        this.crop = crop;
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

    public Crop getCrop() {
        return crop;
    }

    public enum Crop {
        BLUE_JAZZ, CARROT, CAULIFLOWER, COFFEE_BEAN, GARLIC, GREEN_BEAN, KALE, PARSNIP, POTATO, RHUBARB,
        STRAWBERRY, TULIP, RICE, BLUEBERRY, CORN, HOPS, PEPPER, MELON, POPPY, RADISH, RED_CABBAGE, STARFRUIT,
        SPANGLE, SUMMER_SQUASH, SUNFLOWER, TOMATO, WHEAT, AMARANTH, ARTICHOKE, BEET, BOK_CHO, BROCCOLI, CRANBERRY,
        EGGPLANT, FAIRY, GRAPE, PUMPKIN, YAM, SWEET_GEM_BERRY, POWDERMELON, ANCIENT, MIXED
    }
}
