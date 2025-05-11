package Model.enums.animal;

import Model.App;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public enum FishType {
    SALMON("Salmon", 75, "Fall"),
    SARDINE("Sardine", 40, "Fall"),
    SHAD("Shad", 60, "Fall"),
    BLUE_DISCUS("Blue Discus", 120, "Fall"),
    MIDNIGHT_CARP("Midnight Carp", 150, "Winter"),
    SQUID("Squid", 80, "Winter"),
    TUNA("Tuna", 100, "Winter"),
    PERCH("Perch", 55, "Winter"),
    FLOUNDER("Flounder", 100, "Spring"),
    LIONFISH("Lionfish", 100, "Spring"),
    HERRING("Herring", 30, "Spring"),
    GHOSTFISH("Ghostfish", 45, "Spring"),
    TILAPIA("Tilapia", 75, "Summer"),
    DORADO("Dorado", 100, "Summer"),
    SUNFISH("Sunfish", 30, "Summer"),
    RAINBOW_TROUT("Rainbow Trout", 65, "Summer");

    private final String name;
    private final int basePrice;
    private final String season;

    FishType(String name, int basePrice, String season) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public String getSeason() {
        return season;
    }

    public static ArrayList<FishType> getFishListOfThisSeason() {
        ArrayList<FishType> fishList = new ArrayList<FishType>();
        for (FishType fish : FishType.values()) {
            if (fish.getSeason().equals(App.getCurrentGame().getSeason().toString())) {
                fishList.add(fish);
            }
        }
        return fishList;
    }

    public static FishType getRandomFish() {
        ArrayList<FishType> fishList = getFishListOfThisSeason();
        int randomIndex = ThreadLocalRandom.current().nextInt(fishList.size());
        return fishList.get(randomIndex);
    }

    @Override
    public String toString() {
        return name + " (" + basePrice + "g, Season: " + season + ")";
    }
}
