package Model;

import Model.FarmStuff.Farm;

import java.util.ArrayList;

public class Map {
    private Tile[][] tiles = new Tile[300][250];
    private ArrayList<Farm> farms = new ArrayList<>();
    public ArrayList<Model.Buildings.Building> buildings;
    public ArrayList<Model.Shops.Shop> shops;

    public void buildMap(User[] owners , int[] types) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(new Point(i , j));
            }
        }
        for (int i = 0; i < 4; i++) {
            farms.add(new Farm(i+1 , owners[i] , types[i] ,  tiles));
        }
        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 300; j++) {
                System.out.print(tiles[j][i].getSymbol());
            }
            System.out.println();
        }
    }

    public void changeTileSymbol(Tile tile , char symbol , char contentSymbol) {
        tile.setSymbol(symbol);
        tile.setContentSymbol(contentSymbol);
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public void setFarms(ArrayList<Farm> farms) {
        this.farms = farms;
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
