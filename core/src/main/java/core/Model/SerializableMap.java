package core.Model;

import java.io.Serializable;

public class SerializableMap implements Serializable {
    public SerializableTile[][] tiles;
    // Add other simple map-wide properties here if needed later (e.g., map dimensions, global weather state)

    public SerializableMap(Map map) {
        Tile[][] originalTiles = map.getTiles();
        int width = originalTiles.length;
        int height = originalTiles[0].length;
        this.tiles = new SerializableTile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.tiles[i][j] = new SerializableTile(originalTiles[i][j]);
            }
        }
    }

    // Default constructor for Gson deserialization
    public SerializableMap() {}
}
