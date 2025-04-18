package Model.enums.Crops;

public enum ForagingSeeds {
    JAZZ("Spring"),
    CARROT("Spring"),
    CAULIFLOWER("Spring"),
    COFFEE("Spring"),
    GARLIC("Spring"),
    BEAN("Spring"),
    KALE("Spring"),
    PARSNIP("Spring"),
    POTATO("Spring"),
    RHUBARB("Spring"),
    STRAWBERRY("Spring"),
    TULIP("Spring"),
    RICE("Spring"),
    BLUEBERRY("Summer"),
    CORN("Summer"),
    HOPS("Summer"),
    PEPPER("Summer"),
    MELON("Summer"),
    POPPY("Summer"),
    RADISH("Summer"),
    RED_CABBAGE("Summer"),
    STARFRUIT("Summer"),
    SPANGLE("Summer"),
    SUMMER_SQUASH("Summer"),
    SUNFLOWER("Summer"),
    TOMATO("Summer"),
    WHEAT("Summer"),
    AMARANTH("Fall"),
    ARTICHOKE("Fall"),
    BEET("Fall"),
    BOK_CHOY("Fall"),
    BROCCOLI("Fall"),
    CRANBERRY("Fall"),
    EGGPLANT("Fall"),
    FAIRY("Fall"),
    GRAPE("Fall"),
    PUMPKIN("Fall"),
    YAM("Fall"),
    RARE("Fall"),
    POWDERMELON("Winter"),
    ANCIENT("Special"),
    MIXED("Special");

    private final String season;

    ForagingSeeds(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }
}
