package Model.enums.animal;

import Model.animal.Animal;
import Model.animal.AnimalProduct;

public enum AnimalType {
    Chicken(800, 1, "coop", new AnimalProduct[]{AnimalProductDetails.Egg.getProduct(),
            AnimalProductDetails.BigEgg.getProduct(),}),
    Duck(1200, 2, "big coop", new AnimalProduct[]{AnimalProductDetails.DuckEgg.getProduct(),
            AnimalProductDetails.DuckFeather.getProduct(),}),
    Rabbit(8000, 4, "deluxe coop", new AnimalProduct[]{AnimalProductDetails.Wool.getProduct(),
            AnimalProductDetails.RabbitFoot.getProduct(),}),
    Dinosaur(14000, 7, "big coop", new AnimalProduct[]{AnimalProductDetails.DinoEgg.getProduct()}),
    Cow(1500, 1, "barn", new AnimalProduct[]{AnimalProductDetails.Milk.getProduct(),
            AnimalProductDetails.BigMilk.getProduct()}),
    Goat(4000, 2, "big barn", new AnimalProduct[]{AnimalProductDetails.GoatMilk.getProduct(),
            AnimalProductDetails.BigGoatMilk.getProduct()}),
    Sheep(8000, 3, "deluxe barn", new AnimalProduct[]{AnimalProductDetails.Wool.getProduct()}),
    Pig(16000, 0, "deluxe barn", new AnimalProduct[]{AnimalProductDetails.Truffle.getProduct()}),
    ;

    private final int buyingPrice;
    private final int productionRate;
    private final AnimalProduct[] products;
    private final String confinement;

    AnimalType(int buyingPrice, int productionRate, String confinement
            , AnimalProduct[] products) {
        this.buyingPrice = buyingPrice;
        this.productionRate = productionRate;
        this.confinement = confinement;
        this.products = products;
    }

    public Animal createAnimal(String name) {
        return new Animal(name, this, buyingPrice, productionRate, confinement, products);
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
}
