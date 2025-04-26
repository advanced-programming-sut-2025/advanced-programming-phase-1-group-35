package Model;

import Model.enums.Crops.PlantAble;
import Model.enums.TileContents;

import java.util.ArrayList;

public class Tile {
    private User owner = null;
    private char symbol = '0';
    private Coordination coordination;
    private ArrayList<TileContents> contents;
    private boolean gotHitWithThunder = false;
    private boolean isWalkable = false;
    private PlantAble planted;
    public void setGotHitWithThunder(boolean gotHitWithThunder) {
        this.gotHitWithThunder = gotHitWithThunder;
    }
    public boolean hasBeenHitWithThunder() {
        return gotHitWithThunder;
    }

    public void changeTileContents() {

    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public char getSymbol() {
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
}
