package common.Model.FarmStuff;

import common.Model.Tile;
import common.Model.enums.FarmType;
import common.Model.enums.TileType;

import java.awt.*;

public class Lake {
    private Rectangle bounds;
    private char symbol = '~';
    private Farm farm;

    public Lake(FarmType type , Tile[][] tiles , Farm farm) {
        bounds = new Rectangle();
        bounds.setBounds(farm.getBounds().x + type.lakeX,farm.getBounds().y + type.lakeY,
                type.lakeWidth, type.lakeHeight);
        this.farm = farm;
        placeLake(symbol , tiles);
    }
    public void placeLake(char symbol , Tile[][] tiles){
        for (int i = bounds.x ; i < bounds.x + bounds.width ; i++) {
            for (int j = bounds.y ; j < bounds.y + bounds.height ; j++) {
                tiles[i][j].setSymbol(symbol);
                tiles[i][j].setWalkable(false);
                tiles[i][j].setTileType(TileType.Water);

            }
        }
    }
}
