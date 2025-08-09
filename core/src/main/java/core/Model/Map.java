package core.Model;

import core.Controller.Controller;
import core.Model.Buildings.Building;
import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Tree;
import core.Model.FarmStuff.Farm;
import core.Model.FarmStuff.Foraging;
import core.Model.FarmStuff.Rock;
import core.Model.Serializables.SerializableMap;
import core.Model.Serializables.SerializableTile;
import core.Model.Shops.Shop;
import core.Model.enums.Crops.PlantAble;
import core.Model.enums.Shops.ShopEnum;
import core.Model.enums.TileType;

import core.Model.machines.Machine;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    private Tile[][] tiles = new Tile[300][210];
    private ArrayList<Farm> farms = new ArrayList<>();
    private Farm village ;
    public ArrayList<Building> buildings = new ArrayList<>();
    public ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<Crop> Crop = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Machine> machines = new ArrayList<>();
    private ArrayList<Rock> rocks;
    private ArrayList<Foraging> forages;

    public static User[] users;
    public static int[] types;
    public void buildMap(User[] owners , int[] types) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(new Point(i, j));
            }
        }
        for (int i = 0; i < 4; i++) {
            User owner ;
            int type ;
            if(i >= owners.length || i >= types.length) {
                owner = null ;
                type = 1 ;
            }
            else {
                owner = owners[i];
                type = types[i];
            }
            farms.add(new Farm(i + 1, owner, type, tiles));
            if (owners[i] != null) {
                owners[i].setCurrentGameFarmIndex(i);
            }
        }
        village = new Farm(5, null, 5, tiles);
        shops.add(ShopEnum.BlackSmith.createShop());
        shops.add(ShopEnum.CarpenterShop.createShop());
        shops.add(ShopEnum.FishShop.createShop());
        shops.add(ShopEnum.GeneralStore.createShop());
        shops.add(ShopEnum.JojaMart.createShop());
        shops.add(ShopEnum.Ranch.createShop());
        shops.add(ShopEnum.Saloon.createShop());

        DrawPathWays();
    }
    public void reconstructFromSerializableMap(SerializableMap serialMap, Map map) {
        Tile[][] newTiles = map.getTiles();
        SerializableTile[][] serialTiles = serialMap.tiles;

        for (int i = 0; i < newTiles.length; i++) {
            for (int j = 0; j < newTiles[i].length; j++) {

                SerializableTile sTile = serialTiles[i][j];
                if (sTile != null) { // Only Soil tiles were serialized
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
                        ArrayList<ItemInterface> items = new ArrayList<>();
                        for (String itemName : sTile.contentsMap.keySet()) {
                            ItemInterface item = Controller.createItem(itemName);
                            if (item != null) {
                                items.add(item);
                            }
                        }
                        tile.setContents(items);
                    }

                    // Replace the soil tile
                    newTiles[i][j] = tile;
                }
                // else → leave existing non-soil tile unchanged
            }
        }
    }


    // Utility method to check if a coordinate is inside any farm rectangle
    private boolean isInAnyFarm(int x, int y, int[][] farms) {
        for (int[] farm : farms) {
            int x1 = farm[0], y1 = farm[1], x2 = farm[2], y2 = farm[3];
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                return true;
            }
        }
        return false;
    }


    public ArrayList<Machine> getMachines() {
        return machines;
    }

    private void DrawPathWays() {
        DrawHorizontalPath(
                tiles[farms.get(0).getBounds().x + farms.get(0).getBounds().width + 1]
                        [farms.get(0).getBounds().y + farms.get(0).getBounds().height / 2],
                tiles[village.getBounds().x + village.getBounds().width / 2]
                        [farms.get(0).getBounds().y + farms.get(0).getBounds().height / 2]
        );
        DrawHorizontalPath(
                tiles[village.getBounds().x + village.getBounds().width / 2 + 1]
                        [farms.get(3).getBounds().y + farms.get(3).getBounds().height / 2],
                tiles[farms.get(3).getBounds().x]
                        [farms.get(3).getBounds().y + farms.get(3).getBounds().height / 2]
        );
        DrawHorizontalPath(
                tiles[farms.get(0).getBounds().x + farms.get(0).getBounds().width / 2 + 1]
                        [village.getBounds().y + village.getBounds().height / 2],
                tiles[village.getBounds().x]
                        [village.getBounds().y + village.getBounds().height / 2]
        );
        DrawHorizontalPath(
                tiles[village.getBounds().x + village.getBounds().width]
                        [village.getBounds().y + village.getBounds().height / 2],
                tiles[farms.get(2).getBounds().x + farms.get(2).getBounds().width / 2]
                        [village.getBounds().y + village.getBounds().height / 2]
        );
        DrawVerticalPath(
                tiles[village.getBounds().x + village.getBounds().width / 2]
                        [farms.get(0).getBounds().y + farms.get(0).getBounds().height / 2 - 2],
                tiles[village.getBounds().x + village.getBounds().width / 2]
                        [village.getBounds().y]
        );
        DrawVerticalPath(
                tiles[farms.get(0).getBounds().x + farms.get(0).getBounds().width / 2]
                        [village.getBounds().y + village.getBounds().height / 2 - 2],
                tiles[farms.get(0).getBounds().x + farms.get(0).getBounds().width / 2]
                        [farms.get(1).getBounds().y]
        );
        DrawVerticalPath(
                tiles[farms.get(2).getBounds().x + farms.get(2).getBounds().width / 2]
                        [farms.get(2).getBounds().y + farms.get(2).getBounds().height],
                tiles[farms.get(2).getBounds().x + farms.get(2).getBounds().width / 2]
                        [village.getBounds().y + village.getBounds().height / 2 + 3]
        );
        DrawVerticalPath(
                tiles[village.getBounds().x + village.getBounds().width / 2]
                        [village.getBounds().y + village.getBounds().height + 1],
                tiles[village.getBounds().x + village.getBounds().width / 2]
                        [farms.get(3).getBounds().y + farms.get(3).getBounds().height / 2 + 3]
        );
    }

    private void DrawHorizontalPath(Tile begin, Tile end) {
        if (begin.getCoordination().y != end.getCoordination().y) return;
        if (begin.getCoordination().x >= end.getCoordination().x) return;
        for (int i = -2 + begin.getCoordination().y; i < 3 + begin.getCoordination().y; i++) {
            for (int j = begin.coordination.x; j < end.coordination.x; j++) {
                tiles[j][i].setTileType(TileType.Pathway);
                tiles[j][i].setSymbol('═');
                tiles[j][i].setWalkable(true);
            }
        }
    }

    private void DrawVerticalPath(Tile begin, Tile end) {
        if (begin.getCoordination().x != end.getCoordination().x) return;
        if (begin.getCoordination().y >= end.getCoordination().y) return;
        for (int i = -2 + begin.getCoordination().x; i < 3 + begin.getCoordination().x; i++) {
            for (int j = begin.coordination.y; j < end.coordination.y; j++) {
                tiles[i][j].setTileType(TileType.Pathway);
                tiles[i][j].setWalkable(true);
                tiles[i][j].setSymbol('║');
            }
        }
    }

    public static Tile getTileWithCoordination(String xString, String yString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        if(x < 0 || x >= 300) return null;
        if(y < 0 || y >= 250) return null;
        return App.getCurrentGame().getMap().tiles[x][y];
    }

    public void changeTileSymbol(Tile tile, char symbol, char contentSymbol) {
        // symbol is under of content symbol
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

    public void AddCrop(Crop crop) {
        this.Crop.add(crop);
    }

    public ArrayList<Crop> getCrops() {
        return Crop;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuildings(Building building) {
        this.buildings.add(building);
    }

    public void addTrees(Tree tree) {
        this.trees.add(tree);
    }

    public void addRocks(Rock rock) {
        this.rocks.add(rock);
    }

    public void addForages(Foraging forage) {
        this.forages.add(forage);
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    public ArrayList<Foraging> getForages() {
        return forages;
    }

    public Farm getVillage() {
        return village;
    }

    public void setVillage(Farm village) {
        this.village = village;
    }

    public Tile getTileWithDirection(int direction) {
        /*
        directions:
        7 8 9
        4   6
        1 2 3
        */

        Tile currentTile = App.getCurrentGame().getPlayingUser().getCurrentTile();
        int x = currentTile.getCoordination().x;
        int y = currentTile.getCoordination().y;
        return switch (direction) {
            case 7 -> tiles[x - 1][y - 1];
            case 8 -> tiles[x][y - 1];
            case 9 -> tiles[x + 1][y - 1];
            case 4 -> tiles[x - 1][y];
            case 6 -> tiles[x + 1][y];
            case 1 -> tiles[x - 1][y + 1];
            case 2 -> tiles[x][y + 1];
            case 3 -> tiles[x + 1][y + 1];
            default -> null;
        };
    }
}



