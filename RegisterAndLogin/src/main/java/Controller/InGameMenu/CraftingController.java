package Controller.InGameMenu;

import Model.App;
import Model.Tile;
import Model.ItemInterface;
import Model.enums.CraftingItems;

import java.util.List;

public class CraftingController {
    private List<CraftingItems> showRecepies(){return null;}
    private ItemInterface craftItem(String itemName, List<String> Ingredients){
        ItemInterface item = null;
        for (CraftingItems craftingItem : CraftingItems.values()) {
            if (craftingItem.name().equals(itemName)) {
                item = craftingItem;
                break;
            }
        }
        for(String ingredient : Ingredients){
    ItemInterface IngredientItem = null;
            for (CraftingItems craftingItem : CraftingItems.values()) {
                if (craftingItem.name().equals(ingredient)) {
                    IngredientItem = craftingItem;
                    break;
                }
            }
            if (IngredientItem == null) {
                System.out.println("Ingredient " + ingredient + " not found");
                return null;
            }
            if(item == null){
                System.out.println("the given item is not found");
                return null;
            }
            if(!App.getCurrentGame().getPlayingUser().getInventory().itemInterfaces.contains(IngredientItem)){
                System.out.println("you don't have " + IngredientItem.getName() + " in your inventory");
                return null;
            }
            if()
        }


    }
    public void addRecipe(CraftingItems recipe){}
    public boolean doesInventoryHasSpace(){return true;}
    public void placeItem(String ItemName, Tile tile){}
    public ItemInterface findItemWithName(String ItemName){return null;}
    public void addItemToInventory(String ItemName){}

}
