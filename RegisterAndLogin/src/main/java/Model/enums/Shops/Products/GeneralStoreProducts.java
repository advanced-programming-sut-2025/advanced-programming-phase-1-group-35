package Model.enums.Shops.Products;

import Model.enums.Seasons;

public enum GeneralStoreProducts implements ShopProduct{
    BOUQUET("Item","Bouquet", "A gift that shows your romantic interest.", 1000, 2.0),
    WEDDING_RING("Item","Wedding Ring", "It's used to ask for another farmer's hand in marriage.", 10000, 2.0),
    DEHYDRATOR_RECIPE("CraftingRecipe", "Dehydrator (Recipe)", "A recipe to make Dehydrator", 10000, 1.0),
    GRASS_STARTER_RECIPE("CraftingRecipe","Grass Starter (Recipe)", "A recipe to make Grass Starter", 1000, 1.0),
    OIL("Item","Oil", "All purpose cooking oil.", 200, Double.POSITIVE_INFINITY),
    VINEGAR("Item","Vinegar", "An aged fermented liquid used in many cooking recipes.", 200, Double.POSITIVE_INFINITY),
    DELUXE_RETAINING_SOIL("Item","Deluxe Retaining Soil", "This soil has a 100% chance of staying watered overnight. Mix into tilled soil.", 150, Double.POSITIVE_INFINITY),
    SPEED_GRO("Item","Speed-Gro", "Makes the plants grow 1 day earlier.", 100, Double.POSITIVE_INFINITY),
    APPLE("Sapling", "Apple Sapling", "Takes 28 days to produce a mature Apple tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty.", 4000, Double.POSITIVE_INFINITY),
    APRICOT_SAPLING("Sapling","Apricot Sapling", "Takes 28 days to produce a mature Apricot tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty.", 2000, Double.POSITIVE_INFINITY),
    CHERRY_SAPLING("Sapling","Cherry Sapling", "Takes 28 days to produce a mature Cherry tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty.", 3400, Double.POSITIVE_INFINITY),
    ORANGE_SAPLING("Sapling","Orange Sapling", "Takes 28 days to produce a mature Orange tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty.", 4000, Double.POSITIVE_INFINITY),
    PEACH_SAPLING("Sapling","Peach Sapling", "Takes 28 days to produce a mature Peach tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty.", 6000, Double.POSITIVE_INFINITY),
    POMEGRANATE_SAPLING("Sapling","Pomegranate Sapling", "Takes 28 days to produce a mature Pomegranate tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty.", 6000, Double.POSITIVE_INFINITY),
    BASIC_RETAINING_SOIL("Item", "Basic Retaining Soil", "This soil has a chance of staying watered overnight. Mix into tilled soil.", 100, Double.POSITIVE_INFINITY),
    QUALITY_RETAINING_SOIL("Item", "Quality Retaining Soil", "This soil has a good chance of staying watered overnight. Mix into tilled soil.", 150, Double.POSITIVE_INFINITY),
    LARGE_PACK("Item", "Large Pack", "Unlocks the 2nd row of inventory (12 more slots, total 24).", 2000, 1.0),
    DELUXE_PACK("Item", "Deluxe Pack", "Unlocks the 3rd row of inventory (infinite slots).", 10000, 1.0),
    ;
    private String type;
    private String name , description;
    private int price;
    private Double dailyLimit;
    GeneralStoreProducts(String type , String name, String description, int price, Double dailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.type = type;
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
