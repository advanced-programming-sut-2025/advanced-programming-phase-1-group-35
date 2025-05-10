package Model.enums.Buildings;

import Model.App;
import Model.Buildings.Building;
import Model.FarmStuff.Farm;
import Model.Tile;

public enum ShopBuildings {
    BlackSmith('B', 4, 1, 18, 15),
    CarpenterShop('C', 26, 1, 18, 15),
    GeneralStore('G', 48, 1, 18, 15),
    FishShop('F', 2, 24, 15, 15),
    JojaMart('J', 19, 24, 15, 15),
    Ranch('R', 36, 24, 15, 15),
    Saloon('S', 53, 24,15, 15),
    ;
    private char symbol;
    int x,y,width,height;
    ShopBuildings(char symbol , int x, int y, int width, int height) {
        this.symbol = symbol;
        this.x = x ;
        this.y = y ;
        this.width = width;
        this.height = height;
    }
    public Building createBuilding() {
        Building building = new Building();
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        Farm farm = App.getCurrentGame().getMap().getVillage();
        building.setFarm(farm);
        building.setFloorTiles(new Tile[width - 2][height - 2]);
        building.setDoorTile(tiles[x + width/2][y + height]);
        building.placeBuilding(symbol , x , y , width , height , tiles);
        return building;
    }
}
