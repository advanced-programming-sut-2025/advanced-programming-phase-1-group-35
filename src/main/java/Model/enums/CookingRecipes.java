package Model.enums;

import Model.Food;
import Model.ItemInterface;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public enum CookingRecipes implements ItemConstant{
    FRIED_EGG("Fried Egg", Map.of(CookingIngredient.EGG, 1), 50, "", "Starter", 35),
    BAKED_FISH("Baked Fish", Map.of(
            CookingIngredient.SARDINE, 1,
            CookingIngredient.SALMON, 1,
            CookingIngredient.WHEAT, 1
    ), 75, "", "Starter", 100),
    SALAD("Salad", Map.of(
            CookingIngredient.LEEK, 1,
            CookingIngredient.DANDELION, 1
    ), 113, "", "Starter", 110),
    OMELET("Omelet", Map.of(
            CookingIngredient.EGG, 1,
            CookingIngredient.MILK, 1
    ), 100, "", "Stardrop Saloon", 125),
    PUMPKIN_PIE("Pumpkin Pie", Map.of(
            CookingIngredient.PUMPKIN, 1,
            CookingIngredient.WHEAT_FLOUR, 1,
            CookingIngredient.MILK, 1,
            CookingIngredient.SUGAR, 1
    ), 225, "", "Stardrop Saloon", 385),
    SPAGHETTI("Spaghetti", Map.of(
            CookingIngredient.WHEAT_FLOUR, 1,
            CookingIngredient.TOMATO, 1
    ), 75, "", "Stardrop Saloon", 120),
    PIZZA("Pizza", Map.of(
            CookingIngredient.WHEAT_FLOUR, 1,
            CookingIngredient.TOMATO, 1,
            CookingIngredient.CHEESE, 1
    ), 150, "", "Stardrop Saloon", 300),
    TORTILLA("Tortilla", Map.of(CookingIngredient.CORN, 1), 50, "", "Stardrop Saloon", 50),
    MAKI_ROLL("Maki Roll", Map.of(
            CookingIngredient.SARDINE, 1,
            CookingIngredient.RICE, 1,
            CookingIngredient.FIBER, 1
    ), 100, "", "Stardrop Saloon", 220),
    TRIPLE_SHOT_ESPRESSO("Triple Shot Espresso", Map.of(CookingIngredient.COFFEE, 3), 200, "Max Energy +100 (5h)", "Stardrop Saloon", 450),
    COOKIE("Cookie", Map.of(
            CookingIngredient.WHEAT_FLOUR, 1,
            CookingIngredient.SUGAR, 1,
            CookingIngredient.EGG, 1
    ), 90, "", "Stardrop Saloon", 140),
    HASH_BROWNS("Hash Browns", Map.of(
            CookingIngredient.POTATO, 1,
            CookingIngredient.OIL, 1
    ), 90, "Farming (5h)", "Stardrop Saloon", 120),
    PANCAKES("Pancakes", Map.of(
            CookingIngredient.WHEAT_FLOUR, 1,
            CookingIngredient.EGG, 1
    ), 90, "Foraging (11h)", "Stardrop Saloon", 80),
    FRUIT_SALAD("Fruit Salad", Map.of(
            CookingIngredient.BLUEBERRY, 1,
            CookingIngredient.MELON, 1,
            CookingIngredient.APRICOT, 1
    ), 263, "", "Stardrop Saloon", 450),
    RED_PLATE("Red Plate", Map.of(
            CookingIngredient.RED_CABBAGE, 1,
            CookingIngredient.RADISH, 1
    ), 240, "Max Energy +50 (3h)", "Stardrop Saloon", 400),
    BREAD("Bread", Map.of(CookingIngredient.WHEAT_FLOUR, 1), 50, "", "Stardrop Saloon", 60),
    SALMON_DINNER("Salmon Dinner", Map.of(
            CookingIngredient.SALMON, 1,
            CookingIngredient.AMARANTH, 1,
            CookingIngredient.KALE, 1
    ), 125, "", "Leah reward", 300),
    VEGETABLE_MEDLEY("Vegetable Medley", Map.of(
            CookingIngredient.TOMATO, 1,
            CookingIngredient.BEET, 1
    ), 165, "", "Foraging Level 2", 120),
    FARMERS_LUNCH("Farmer's Lunch", Map.of(
            CookingIngredient.OMELET, 1,
            CookingIngredient.PARSNIP, 1
    ), 200, "Farming (5h)", "Farming Level 1", 150),
    SURVIVAL_BURGER("Survival Burger", Map.of(
            CookingIngredient.BREAD, 1,
            CookingIngredient.CARROT, 1,
            CookingIngredient.EGGPLANT, 1
    ), 125, "Foraging (5h)", "Foraging Level 3", 180),
    DISH_O_THE_SEA("Dish O' the Sea", Map.of(
            CookingIngredient.SARDINE, 2,
            CookingIngredient.HASH_BROWNS, 1
    ), 150, "Fishing (5h)", "Fishing Level 2", 220),
    SEAFOAM_PUDDING("Seafoam Pudding", Map.of(
            CookingIngredient.FLOUNDER, 1,
            CookingIngredient.MIDNIGHT_CARP, 1
    ), 175, "Fishing (10h)", "Fishing Level 3", 300),
    MINERS_TREAT("Miner's Treat", Map.of(
            CookingIngredient.CARROT, 2,
            CookingIngredient.SUGAR, 1,
            CookingIngredient.MILK, 1
    ), 125, "Mining (5h)", "Mining Level 1", 200),
    TROUT_SOUP("trout soup", null, 200 , "", "", 250),

    ;

    private final String displayName;
    private final Map<CookingIngredient, Integer> ingredients;
    private final int energy;
    private final String buff;
    private final String source;
    private final int price;

    CookingRecipes(String displayName, Map<CookingIngredient, Integer> ingredients, int energy, String buff, String source, int price) {
        this.displayName = displayName;
        this.ingredients = Collections.unmodifiableMap(ingredients);
        this.energy = energy;
        this.buff = buff;
        this.source = source;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<CookingIngredient, Integer> getIngredients() {
        return ingredients;
    }

    public int getEnergy() {
        return energy;
    }

    public String getBuff() {
        return buff;
    }

    public String getSource() {
        return source;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return this.displayName;
    }

    @Override
    public ItemInterface getItem() throws IOException {
        return new Food(this);
        //TODO
    }
}
