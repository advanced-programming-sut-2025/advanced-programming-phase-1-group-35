package Model.FarmStuff;

import Model.Tile;
import Model.enums.FarmType;
import Model.enums.TileType;

import java.awt.*;

public class Quarry{
    private Rectangle bounds;
    private Farm farm;

    public Quarry(FarmType type , Tile[][] tiles , Farm farm) {
        bounds = new Rectangle();
        bounds.setBounds(farm.getBounds().x + type.quarryX,farm.getBounds().y + type.quarryY,
                type.quarryWidth, type.quarryHeight);
        this.farm = farm;
        placeQuarry(tiles);
    }
    public void placeQuarry(Tile[][] tiles){
        for (int i = bounds.x ; i < bounds.x + bounds.width ; i++) {
            for (int j = bounds.y ; j < bounds.y + bounds.height ; j++) {
                new Rock(tiles[i][j]);
            }
        }
    }
}
