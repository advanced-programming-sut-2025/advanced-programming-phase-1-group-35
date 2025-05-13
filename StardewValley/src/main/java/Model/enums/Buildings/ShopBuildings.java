package Model.enums.Buildings;

import Model.App;
import Model.Buildings.Building;
import Model.FarmStuff.Farm;
import Model.Tile;

public enum ShopBuildings {
    BlackSmith('B', 4, 1, 18, 15, 1),
    CarpenterShop('C', 26, 1, 18, 15, 1),
    GeneralStore('G', 48, 1, 18, 15, 1),
    FishShop('F', 2, 24, 15, 15, 2),
    JojaMart('J', 19, 24, 15, 15, 2),
    Ranch('M', 36, 24, 15, 15, 2),
    Saloon('S', 53, 24,15, 15, 2),
    ;
    private char symbol;
    int x,y,width,height;
    int doorPos;
    ShopBuildings(char symbol , int x, int y, int width, int height, int doorPos) {
        this.symbol = symbol;
        this.x = x ;
        this.y = y ;
        this.width = width;
        this.height = height;
        this.doorPos = doorPos;
    }
    public Building createBuilding() {
        Building building = new Building();
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        Farm farm = App.getCurrentGame().getMap().getVillage();
        building.setFarm(farm);
        building.setFloorTiles(new Tile[width - 2][height - 2]);
        if(doorPos == 1)
            building.setDoorTile(tiles[farm.getBounds().x + x + width/2]
                                      [farm.getBounds().y + y + height - 1]);
        else if(doorPos == 2)
            building.setDoorTile(tiles[farm.getBounds().x + x + width/2]
                                      [farm.getBounds().y + y]);
        building.placeBuilding(symbol , x , y , width , height , tiles);
        return building;
    }
}
