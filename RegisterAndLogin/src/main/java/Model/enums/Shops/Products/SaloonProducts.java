package Model.enums.Shops.Products;

public enum SaloonProducts {
    BEER("Food", "Beer", "Drink in moderation.", 400, Double.POSITIVE_INFINITY),
    SALAD("Food","Salad", "A healthy garden salad.", 220, Double.POSITIVE_INFINITY),
    BREAD("Food","Bread", "A crusty baguette.", 120, Double.POSITIVE_INFINITY),
    SPAGHETTI("Food","Spaghetti", "An old favorite.", 240, Double.POSITIVE_INFINITY),
    PIZZA("Food","Pizza", "It's popular for all the right reasons.", 600, Double.POSITIVE_INFINITY),
    COFFEE("Food","Coffee", "It smells delicious. This is sure to give you a boost.", 300, Double.POSITIVE_INFINITY),
    HASHBROWNS_RECIPE("CookingRecipe","Hashbrowns Recipe", "A recipe to make Hashbrowns", 50, 1.0),
    OMELET_RECIPE("CookingRecipe","Omelet Recipe", "A recipe to make Omelet", 100, 1.0),
    PANCAKES_RECIPE("CookingRecipe","Pancakes Recipe", "A recipe to make Pancakes", 100, 1.0),
    BREAD_RECIPE("CookingRecipe","Bread Recipe", "A recipe to make Bread", 100, 1.0),
    TORTILLA_RECIPE("CookingRecipe","Tortilla Recipe", "A recipe to make Tortilla", 100, 1.0),
    PIZZA_RECIPE("CookingRecipe","Pizza Recipe", "A recipe to make Pizza", 150, 1.0),
    MAKI_ROLL_RECIPE("CookingRecipe","Maki Roll Recipe", "A recipe to make Maki Roll", 300, 1.0),
    TRIPLE_SHOT_ESPRESSO_RECIPE("CookingRecipe","Triple Shot Espresso Recipe", "A recipe to make Triple Shot Espresso", 5000, 1.0),
    COOKIE_RECIPE("CookingRecipe","Cookie Recipe", "A recipe to make Cookie", 300, 1.0),
    ;
    private String type;
    private String name , description;
    private int price;
    private Double dailyLimit;

    SaloonProducts(String type, String name, String description, int price, Double dailyLimit) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }
}
