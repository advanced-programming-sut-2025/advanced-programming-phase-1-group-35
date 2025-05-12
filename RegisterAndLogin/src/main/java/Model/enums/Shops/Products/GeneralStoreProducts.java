package Model.enums.Shops.Products;

import Model.enums.Seasons;

public enum GeneralStoreProducts implements ShopProduct{
    //All year round stock
    BOUQUET("Item","Bouquet", "A gift that shows your romantic interest.", 1000, 2.0, null),
    WEDDING_RING("Item","Wedding Ring", "It's used to ask for another farmer's hand in marriage.", 10000, 2.0, null),
    DEHYDRATOR_RECIPE("CraftingRecipe", "Dehydrator (Recipe)", "A recipe to make Dehydrator", 10000, 1.0, null),
    GRASS_STARTER_RECIPE("CraftingRecipe","Grass Starter (Recipe)", "A recipe to make Grass Starter", 1000, 1.0, null),
    OIL("Item","Oil", "All purpose cooking oil.", 200, Double.POSITIVE_INFINITY, null),
    VINEGAR("Item","Vinegar", "An aged fermented liquid used in many cooking recipes.", 200, Double.POSITIVE_INFINITY, null),
    DELUXE_RETAINING_SOIL("Item","Deluxe Retaining Soil", "This soil has a 100% chance of staying watered overnight. Mix into tilled soil.", 150, Double.POSITIVE_INFINITY, null),
    SPEED_GRO("Item","Speed-Gro", "Makes the plants grow 1 day earlier.", 100, Double.POSITIVE_INFINITY, null),
    APPLE("Sapling", "Apple Sapling", "Takes 28 days to produce a mature Apple tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty.", 4000, Double.POSITIVE_INFINITY, null),
    APRICOT_SAPLING("Sapling","Apricot Sapling", "Takes 28 days to produce a mature Apricot tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty.", 2000, Double.POSITIVE_INFINITY, null),
    CHERRY_SAPLING("Sapling","Cherry Sapling", "Takes 28 days to produce a mature Cherry tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty.", 3400, Double.POSITIVE_INFINITY, null),
    ORANGE_SAPLING("Sapling","Orange Sapling", "Takes 28 days to produce a mature Orange tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty.", 4000, Double.POSITIVE_INFINITY, null),
    PEACH_SAPLING("Sapling","Peach Sapling", "Takes 28 days to produce a mature Peach tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty.", 6000, Double.POSITIVE_INFINITY, null),
    POMEGRANATE_SAPLING("Sapling","Pomegranate Sapling", "Takes 28 days to produce a mature Pomegranate tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty.", 6000, Double.POSITIVE_INFINITY, null),
    BASIC_RETAINING_SOIL("Item", "Basic Retaining Soil", "This soil has a chance of staying watered overnight. Mix into tilled soil.", 100, Double.POSITIVE_INFINITY, null),
    QUALITY_RETAINING_SOIL("Item", "Quality Retaining Soil", "This soil has a good chance of staying watered overnight. Mix into tilled soil.", 150, Double.POSITIVE_INFINITY, null),
    //BackPacks
    LARGE_PACK("BackPack", "Large Pack", "Unlocks the 2nd row of inventory (12 more slots, total 24).", 2000, 1.0, null),
    DELUXE_PACK("BackPack", "Deluxe Pack", "Unlocks the 3rd row of inventory (infinite slots).", 10000, 1.0, null),
    // Spring Stock
    PARSNIP("Seed","Parsnip Seeds", "Plant these in the spring. Takes 4 days to mature.", 25, 5.0, Seasons.Spring),
    BEAN("Seed","Bean Starter", "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis.", 75, 5.0 , Seasons.Spring),
    CAULIFLOWER("Seed","Cauliflower Seeds", "Plant these in the spring. Takes 12 days to produce a large cauliflower.", 100, 5.0, Seasons.Spring),
    POTATO("Seed","Potato Seeds", "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest.", 62, 5.0, Seasons.Spring),
    STRAWBERRY("Seed","Strawberry Seeds", "Plant these in spring. Takes 8 days to mature, and keeps producing strawberries after that.", 100, 5.0, Seasons.Spring),
    TULIP("Seed","Tulip Bulb", "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors.", 25, 5.0, Seasons.Spring),
    KALE("Seed","Kale Seeds", "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe.", 87, 5.0, Seasons.Spring),
    COFFEE("Seed","Coffee Beans", "Plant in summer or spring. Takes 10 days to grow, Then produces coffee Beans every other day.", 200, 1.0, Seasons.Spring),
    CARROT("Seed","Carrot Seeds", "Plant in the spring. Takes 3 days to grow.", 5, 10.0, Seasons.Spring),
    RHUBARB("Seed","Rhubarb Seeds", "Plant these in the spring. Takes 13 days to mature.", 100, 5.0, Seasons.Spring),
    JAZZ("Seed","Jazz Seeds", "Plant in spring. Takes 7 days to produce a blue puffball flower.", 37, 5.0, Seasons.Spring),
    // Summer Stock
    TOMATO("Seed", "Tomato Seeds", "Plant these in the summer. Takes 11 days to mature, and continues to produce after first harvest.", 62, 5.0, Seasons.Summer),
    PEPPER("Seed","Pepper Seeds", "Plant these in the summer. Takes 5 days to mature, and continues to produce after first harvest.", 50, 5.0, Seasons.Summer),
    WHEAT("Seed","Wheat Seeds", "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe.", 12, 10.0, Seasons.Summer),
    SUMMER_SQUASH("Seed","Summer Squash Seeds", "Plant in the summer. Takes 6 days to grow, and continues to produce after first harvest.", 10, 10.0, Seasons.Summer),
    RADISH("Seed","Radish Seeds", "Plant these in the summer. Takes 6 days to mature.", 50, 5.0, Seasons.Summer),
    MELON("Seed","Melon Seeds", "Plant these in the summer. Takes 12 days to mature.", 100, 5.0, Seasons.Summer),
    HOPS("Seed","Hops Starter", "Plant these in the summer. Takes 11 days to grow, but keeps producing after that. Grows on a trellis.", 75, 5.0, Seasons.Summer),
    POPPY("Seed","Poppy Seeds", "Plant in summer. Produces a bright red flower in 7 days.", 125, 5.0, Seasons.Summer),
    SPANGLE("Seed","Spangle Seeds", "Plant in summer. Takes 8 days to produce a vibrant tropical flower. Assorted colors.", 62, 5.0, Seasons.Summer),
    STARFRUIT("Seed","Starfruit Seeds", "Plant these in the summer. Takes 13 days to mature.", 400, 5.0, Seasons.Summer),
    SUNFLOWER("Seed","Sunflower Seeds", "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.", 125, 5.0, Seasons.Summer),
    // Fall Stock
    CORN("Seed", "Corn Seeds", "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest.", 187, 5.0, Seasons.Fall),
    EGGPLANT("Seed","Eggplant Seeds", "Plant these in the fall. Takes 5 days to mature, and continues to produce after first harvest.", 25, 5.0, Seasons.Fall),
    PUMPKIN("Seed","Pumpkin Seeds", "Plant these in the fall. Takes 13 days to mature.", 125, 5.0, Seasons.Fall),
    BROCCOLI("Seed","Broccoli Seeds", "Plant in the fall. Takes 8 days to mature, and continues to produce after first harvest.", 15, 5.0, Seasons.Fall),
    AMARANTH("Seed","Amaranth Seeds", "Plant these in the fall. Takes 7 days to grow. Harvest with the scythe.", 87, 5.0, Seasons.Fall),
    GRAPE("Seed","Grape Starter", "Plant these in the fall. Takes 10 days to grow, but keeps producing after that. Grows on a trellis.", 75, 5.0, Seasons.Fall),
    BEET("Seed","Beet Seeds", "Plant these in the fall. Takes 6 days to mature.", 20, 5.0, Seasons.Fall),
    YAM("Seed","Yam Seeds", "Plant these in the fall. Takes 10 days to mature.", 75, 5.0, Seasons.Fall),
    BOK_CHO("Seed","Bok Choy Seeds", "Plant these in the fall. Takes 4 days to mature.", 62, 5.0, Seasons.Fall),
    CRANBERRY("Seed","Cranberry Seeds", "Plant these in the fall. Takes 7 days to mature, and continues to produce after first harvest.", 300, 5.0, Seasons.Fall),
    FAIRY("Seed","Fairy Seeds", "Plant in fall. Takes 12 days to produce a mysterious flower. Assorted Colors.", 250, 5.0, Seasons.Fall),
    RARE("Seed","Rare Seed", "Sow in fall. Takes all season to grow.", 1000, 1.0, Seasons.Fall),
    // Winter Stock
    POWDERMELON("Seed","Powdermelon Seeds", "This special melon grows in the winter. Takes 7 days to grow.", 20, 10.0, Seasons.Winter),
    ;
    private String type;
    private String name , description;
    private int price;
    private Double dailyLimit;
    private Seasons season;
    GeneralStoreProducts(String type , String name, String description, int price, Double dailyLimit, Seasons season) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.type = type;
        this.season = season;
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
        return season;
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
