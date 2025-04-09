package Model.machines;

import Model.Buildings.Building;
import Model.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class Artisan extends Building {
    private ArrayList<Model.machines.ArtisanProduct> products;
    private HashMap<Model.machines.ArtisanProduct, Integer> productsAvailable;

    public Result use(){
    return null;
    }
    public Result get(){
    return null;
    }

    public ArrayList<Model.machines.ArtisanProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Model.machines.ArtisanProduct> products) {
        this.products = products;
    }

    public HashMap<Model.machines.ArtisanProduct, Integer> getProductsAvailable() {
        return productsAvailable;
    }

    public void setProductsAvailable(HashMap<Model.machines.ArtisanProduct, Integer> productsAvailable) {
        this.productsAvailable = productsAvailable;
    }
}
