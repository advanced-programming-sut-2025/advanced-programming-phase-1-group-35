package Model.animal;

import Model.ItemInterface;
import Model.enums.animal.AnimalProductDetails;

public class AnimalProduct implements ItemInterface {
    private AnimalProductDetails productDetails;
    private int price;
    private String name;
    public AnimalProduct(String name , int price) {
        this.name = name;
        try {
            this.productDetails = AnimalProductDetails.valueOf(name);
        }catch(IllegalArgumentException e) {}
        this.price = price;
    }

    public AnimalProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(AnimalProductDetails productDetails) {
        this.productDetails = productDetails;
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
