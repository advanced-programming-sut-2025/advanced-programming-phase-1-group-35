package Model.enums.Shops;

import Model.Buildings.Building;
import Model.NPCs.NPC;
import Model.enums.Buildings.ShopBuildings;
import Model.enums.NPCs.NPCs;

import java.time.LocalTime;

public enum ShopEnum {
    BlackSmith(NPCs.Clint.createNPC() , ShopBuildings.BlackSmith.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(16 , 0)  ),
    Ranch(NPCs.Marnie.createNPC(), ShopBuildings.Ranch.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(16 , 0)),
    JojaMart(NPCs.Morris.createNPC(), ShopBuildings.JojaMart.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(23 , 0)),
    CarpenterShop(NPCs.Robin.createNPC(), ShopBuildings.CarpenterShop.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(20 , 0)),
    Saloon(NPCs.Gus.createNPC(), ShopBuildings.Saloon.createBuilding(),
            LocalTime.of(12 , 0) , LocalTime.of(0 , 0)),
    GeneralStore(NPCs.Pierre.createNPC(), ShopBuildings.GeneralStore.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(17 , 0)),
    FishShop(NPCs.Willy.createNPC(), ShopBuildings.FishShop.createBuilding(),
            LocalTime.of(9 , 0) , LocalTime.of(17 , 0)),
    ;

    public final NPC owner;
    public final Building building;
    public final LocalTime openTime;
    public final LocalTime closeTime;
    ShopEnum(NPC owner, Building building, LocalTime openTime, LocalTime closeTime) {
        this.owner = owner;
        this.building = building;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
