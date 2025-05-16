package Model.FarmStuff;

import Model.Item;
import Model.Tile;
import Model.enums.TileType;

import java.util.ArrayList;
import java.util.Collection;

public class Rock {
    private char symbol = 'R';

    public Rock() {
    }

    public void place(Tile tile){
        tile.setSymbol(symbol);
        ArrayList contents = new ArrayList();
        contents.add(this);
        tile.setContents(contents);
        tile.setTileType(TileType.Rock);
        tile.setWalkable(false);
    }

    public static Item mine(Tile tile){
        tile.setWalkable(true);
        tile.setContentSymbol('0');
        tile.setContents(null);
        return new Item(20 , "stone");
    }

    public char getSymbol() {
        return symbol;
    }
}
