package Model.FarmStuff.Home;

import Model.Buildings.Building;
import Model.FarmStuff.Farm;
import Model.Tile;

public class Cabin extends Building {
    private Farm farm;
    public Cabin(Farm farm , Tile[][] tiles) {
        this.farm = farm;
        placeCabin(tiles);
    }
    public void placeCabin(Tile[][] tiles) {
        super.bounds.setBounds(farm.getBounds().x + 55 , farm.getBounds().y + 6 , 9 , 6);
        for (int i = bounds.x ; i < bounds.x + bounds.width ; i++) {
            for (int j = bounds.y ; j < bounds.y + bounds.height ; j++) {
                tiles[i][j].setSymbol('#');
                tiles[j][i].setWalkable(false);
            }
        }
    }
}
