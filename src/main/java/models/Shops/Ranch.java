package models.Shops;

import models.animal.Animal;

import java.util.HashMap;

public class Ranch extends Shop {
    private HashMap<Animal , Integer> liveStock = new HashMap<>();

    public HashMap<Animal, Integer> getLiveStock() {
        return liveStock;
    }

    public void setLiveStock(HashMap<Animal, Integer> liveStock) {
        this.liveStock = liveStock;
    }

    @Override
    public void showProducts(){
        super.showProducts();
    }
}
