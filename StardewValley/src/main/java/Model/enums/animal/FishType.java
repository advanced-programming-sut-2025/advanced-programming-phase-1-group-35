package Model.enums.animal;

import Model.App;
import Model.ItemInterface;
import Model.animal.Fish;
import Model.ItemInterface;
import Model.enums.ItemConstant;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public enum FishType implements ItemInterface,ItemConstant {
    SALMON("Salmon", 75, "Fall", "common"),
    SARDINE("Sardine", 40, "Fall","common"),
    SHAD("Shad", 60, "Fall","common"),
    BLUE_DISCUS("Blue Discus", 120, "Fall","common"),
    MIDNIGHT_CARP("Midnight Carp", 150, "Winter","common"),
    SQUID("Squid", 80, "Winter","common"),
    TUNA("Tuna", 100, "Winter","common"),
    PERCH("Perch", 55, "Winter","common"),
    FLOUNDER("Flounder", 100, "Spring","common"),
    LIONFISH("Lionfish", 100, "Spring","common"),
    HERRING("Herring", 30, "Spring","common"),
    GHOST_FISH("Ghostfish", 45, "Spring","common"),
    TILAPIA("Tilapia", 75, "Summer","common"),
    DORADO("Dorado", 100, "Summer","common"),
    SUNFISH("Sunfish", 30, "Summer","common"),
    RAINBOW_TROUT("Rainbow Trout", 65, "Summer","common"),
    //Legendary fish
    LEGEND("Legend", 5000, "Spring","legendary"),
    GLACIER_FISH("Glacierfish", 1000, "Summer","legendary"),
    ANGLER("Angler", 900, "Summer","legendary"),
    CRIMSON_FISH("Crimson Fish", 1500, "Summer","legendary"),
    ;
    private final String name;
    private final int basePrice;
    private final String season;
    private final String rarity;

    FishType(String name, int basePrice, String season, String rarity) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.rarity = rarity;
    }

    @Override
    public int getPrice() {
        return this.basePrice;
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

    @Override
    public ItemInterface getItem() {
        return new Fish(this.name, this.basePrice, this.season, this.rarity);
    }
}
