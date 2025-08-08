package core.Model.FarmStuff;

import core.Model.Item;
import core.Model.ItemInterface;
import core.Model.Tile;
import core.Model.enums.TileType;

import java.util.ArrayList;

public class Rock implements ItemInterface {
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

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "Rock";
    }
}
