package Model;

import Model.Buildings.Building;
import Model.Buildings.Building;
import Model.CropClasses.Crop;
import Model.CropClasses.Tree;
import Model.FarmStuff.Farm;
import Model.Shops.Shop;
import Model.enums.Shops.ShopEnum;
import Model.FarmStuff.Foraging;
import Model.FarmStuff.Rock;
import Model.Shops.Shop;
import Model.enums.Crops.PlantAble;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    private Tile[][] tiles = new Tile[300][250];
    private ArrayList<Farm> farms = new ArrayList<>();
    public ArrayList<Building> buildings;
    public ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<Crop> Crop = new ArrayList<>();
    private ArrayList<Tree> trees;
    private ArrayList<Rock> rocks;
    private ArrayList<Foraging> forages;
    public void buildMap(User[] owners , int[] types) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(new Point(i , j));
            }
        }
        for (int i = 0; i < 4; i++) {
            farms.add(new Farm(i+1 , owners[i] , types[i] ,  tiles));
            if(owners[i] != null){
                owners[i].setCurrentGameFarmIndex(i);
            }
        }
        shops.add(ShopEnum.BlackSmith.createShop());
        shops.add(ShopEnum.CarpenterShop.createShop());
        shops.add(ShopEnum.FishShop.createShop());
        shops.add(ShopEnum.GeneralStore.createShop());
        shops.add(ShopEnum.JojaMart.createShop());
        shops.add(ShopEnum.Ranch.createShop());
        shops.add(ShopEnum.Saloon.createShop());
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
}



