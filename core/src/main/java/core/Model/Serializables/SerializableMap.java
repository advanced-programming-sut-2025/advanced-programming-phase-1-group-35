package core.Model.Serializables;

import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Tree;
import core.Model.Map;
import core.Model.Tile;
import core.Model.enums.TileType;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableMap implements Serializable {
    public SerializableTile[][] tiles;
    public ArrayList<SerializableCrop> crops = new ArrayList<>();
    public ArrayList<SerializableTree> trees = new ArrayList<>();

    // Add other simple map-wide properties here if needed later (e.g., map dimensions, global weather state)

    public SerializableMap(Map map) {
        Tile[][] originalTiles = map.getTiles();
        int width = originalTiles.length;
        int height = originalTiles[0].length;
        this.tiles = new SerializableTile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(originalTiles[i][j].getTileType().equals(TileType.Soil)) {
                    this.tiles[i][j] = new SerializableTile(originalTiles[i][j]);
                }
            }
        }
        for(Crop crop : map.getCrops()) {
            crops.add(new SerializableCrop(crop));
        }
        for (Tree tree : map.getTrees()) {
            trees.add(new SerializableTree(tree));
        }
    }

    // Default constructor for Gson deserialization
    public SerializableMap() {}
}
