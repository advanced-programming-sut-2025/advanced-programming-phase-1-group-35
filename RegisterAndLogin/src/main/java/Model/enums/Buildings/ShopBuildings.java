package Model.enums.Buildings;

import Model.Buildings.Building;

public enum ShopBuildings {
    BlackSmith,
    CarpenterShop,
    FishShop,
    GeneralStore,
    JojaMart,
    Ranch,
    Saloon,
    ;

    public Building createBuilding() {
        return new Building();
    }
}
