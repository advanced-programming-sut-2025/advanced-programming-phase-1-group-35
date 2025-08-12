package core.Model.Serializables;

import core.Controller.Controller;
import core.Model.ItemInterface;
import core.Model.Point;
import core.Model.Tile;
import core.Model.enums.Crops.PlantAble;
import core.Model.enums.TileType;
import java.io.Serializable;
import java.util.ArrayList;
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

    public static Tile deserializeTile(SerializableTile sTile) {
        Tile tile = new Tile(sTile.coordination);
        tile.setOwnerID(sTile.ownerID);
        tile.setTileType(sTile.tileType);
        tile.setSymbol(sTile.symbol);
        tile.setContentSymbol(sTile.contentSymbol);
        tile.setWalkable(sTile.isWalkable);
        tile.setPlowed(sTile.isPlowed);
        tile.setFertilized(sTile.isFertilized);
        tile.setWatered(sTile.isWatered);

        // Restore planted item if present
        if (sTile.plantedItemName != null) {
            PlantAble planted = PlantAble.getPlantAbleByName(sTile.plantedItemName, tile);
            tile.setPlanted(planted);
        }

        // Restore item contents
        if (sTile.contentsMap != null) {
        try{
            ArrayList<ItemInterface> items = new ArrayList<>();
            for (String itemName : sTile.contentsMap.keySet()) {
                ItemInterface item = Controller.createItem(itemName);
                if (item != null) {
                    items.add(item);
                }
            }
            tile.setContents(items);
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        return tile;
    }

    // Default constructor for Gson deserialization
    public SerializableTile() {}
}
