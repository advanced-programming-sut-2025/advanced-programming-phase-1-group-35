package Model.enums.Shops.Products;

import Model.enums.ItemConstant;
import Model.enums.Seasons;

public enum RanchProducts implements ShopProduct, ItemConstant {
    HAY("Item", "Hay", "Dried grass used as animal food.", 50, Double.POSITIVE_INFINITY),
    MILK_PAIL("Tool", "Milk Pail", "Gather milk from your animals.", 1000, 1.0),
    SHEARS("Tool", "Shears", "Use this to collect wool from sheep", 1000, 1.0),
    CHICKEN("Animal", "Chicken", "Well cared-for chickens lay eggs every day. Lives in the coop.", 800, 2.0),
    COW("Animal","Cow", "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn.", 1500, 2.0),
    GOAT("Animal","Goat", "Happy provide goat milk every other day. A milk pail is required to harvest the milk. Lives in the barn.", 4000, 2.0),
    DUCK("Animal","Duck", "Happy lay duck eggs every other day. Lives in the coop.", 1200, 2.0),
    SHEEP("Animal","Sheep", "Can be shorn for wool. A pair of shears is required to harvest the wool. Lives in the barn.", 8000, 2.0),
    RABBIT("Animal","Rabbit", "These are wooly rabbits! They shed precious wool every few days. Lives in the coop.", 8000, 2.0),
    DINOSAUR("Animal","Dinosaur", "The Dinosaur is a farm animal that lives in a Big Coop", 14000, 2.0),
    PIG("Animal","Pig", "These pigs are trained to find truffles! Lives in the barn.", 16000, 2.0)
    ;
    private String type;
    private String name , description;
    private int price;
    private Double dailyLimit;


    RanchProducts(String type, String name, String description, int price, Double dailyLimit) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getDailyLimit() {
        double dl = 0;
        if(dailyLimit == Double.POSITIVE_INFINITY) return 1000000;
        dl = dailyLimit;
        return (int) dl;
    }

    @Override
    public Seasons getSeason() {
        return null;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
