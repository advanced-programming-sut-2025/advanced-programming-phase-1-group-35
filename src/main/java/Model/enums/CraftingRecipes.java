package Model.enums;

import Model.ItemInterface;

public enum CraftingRecipes implements ItemConstant {
    CherryBomb("4 copper ore + 1 coal",CraftingItems.CherryBomb),
    Bomb("4 iron ore + 1 coal",CraftingItems.Bomb),
    MegaBomb("4 gold ore + 1 coal",CraftingItems.MegaBomb),
    Sprinkler("1 copper bar + 1 iron bar",CraftingItems.Sprinkler),
    QualitySprinkler("1 Iron bar + 1 Gold bar",CraftingItems.QualitySprinkler),
    IridiumSprinkler("1 gold bar + 1 iridium bar",CraftingItems.IridiumSprinkler),
    CharcoalKlin("20 wood + 2 Copper bar",CraftingItems.CharcoalKlin),
    Furnace("20 Copper ore + 25 Stone",CraftingItems.Furnace),
    Scarecrow("50 wood + 1 coal + 20 Fiber",CraftingItems.Scarecrow),
    DeluxeScarecrow("50 wood + 1 coal + 20 Fiber + 1 iridium ore",CraftingItems.DeluxeScarecrow),
    BeeHouse("40 wood + 8 coal + 1 iron bar",CraftingItems.BeeHouse),
    CheesePress("45 wood + 45 stone + 1 copper bar",CraftingItems.CheesePress),
    Keg("30 wood + 1 copper bar + 1 iron bar",CraftingItems.Keg),
    Loom("60 wood + 30 fiber",CraftingItems.Loom),
    MayonnaiseMachine("15 wood + 15 stone + 1 copper bar",CraftingItems.MayonnaiseMachine),
    OilMaker("100 wood + 1 gold bar + 1 iron bar",CraftingItems.OilMaker),
    PreservesJar("50 wood + 40 stone + 8 coal",CraftingItems.PreservesJar),
    Dehydrator("30 wood + 20 stone + 30 fiber",CraftingItems.Dehydrator),
    GrassStarter("1 wood + 1 fiber",CraftingItems.GrassStarter),
    FishSmoker("50 wood + 3 iron bar + 10 coal",CraftingItems.FishSmoker),
    MysticTreeSeed("5 acorn + 5 maple seed + 5 pine cone + 5 mahogany seed",CraftingItems.MysticTreeSeed),;

    private final String recipe;
    private final ItemInterface item;
    CraftingRecipes(String recipeName, ItemInterface item) {
        this.recipe = recipeName;
        this.item = item;
    }


    @Override
    public ItemInterface getItem(){
        return item;
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
