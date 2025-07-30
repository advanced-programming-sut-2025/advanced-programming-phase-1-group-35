package Model.machines;

import Controller.InGameMenu.ArtisanController;
import Model.ItemInterface;
import Model.enums.machines.ArtisanProductDetails;

import java.util.ArrayList;

public abstract class Machine implements ItemInterface {
    protected float x, y;
    protected boolean inUse = false;
    protected float timeInUse = 0;
    protected ArtisanController controller;
    protected ArrayList<ArtisanProductDetails> products;
    protected ArtisanProductDetails productBeingBuilt;
    protected boolean finished = false;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ArtisanProductDetails getProductBeingBuilt() {
        return productBeingBuilt;
    }

    public void setProductBeingBuilt(ArtisanProductDetails productBeingBuilt) {
        this.productBeingBuilt = productBeingBuilt;
    }

    public void setController(ArtisanController controller) {
        this.controller = controller;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public void setProducts(ArrayList<ArtisanProductDetails> products) {
        this.products = products;
    }

    public void setTimeInUse(float timeInUse) {
        this.timeInUse = timeInUse;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ArtisanController getController() {
        return controller;
    }

    public boolean isInUse() {
        return inUse;
    }

    public ArrayList<ArtisanProductDetails> getProducts() {
        return products;
    }

    public float getTimeInUse() {
        return timeInUse;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

