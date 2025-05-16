package Model.enums.Shops.Products;

import Model.ItemInterface;
import Model.Shops.ShopItem;
import Model.ItemInterface;
import Model.enums.ItemConstant;
import Model.enums.Seasons;

import java.io.IOException;

public enum CarpenterShopProducts implements ShopProduct, ItemInterface,ItemConstant {
    WOOD("Item", "Wood", "A sturdy, yet flexible plant material with a wide variety of uses.", 10, Double.POSITIVE_INFINITY),
    STONE("Item", "Stone", "A common material with many uses in crafting and building.", 20, Double.POSITIVE_INFINITY),
    BARN("AnimalHouse", "Barn", "Houses 4 barn-dwelling animals.", 6000, 1.0),
    BIG_BARN("AnimalHouse","Big Barn", "Houses 8 barn-dwelling animals. Unlocks goats.", 12000, 1.0),
    DELUXE_BARN("AnimalHouse","Deluxe Barn", "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.", 25000, 1.0),
    COOP("AnimalHouse","Coop", "Houses 4 coop-dwelling animals.", 4000, 1.0),
    BIG_COOP("AnimalHouse","Big Coop", "Houses 8 coop-dwelling animals. Unlocks ducks.", 10000, 1.0),
    DELUXE_COOP("AnimalHouse","Deluxe Coop", "Houses 12 coop-dwelling animals. Unlocks rabbits.", 20000, 1.0),
    WELL("AnimalHouse","Well", "Provides a place for you to refill your watering can.", 1000, 1.0),
    SHIPPING_BIN("AnimalHouse","Shipping Bin", "Items placed in it will be included in the nightly shipment.", 250, Double.POSITIVE_INFINITY),
    ;
    private String type;
    private String name , description;
    private int price;
    private Double dailyLimit;
    CarpenterShopProducts(String type, String name, String description, int price, Double dailyLimit) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getDailyLimit() {
        double dl = 0;
        if(dailyLimit == Double.POSITIVE_INFINITY) return 1000000;
        dl = dailyLimit;
        return (int) dl;
    }

    @Override
    public Seasons getSeason() {
        return null;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ItemInterface getItem() throws IOException {
        double dl = dailyLimit;
        int p = dailyLimit == Double.POSITIVE_INFINITY ? 1000000 : (int)dl;
        ShopItem item = new ShopItem(this.name, this.price, p, null, this.type,this.description);
        return (ItemInterface) item.makeInstance();
    }
}
