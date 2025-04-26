package Model.enums.Crops;

import Model.enums.Seasons;

import java.util.Arrays;
import java.util.List;

import static Model.enums.Crops.CropEnum.*;
import static Model.enums.Seasons.*;

public enum ForagingSeeds implements PlantAble {
    JAZZ(Arrays.asList(Spring), CropEnum.BLUE_JAZZ),
    CARROT(Arrays.asList(Spring), CropEnum.CARROT),
    CAULIFLOWER(Arrays.asList(Spring), CropEnum.CAULIFLOWER),
    COFFEE(Arrays.asList(Spring), CropEnum.COFFEE_BEAN),
    GARLIC(Arrays.asList(Spring), CropEnum.GARLIC),
    BEAN(Arrays.asList(Spring), CropEnum.GREEN_BEAN),
    KALE(Arrays.asList(Spring), CropEnum.KALE),
    PARSNIP(Arrays.asList(Spring), CropEnum.PARSNIP),
    POTATO(Arrays.asList(Spring), CropEnum.POTATO),
    RHUBARB(Arrays.asList(Spring), CropEnum.RHUBARB),
    STRAWBERRY(Arrays.asList(Spring), CropEnum.STRAWBERRY),
    TULIP(Arrays.asList(Spring), CropEnum.TULIP),
    RICE(Arrays.asList(Spring), UNMILLED_RICE),
    BLUEBERRY(Arrays.asList(Summer), CropEnum.BLUEBERRY),
    CORN(Arrays.asList(Summer), CropEnum.CORN),
    HOPS(Arrays.asList(Summer), CropEnum.HOPS),
    PEPPER(Arrays.asList(Summer), HOT_PEPPER),
    MELON(Arrays.asList(Summer), CropEnum.MELON),
    POPPY(Arrays.asList(Summer), CropEnum.POPPY),
    RADISH(Arrays.asList(Summer), CropEnum.RADISH),
    RED_CABBAGE(Arrays.asList(Summer), CropEnum.RED_CABBAGE),
    STARFRUIT(Arrays.asList(Summer), CropEnum.STARFRUIT),
    SPANGLE(Arrays.asList(Summer), SUMMER_SPANGLE),
    SUMMER_SQUASH(Arrays.asList(Summer), CropEnum.SUMMER_SQUASH),
    SUNFLOWER(Arrays.asList(Summer), CropEnum.SUNFLOWER),
    TOMATO(Arrays.asList(Summer), CropEnum.TOMATO),
    WHEAT(Arrays.asList(Summer), CropEnum.WHEAT),
    AMARANTH(Arrays.asList(Fall), CropEnum.AMARANTH),
    ARTICHOKE(Arrays.asList(Fall), CropEnum.ARTICHOKE),
    BEET(Arrays.asList(Fall), CropEnum.BEET),
    BOK_CHOY(Arrays.asList(Fall), CropEnum.BOK_CHOY),
    BROCCOLI(Arrays.asList(Fall), CropEnum.BROCCOLI),
    CRANBERRY(Arrays.asList(Fall), CRANBERRIES),
    EGGPLANT(Arrays.asList(Fall), CropEnum.EGGPLANT),
    FAIRY(Arrays.asList(Fall), FAIRY_ROSE),
    GRAPE(Arrays.asList(Fall), CropEnum.GRAPE),
    PUMPKIN(Arrays.asList(Fall), CropEnum.PUMPKIN),
    YAM(Arrays.asList(Fall), CropEnum.YAM),
    RARE(Arrays.asList(Fall), CropEnum.SWEET_GEM_BERRY),
    POWDERMELON(Arrays.asList(Winter), CropEnum.POWDERMELON),
    ANCIENT(Arrays.asList(Spring, Summer, Fall, Winter), null),
    MIXED(Arrays.asList(Spring, Summer, Fall, Winter), null);

    private final List<Seasons> seasons;
    private final CropEnum cropEnum;

    ForagingSeeds(List<Seasons> seasons, CropEnum cropEnum) {
        this.seasons = seasons;
        this.cropEnum = cropEnum;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public CropEnum getCrop() {
        return cropEnum;
    }

}
