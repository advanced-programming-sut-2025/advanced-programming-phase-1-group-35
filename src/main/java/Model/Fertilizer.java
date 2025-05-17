package Model;

import Model.enums.Seasons;
import Model.enums.Shops.Products.ShopProduct;

public class Fertilizer implements ItemInterface, ShopProduct {
    private int price;
    private String name;

    public int getPrice() {
        return price;
    }

    @Override
    public int getDailyLimit() {
        return 0;
    }

    @Override
    public Seasons getSeason() {
        return null;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    public String getName() {
        return name;
    }
}
