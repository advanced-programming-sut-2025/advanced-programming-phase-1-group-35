package core.Controller.InGameMenu;

import com.StardewValley.Main;
import core.GraphicView.CraftingUI;
import core.Model.App;
import core.Model.ItemInterface;
import core.Model.Result;
import core.Model.Tile;
import core.Model.enums.CraftingItems;
import core.Model.enums.CraftingRecipes;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CraftingController {
    private CraftingUI craftingUI;

    public CraftingUI getCraftingUI() {
        return craftingUI;
    }

    public void setCraftingUI(CraftingUI craftingUI) {
        this.craftingUI = craftingUI;
    }


    private CraftingItems selectedItem;

    public void setSelectedItem(CraftingItems item) {
        this.selectedItem = item;
    }

    public CraftingItems getSelectedItem() {
        return selectedItem;
    }

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

    public List<ImageButton> showRecipes() {
        List<ImageButton> buttons = new ArrayList<>();

        for (CraftingRecipes recipe : App.getCurrentGame().getPlayingUser().getCraftingRecipes()) {
            CraftingItems item = (CraftingItems) recipe.getItem();
            Texture texture = new Texture(item.getPath());

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

            ImageButton button = new ImageButton(style);

            // You can also store metadata on the button if needed
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle crafting action
                    craftingUI.getSelectedRecipe().setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
                    craftingUI.getSelectedRecipeLabel().setText(item.getName());
                    selectedItem = item;
                    //System.out.println("Crafting: " + item.getName());
                }
            });

            buttons.add(button);
        }

        return buttons;
    }
    public void handleButtons(){

        if(craftingUI.getCraft().isChecked()){
            craftingUI.getCraft().setChecked(false);
            List<String> ingredientNames = new ArrayList<>();
            for (ItemInterface ingredient : selectedItem.getIngredients().keySet()) {
                int count = selectedItem.getIngredients().get(ingredient);
                for (int i = 0; i < count; i++) {
                    ingredientNames.add(ingredient.getName());
                }
            }
            Result result = craftItem(selectedItem.getName(), ingredientNames);
            if(result.isSuccess()){
                craftingUI.getCraftMessage().setColor(Color.GREEN);
            }
            craftingUI.getCraftMessage().setText(result.toString());

        }

        else if(craftingUI.getBack().isChecked()){
            craftingUI.getBack().setChecked(false);
            Main.getGame().setScreen(craftingUI.getGameMenuUI());
        }
        else if(craftingUI.getAddRecipes().isChecked()){
            craftingUI.getAddRecipes().setChecked(false);
            List<CraftingRecipes> unknownRecipes = Arrays.stream(CraftingRecipes.values())
                .filter(r -> !App.getCurrentGame().getPlayingUser().getCraftingRecipes().contains(r))
                .toList();

            if (!unknownRecipes.isEmpty()) {
                CraftingRecipes cr = unknownRecipes.get(new Random().nextInt(unknownRecipes.size()));
                App.getCurrentGame().getPlayingUser().getCraftingRecipes().add(cr);
            }

            craftingUI.refreshRecipes();
        }

    }

}
