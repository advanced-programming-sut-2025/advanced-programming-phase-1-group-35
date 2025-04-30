package Model;

import Model.enums.Crops.PlantAble;
import Model.enums.TileContents;
import Model.enums.TileType;

import java.util.ArrayList;

public class Tile {
    private User owner = null;
    private TileType tileType = TileType.OutSideFarm;
    private char symbol = '0';
    private char contentSymbol = '0';

    public Point getCoordination() {
        return coordination;
    }

    public Point coordination;
    private ArrayList<TileContents> contents;
    private boolean gotHitWithThunder = false;
    private boolean isWalkable = false;
    private PlantAble planted;
    private boolean isPlowed = false;
    public boolean isFertilized = false;
    public boolean isWatered = false;
    private int daysSinceWatered = 0;

    public int getDaysSinceWatered() {
        return daysSinceWatered;
    }

    public void setDaysSinceWatered(int daysSinceWatered) {
        this.daysSinceWatered = daysSinceWatered;
    }

    public boolean isGotHitWithThunder() {
        return gotHitWithThunder;
    }

    public ArrayList<TileContents> getContents() {
        return contents;
    }

    public void setContents(ArrayList<TileContents> contents) {
        this.contents = contents;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }

    public PlantAble getPlanted() {
        return planted;
    }

    public void setPlanted(PlantAble planted) {
        this.planted = planted;
    }

    public Tile(Point coordination) {
        this.coordination = coordination;
    }

    public void setGotHitWithThunder(boolean gotHitWithThunder) {
        this.gotHitWithThunder = gotHitWithThunder;
    }
    public boolean hasBeenHitWithThunder() {
        return gotHitWithThunder;
    }

    public void changeTileContents(PlantAble planted) {
    this.planted = planted;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public char getSymbol() {
        if(contentSymbol != '0'){
            return contentSymbol;
        }
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public char getContentSymbol() {
        return contentSymbol;
    }

    public void setContentSymbol(char contentSymbol) {
        this.contentSymbol = contentSymbol;
    }


    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }
}
