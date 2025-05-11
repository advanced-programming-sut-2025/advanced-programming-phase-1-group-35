package Model;

import Model.enums.CookingIngredient;

public class CookingMaterial implements ItemInterface {
    public CookingIngredient ingredientName;

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return ingredientName.toString().toLowerCase();
    }
}
