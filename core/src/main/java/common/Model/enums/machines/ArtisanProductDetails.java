package common.Model.enums.machines;

import common.Model.ItemInterface;
import common.Model.enums.Crops.CropEnum;
import common.Model.enums.Crops.Fruit;
import common.Model.enums.ItemConstant;
import common.Model.enums.animal.FishType;
import common.Model.machines.ArtisanProduct;

import java.util.HashMap;

import static common.Model.enums.Crops.CropEnum.*;
import static common.Model.enums.Crops.SeedEnum.SUNFLOWERSEED;
import static common.Model.enums.animal.AnimalProductDetails.*;
public enum ArtisanProductDetails implements ItemInterface, ItemConstant {
    Honey("It's a sweet syrup produced by bees.", 75, 4*24, null, 350,"craftingItems/honey"),
    Cheese("It's your basic cheese.", 100, 3, new HashMap<>() {{put(new ItemInterface[]{Milk}, 1);}}, 230,"craftingItems/cheese"),
    goatCheese("Soft cheese made from goat's milk.", 100, 3, new HashMap<>() {{put(new ItemInterface[]{GoatMilk},1);}}, 400,"craftingItems/goatcheese"),
    Beer("Drink in moderation.", 50, 24, new HashMap<>() {{put(new ItemInterface[]{WHEAT},1);}}, 200,"craftingItems/beer"),
    Vinegar("An aged fermented liquid used in many cooking recipes.", 13, 10, new HashMap<>() {{put(new ItemInterface[]{UNMILLED_RICE},1);}}, 100,"craftingItems/vinegar"),
    Coffee("It smells delicious. This is sure to give you a boost.", 75, 2, new HashMap<>() {{put(new ItemInterface[]{COFFEE_BEAN},5);}}, 150,"craftingItems/coffee"),
    Juice("A sweet, nutritious beverage.", -1, 4*24, new HashMap<>() {{put(CropEnum.values(), 1);}}, -1,"craftingItems/juice"),//TODO: the given crop base price * 2.5
    Mead("A fermented beverage made from honey.", 100, 10, new HashMap<>() {{put(new ItemInterface[]{Honey},1);}}, 300,"craftingItems/mead"),
    PaleAle("Drink in moderation.", 50, 3*24, new HashMap<>() {{put(new ItemInterface[]{HOPS},1);}}, 300,"craftingItems/paleale"),
    Wine("Drink in moderation.", -1, 7*24, new HashMap<>() {{put(Fruit.values(),1);}}, -1,"craftingItems/wine"),
    DriedMushroom("A package of gourmet mushrooms.", 50, -1, new HashMap<>() {{put(new ItemInterface[]{COMMON_MUSHROOM},5);put(new ItemInterface[]{RED_MUSHROOM},5);put(new ItemInterface[]{PURPLE_MUSHROOM},5);}}, -1,"craftingItems/driedmushroom"),
    DriedFruit("Chewy pieces of dried fruit.", 75, -1, new HashMap<>() {{put(Fruit.values(),5);}}, -1,"craftingItems/driedfruit"), //TODO:x*7.5 +25
    Raisins("It's said to be the Junimos' favorite food.", 125, -1, new HashMap<>() {{put(new ItemInterface[]{GRAPE},5);}}, 600,"craftingItems/raisins"),
    Cloth("A bolt of fine wool cloth.", -1, 4, new HashMap<>() {{put(new ItemInterface[]{Wool},1);}}, 470,"craftingItems/cloth"),
    Mayonnaise("It looks spreadable.", 50, 3, new HashMap<>() {{put(new ItemInterface[]{Egg},1);}}, 190,"craftingItems/mayonnaise"),
    DuckMayonnaise("It's a rich, yellow mayonnaise.", 75, 3, new HashMap<>() {{put(new ItemInterface[]{DuckEgg},1);}}, 375,"craftingItems/duckmayonnaise"),
    DinosaurMayonnaise("It's thick and creamy, with a vivid green hue.\nIt smells like grass and leather.", 125, 3, new HashMap<>() {{put(new ItemInterface[]{DinoEgg},1);}}, 800,"craftingItems/dinomayonnaise"),
    TruffleOil("A gourmet cooking ingredient.", 38, 6, new HashMap<>() {{put(new ItemInterface[]{Truffle},1);}}, 1065,"craftingItems/truffleoil"),
    CornOil("All purpose cooking oil.", 13, 6, new HashMap<>() {{put(new ItemInterface[]{CORN},1);}}, 100,"craftingItems/cornoil"),
    SunFlowerOil("All purpose cooking oil.", 13, 1, new HashMap<>() {{put(new ItemInterface[]{SUNFLOWER},1);}}, 100,"craftingItems/sunfloweroil"),
    SunFlowerSeedsOil("All purpose cooking oil.", 13, 48, new HashMap<>() {{put(new ItemInterface[]{SUNFLOWERSEED},1);}}, 100,"craftingItems/sunflowerseedsoil"),
    Pickles("A jar of your home-made pickles.", -1, 6, new HashMap<>() {{put(CropEnum.values(),1);}}, -1,"craftingItems/pickles"),
    Jelly("Gooey.", -1, 3*24, new HashMap<>() {{put(Fruit.values(),1);}}, -1,"craftingItems/jelly"),
    SmokedFish("A whole fish, smoked to perfection.", -1, 1, new HashMap<>() {{put(FishType.values(),1);}}, -1,"craftingItems/smokedfish"),
    AnyMetalBar("Turns ore and coal into metal bars.", -1, 4, new HashMap<>() {{put(FishType.values(),1);}}, -1,"craftingItems/anymetalbar"),    // TODO:it needs coal too
    Coal("Turns 10 pieces of wood into one piece of coal.", -1, 1, null, 50,"craftingItems/coal");   //TODO


    public final String description;
    public final int energy;
    public final int processingTime; //Hours
    public final HashMap<ItemInterface[],Integer> ingredients; // to be set manually in logic
    public final int sellPrice;
    public final String path;
    ArtisanProductDetails(String description, int energy, int processingTime, HashMap<ItemInterface[],Integer> ingredients, int sellPrice, String path) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
        this.path = path;
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
