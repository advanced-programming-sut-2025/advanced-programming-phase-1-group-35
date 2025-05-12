package Model.Tools;

import Model.*;
import Model.enums.CookingIngredient;
import Model.enums.ToolTypes;

import java.util.HashMap;

public class BackPack {
    public HashMap<ItemInterface, Integer> items;
    private int capacity = 12;

    public BackPack() {
        // TODO : initial tools that everyone have
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

    public void upgradeBackPack(int newCapacity) {
        // TODO : check if in Pierre shop
        this.capacity = newCapacity;
    }
}
