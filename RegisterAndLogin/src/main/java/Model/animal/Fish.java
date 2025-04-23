package Model.animal;

import Model.Item;

public class Fish extends Item {
    private String name;
    private int basePrice;
    private String season;
    private String rarity;

    public Fish(String name, int basePrice, String season, String rarity) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.rarity = rarity;
    }

    public int getSellingPrice() {
        return 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}
