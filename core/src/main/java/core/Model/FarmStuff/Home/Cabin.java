package core.Model.FarmStuff.Home;

import core.Model.Buildings.Building;
import core.Model.FarmStuff.Farm;
import core.Model.Tile;

public class Cabin extends Building {
    public Refrigerator refrigerator;
    public Cabin(Farm farm , Tile[][] tiles) {
        this.farm = farm;
        floorTiles = new Tile[6][7];
        doorTile = tiles[farm.getBounds().x + 59][farm.getBounds().y + 11];
        placeBuilding('#' , 55 , 6 , 8 , 9 , tiles, "buildings/cabin/Cabin.png");
    }
}
