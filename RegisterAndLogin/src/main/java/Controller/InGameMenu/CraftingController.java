package Controller.InGameMenu;

import Model.App;
import Model.Result;
import Model.Tile;
import Model.ItemInterface;
import Model.enums.CraftingItems;

import java.util.List;

public class CraftingController {
    private List<CraftingItems> showRecepies(){return null;}


    private Result craftItem(String itemName, List<String> Ingredients){
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
                return new Result(false,"Ingredient " + ingredient + " not found");
            }
            if(item == null){
                return new Result(false,"the given item is not found");
            }
            if(!App.getCurrentGame().getPlayingUser().backPack.items.keySet().contains(IngredientItem)){
                return new Result(false,"you don't have " + IngredientItem.getName() + " in your inventory");
            }
            CraftingItems craftingItem = (CraftingItems)item;
            int amountInInventory = App.getCurrentGame().getPlayingUser().backPack.items.get(craftingItem);
            int requiredAmount = craftingItem.getIngredients().get(IngredientItem);
            if(amountInInventory < requiredAmount){
                return new Result(false ,"You don't have enough " + IngredientItem.getName() + " in your inventory\n" +
                        "you need " + requiredAmount + " " + IngredientItem.getName());
            }
            App.getCurrentGame().getPlayingUser().backPack.items.remove(IngredientItem,requiredAmount);
        }
        if(!App.getCurrentGame().getPlayingUser().backPack.doesBackPackHasSpace()){
            return new Result(false,"you don't have enough space in your inventory");
        }
        App.getCurrentGame().getPlayingUser().backPack.items.put(item,1);
        return new Result(true,itemName + " crafted and is now available");
    }
    public void addRecipe(CraftingItems recipe){}
    public boolean doesInventoryHasSpace(){return true;}
    public void placeItem(String ItemName, Tile tile){}
    public ItemInterface findItemWithName(String ItemName){return null;}
    public void addItemToInventory(String ItemName){}

}
