package Model.Tools;

import Controller.InGameMenu.ShopMenuController;
import Model.*;
import Model.enums.CookingIngredient;
import Model.enums.CookingRecipes;
import Model.enums.ToolTypes;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack {
    public HashMap<ItemInterface, Integer> items = new HashMap<>();
    public ArrayList<ItemInterface> refrigerator = new ArrayList<>();
    private int capacity = 20;

    public BackPack() {
        this.items.put(new Tool(100, 5, ToolTypes.HOE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.AXE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.PICKAXE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.SCYTHE), 1);
        this.items.put(new Tool(100, 5, ToolTypes.FISHING_ROD), 1);
        this.items.put(new Tool(100, 5, ToolTypes.SHEARS), 1);
        this.items.put(new Tool(100, 5, ToolTypes.MILK_PAIL), 1);
        //this.items.put(new Tool(100, 5, ToolTypes.WATERING_CAN), 1);
        this.items.put(new WateringCan("can"), 1);
        this.items.put(new CookingMaterial(CookingIngredient.EGG), 5);
        this.items.put(new CookingMaterial(CookingIngredient.CARROT), 20);
        this.items.put(new CookingMaterial(CookingIngredient.CHEESE), 3);
        this.items.put(new CookingMaterial(CookingIngredient.TOMATO), 5);
        this.items.put(new Food(CookingRecipes.PIZZA), 2);
        this.refrigerator.add(new Food(CookingRecipes.TROUT_SOUP));
        this.refrigerator.add(new Food(CookingRecipes.OMELET));
        this.refrigerator.add(new CookingMaterial(CookingIngredient.APRICOT));
    }

    public boolean doesBackPackHasSpace() {
        return items.size() < capacity;
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
                if (food.recipe.toString().equalsIgnoreCase(foodName)) {
                    return food;
                }
            }
        }
        return null;
    }

    public CookingMaterial getCookingMaterial(CookingIngredient ingredient) {
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
                if (item.getName().equalsIgnoreCase(itemName)) {
                    return item;
                }
        }
        return null;
    }

    public Result upgradeBackPack() {
        ShopMenuController controller = new ShopMenuController();
        if (controller.findShopByTile(App.getCurrentGame().getPlayingUser().getCurrentTile()) != null &&
                !controller.findShopByTile(App.getCurrentGame().getPlayingUser().getCurrentTile()).
                getName().equalsIgnoreCase("GeneralStore")) {
            return new Result(false, "You are not in Pierre shop");
        }
        if (this.capacity == 20) {
            this.capacity = 32;
            return new Result(true, "capacity now is " + this.capacity);
        } else if (this.capacity > 20) {
            this.capacity = Integer.MAX_VALUE;
            return new Result(true, "capacity now is infinite");
        }
        return null;
    }
}
