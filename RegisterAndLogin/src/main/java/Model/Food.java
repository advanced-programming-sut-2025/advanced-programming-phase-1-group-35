package Model;

import Model.enums.CookingIngredient;
import Model.enums.CookingRecipes;

import java.util.HashMap;

public class Food implements ItemInterface {
    public CookingRecipes recipe;

    public Food(CookingRecipes recipe) {
        this.recipe = recipe;
    }

    @Override
    public int getPrice() {
        return recipe.getPrice();
    }

    @Override
    public String getName() {
        return recipe.getDisplayName();
    }
}
