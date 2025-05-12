package Model.enums.Crops;

import Model.enums.ItemConstant;
import Model.enums.Seasons;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static Model.enums.Crops.CropEnum.*;
import static Model.enums.Seasons.*;

public enum ForagingSeeds implements ItemConstant {
    JAZZ(Arrays.asList(Spring), CropEnum.BLUE_JAZZ, SeedEnum.JAZZ),
    CARROT(Arrays.asList(Spring), CropEnum.CARROT, SeedEnum.CARROT),
    CAULIFLOWER(Arrays.asList(Spring), CropEnum.CAULIFLOWER, SeedEnum.CAULIFLOWER),
    COFFEE(Arrays.asList(Spring), CropEnum.COFFEE_BEAN, SeedEnum.COFFEE),
    GARLIC(Arrays.asList(Spring), CropEnum.GARLIC, SeedEnum.GARLIC),
    BEAN(Arrays.asList(Spring), CropEnum.GREEN_BEAN, SeedEnum.BEAN),
    KALE(Arrays.asList(Spring), CropEnum.KALE, SeedEnum.KALE),
    PARSNIP(Arrays.asList(Spring), CropEnum.PARSNIP, SeedEnum.PARSNIP),
    POTATO(Arrays.asList(Spring), CropEnum.POTATO, SeedEnum.POTATO),
    RHUBARB(Arrays.asList(Spring), CropEnum.RHUBARB, SeedEnum.RHUBARB),
    STRAWBERRY(Arrays.asList(Spring), CropEnum.STRAWBERRY, SeedEnum.STRAWBERRY),
    TULIP(Arrays.asList(Spring), CropEnum.TULIP, SeedEnum.TULIP),
    RICE(Arrays.asList(Spring), UNMILLED_RICE, SeedEnum.RICE),
    BLUEBERRY(Arrays.asList(Summer), CropEnum.BLUEBERRY, SeedEnum.BLUEBERRY),
    CORN(Arrays.asList(Summer), CropEnum.CORN, SeedEnum.CORN),
    HOPS(Arrays.asList(Summer), CropEnum.HOPS, SeedEnum.HOPS),
    PEPPER(Arrays.asList(Summer), HOT_PEPPER, SeedEnum.PEPPER),
    MELON(Arrays.asList(Summer), CropEnum.MELON, SeedEnum.MELON),
    POPPY(Arrays.asList(Summer), CropEnum.POPPY, SeedEnum.POPPY),
    RADISH(Arrays.asList(Summer), CropEnum.RADISH, SeedEnum.RADISH),
    RED_CABBAGE(Arrays.asList(Summer), CropEnum.RED_CABBAGE, SeedEnum.RED_CABBAGE),
    STARFRUIT(Arrays.asList(Summer), CropEnum.STARFRUIT, SeedEnum.STARFRUIT),
    SPANGLE(Arrays.asList(Summer), SUMMER_SPANGLE, SeedEnum.SPANGLE),
    SUMMER_SQUASH(Arrays.asList(Summer), CropEnum.SUMMER_SQUASH, SeedEnum.SUMMER_SQUASH),
    SUNFLOWER(Arrays.asList(Summer), CropEnum.SUNFLOWER, SeedEnum.SUNFLOWER),
    TOMATO(Arrays.asList(Summer), CropEnum.TOMATO, SeedEnum.TOMATO),
    WHEAT(Arrays.asList(Summer), CropEnum.WHEAT, SeedEnum.WHEAT),
    AMARANTH(Arrays.asList(Fall), CropEnum.AMARANTH, SeedEnum.AMARANTH),
    ARTICHOKE(Arrays.asList(Fall), CropEnum.ARTICHOKE, SeedEnum.ARTICHOKE),
    BEET(Arrays.asList(Fall), CropEnum.BEET, SeedEnum.BEET),
    BOK_CHO(Arrays.asList(Fall), CropEnum.BOK_CHOY,SeedEnum.BOK_CHO),
    BROCCOLI(Arrays.asList(Fall), CropEnum.BROCCOLI, SeedEnum.BROCCOLI),
    CRANBERRY(Arrays.asList(Fall), CRANBERRIES, SeedEnum.CRANBERRY),
    EGGPLANT(Arrays.asList(Fall), CropEnum.EGGPLANT, SeedEnum.EGGPLANT),
    FAIRY(Arrays.asList(Fall), FAIRY_ROSE,SeedEnum.FAIRY),
    GRAPE(Arrays.asList(Fall), CropEnum.GRAPE, SeedEnum.GRAPE),
    PUMPKIN(Arrays.asList(Fall), CropEnum.PUMPKIN, SeedEnum.PUMPKIN),
    YAM(Arrays.asList(Fall), CropEnum.YAM, SeedEnum.YAM),
    RARE(Arrays.asList(Fall), CropEnum.SWEET_GEM_BERRY, SeedEnum.SWEET_GEM_BERRY),
    POWDERMELON(Arrays.asList(Winter), CropEnum.POWDERMELON, SeedEnum.POWDERMELON),
    ANCIENT(Arrays.asList(Spring, Summer, Fall, Winter), null,SeedEnum.ANCIENT),
    MIXED(Arrays.asList(Spring, Summer, Fall, Winter), null,SeedEnum.MIXED),;

    private final List<Seasons> seasons;
    private final CropEnum cropEnum;
    private final SeedEnum seedEnum;

    ForagingSeeds(List<Seasons> seasons, CropEnum cropEnum, SeedEnum seedEnum) {
        this.seasons = seasons;
        this.cropEnum = cropEnum;
        this.seedEnum = seedEnum;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public static ForagingSeeds findForagingSeeds(SeedEnum seedEnum) {
        for (ForagingSeeds foragingSeeds : ForagingSeeds.values()) {
            if (foragingSeeds.seedEnum.equals(seedEnum)) {
                return foragingSeeds;
            }
        }
        return null;
    }

    public CropEnum getCrop() {
        return cropEnum;
    }

    public static SeedEnum getRandomForagingSeed() {
        List<ForagingSeeds> foragingSeeds = List.of(ForagingSeeds.values()).stream()
                .collect(Collectors.toList());

        if (foragingSeeds.isEmpty()) {
            return null; // or throw an exception if you prefer
        }

        Random random = new Random();
        return foragingSeeds.get(random.nextInt(foragingSeeds.size())).seedEnum;
    }
}
