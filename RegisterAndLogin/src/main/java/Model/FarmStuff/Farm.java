package Model.FarmStuff;

import Model.*;
import Model.FarmStuff.*;
import Model.FarmStuff.Home.*;
import Model.*;
import Model.FarmStuff.Foraging;
import Model.FarmStuff.Greenhouse;
import Model.FarmStuff.Lake;
import Model.FarmStuff.Quarry;
import Model.FarmStuff.Tree;

import java.awt.*;
import java.util.ArrayList;

public class Farm {
    private User owner = null;
    private Rectangle bounds = new Rectangle();
    private Cabin cabin;
    private Lake lake;
    private Greenhouse greenhouse;
    private Quarry quarry;
    private ArrayList<Tree> trees;
    private ArrayList<Rock> rocks;
    private ArrayList<Foraging> forages;

    public Farm(int number , User owner , int type , Tile[][] tiles) {
        this.owner = owner;
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
                tiles[i][j].setSymbol('.');
                tiles[i][j].setWalkable(true);
            }
        }
        cabin = new Cabin(this , tiles);
        greenhouse = new Greenhouse(this , tiles);

        if(owner != null) {
            Rectangle bounds = cabin.getBounds();
            Tile spawnTile = tiles[bounds.x + bounds.width/2][bounds.y + bounds.height + 3];
            owner.setCurrentTile(spawnTile);
            owner.setSymbol((char)('0' + number));
            spawnTile.setContentSymbol((char) ('0' + number));
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
}
