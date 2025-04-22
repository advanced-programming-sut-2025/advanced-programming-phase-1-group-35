package Model.Shops;

import Model.Buildings.FarmBuilding;
import Model.Item;

import java.util.HashMap;

public class Carpenter extends Shop {
    private HashMap<FarmBuilding, Integer> farmBuildings;

    @Override
    public void showProducts(){
        super.showProducts();
    }

    public HashMap<FarmBuilding, Integer> getFarmBuildings() {
        return farmBuildings;
    }

    public void setFarmBuildings(HashMap<FarmBuilding, Integer> farmBuildings) {
        this.farmBuildings = farmBuildings;
    }
}
