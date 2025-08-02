package core.Model.enums.Shops.Products;

import core.Model.enums.Seasons;

public  interface ShopProduct {
    public String getName();
    public int getPrice();
    public int getDailyLimit();
    public Seasons getSeason();
    public String getType();
    public String getDescription();
}
