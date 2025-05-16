package Model.enums;

import Model.ItemInterface;

import java.io.IOException;

public enum CraftingRecipes implements ItemConstant{
    //to be added
    ;

    private final String recipeName;
    private boolean isRecipeKnown;
    CraftingRecipes(String recipeName, boolean isRecipeKnown){
        this.recipeName = recipeName;
        this.isRecipeKnown = isRecipeKnown;
    }

    @Override
    public ItemInterface getItem() throws IOException {
        return null;
        //TODO
    }
}
