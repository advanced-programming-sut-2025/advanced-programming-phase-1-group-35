package Model.enums.animal;

import Model.App;
import Model.Buildings.AnimalHouse;
import Model.Game;
import Model.Rect;
import Model.User;
import Model.animal.Animal;
import Model.animal.AnimalProduct;
import com.badlogic.gdx.graphics.Texture;

public enum AnimalType {
    Chicken(800, 1, "coop", new AnimalProduct[]{AnimalProductDetails.Egg.getProduct(),
        AnimalProductDetails.BigEgg.getProduct(),}, "assets/animals/Chicken.png"),
    Duck(1200, 2, "coop", new AnimalProduct[]{AnimalProductDetails.DuckEgg.getProduct(),
        AnimalProductDetails.DuckFeather.getProduct(),}, "assets/animals/Duck.png"),
    Rabbit(8000, 4, "coop", new AnimalProduct[]{AnimalProductDetails.Wool.getProduct(),
        AnimalProductDetails.RabbitFoot.getProduct(),}, "assets/animals/Rabbit.png"),
    Dinosaur(14000, 7, "coop", new AnimalProduct[]{AnimalProductDetails.DinoEgg.getProduct()}, "assets/animals/Dinosaur.png"),
    Cow(1500, 1, "barn", new AnimalProduct[]{AnimalProductDetails.Milk.getProduct(),
        AnimalProductDetails.BigMilk.getProduct()}, "assets/animals/Cow.png"),
    Goat(4000, 2, "barn", new AnimalProduct[]{AnimalProductDetails.GoatMilk.getProduct(),
        AnimalProductDetails.BigGoatMilk.getProduct()}, "assets/animals/Goat.png"),
    Sheep(8000, 3, "barn", new AnimalProduct[]{AnimalProductDetails.Wool.getProduct()}, "assets/animals/Sheep.png"),
    Pig(16000, 0, "barn", new AnimalProduct[]{AnimalProductDetails.Truffle.getProduct()}, "assets/animals/Pig.png"),
    ;

    private final int buyingPrice;
    private final int productionRate;
    private final AnimalProduct[] products;
    private final String confinement;
    public final String texturePath;

    AnimalType(int buyingPrice, int productionRate, String confinement
        , AnimalProduct[] products, String texturePath) {
        this.buyingPrice = buyingPrice;
        this.productionRate = productionRate;
        this.confinement = confinement;
        this.products = products;
        this.texturePath = texturePath;
    }

    public Animal createAnimal(String name) {
        return new Animal(name, this, buyingPrice, productionRate, confinement, products, getAnimalHouse(App.getCurrentGame().getPlayingUser(), this));
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

    public AnimalHouse getAnimalHouse(User player, AnimalType animalType) {
        AnimalHouse house = null;
        for (AnimalHouse animalHouse : player.getFarm().animalHouses) {
            if (animalHouse.getType().equalsIgnoreCase(animalType.getConfinement())) {
                house = animalHouse;
            }
        }
        return house;
    }

    public Texture getTexture() {
        return new Texture(texturePath);
    }
}
