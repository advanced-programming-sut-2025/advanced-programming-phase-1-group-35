package core.Controller;

import core.Model.GameAssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import core.Model.ItemInterface;
import core.Model.enums.CookingRecipes;
import core.Model.enums.CraftingRecipes;
import core.Model.enums.Crops.*;
import core.Model.enums.ItemConstant;
import core.Model.enums.Shops.Products.*;
import core.Model.enums.ToolTypes;
import core.Model.enums.animal.AnimalProductDetails;
import core.Model.enums.animal.FishType;
import core.Model.enums.machines.ArtisanProductDetails;

import java.io.IOException;

public class Controller {
    protected Stage stage;

    public static ItemInterface createItem(String itemName) {
            Class<? extends ItemConstant>[] enumClasses = new Class[]{
                AnimalProductDetails.class,
                ArtisanProductDetails.class,
                BlackSmithProducts.class,
                CarpenterShopProducts.class,
                GeneralStoreProducts.class,
                FishShopProducts.class,
                RanchProducts.class,
                SaloonProducts.class,
                JojaMartProducts.class,
                CookingRecipes.class,
                CraftingRecipes.class,
                CropEnum.class,
                FishType.class,
                ForagingSeeds.class,
                Fruit.class,
                Minerals.class,
                MixedSeeds.class,
                SaplingEnum.class,
                SeedEnum.class,
                ToolTypes.class
            };
            for (Class<? extends ItemConstant> enumClass : enumClasses) {
                for (ItemConstant constant : enumClass.getEnumConstants()) {
                    try {
                        if (constant.getItem().getName().equalsIgnoreCase(itemName)) {
                            return constant;
                        }
                    } catch (IOException e) {
                    }
                }
            }
            return null;
        }


    public void showErrorDialog(String title, String message) {
        Skin skin = GameAssetManager.getDefaultSkin();
        Dialog dialog = new Dialog(title, skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }
    public static String formatUpperSnakeCase(String input) {
        String[] parts = input.split("_");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String word = parts[i].toLowerCase();
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
            }
            if (i < parts.length - 1) {
                result.append("_");
            }
        }
        return result.toString();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
