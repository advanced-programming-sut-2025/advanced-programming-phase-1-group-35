package Model.animal;

import Model.Item;

public class AnimalProduct extends Item {
    private int price;
    public AnimalProduct(String name , int price) {
        super(name);
        this.price = price;
    }


}
