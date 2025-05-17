package Model;

import Model.Buildings.Building;
import Model.CropClasses.Crop;
import Model.CropClasses.Tree;
import Model.FarmStuff.Farm;
import Model.Shops.Shop;
import Model.enums.Colors;
import Model.enums.NPCs.NPCs;
import Model.enums.Shops.ShopEnum;
import Model.FarmStuff.Foraging;
import Model.FarmStuff.Rock;
import Model.enums.TileType;

import java.util.ArrayList;

import static Model.enums.Colors.RESET;

public class Map {
    private Tile[][] tiles = new Tile[300][250];
    private ArrayList<Farm> farms = new ArrayList<>();
    private Farm village ;
    public ArrayList<Building> buildings = new ArrayList<>();
    public ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<Crop> Crop = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Rock> rocks;
    private ArrayList<Foraging> forages;


    public void buildMap(User[] owners , int[] types) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(new Point(i, j));
            }
        }
        for (int i = 0; i < 4; i++) {
            farms.add(new Farm(i + 1, owners[i], types[i], tiles));
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
        for (int i = -1 ; i < 300 ; i++){
            System.out.printf("%4d", i);
        }
        System.out.println();
        for (int i = 0; i < 250; i++) {
            System.out.printf("%4d", i);
            for (int j = 0; j < 300; j++) {
                if (tiles[j][i].getTileType().equals(TileType.BuildingWall)) {
                    System.out.printf("%s%4c%s", Colors.YELLOW, tiles[j][i].getSymbol(), RESET);
                } else if (tiles[j][i].getTileType().equals(TileType.Water)) {
                    System.out.printf("%s%4c%s", Colors.BLUE, tiles[j][i].getSymbol(), RESET);
                } else if (tiles[j][i].getTileType().equals(TileType.Pathway)) {
                    System.out.printf("%s%s%4c%s", Colors.YELLOW_UNDERLINED, Colors.GREEN, tiles[j][i].getSymbol(), RESET);
                } else {
                    System.out.printf("%s%4c%s", Colors.WHITE, tiles[j][i].getSymbol(), RESET);
                }
            }
            Game game = App.getCurrentGame();
            game.getNpcs().add(NPCs.Abigail.createNPC());
            game.getNpcs().add(NPCs.Sebastian.createNPC());
            game.getNpcs().add(NPCs.Lia.createNPC());
            game.getNpcs().add(NPCs.Robin.createNPC());
            game.getNpcs().add(NPCs.Harvey.createNPC());
            System.out.println();
        }
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



