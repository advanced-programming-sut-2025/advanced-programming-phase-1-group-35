package Model.enums.animal;

import Model.ItemInterface;

public enum FishType implements ItemInterface {
    Salmon,
    Sardine,
    Shad,
    BlueDiscus,

    MidnightCarp,
    Squid,
    Tuna,
    Perch,

    Flounder,
    Lionfish,
    Herring,
    GhostFish,

    Tilapia,
    Dorado,
    Sunfish,
    RainbowTrout,

    Legend,
    GlacierFish,
    Angler,
    CrimsonFish;

    public String name , season , type ;
    public int basePrice;

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }
}
