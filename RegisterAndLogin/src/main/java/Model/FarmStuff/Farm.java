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

    public Farm(int number) {

    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
