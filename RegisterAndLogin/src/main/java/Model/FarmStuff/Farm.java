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
    private Rectangle bounds;
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
            case 2:
                x = 10;
                y = 145;
            case 3:
                x = 185;
                y = 10;
            case 4:
                x = 185;
                y = 145;
        }
        bounds = new Rectangle(75 , 55 );
        bounds.setLocation(x, y);
        for(int i = bounds.x ; i <= bounds.x + bounds.width ; i ++) {
            for(int j = bounds.y ; j <= bounds.y + bounds.height ; j ++) {
                tiles[i][j].setOwner(owner);
            }
        }
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
