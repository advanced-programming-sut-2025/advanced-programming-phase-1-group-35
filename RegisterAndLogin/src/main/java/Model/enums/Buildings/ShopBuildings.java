package Model.enums.Buildings;

import Model.Buildings.Building;

public enum ShopBuildings {
    BlackSmith(),
    CarpenterShop(),
    FishShop(),
    GeneralStore(),
    JojaMart(),
    Ranch(),
    Saloon(),
    ;
    private char symbol;
    int x,y,width,height;

    public Building createBuilding() {
        return new Building();
    }
}
