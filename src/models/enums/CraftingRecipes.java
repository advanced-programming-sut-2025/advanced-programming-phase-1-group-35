package models.enums;

public enum CraftingRecipes {
    //to be added
    ;

    private final String recipeName;
    private boolean isRecipeKnown;
    CraftingRecipes(String recipeName, boolean isRecipeKnown){
        this.recipeName = recipeName;
        this.isRecipeKnown = isRecipeKnown;
    }
}
