package Model.enums;

public enum CraftingRecipes implements ItemConstant{
    //to be added
    ;

    private final String recipeName;
    private boolean isRecipeKnown;
    CraftingRecipes(String recipeName, boolean isRecipeKnown){
        this.recipeName = recipeName;
        this.isRecipeKnown = isRecipeKnown;
    }
}
