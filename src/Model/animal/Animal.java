
//A class for every animal , all factory owners are proud now.

package Model.animal;

import Model.Result;
import Model.Coordination;


public class Animal {
    private final String name;
    private final int buyingPrice;
    private final int productionRate;
    private final Model.animal.AnimalProduct[] products;
    private final String confinement;
    private int friendship;
    private Coordination coordination;
    private int daysPastLastProduction;
    private boolean isInHouse;

    public Animal(String name , int buyingPrice, int productionRate, String confinement
    , Model.animal.AnimalProduct[] products) {
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.productionRate = productionRate;
        this.confinement = confinement;
        this.products = products;
    }

    public boolean buyAnimal(){

    }
    public String petAnimal(){

    }
    public String AnimalDetails(){

    }
    public Result shepherdAnimal(){

    }
    public Result feedHay(){

    }
    public String produces(){

    }
    public Result collectProducts(){

    }
    public Result sellAnimal(){

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

    public Model.animal.AnimalProduct[] getProducts() {
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

    public Coordination getCoordination() {
        return coordination;
    }

    public void setCoordination(Coordination coordination) {
        this.coordination = coordination;
    }

    public boolean isInHouse() {
        return isInHouse;
    }

    public void setInHouse(boolean inHouse) {
        isInHouse = inHouse;
    }
}
