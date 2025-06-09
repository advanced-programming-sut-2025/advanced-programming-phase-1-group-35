
//A class for every animal , all factory owners are proud now.

package Model.animal;

import Model.ItemInterface;
import Model.Result;
import Model.Point;
import Model.enums.animal.AnimalType;


public class Animal implements ItemInterface {
    private final String name;
    private final AnimalType animalType;
    private final int buyingPrice;
    private double productionQuality;
    private int productionRate;
    private final AnimalProduct[] products;
    private final String confinement;
    private int friendship;
    private Point coordination;
    private int daysPastLastProduction;
    private boolean isInHouse;
    private boolean isFeedToday;
    private boolean canProduceTomorrow;
    private boolean isNazToday; // TODO : reset these 2 after a day passed


    public Animal(String name, AnimalType animalType, int buyingPrice, double productionRate, String confinement
    , AnimalProduct[] products) {
        this.name = name;
        this.animalType = animalType;
        this.buyingPrice = buyingPrice;
        this.productionQuality = productionRate;
        this.confinement = confinement;
        this.products = products;
        this.isFeedToday = false;
        this.isNazToday = false;
        this.canProduceTomorrow = false;
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

    public void setProductionQuality(double productionQuality) {
        this.productionQuality = productionQuality;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    public int getPrice() {
        return buyingPrice;
    }

    public double getProductionQuality() {
        return productionQuality;
    }

    public AnimalProduct[] getProducts() {
        return products;
    }

    public boolean isCanProduceTomorrow() {
        return canProduceTomorrow;
    }

    public void setCanProduceTomorrow(boolean canProduceTomorrow) {
        this.canProduceTomorrow = canProduceTomorrow;
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

    public boolean isFeedToday() {
        return isFeedToday;
    }

    public void setFeedToday(boolean feedToday) {
        isFeedToday = feedToday;
    }

    public boolean isNazToday() {
        return isNazToday;
    }

    public void setNazToday(boolean nazToday) {
        isNazToday = nazToday;
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

    public void updateQuality() {
        double quality = friendship / 1000.0;
        quality *= (0.5 + (0.5 * Math.random()));
        this.productionQuality = quality;
    }

    public int getSellingPrice() {
        double sellingPrice = buyingPrice * ((friendship / 1000.0) + 0.3);
        if (productionQuality >= 0.5 && productionQuality < 0.7) {
            sellingPrice *= 1.25;
        } else if (productionQuality >= 0.7 && productionQuality < 0.9) {
            sellingPrice *= 1.5;
        } else if (productionQuality >= 0.9) {
            sellingPrice *= 2;
        }
        return (int) sellingPrice;
    }
}
