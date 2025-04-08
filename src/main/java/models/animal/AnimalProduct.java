package models.animal;

import models.Item;

public class AnimalProduct extends Item {
    private int price;
    public AnimalProduct(String name , int price) {
        super.name = name;
        this.price = price;
    }


}
