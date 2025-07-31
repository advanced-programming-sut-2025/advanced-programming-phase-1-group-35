package common.Model.FarmStuff.Home;

import common.Model.Buildings.Building;
import common.Model.FarmStuff.Farm;
import common.Model.Tile;

public class Cabin extends Building {
    public Refrigerator refrigerator;
    public Cabin(Farm farm , Tile[][] tiles) {
        this.farm = farm;
        floorTiles = new Tile[6][7];
        doorTile = tiles[farm.getBounds().x + 59][farm.getBounds().y + 11];
        placeBuilding('#' , 55 , 6 , 8 , 9 , tiles, "buildings/cabin/Cabin.png");
    }
}
