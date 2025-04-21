package Model;

import Model.enums.TileContents;

import java.util.ArrayList;

public class Tile {
    private Coordination coordination;
    private ArrayList<TileContents> contents;
    private boolean gotHitWithThunder;

    public void changeTileContents() {

    }
    public void setGotHitWithThunder(boolean gotHitWithThunder) {
        this.gotHitWithThunder = gotHitWithThunder;
    }
    public boolean hasBeenHitWithThunder() {
        return gotHitWithThunder;
    }

}
