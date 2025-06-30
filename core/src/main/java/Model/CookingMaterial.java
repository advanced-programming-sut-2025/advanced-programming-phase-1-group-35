package Model;

import Model.enums.CookingIngredient;

public class CookingMaterial implements ItemInterface {
    public CookingIngredient ingredientName;

    public CookingMaterial(CookingIngredient cookingIngredient) {
        this.ingredientName = cookingIngredient;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return ingredientName.toString().toLowerCase();
    }
}
