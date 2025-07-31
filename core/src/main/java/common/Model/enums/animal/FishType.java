package common.Model.enums.animal;

import common.Model.App;
import common.Model.ItemInterface;
import common.Model.animal.Fish;
import common.Model.enums.ItemConstant;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public enum FishType implements ItemInterface, ItemConstant {
    SALMON("Salmon", 75, "Fall", "common", "assets/fish/Salmon.png"),
    SARDINE("Sardine", 40, "Fall", "common", "assets/fish/Sardine.png"),
    SHAD("Shad", 60, "Fall", "common", "assets/fish/Shad.png"),
    BLUE_DISCUS("Blue Discus", 120, "Fall", "common", "assets/fish/Blue_Discus.png"),
    MIDNIGHT_CARP("Midnight Carp", 150, "Winter", "common", "assets/fish/Midnight_Carp.png"),
    SQUID("Squid", 80, "Winter", "common", "assets/fish/Squid.png"),
    TUNA("Tuna", 100, "Winter", "common", "assets/fish/Tuna.png"),
    PERCH("Perch", 55, "Winter", "common", "assets/fish/Perch.png"),
    FLOUNDER("Flounder", 100, "Spring", "common", "assets/fish/Flounder.png"),
    LIONFISH("Lionfish", 100, "Spring", "common", "assets/fish/Lionfish.png"),
    HERRING("Herring", 30, "Spring", "common", "assets/fish/Herring.png"),
    GHOST_FISH("Ghostfish", 45, "Spring", "common", "assets/fish/Ghostfish.png"),
    TILAPIA("Tilapia", 75, "Summer", "common", "assets/fish/Tilapia.png"),
    DORADO("Dorado", 100, "Summer", "common", "assets/fish/Dorado.png"),
    SUNFISH("Sunfish", 30, "Summer", "common", "assets/fish/Sunfish.png"),
    RAINBOW_TROUT("Rainbow Trout", 65, "Summer", "common", "assets/fish/Rainbow_trout.png");
    //Legendary fish
//    LEGEND("Legend", 5000, "Spring", "legendary"),
//    GLACIER_FISH("Glacierfish", 1000, "Summer", "legendary"),
//    ANGLER("Angler", 900, "Summer", "legendary"),
//    CRIMSON_FISH("Crimson Fish", 1500, "Summer", "legendary");
    private final String name;
    private final int basePrice;
    private final String season;
    private final String rarity;
    public final String texturePath;

    FishType(String name, int basePrice, String season, String rarity, String texturePath) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.rarity = rarity;
        this.texturePath = texturePath;
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
            if (fish.getSeason().equals(App.getCurrentGame().getGameCalender().getSeason().toString())) {
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

    public Texture getTexture() {
        return new Texture(texturePath);
    }
}
