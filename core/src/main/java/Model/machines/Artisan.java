package Model.machines;

import Model.Buildings.Building;
import Model.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class Artisan extends Building {
    private ArrayList<ArtisanProduct> products;
    private HashMap<ArtisanProduct, Integer> productsAvailable;

    public Result use(){
    return null;
    }
    public Result get(){
    return null;
    }

    public ArrayList<ArtisanProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ArtisanProduct> products) {
        this.products = products;
    }

    public HashMap<ArtisanProduct, Integer> getProductsAvailable() {
        return productsAvailable;
    }

    public void setProductsAvailable(HashMap<ArtisanProduct, Integer> productsAvailable) {
        this.productsAvailable = productsAvailable;
    }
}
