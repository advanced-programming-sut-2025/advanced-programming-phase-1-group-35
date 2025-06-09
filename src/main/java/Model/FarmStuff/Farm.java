package Model.FarmStuff;

import Model.*;
import Model.Buildings.AnimalHouse;
import Model.Buildings.Building;
import Model.CropClasses.Crop;
import Model.CropClasses.Tree;
import Model.FarmStuff.*;
import Model.FarmStuff.Home.*;
import Model.Tools.Tool;
import Model.Shops.Shop;
import Model.animal.Animal;
import Model.enums.FarmType;
import Model.enums.Shops.ShopEnum;
import Model.enums.TileType;
import Model.enums.ToolTypes;

import java.awt.*;
import java.util.ArrayList;

public class Farm {
    private User owner = null;
    private Rectangle bounds = new Rectangle();
    private Cabin cabin;
    private Lake lake;
    private Greenhouse greenhouse;
    private Quarry quarry;
    private ShippingBin shippingBin;
    private ArrayList<Model.CropClasses.Crop> Crop = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Rock> rocks = new ArrayList<>();
    private ArrayList<Foraging> forages = new ArrayList<>();
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<AnimalHouse> animalHouses = new ArrayList<>();
    public ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Shop> shops = new ArrayList<>();
    public Farm(int number , User owner , int type , Tile[][] tiles) {
        this.owner = owner;
        if(number == 5)initVillage(tiles);
        else initFarm(type, number, tiles);
    }

    public void initFarm(int type , int number , Tile[][] tiles) {
        FarmType farmType = FarmType.values()[type];
        int x = 0 , y = 0;
        switch (number) {
            case 1:
                x = 10;
                y = 10;
                break;
            case 2:
                x = 10;
                y = 145;
                break;
            case 3:
                x = 185;
                y = 10;
                break;
            case 4:
                x = 185;
                y = 145;
                break;
        }
        bounds.setBounds(x , y , 75 , 55);
        for(int i = bounds.x ; i <= bounds.x + bounds.width ; i ++) {
            for(int j = bounds.y ; j <= bounds.y + bounds.height ; j ++) {
                tiles[i][j].setOwner(owner);
                if(owner != null)tiles[i][j].setOwnerID(owner.getID());
                tiles[i][j].setSymbol('.');
                tiles[i][j].setWalkable(true);
                tiles[i][j].setTileType(TileType.Soil);
            }
        }
        cabin = new Cabin(this , tiles);
        greenhouse = new Greenhouse(this , tiles);
        lake = new Lake(farmType , tiles , this);
        quarry = new Quarry(farmType , tiles , this);
        shippingBin = new ShippingBin(this , tiles);

        if(owner != null) {// placing the player
            owner.backPack.items.put(new Tool(100, 5, ToolTypes.HOE), 1);
            owner.backPack.items.put(new Tool(100, 5, ToolTypes.AXE), 1);
            owner.backPack.items.put(new Tool(100, 5, ToolTypes.PICKAXE), 1);
            owner.backPack.items.put(new Tool(100, 5, ToolTypes.SCYTHE), 1);
            owner.backPack.items.put(new Tool(100, 5, ToolTypes.FISHING_ROD), 1);
            owner.backPack.items.put(new Tool(100, 5, ToolTypes.TRASH_CAN), 1);
            owner.backPack.items.put(new Tool(100, 5, ToolTypes.MILK_PAIL), 1);




            Rectangle bounds = cabin.getBounds();
            Tile spawnTile = tiles[bounds.x + bounds.width/2][bounds.y + bounds.height + 3];
            owner.setCurrentTile(spawnTile);
            owner.setSymbol((char)('0' + number));
            spawnTile.setContentSymbol((char) ('0' + number));
        }
    }

    private void initVillage(Tile[][] tiles) {
        int x = 100 , y = 90;
        bounds.setBounds(x , y , 70 , 40);
        for(int i = bounds.x ; i <= bounds.x + bounds.width ; i ++) {
            for(int j = bounds.y ; j <= bounds.y + bounds.height ; j ++) {
                tiles[i][j].setOwner(owner);
                if(owner != null)tiles[i][j].setOwnerID(owner.getID());
                tiles[i][j].setSymbol('.');
                tiles[i][j].setWalkable(true);
                tiles[i][j].setTileType(TileType.OutSideFarm);
            }
        }
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public Greenhouse getGreenhouse() {
        return greenhouse;
    }

    public void setGreenhouse(Greenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    public Quarry getQuarry() {
        return quarry;
    }

    public void setQuarry(Quarry quarry) {
        this.quarry = quarry;
    }

    public Lake getLake() {
        return lake;
    }

    public void setLake(Lake lake) {
        this.lake = lake;
    }

//    public void AddCrop(Crop crop) {
//        this.Crop.add(crop);
//    }
//
//    public ArrayList<Crop> getCrops() {
//        return Crop;
//    }
//
//    public ArrayList<Building> getBuildings() {
//        return buildings;
//    }
//
//    public void addBuildings(Building building) {
//        this.buildings.add(building);
//    }
//    public void addTrees(Tree tree) {
//        this.trees.add(tree);
//    }
//    public void addRocks(Rock rock) {
//        this.rocks.add(rock);
//    }
//    public void addForages(Foraging forage) {
//        this.forages.add(forage);
//    }
//
//    public ArrayList<Shop> getShops() {
//        return shops;
//    }
//
//    public ArrayList<Tree> getTrees() {
//        return trees;
//    }
//
//    public ArrayList<Rock> getRocks() {
//        return rocks;
//    }
//
//    public ArrayList<Foraging> getForages() {
//        return forages;
//    }

    public boolean isAnimalNameExist(String animalName) {
        for (Animal animal : animals) {
            if (animal.getName().equals(animalName)) {
                return true;
            }
        }
        return false;
    }

    public Animal findAnimal(String animalName) {
        for (Animal animal : animals) {
            if (animal.getName().equals(animalName)) {
                return animal;
            }
        }
        return null;
    }

    public ArrayList<Model.CropClasses.Crop> getCrop() {
        return Crop;
    }

    public void setCrop(ArrayList<Model.CropClasses.Crop> crop) {
        Crop = crop;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void setTrees(ArrayList<Tree> trees) {
        this.trees = trees;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    public void setRocks(ArrayList<Rock> rocks) {
        this.rocks = rocks;
    }

    public ArrayList<Foraging> getForages() {
        return forages;
    }

    public void setForages(ArrayList<Foraging> forages) {
        this.forages = forages;
    }

    public ShippingBin getShippingBin() {
        return shippingBin;
    }

    public void setShippingBin(ShippingBin shippingBin) {
        this.shippingBin = shippingBin;
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


}
