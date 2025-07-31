package common.Model.enums.Shops.Products;

import common.Model.enums.Seasons;

public  interface ShopProduct {
    public String getName();
    public int getPrice();
    public int getDailyLimit();
    public Seasons getSeason();
    public String getType();
    public String getDescription();
}
