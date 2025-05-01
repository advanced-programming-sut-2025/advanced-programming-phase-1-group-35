package Model;

import Model.enums.CookingIngredient;
import Model.enums.CookingRecipes;

import java.util.HashMap;

public class Food {
    public CookingRecipes recipe;

    public Food(CookingRecipes recipe) {
        this.recipe = recipe;
    }
}
