package enums.machines;

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

    public ArtisanProduct getArtisanProduct() {
        return new ArtisanProduct();
    }
}
