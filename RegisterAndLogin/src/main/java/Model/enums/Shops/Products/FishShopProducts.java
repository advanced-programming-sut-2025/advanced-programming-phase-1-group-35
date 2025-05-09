package Model.enums.Shops.Products;

public enum FishShopProducts implements ShopProduct{
    FISH_SMOKER_RECIPE("CraftingRecipe", "Fish Smoker (Recipe)", "A recipe to make Fish Smoker", 10000, 1.0),
    TROUT_SOUP("Food", "Trout Soup", "Pretty salty.", 250, 1.0),
    BAMBOO_POLE("FishingRod", "Bamboo Pole", "Use in the water to catch fish.", 500, 1.0),
    TRAINING_ROD("FishingRod","Training Rod", "It's a lot easier to use than other rods, but can only catch basic fish.", 25, 1.0),
    FIBERGLASS_ROD("FishingRod","Fiberglass Rod", "Use in the water to catch fish.", 1800, 1.0),
    IRIDIUM_ROD("FishingRod","Iridium Rod", "Use in the water to catch fish.", 7500, 1.0)
    ;
    private String type;
    private String name , description;
    private int price;
    private Double dailyLimit;
    FishShopProducts(String type , String name, String description, int price, Double dailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.type = type;
    }
}
