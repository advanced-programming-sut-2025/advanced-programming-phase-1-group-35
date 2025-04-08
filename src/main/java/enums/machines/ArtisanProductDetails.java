package enums.machines;

import models.Item;
import models.machines.ArtisanProduct;

public enum ArtisanProductDetails {
    Honey,

    Cheese,
    goatCheese,

    Beer,
    Vinegar,
    Coffee,
    Juice,
    Mead,
    PaleAle,
    Wine,

    DriedMushroom,
    DriedFruit,
    Raisins,

    Coal;
    public int energy , processingTime , sellPrice ;
    public Item[] ingredients;

    public ArtisanProduct getArtisanProduct() {
        return new ArtisanProduct();
    }
}
