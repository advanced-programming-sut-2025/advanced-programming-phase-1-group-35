package Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AssetManager {
    // Recipe assets
    public Texture bakedFish = new Texture("assets/recipe/Baked_Fish.png");
    public Texture bread = new Texture("assets/recipe/Bread.png");
    public Texture cookie = new Texture("assets/recipe/Cookie.png");
    public Texture dishOfTheSea = new Texture("assets/recipe/Dish_O_The_Sea.png");
    public Texture farmersLunch = new Texture("assets/recipe/Farmers_Lunch.png");
    public Texture friedEgg = new Texture("assets/recipe/Fried_Egg.png");
    public Texture fruitSalad = new Texture("assets/recipe/Fruit_Salad.png");
    public Texture makiRoll = new Texture("assets/recipe/Maki_Roll.png");
    public Texture minersTreat = new Texture("assets/recipe/Miners_Treat.png");
    public Texture omelet = new Texture("assets/recipe/Omelet.png");
    public Texture pancakes = new Texture("assets/recipe/Pancakes.png");
    public Texture pizza = new Texture("assets/recipe/Pizza.png");
    public Texture pumpkinPie = new Texture("assets/recipe/Pumpkin_Pie.png");
    public Texture redPlate = new Texture("assets/recipe/Red_Plate.png");
    public Texture salad = new Texture("assets/recipe/Salad.png");
    public Texture salmonDinner = new Texture("assets/recipe/Salmon_Dinner.png");
    public Texture seafoamPudding = new Texture("assets/recipe/Seafoam_Pudding.png");
    public Texture spaghetti = new Texture("assets/recipe/Spaghetti.png");
    public Texture survivalBurger = new Texture("assets/recipe/Survival_Burger.png");
    public Texture tortilla = new Texture("assets/recipe/Tortilla.png");
    public Texture tripleShotEspresso = new Texture("assets/recipe/Triple_Shot_Espresso.png");
    public Texture troutSoup = new Texture("assets/recipe/Trout_Soup.png");
    public Texture vegetableMedley = new Texture("assets/recipe/Vegetable_Medley.png");
    public Texture cupOfCoffee = new Texture("assets/recipe/Coffee.png");
    public Texture hashbrowns = new Texture("assets/recipe/Hashbrowns.png");

    // Tool assets
    public Texture axe = new Texture("assets/tools/axe.png");
    public Texture fishingRod = new Texture("assets/tools/fishing_rod.png");
    public Texture hoe = new Texture("assets/tools/hoe.png");
    public Texture milkPail = new Texture("assets/tools/milk_pail.png");
    public Texture pickaxe = new Texture("assets/tools/pickaxe.png");
    public Texture scythe = new Texture("assets/tools/scythe.png");
    public Texture shears = new Texture("assets/tools/shears.png");
    public Texture wateringCan = new Texture("assets/tools/watering_can.png");

    // Cooking Ingredient assets
    public Texture amaranth = new Texture("assets/Cooking_Ingredient/Amaranth.png");
    public Texture apricot = new Texture("assets/Cooking_Ingredient/Apricot.png");
    public Texture beet = new Texture("assets/Cooking_Ingredient/Beet.png");
    public Texture blueberry = new Texture("assets/Cooking_Ingredient/Blueberry.png");
    public Texture carrot = new Texture("assets/Cooking_Ingredient/Carrot.png");
    public Texture cheese = new Texture("assets/Cooking_Ingredient/Cheese.png");
    public Texture coffee = new Texture("assets/Cooking_Ingredient/Coffee.png");
    public Texture corn = new Texture("assets/Cooking_Ingredient/Corn.png");
    public Texture egg = new Texture("assets/Cooking_Ingredient/Egg.png");
    public Texture eggplant = new Texture("assets/Cooking_Ingredient/Eggplant.png");
    public Texture fiber = new Texture("assets/Cooking_Ingredient/Fiber.png");
    public Texture flounder = new Texture("assets/Cooking_Ingredient/Flounder.png");
    public Texture kale = new Texture("assets/Cooking_Ingredient/Kale.png");
    public Texture melon = new Texture("assets/Cooking_Ingredient/Melon.png");
    public Texture midnightCarp = new Texture("assets/Cooking_Ingredient/Midnight_Carp.png");
    public Texture milk = new Texture("assets/Cooking_Ingredient/Milk.png");
    public Texture oil = new Texture("assets/Cooking_Ingredient/Oil.png");
    public Texture parsnip = new Texture("assets/Cooking_Ingredient/Parsnip.png");
    public Texture potato = new Texture("assets/Cooking_Ingredient/Potato.png");
    public Texture pumpkin = new Texture("assets/Cooking_Ingredient/Pumpkin.png");
    public Texture radish = new Texture("assets/Cooking_Ingredient/Radish.png");
    public Texture redCabbage = new Texture("assets/Cooking_Ingredient/Red_Cabbage.png");
    public Texture rice = new Texture("assets/Cooking_Ingredient/Rice.png");
    public Texture salmon = new Texture("assets/Cooking_Ingredient/Salmon.png");
    public Texture sardine = new Texture("assets/Cooking_Ingredient/Sardine.png");
    public Texture sugar = new Texture("assets/Cooking_Ingredient/Sugar.png");
    public Texture tomato = new Texture("assets/Cooking_Ingredient/Tomato.png");
    public Texture wheat = new Texture("assets/Cooking_Ingredient/Wheat.png");

    // Animal Product assets
    public Texture dinosaurEgg = new Texture("assets/animal_product/Dinosaur_Egg.png");
    public Texture duckEgg = new Texture("assets/animal_product/Duck_Egg.png");
    public Texture duckFeather = new Texture("assets/animal_product/Duck_Feather.png");
    public Texture largeEgg = new Texture("assets/animal_product/Large_Egg.png");
    public Texture goatMilk = new Texture("assets/animal_product/Goat_Milk.png");
    public Texture largeGoatMilk = new Texture("assets/animal_product/Large_Goat_Milk.png");
    public Texture largeMilk = new Texture("assets/animal_product/Large_Milk.png");
    public Texture rabbitsFoot = new Texture("assets/animal_product/Rabbits_Foot.png");
    public Texture truffle = new Texture("assets/animal_product/Truffle.png");
    public Texture wool = new Texture("assets/animal_product/Wool.png");

    //Default trees
    public static Texture[] trees = new Texture[]{
        new Texture("Default (1).png"),
        new Texture("Default (2).png"),
        new Texture("Default (3).png"),
    };

    public static Image glow(){
        TextureRegionDrawable glowT = new TextureRegionDrawable(new TextureRegion(new Texture("glow.png")));
        return new Image(glowT);
    }

}
