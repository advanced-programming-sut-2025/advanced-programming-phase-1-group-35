package common.Model.enums.machines;

import common.Model.machines.Artisan;

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
