package Controller.InGameMenu;

import Model.Tile;
import Model.ItemInterface;
import Model.enums.CraftingItems;

import java.util.List;

public class CraftingController {
    private List<CraftingItems> showRecepies(){return null;}
    private ItemInterface craftItem(String itemName){return null;}
    public void addRecipe(CraftingItems recipe){}
    public boolean doesInventoryHasSpace(){return true;}
    public void placeItem(String ItemName, Tile tile){}
    public ItemInterface findItemWithName(String ItemName){return null;}
    public void addItemToInventory(String ItemName){}

}
