package Model.animal;

import Model.ItemInterface;

public class AnimalProduct implements ItemInterface {
    private int price;
    private String name;
    public AnimalProduct(String name , int price) {
        this.name = name;
        this.price = price;
    }


}
