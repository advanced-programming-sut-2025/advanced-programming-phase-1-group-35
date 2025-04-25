
//A class for every animal , all factory owners are proud now.

package Model.animal;

import Model.Result;
import Model.Point;


public class Animal {
    private final String name;
    private final int buyingPrice;
    private final int productionRate;
    private final AnimalProduct[] products;
    private final String confinement;
    private int friendship;
    private Point coordination;
    private int daysPastLastProduction;
    private boolean isInHouse;

    public Animal(String name , int buyingPrice, int productionRate, String confinement
    , AnimalProduct[] products) {
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.productionRate = productionRate;
        this.confinement = confinement;
        this.products = products;
    }

    public Result buyAnimal(){
        return null;
    }
    public String petAnimal(){
        return null;
    }
    public String AnimalDetails(){
        return null;
    }
    public Result shepherdAnimal(){
        return null;
    }
    public Result feedHay(){
        return null;
    }
    public String produces(){
        return null;
    }
    public Result collectProducts(){
        return null;
    }
    public Result sellAnimal(){
        return null;
    }



    public String getName() {
        return name;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public AnimalProduct[] getProducts() {
        return products;
    }

    public String getConfinement() {
        return confinement;
    }

    public int getFriendship() {
        return friendship;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public int getDaysPastLastProduction() {
        return daysPastLastProduction;
    }

    public void setDaysPastLastProduction(int daysPastLastProduction) {
        this.daysPastLastProduction = daysPastLastProduction;
    }

    public Point getCoordination() {
        return coordination;
    }

    public void setCoordination(Point coordination) {
        this.coordination = coordination;
    }

    public boolean isInHouse() {
        return isInHouse;
    }

    public void setInHouse(boolean inHouse) {
        isInHouse = inHouse;
    }
}
