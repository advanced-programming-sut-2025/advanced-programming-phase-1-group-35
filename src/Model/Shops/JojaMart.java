package Model.Shops;

import Model.Item;

import java.util.HashMap;

public class JojaMart extends Shop {
    private HashMap<Item, Integer> springStock;
    private HashMap<Item, Integer> summerStock;
    private HashMap<Item, Integer> fallStock;
    private HashMap<Item, Integer> winterStock;

    @Override
    public void showProducts(){
        super.showProducts();
    }


    public HashMap<Item, Integer> getSpringStock() {
        return springStock;
    }

    public void setSpringStock(HashMap<Item, Integer> springStock) {
        this.springStock = springStock;
    }

    public HashMap<Item, Integer> getSummerStock() {
        return summerStock;
    }

    public void setSummerStock(HashMap<Item, Integer> summerStock) {
        this.summerStock = summerStock;
    }

    public HashMap<Item, Integer> getFallStock() {
        return fallStock;
    }

    public void setFallStock(HashMap<Item, Integer> fallStock) {
        this.fallStock = fallStock;
    }

    public HashMap<Item, Integer> getWinterStock() {
        return winterStock;
    }

    public void setWinterStock(HashMap<Item, Integer> winterStock) {
        this.winterStock = winterStock;
    }
}
