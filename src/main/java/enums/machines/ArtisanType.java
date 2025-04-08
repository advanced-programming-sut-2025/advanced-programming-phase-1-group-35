package enums.machines;

import models.machines.Artisan;

public enum ArtisanType {
    BeeHouse,
    CheesePress,
    Keg,
    Dehydrator,
    CharcoalKiln;

    public Artisan createArtisan() {
        return new Artisan();
    }
}
