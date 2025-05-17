package Model.Shops;

import Controller.InGameMenu.AnimalController;
import Controller.InGameMenu.CookingController;
import Model.CropClasses.Sapling;
import Model.CropClasses.Seed;
import Model.Food;
import Model.Item;
import Model.Mineral;
import Model.Result;
import Model.Tools.FishingPole;
import Model.Tools.Tool;
import Model.animal.Animal;
import Model.enums.CookingRecipes;
import Model.enums.Crops.SaplingEnum;
import Model.enums.Crops.SeedEnum;
import Model.enums.GameMenuCommands;
import Model.enums.Seasons;
import Model.enums.ToolTypes;
import Model.enums.animal.AnimalType;
import View.GameMenu;

import java.io.IOException;
import java.util.regex.Matcher;

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
            AnimalController animalController = new AnimalController();

                GameMenu.print("where do you plan on putting this building buddy ?");
                String input = GameMenu.scan();
                Matcher matcher;
                if (input.equals("cancel")) {
                    return null;
                }
                if ((matcher = GameMenuCommands.buildABuilding.getMatcher(input)) == null) return null;
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                return animalController.buildAnimalHouse(name, x, y);

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
            String animalName = "";
            GameMenu.print("Please enter the name of the animal: ");
            animalName = GameMenu.scan();
            if (name.equals("cancel")) return null;
            AnimalController animalController = new AnimalController();
            while (true) {
                String input = GameMenu.scan();
                Matcher matcher;
                if (input.equals("cancel")) return null;
                if ((matcher = GameMenuCommands.placeAnimal.getMatcher(input)) == null) continue;
                Result result = animalController.buyAnimal(name, animalName);
                if (result.isSuccess()) return result;
            }
        } else if (type.equals("CookingRecipe")) {
            CookingRecipes cookingRecipe = null;
            try {
                cookingRecipe = CookingRecipes.valueOf(name);
            } catch (IllegalArgumentException e) {
                GameMenu.print("illegal Argument");
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
