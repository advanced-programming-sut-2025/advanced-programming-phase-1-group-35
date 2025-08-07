package core.Model;

import core.Model.enums.TileType;
import java.io.Serializable;
import java.util.HashMap;

public class SerializableTile implements Serializable {
    public Point coordination; // Assuming Point is simple (int x, int y) and doesn't cause issues
    public int ownerID;
    public TileType tileType;
    public char symbol;
    public char contentSymbol;
    public boolean isWalkable;
    public boolean isPlowed;
    public boolean isFertilized;
    public boolean isWatered;
    public String plantedItemName; // Store only the name of the planted item
    public HashMap<String, String> contentsMap; // Store names and types of other contents

    public SerializableTile(Tile tile) {
        this.coordination = tile.getCoordination();
        this.ownerID = tile.getOwnerID();
        this.tileType = tile.getTileType();
        this.symbol = tile.getSymbol();
        this.contentSymbol = tile.getContentSymbol();
        this.isWalkable = tile.isWalkable();
        this.isPlowed = tile.isPlowed();
        this.isFertilized = tile.isFertilized();
        this.isWatered = tile.isWatered();

        if (tile.getPlanted() != null) {
            this.plantedItemName = tile.getPlanted().getName();
        } else {
            this.plantedItemName = null;
        }

        this.contentsMap = new HashMap<>();
        if (tile.getContents() != null) {
            for (ItemInterface item : tile.getContents()) {
                if (item != null) {
                    // Store the item's name and its simple class name
                    this.contentsMap.put(item.getName(), item.getClass().getSimpleName());
                }
            }
        }
    }

    // Default constructor for Gson deserialization
    public SerializableTile() {}
}
