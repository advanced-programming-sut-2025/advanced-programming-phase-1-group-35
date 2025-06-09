package Model.enums.machines;

import Model.ItemInterface;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.Fruit;
import Model.enums.ItemConstant;
import Model.enums.animal.FishType;
import Model.enums.ItemConstant;
import Model.machines.ArtisanProduct;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import static Model.enums.Crops.SeedEnum.SUNFLOWERSEED;
import static Model.enums.animal.AnimalProductDetails.GoatMilk;
import static Model.enums.animal.AnimalProductDetails.Milk;
import static Model.enums.animal.AnimalProductDetails.*;
import static Model.enums.Crops.CropEnum.*;
public enum ArtisanProductDetails implements ItemInterface, ItemConstant {
    Honey("It's a sweet syrup produced by bees.", 75, 4*24, null, 350),
    Cheese("It's your basic cheese.", 100, 3, new HashMap<>() {{put(new ItemInterface[]{Milk}, 1);}}, 230),  // use logic for Large Milk price
    goatCheese("Soft cheese made from goat's milk.", 100, 3, new HashMap<>() {{put(new ItemInterface[]{GoatMilk},1);}}, 400), // handle Large Goat Milk
    Beer("Drink in moderation.", 50, 24, new HashMap<>() {{put(new ItemInterface[]{WHEAT},1);}}, 200),
    Vinegar("An aged fermented liquid used in many cooking recipes.", 13, 10, new HashMap<>() {{put(new ItemInterface[]{UNMILLED_RICE},1);}}, 100),
    Coffee("It smells delicious. This is sure to give you a boost.", 75, 2, new HashMap<>() {{put(new ItemInterface[]{COFFEE_BEAN},5);}}, 150),
    Juice("A sweet, nutritious beverage.", -1, 4*24, new HashMap<>() {{put(CropEnum.values(), 1);}}, -1),//TODO: the given crop base price * 2.5
    Mead("A fermented beverage made from honey.", 100, 10, new HashMap<>() {{put(new ItemInterface[]{Honey},1);}}, 300),
    PaleAle("Drink in moderation.", 50, 3*24, new HashMap<>() {{put(new ItemInterface[]{HOPS},1);}}, 300),
    Wine("Drink in moderation.", -1, 7*24, new HashMap<>() {{put(Fruit.values(),1);}}, -1),  // computed from fruit
    DriedMushroom("A package of gourmet mushrooms.", 50, -1, new HashMap<>() {{put(new ItemInterface[]{COMMON_MUSHROOM},5);put(new ItemInterface[]{RED_MUSHROOM},5);put(new ItemInterface[]{PURPLE_MUSHROOM},5);}}, -1),  // TODO:x*7.5 + 25
    DriedFruit("Chewy pieces of dried fruit.", 75, -1, new HashMap<>() {{put(Fruit.values(),5);}}, -1),        //TODO:x*7.5 +25
    Raisins("It's said to be the Junimos' favorite food.", 125, -1, new HashMap<>() {{put(new ItemInterface[]{GRAPE},5);}}, 600),
    Cloth("A bolt of fine wool cloth.", -1, 4, new HashMap<>() {{put(new ItemInterface[]{Wool},1);}}, 470),
    Mayonnaise("It looks spreadable.", 50, 3, new HashMap<>() {{put(new ItemInterface[]{Egg},1);}}, 190), // handle Large Egg logic
    DuckMayonnaise("It's a rich, yellow mayonnaise.", 75, 3, new HashMap<>() {{put(new ItemInterface[]{DuckEgg},1);}}, 375),
    DinosaurMayonnaise("It's thick and creamy, with a vivid green hue.\nIt smells like grass and leather.", 125, 3, new HashMap<>() {{put(new ItemInterface[]{DinoEgg},1);}}, 800),
    TruffleOil("A gourmet cooking ingredient.", 38, 6, new HashMap<>() {{put(new ItemInterface[]{Truffle},1);}}, 1065),
    CornOil("All purpose cooking oil.", 13, 6, new HashMap<>() {{put(new ItemInterface[]{CORN},1);}}, 100),
    SunFlowerOil("All purpose cooking oil.", 13, 1, new HashMap<>() {{put(new ItemInterface[]{SUNFLOWER},1);}}, 100),
    SunFlowerSeedsOil("All purpose cooking oil.", 13, 48, new HashMap<>() {{put(new ItemInterface[]{SUNFLOWERSEED},1);}}, 100),
    Pickles("A jar of your home-made pickles.", -1, 6, new HashMap<>() {{put(CropEnum.values(),1);}}, -1), // computed
    Jelly("Gooey.", -1, 3*24, new HashMap<>() {{put(Fruit.values(),1);}}, -1),  // computed
    SmokedFish("A whole fish, smoked to perfection.", -1, 1, new HashMap<>() {{put(Fruit.values(),1);}}, -1), // computed
    AnyMetalBar("Turns ore and coal into metal bars.", -1, 4, new HashMap<>() {{put(FishType.values(),1);}}, -1), //TODO:it needs coal too
    Coal("Turns 10 pieces of wood into one piece of coal.", -1, 1, null, 50); //TODO

    public final String description;
    public final int energy;
    public final int processingTime; //Hours
    public final HashMap<ItemInterface[],Integer> ingredients; // to be set manually in logic
    public final int sellPrice;

    ArtisanProductDetails(String description, int energy, int processingTime, HashMap<ItemInterface[],Integer> ingredients, int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }

    public ArtisanProduct getArtisanProduct() {
        return new ArtisanProduct(this);
    }

    @Override
    public int getPrice() {
        return this.sellPrice;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public ItemInterface getItem() {
        return null;
        //TODO
    }
}
