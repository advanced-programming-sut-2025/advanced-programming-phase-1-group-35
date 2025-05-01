package Model.FarmStuff.Home;

import Model.Buildings.Building;
import Model.FarmStuff.Farm;
import Model.Tile;

public class Cabin extends Building {
    public Cabin(Farm farm , Tile[][] tiles) {
        this.farm = farm;
        floorTiles = new Tile[7][4];
        placeBuilding('#' , 55 , 6 , 9 , 6 , tiles);
    }
}
