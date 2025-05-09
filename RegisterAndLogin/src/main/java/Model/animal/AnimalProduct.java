package Model.animal;

import Model.ItemInterface;

public class AnimalProduct implements ItemInterface {
    private int price;
    private String name;
    public AnimalProduct(String name , int price) {
        this.name = name;
        this.price = price;
    }


    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
