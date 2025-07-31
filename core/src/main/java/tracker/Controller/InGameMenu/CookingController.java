package tracker.Controller.InGameMenu;

import common.Model.*;
import common.Model.enums.CookingIngredient;
import common.Model.enums.CookingRecipes;

import java.util.Map;

public class CookingController {
    public Result placeItemInFridge(String itemName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        //User player = App.getLoggedInUser();
        CookingMaterial ingredient = null;
        for (ItemInterface item : player.backPack.items.keySet()) {
            if (item instanceof CookingMaterial && itemName.equals(((CookingMaterial) item).getName())) {
                ingredient = (CookingMaterial) item;
            }
        }
        if (ingredient == null || !player.backPack.items.containsKey(ingredient)) {
            return new Result(false, "You don't have a item in your inventory");
        }
        player.getFarm().getCabin().refrigerator.ingredients.put(ingredient, player.cabin.refrigerator.ingredients.getOrDefault(ingredient, 0) + 1);
        player.backPack.items.put(ingredient, player.backPack.items.get(ingredient) - 1);
        if (player.backPack.items.get(ingredient) == 0) {
            player.backPack.items.remove(ingredient);
        }
        return new Result(true, "successfully added ingredient to the refrigerator");
    }

//    public Result pickItemFromFridge(String itemName) {
//        if (isInCabin() != null) {
//            return isInCabin();
//        }
//        Game game = App.getCurrentGame();
//        User player = game.getPlayingUser();
//        CookingMaterial ingredient = null;
//        for (CookingMaterial material : player.cabin.refrigerator.ingredients.keySet()) {
//            if (itemName.equals(material.getName())) {
//                ingredient = material;
//            }
//        }
//        if (ingredient == null) {
//            return new Result(false, "You don't have a item in your refrigerator");
//        } if (player.backPack.doesBackPackHasSpace()) {
//            player.cabin.refrigerator.ingredients.put(ingredient, player.cabin.refrigerator.ingredients.get(ingredient) - 1);
//            player.backPack.items.put(ingredient, player.backPack.items.getOrDefault(ingredient, 0) + 1);
//        } else {
//            return new Result(false, "You don't have space in your backpack");
//        }
//        if (player.cabin.refrigerator.ingredients.get(ingredient) == 0) {
//            player.cabin.refrigerator.ingredients.remove(ingredient);
//        }
//        return new Result(true, "successfully added ingredient to the refrigerator");
//    }

    public Result showCookingRecipes() {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        //User player = App.getLoggedInUser();
        if (player.learnedRecipes.isEmpty()) {
            addCookingRecipe(CookingRecipes.FRIED_EGG);
            addCookingRecipe(CookingRecipes.PIZZA);
            addCookingRecipe(CookingRecipes.COOKIE);
            addCookingRecipe(CookingRecipes.BAKED_FISH);
            addCookingRecipe(CookingRecipes.SALAD);
            addCookingRecipe(CookingRecipes.OMELET);
            addCookingRecipe(CookingRecipes.PUMPKIN_PIE);
            addCookingRecipe(CookingRecipes.SPAGHETTI);
            addCookingRecipe(CookingRecipes.TORTILLA);
            addCookingRecipe(CookingRecipes.MAKI_ROLL);
            addCookingRecipe(CookingRecipes.TRIPLE_SHOT_ESPRESSO);
            addCookingRecipe(CookingRecipes.PANCAKES);
            addCookingRecipe(CookingRecipes.FRUIT_SALAD);
            addCookingRecipe(CookingRecipes.RED_PLATE);
            addCookingRecipe(CookingRecipes.BREAD);
        }

        StringBuilder output = new StringBuilder();
        output.append("Cooking recipes:\n");
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
            recipe = CookingRecipes.valueOf(recipeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid recipe name");
        }
        if (!player.learnedRecipes.contains(recipe)) {
            return new Result(false, "You don't know how to cook this recipe");
        } else if (!doesPlayerHaveIngredientsForThisFood(recipe, player)) {
            return new Result(false, "You don't have enough ingredients to cook this recipe");
        }
        player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() - 3);
        for (Map.Entry<CookingIngredient, Integer> entry : recipe.getIngredients().entrySet()) {
            CookingIngredient ingredient = entry.getKey();
            CookingMaterial material = player.backPack.getCookingMaterial(entry.getKey());
            int requiredAmount = entry.getValue();
            int availableAmountInBackPack = player.backPack.items.getOrDefault(material, 0);

            if (availableAmountInBackPack >= requiredAmount) {
                player.backPack.items.put(material, player.backPack.items.get(material) - requiredAmount);
            } else {
                return new Result(false, "You don't have enough ingredients to cook this recipe");
            }
        }
        if (!player.backPack.doesBackPackHasSpace()) {
            return new Result(false, "You don't have enough space in your backpack");
        }
        player.backPack.items.put(new Food(recipe), 1);
        return new Result(true, "you cooked " + recipeName + " successfully! It looks delicious! your energy is: " + player.getEnergy().getEnergyAmount());
    }

    private boolean doesPlayerHaveIngredientsForThisFood(CookingRecipes recipe, User player) {
        for (Map.Entry<CookingIngredient, Integer> entry : recipe.getIngredients().entrySet()) {
            CookingIngredient ingredient = entry.getKey();
            CookingMaterial material = player.backPack.getCookingMaterial(ingredient);
            int requiredAmount = entry.getValue();
            int availableAmountInBackPack = player.backPack.items.getOrDefault(material, 0);

            if (availableAmountInBackPack < requiredAmount) {
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
        player.backPack.items.remove(food);
        player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() + food.recipe.getEnergy());
        return new Result(true, "you ate a " + food.recipe.getDisplayName());
    }

}
