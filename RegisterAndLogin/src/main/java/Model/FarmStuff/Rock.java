package Model.FarmStuff;

import Model.Item;
import Model.Tile;
import Model.enums.TileType;

import java.util.ArrayList;
import java.util.Collection;

public class Rock {
    private char symbol = 'R';
    Tile tile ; // if tile is null it means it is mined

    public Rock(Tile tile) {
        this.tile = tile;
        place(tile);
    }

    public void place(Tile tile){
        this.tile = tile;
        tile.setSymbol(symbol);
        ArrayList contents = new ArrayList();
        contents.add(this);
        tile.setContents(contents);
        tile.setTileType(TileType.Rock);
        tile.setWalkable(false);
    }

    public Item mine(){
        tile.setWalkable(true);
        tile.setContentSymbol('0');
        tile.setContents(null);
        return new Item(1 , "stone");
    }
}
