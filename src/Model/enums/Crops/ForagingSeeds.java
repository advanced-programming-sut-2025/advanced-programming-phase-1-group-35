package Model.enums.Crops;

import Model.enums.Seasons;

import java.util.Arrays;
import java.util.List;

import static Model.enums.Seasons.*;

public enum ForagingSeeds implements PlantAble {
    JAZZ(Arrays.asList(Spring)),
    CARROT(Arrays.asList(Spring)),
    CAULIFLOWER(Arrays.asList(Spring)),
    COFFEE(Arrays.asList(Spring)),
    GARLIC(Arrays.asList(Spring)),
    BEAN(Arrays.asList(Spring)),
    KALE(Arrays.asList(Spring)),
    PARSNIP(Arrays.asList(Spring)),
    POTATO(Arrays.asList(Spring)),
    RHUBARB(Arrays.asList(Spring)),
    STRAWBERRY(Arrays.asList(Spring)),
    TULIP(Arrays.asList(Spring)),
    RICE(Arrays.asList(Spring)),
    BLUEBERRY(Arrays.asList(Summer)),
    CORN(Arrays.asList(Summer)),
    HOPS(Arrays.asList(Summer)),
    PEPPER(Arrays.asList(Summer)),
    MELON(Arrays.asList(Summer)),
    POPPY(Arrays.asList(Summer)),
    RADISH(Arrays.asList(Summer)),
    RED_CABBAGE(Arrays.asList(Summer)),
    STARFRUIT(Arrays.asList(Summer)),
    SPANGLE(Arrays.asList(Summer)),
    SUMMER_SQUASH(Arrays.asList(Summer)),
    SUNFLOWER(Arrays.asList(Summer)),
    TOMATO(Arrays.asList(Summer)),
    WHEAT(Arrays.asList(Summer)),
    AMARANTH(Arrays.asList(Fall)),
    ARTICHOKE(Arrays.asList(Fall)),
    BEET(Arrays.asList(Fall)),
    BOK_CHOY(Arrays.asList(Fall)),
    BROCCOLI(Arrays.asList(Fall)),
    CRANBERRY(Arrays.asList(Fall)),
    EGGPLANT(Arrays.asList(Fall)),
    FAIRY(Arrays.asList(Fall)),
    GRAPE(Arrays.asList(Fall)),
    PUMPKIN(Arrays.asList(Fall)),
    YAM(Arrays.asList(Fall)),
    RARE(Arrays.asList(Fall)),
    POWDERMELON(Arrays.asList(Winter)),
    ANCIENT(Arrays.asList(Spring, Summer, Fall, Winter)),
    MIXED(Arrays.asList(Spring, Summer, Fall, Winter));

    private final List<Seasons> seasons;
    private final Crop crop;
    ForagingSeeds(List<Seasons> seasons, Crop crop) {
        this.seasons = seasons;
        this.crop = crop;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

}
