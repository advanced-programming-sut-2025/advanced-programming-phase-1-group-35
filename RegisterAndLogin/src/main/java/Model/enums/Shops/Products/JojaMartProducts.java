package Model.enums.Shops.Products;

import Model.enums.Seasons;

public enum JojaMartProducts implements ShopProduct{
    //year round stock (season is null)
    JOJA_COLA("Food","Joja Cola", "The flagship product of Joja corporation.", 75, Double.POSITIVE_INFINITY, null),
    ANCIENT("Seed", "Ancient Seed", "Could these still grow?", 500, 1.0, null),
    GRASS("Seed", "Grass Starter", "Place this on your farm to start a new patch of grass.", 125, Double.POSITIVE_INFINITY, null),
    SUGAR("Item", "Sugar", "Adds sweetness to pastries and candies. Too much can be unhealthy.", 125, Double.POSITIVE_INFINITY , null),
    WHEAT_FLOUR("Item", "Wheat Flour", "A common cooking ingredient made from crushed wheat seeds.", 125, Double.POSITIVE_INFINITY , null),
    RICE("Item", "Rice", "A basic grain often served under vegetables.", 250, Double.POSITIVE_INFINITY , null),
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
    JojaMartProducts(String type, String name, String description, int price, Double dailyLimit, Seasons season) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.season = season;
    }
}
