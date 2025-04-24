package Model.enums.Crops;

import Model.enums.Seasons;

import java.util.Arrays;
import java.util.List;

import static Model.enums.Crops.Crop.*;
import static Model.enums.Seasons.*;

public enum ForagingSeeds implements PlantAble {
    JAZZ(Arrays.asList(Spring), Crop.BLUE_JAZZ),
    CARROT(Arrays.asList(Spring), Crop.CARROT),
    CAULIFLOWER(Arrays.asList(Spring), Crop.CAULIFLOWER),
    COFFEE(Arrays.asList(Spring), Crop.COFFEE_BEAN),
    GARLIC(Arrays.asList(Spring), Crop.GARLIC),
    BEAN(Arrays.asList(Spring), Crop.GREEN_BEAN),
    KALE(Arrays.asList(Spring), Crop.KALE),
    PARSNIP(Arrays.asList(Spring), Crop.PARSNIP),
    POTATO(Arrays.asList(Spring), Crop.POTATO),
    RHUBARB(Arrays.asList(Spring), Crop.RHUBARB),
    STRAWBERRY(Arrays.asList(Spring), Crop.STRAWBERRY),
    TULIP(Arrays.asList(Spring), Crop.TULIP),
    RICE(Arrays.asList(Spring), UNMILLED_RICE),
    BLUEBERRY(Arrays.asList(Summer), Crop.BLUEBERRY),
    CORN(Arrays.asList(Summer), Crop.CORN),
    HOPS(Arrays.asList(Summer), Crop.HOPS),
    PEPPER(Arrays.asList(Summer), HOT_PEPPER),
    MELON(Arrays.asList(Summer), Crop.MELON),
    POPPY(Arrays.asList(Summer), Crop.POPPY),
    RADISH(Arrays.asList(Summer), Crop.RADISH),
    RED_CABBAGE(Arrays.asList(Summer), Crop.RED_CABBAGE),
    STARFRUIT(Arrays.asList(Summer), Crop.STARFRUIT),
    SPANGLE(Arrays.asList(Summer), SUMMER_SPANGLE),
    SUMMER_SQUASH(Arrays.asList(Summer), Crop.SUMMER_SQUASH),
    SUNFLOWER(Arrays.asList(Summer), Crop.SUNFLOWER),
    TOMATO(Arrays.asList(Summer), Crop.TOMATO),
    WHEAT(Arrays.asList(Summer), Crop.WHEAT),
    AMARANTH(Arrays.asList(Fall), Crop.AMARANTH),
    ARTICHOKE(Arrays.asList(Fall), Crop.ARTICHOKE),
    BEET(Arrays.asList(Fall), Crop.BEET),
    BOK_CHOY(Arrays.asList(Fall), Crop.BOK_CHOY),
    BROCCOLI(Arrays.asList(Fall), Crop.BROCCOLI),
    CRANBERRY(Arrays.asList(Fall), CRANBERRIES),
    EGGPLANT(Arrays.asList(Fall), Crop.EGGPLANT),
    FAIRY(Arrays.asList(Fall), FAIRY_ROSE),
    GRAPE(Arrays.asList(Fall), Crop.GRAPE),
    PUMPKIN(Arrays.asList(Fall), Crop.PUMPKIN),
    YAM(Arrays.asList(Fall), Crop.YAM),
    RARE(Arrays.asList(Fall), Crop.SWEET_GEM_BERRY),
    POWDERMELON(Arrays.asList(Winter), Crop.POWDERMELON),
    ANCIENT(Arrays.asList(Spring, Summer, Fall, Winter), null),
    MIXED(Arrays.asList(Spring, Summer, Fall, Winter), null);

    private final List<Seasons> seasons;
    private final Crop crop;

    ForagingSeeds(List<Seasons> seasons, Crop crop) {
        this.seasons = seasons;
        this.crop = crop;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public Crop getCrop() {
        return crop;
    }

}
