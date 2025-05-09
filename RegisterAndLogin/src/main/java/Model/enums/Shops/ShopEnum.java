package Model.enums.Shops;

import Model.Buildings.Building;
import Model.NPCs.NPC;
import Model.Shops.Shop;
import Model.enums.Buildings.ShopBuildings;
import Model.enums.NPCs.NPCs;
import Model.enums.Shops.Products.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ShopEnum {
    BlackSmith(NPCs.Clint.createNPC() , ShopBuildings.BlackSmith.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(16 , 0),
            Arrays.stream(BlackSmithProducts.values()).collect(Collectors.toList())),
    Ranch(NPCs.Marnie.createNPC(), ShopBuildings.Ranch.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(16 , 0),
            Arrays.stream(RanchProducts.values()).collect(Collectors.toList())),
    JojaMart(NPCs.Morris.createNPC(), ShopBuildings.JojaMart.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(23 , 0),
            Arrays.stream(JojaMartProducts.values()).collect(Collectors.toList())),
    CarpenterShop(NPCs.Robin.createNPC(), ShopBuildings.CarpenterShop.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(20 , 0),
            Arrays.stream(CarpenterShopProducts.values()).collect(Collectors.toList())),
    Saloon(NPCs.Gus.createNPC(), ShopBuildings.Saloon.createBuilding(),
            LocalTime.of(12 , 0) , LocalTime.of(0 , 0),
            Arrays.stream(SaloonProducts.values()).collect(Collectors.toList())),
    GeneralStore(NPCs.Pierre.createNPC(), ShopBuildings.GeneralStore.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(17 , 0),
            Arrays.stream(GeneralStoreProducts.values()).collect(Collectors.toList())),
    FishShop(NPCs.Willy.createNPC(), ShopBuildings.FishShop.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(17 , 0),
            Arrays.stream(FishShopProducts.values()).collect(Collectors.toList())),
    ;

    public final NPC owner;
    public final Building building;
    public final LocalTime openTime;
    public final LocalTime closeTime;
    public final List<ShopProduct> shopProducts;
    ShopEnum(NPC owner, Building building, LocalTime openTime, LocalTime closeTime, List<ShopProduct> shopProducts) {
        this.owner = owner;
        this.building = building;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.shopProducts = shopProducts;
    }

    public Shop createShop() {
        return new Shop();
    }
}
