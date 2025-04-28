package Model.animal;

import Model.Item;

public class AnimalProduct implements Item {
    private int price;
    private String name;
    public AnimalProduct(String name , int price) {
        this.name = name;
        this.price = price;
    }


}
