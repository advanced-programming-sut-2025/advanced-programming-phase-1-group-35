package Model.Shops;

import Model.enums.Seasons;

public class ShopItem {
    private final String name;
    private int price;
    private String description;
    private final int dailyLimit;
    private int dailyBoughtCount;
    private Seasons season = null;
    private String type ;

    public ShopItem(String name, int price, int dailyLimit
    , Seasons season, String type, String description) {
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.season = season;
        this.type = type;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public int getDailyBoughtCount() {
        return dailyBoughtCount;
    }

    public void setDailyBoughtCount(int dailyBoughtCount) {
        this.dailyBoughtCount = dailyBoughtCount;
    }

    public Seasons getSeason() {
        return season;
    }

    public void setSeason(Seasons season) {
        this.season = season;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
