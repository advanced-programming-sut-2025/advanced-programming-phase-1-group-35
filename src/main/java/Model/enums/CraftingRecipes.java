package Model.enums;

import Model.ItemInterface;

import java.io.IOException;

public enum CraftingRecipes implements ItemConstant {
    CherryBomb("4 copper ore + 1 coal"),
    Bomb("4 iron ore + 1 coal"),
    MegaBomb("4 gold ore + 1 coal"),
    Sprinkler("1 copper bar + 1 iron bar"),
    QualitySprinkler("1 Iron bar + 1 Gold bar"),
    IridiumSprinkler("1 gold bar + 1 iridium bar"),
    CharcoalKlin("20 wood + 2 Copper bar"),
    Furnace("20 Copper ore + 25 Stone"),
    Scarecrow("50 wood + 1 coal + 20 Fiber"),
    DeluxeScarecrow("50 wood + 1 coal + 20 Fiber + 1 iridium ore"),
    BeeHouse("40 wood + 8 coal + 1 iron bar"),
    CheesePress("45 wood + 45 stone + 1 copper bar"),
    Keg("30 wood + 1 copper bar + 1 iron bar"),
    Loom("60 wood + 30 fiber"),
    MayonnaiseMachine("15 wood + 15 stone + 1 copper bar"),
    OilMaker("100 wood + 1 gold bar + 1 iron bar"),
    PreservesJar("50 wood + 40 stone + 8 coal"),
    Dehydrator("30 wood + 20 stone + 30 fiber"),
    GrassStarter("1 wood + 1 fiber"),
    FishSmoker("50 wood + 3 iron bar + 10 coal"),
    MysticTreeSeed("5 acorn + 5 maple seed + 5 pine cone + 5 mahogany seed");

    private final String recipe;

    CraftingRecipes(String recipeName) {
        this.recipe = recipeName;
    }

    @Override
    public ItemInterface getItem() throws IOException {
        return null;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return name(); // Returns the enum name
    }

    public String getRecipe() {
        return recipe;
    }
    @Override
    public String toString() {
        return name() + ": " + getRecipe();
    }
}
