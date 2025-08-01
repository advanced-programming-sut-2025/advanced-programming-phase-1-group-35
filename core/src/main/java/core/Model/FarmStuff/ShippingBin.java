package core.Model.FarmStuff;

import core.Model.TextureSplitter;
import core.Model.Tile;
import core.Model.enums.TileType;

import java.awt.*;

public class ShippingBin {
    private Rectangle bounds;
    private char symbol = '∏';

    public ShippingBin(Farm farm , Tile[][] tiles) {
        this.bounds = new Rectangle();
        bounds.setBounds(farm.getCabin().getBounds().x - 4 ,
                farm.getCabin().getBounds().y, 2 , 2);
        placeShippingBin(tiles);
    }

    public void placeShippingBin(Tile[][] tiles) {
        TextureSplitter splitter = new TextureSplitter("buildings/shipping bin/bin.png", 2, 2);
        for(int i = bounds.x; i < bounds.x + bounds.width; i++) {
            for(int j = bounds.y; j < bounds.y + bounds.height; j++) {
                tiles[i][j].setSymbol(symbol);
                tiles[i][j].setTileType(TileType.ShippingBin);
                tiles[i][j].setWalkable(false);
                tiles[i][j].setTexture(splitter.getRegion(1 - (j - bounds.y), i - bounds.x));
            }
        }
    }
}
