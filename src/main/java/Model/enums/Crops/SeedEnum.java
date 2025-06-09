package Model.enums.Crops;

import Controller.InGameMenu.FarmingController;
import Model.App;
import Model.CropClasses.Seed;
import Model.ItemInterface;
import Model.enums.ItemConstant;

public enum SeedEnum implements ItemInterface, ItemConstant {
    JAZZ("Jazz Seeds", false, CropEnum.BLUE_JAZZ),
    CARROT("Carrot Seeds", false, CropEnum.CARROT),
    CAULIFLOWER("Cauliflower Seeds", false, CropEnum.CAULIFLOWER),
    COFFEE("Coffee Bean", false, CropEnum.COFFEE_BEAN),
    GARLIC("Garlic Seeds", false, CropEnum.GARLIC),
    BEAN("Bean Starter", false, CropEnum.GREEN_BEAN),
    KALE("Kale Seeds", false, CropEnum.KALE),
    PARSNIP("Parsnip Seeds", false, CropEnum.PARSNIP),
    POTATO("Potato Seeds", false, CropEnum.POTATO),
    RHUBARB("Rhubarb Seeds", false, CropEnum.RHUBARB),
    STRAWBERRY("Strawberry Seeds", false, CropEnum.STRAWBERRY),
    TULIP("Tulip Bulb", false, CropEnum.TULIP),
    RICE("Rice Shoot", false, CropEnum.UNMILLED_RICE),
    BLUEBERRY("Blueberry Seeds", false, CropEnum.BLUEBERRY),
    CORN("Corn Seeds", false, CropEnum.CORN),
    HOPS("Hops Starter", false, CropEnum.HOPS),
    PEPPER("Pepper Seeds", false, CropEnum.HOT_PEPPER),
    MELON("Melon Seeds", false, CropEnum.MELON),
    POPPY("Poppy Seeds", false, CropEnum.POPPY),
    RADISH("Radish Seeds", false, CropEnum.RADISH),
    RED_CABBAGE("Red Cabbage Seeds", false, CropEnum.RED_CABBAGE),
    STARFRUIT("Starfruit Seeds", false, CropEnum.STARFRUIT),
    SPANGLE("Spangle Seeds", false, CropEnum.SUMMER_SPANGLE),
    SUMMER_SQUASH("Summer Squash Seeds", false, CropEnum.SUMMER_SQUASH),
    SUNFLOWERSEED("Sunflower Seeds", false, CropEnum.SUNFLOWER),
    TOMATO("Tomato Seeds", false, CropEnum.TOMATO),
    WHEAT("Wheat Seeds", false, CropEnum.WHEAT),
    AMARANTH("Amaranth Seeds", false, CropEnum.AMARANTH),
    ARTICHOKE("Artichoke Seeds", false, CropEnum.ARTICHOKE),
    BEET("Beet Seeds", false, CropEnum.BEET),
    BOK_CHO("Bok Choy Seeds", false, CropEnum.BOK_CHOY),
    BROCCOLI("Broccoli Seeds", false, CropEnum.BROCCOLI),
    CRANBERRY("Cranberry Seeds", false, CropEnum.CRANBERRIES),
    EGGPLANT("Eggplant Seeds", false, CropEnum.EGGPLANT),
    FAIRY("Fairy Seeds", false, CropEnum.FAIRY_ROSE),
    GRAPE("Grape Starter", false, CropEnum.GRAPE),
    PUMPKIN("Pumpkin Seeds", false, CropEnum.PUMPKIN),
    YAM("Yam Seeds", false, CropEnum.YAM),
    SWEET_GEM_BERRY("Rare Seed", false, CropEnum.SWEET_GEM_BERRY),
    POWDERMELON("Powdermelon Seeds", false, CropEnum.POWDERMELON),
    ANCIENT("Ancient Seeds", false, null),
    MIXED("Mixed Seeds", true, null);

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
        if(this == MIXED) return FarmingController.MixedSeedCrop(App.getCurrentGame().getGameCalender().getSeason());
        if(this.cropEnum != null) return cropEnum;
        for(CropEnum cropEnum1 : CropEnum.values()) {
            if(cropEnum1.source == null) continue;
            else if(cropEnum1.source.equals(this)) {
                return cropEnum1;
            }
        }
        return null;
    }


    @Override
    public ItemInterface getItem() {
        return new Seed(this);
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
