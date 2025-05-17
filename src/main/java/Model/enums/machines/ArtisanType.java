package Model.enums.machines;

import Model.machines.Artisan;

public enum ArtisanType {
    BeeHouse,
    CheesePress,
    Keg,
    Dehydrator,
    CharcoalKiln;

    ArtisanProductDetails[] productDetails;
    public Artisan createArtisan() {
        return new Artisan();
    }
}
