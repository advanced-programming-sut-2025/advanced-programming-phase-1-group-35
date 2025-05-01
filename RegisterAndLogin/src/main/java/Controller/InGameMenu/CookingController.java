package Controller.InGameMenu;

import Model.*;
import Model.enums.CookingIngredient;
import Model.enums.CookingRecipes;

import java.util.Map;

public class CookingController {
    // TODO : check if player is inside the cabin and show proper error if not

    public Result placeItemInFridge(String itemName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        CookingIngredient ingredient = null;
        try {
            ingredient = CookingIngredient.valueOf(itemName);
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid item name");
        }
        if (!player.backPack.ingredients.containsKey(ingredient)) {
            return new Result(false, "You don't have a item in your inventory");
        }
        player.cabin.refrigerator.ingredients.put(ingredient, player.cabin.refrigerator.ingredients.getOrDefault(ingredient, 0) + 1);
        player.backPack.ingredients.put(ingredient, player.backPack.ingredients.get(ingredient) - 1);
        if (player.backPack.ingredients.get(ingredient) == 0) {
            player.backPack.ingredients.remove(ingredient);
        }
        return new Result(true, "successfully added ingredient to the refrigerator");
    }

    public Result pickItemFromFridge(String itemName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        CookingIngredient ingredient = null;
        try {
            ingredient = CookingIngredient.valueOf(itemName);
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid item name");
        }
        if (!player.cabin.refrigerator.ingredients.containsKey(ingredient)) {
            return new Result(false, "You don't have a item in your refrigerator");
        }
        player.cabin.refrigerator.ingredients.put(ingredient, player.cabin.refrigerator.ingredients.get(ingredient) - 1);
        player.backPack.ingredients.put(ingredient, player.backPack.ingredients.getOrDefault(ingredient, 0) + 1);
        if (player.cabin.refrigerator.ingredients.get(ingredient) == 0) {
            player.backPack.ingredients.remove(ingredient);
        }
        return new Result(true, "successfully added ingredient to the refrigerator");
    }

    public Result showCookingRecipes() {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        StringBuilder output = new StringBuilder();
        for (CookingRecipes recipe : player.learnedRecipes) {
            output.append(recipe.getDisplayName()).append("\n");
        }
        return new Result(true, output.toString());
    }

    public void addCookingRecipe(CookingRecipes recipe) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        player.learnedRecipes.add(recipe);
    }

    public Result cook(String recipeName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        CookingRecipes recipe = null;
        try {
            recipe = CookingRecipes.valueOf(recipeName);
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid recipe name");
        }
        if (!player.learnedRecipes.contains(recipe)) {
            return new Result(false, "You don't know how to cook this recipe");
        } else if (!doesPlayerHaveIngredientsForThisFood(recipe, player)) {
            return new Result(false, "You don't have enough ingredients to cook this recipe");
        }
        player.energy.setEnergyAmount(player.energy.getEnergyAmount() - 3);
        for (Map.Entry<CookingIngredient, Integer> entry : recipe.getIngredients().entrySet()) {
            CookingIngredient ingredient = entry.getKey();
            int requiredAmount = entry.getValue();
            int availableAmountInFridge = player.cabin.refrigerator.ingredients.getOrDefault(ingredient, 0);
            int availableAmountInBackPack = player.backPack.ingredients.getOrDefault(ingredient, 0);

            if (availableAmountInBackPack >= requiredAmount) {
                player.backPack.ingredients.put(ingredient, player.backPack.ingredients.get(ingredient) - requiredAmount);
            } else {
                player.cabin.refrigerator.ingredients.put(ingredient, player.cabin.refrigerator.ingredients.get(ingredient) - requiredAmount);
            }
        }
        player.backPack.foods.add(new Food(recipe));
        return new Result(true, "you cooked " + recipeName + " successfully! It looks delicious!");
    }

    private boolean doesPlayerHaveIngredientsForThisFood(CookingRecipes recipe, User player) {
        for (Map.Entry<CookingIngredient, Integer> entry : recipe.getIngredients().entrySet()) {
            CookingIngredient ingredient = entry.getKey();
            int requiredAmount = entry.getValue();
            int availableAmountInFridge = player.cabin.refrigerator.ingredients.getOrDefault(ingredient, 0);
            int availableAmountInBackPack = player.backPack.ingredients.getOrDefault(ingredient, 0);

            if (availableAmountInBackPack < requiredAmount && availableAmountInFridge < requiredAmount) {
                return false;
            }
        }
        return true;
    }

    public Result eatFood(String name) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Food food = player.backPack.getFood(name);
        if (food == null) {
            return new Result(false, "You don't have this food in your backpack!");
        }
        player.backPack.foods.remove(food);
        player.energy.setEnergyAmount(player.energy.getEnergyAmount() + food.recipe.getEnergy());
        // TODO : buff
        return new Result(true, "you ate a " + food.recipe.getDisplayName());
    }

    // TODO
    private boolean doesInventoryHaveSpace() {
        return true;
    }

}
