package Model.enums.machines;

import Model.machines.Artisan;

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
