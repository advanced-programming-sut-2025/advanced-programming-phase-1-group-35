package Model.animal;

import Model.Item;
import Model.enums.animal.AnimalProductDetails;

public class AnimalProduct extends Item {
    private AnimalProductDetails productDetails;
    private int price;
    public AnimalProduct(String name , int price) {
        super(name);
        this.productDetails = AnimalProductDetails.valueOf(name);
        this.price = price;
    }

    public AnimalProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(AnimalProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
