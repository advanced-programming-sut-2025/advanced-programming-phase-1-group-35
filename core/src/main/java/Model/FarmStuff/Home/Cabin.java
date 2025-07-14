package Model.FarmStuff.Home;

import Model.Buildings.Building;
import Model.FarmStuff.Farm;
import Model.Tile;

public class Cabin extends Building {
    public Refrigerator refrigerator;
    public Cabin(Farm farm , Tile[][] tiles) {
        this.farm = farm;
        floorTiles = new Tile[7][7];
        doorTile = tiles[farm.getBounds().x + 59][farm.getBounds().y + 11];
        placeBuilding('#' , 55 , 6 , 9 , 9 , tiles, "buildings/cabin/Cabin.png");
    }
}
