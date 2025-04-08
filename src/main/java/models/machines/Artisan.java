package models.machines;

import models.Buildings.Building;
import models.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class Artisan extends Building {
    private ArrayList<ArtisanProduct> products;
    private HashMap<ArtisanProduct , Integer> productsAvailable;

    public Result use(){

    }
    public Result get(){

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
