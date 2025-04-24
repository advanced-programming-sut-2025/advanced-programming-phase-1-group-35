package Model.Tools;

import Model.Item;

import java.util.ArrayList;

public class BackPack extends Tool {
    public ArrayList<Tool> tools;
    private int capacity = 12;



    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public int getCapacity() {
        return capacity;
    }
    @Override
    public void reduceEnergy() {

    }
}
