package core.Model.Shops;

import core.Controller.InGameMenu.CookingController;
import core.Model.CropClasses.Sapling;
import core.Model.CropClasses.Seed;
import core.Model.Food;
import core.Model.Item;
import core.Model.Mineral;
import core.Model.Tools.FishingPole;
import core.Model.enums.CookingRecipes;
import core.Model.enums.Crops.SaplingEnum;
import core.Model.enums.Crops.SeedEnum;
import core.Model.enums.Seasons;
import core.Model.enums.ToolTypes;


import java.io.IOException;

public class ShopItem {
    private final String name;
    private int price;
    private String description;
    private final int dailyLimit;
    private int dailyBoughtCount;
    private Seasons season = null;
    private String type;

    public ShopItem(String name, int price, int dailyLimit
            , Seasons season, String type, String description) {
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.season = season;
        this.type = type;
        this.description = description;
    }

    public Object makeInstance() throws IOException {
        if (type.equals("Mineral")) {
            return new Mineral(name, description, price);
        } else if (type.equals("ToolUpgrade")) {
            //TODO : add tool upgrade
        } else if (type.equals("Item")) {
            return new Item(price, name);
        } else if (type.equals("AnimalHouse")) {

        } else if (type.equals("CraftingRecipe")) {
            //TODO : Update when crafting recipes added
        } else if (type.equals("Food")) {
            CookingRecipes cookingRecipe = null;
            for (CookingRecipes value : CookingRecipes.values()) {
                if (value.toString().equals(name)) {
                    cookingRecipe = value;
                }
            }
            return new Food(cookingRecipe);
        } else if (type.equals("FishingRod")) {
            return new FishingPole(name, price);
        } else if (type.equals("Sapling")) {
            SaplingEnum sapling = null;
            for (SaplingEnum value : SaplingEnum.values()) {
                if (value.toString().equals(name)) {
                    sapling = value;
                }
            }
            assert sapling != null;
            return new Sapling(sapling.getTree());
        } else if (type.equals("Seed")) {
            SeedEnum seed = null;
            for (SeedEnum value : SeedEnum.values()) {
                if (value.toString().equals(name)) {
                    seed = value;
                }
            }
            assert seed != null;
            return new Seed(seed);
        } else if (type.equals("Tool")) {
            ToolTypes tool = null;
            for (ToolTypes value : ToolTypes.values()) {
                if (value.toString().equals(name)) {
                    tool = value;
                }
            }
            assert tool != null;
            //TODO : update when tools completed
        } else if (type.equals("Animal")) {

        } else if (type.equals("CookingRecipe")) {
            CookingRecipes cookingRecipe = null;
            try {
                cookingRecipe = CookingRecipes.valueOf(name);
            } catch (IllegalArgumentException e) {

            }
            new CookingController().addCookingRecipe(cookingRecipe);
            assert cookingRecipe != null;
            return cookingRecipe;
        }

        return null;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public int getDailyBoughtCount() {
        return dailyBoughtCount;
    }

    public void setDailyBoughtCount(int dailyBoughtCount) {
        this.dailyBoughtCount = dailyBoughtCount;
    }

    public Seasons getSeason() {
        return season;
    }

    public void setSeason(Seasons season) {
        this.season = season;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
