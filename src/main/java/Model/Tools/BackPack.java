package Model.Tools;

import Model.*;
import Model.enums.CookingIngredient;
import Model.enums.ToolTypes;

import java.util.HashMap;

public class BackPack {
    public HashMap<ItemInterface, Integer> items = new HashMap<>();
    private int capacity = 12;

    public BackPack() {
        this.items.put(new Tool(100, 5, ToolTypes.HOE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.AXE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.PICKAXE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.SCYTHE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.FISHING_ROD), 1);
        this.items.put(new Tool(100, 5, ToolTypes.TRASH_CAN), 1);
        this.items.put(new Tool(100, 5, ToolTypes.MILK_PAIL), 1);
    }

    public boolean doesBackPackHasSpace() {
        return items.size() <= capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void removeItem(Item item) {

    }

    public Food getFood(String foodName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        for (ItemInterface item : items.keySet()) {
            if (item instanceof Food food) {
                if (food.recipe.getDisplayName().equals(foodName)) {
                    return food;
                }
            }
        }
        return null;
    }

    public CookingMaterial getCookingMaterial(CookingIngredient ingredient) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        for (ItemInterface item : items.keySet()) {
            if (item instanceof CookingMaterial && ingredient.toString().equals(((CookingMaterial) item).ingredientName.toString())) {
                return (CookingMaterial) item;
            }
        }
        return null;
    }

    public boolean isToolInBackPack(ToolTypes toolType) {
        for (ItemInterface item : items.keySet()) {
            if (item instanceof Tool toolName) {
                if (toolName.getToolType().equals(toolType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ItemInterface findItem(String itemName) {
        for (ItemInterface item : items.keySet()) {
            if (item instanceof Item) {
                if (item.getName().equals(itemName)) {
                    return item;
                }
            }
        }
        return null;
    }

    public void upgradeBackPack(int newCapacity) {
        // TODO : check if in Pierre shop
        this.capacity = newCapacity;
    }
}
