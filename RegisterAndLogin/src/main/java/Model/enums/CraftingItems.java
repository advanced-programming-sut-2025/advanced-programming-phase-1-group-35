package Model.enums;

import Model.ItemInterface;
import Model.Tools.SkillLevel;
import Model.enums.Crops.ForagingSeeds;
import Model.enums.Crops.Minerals;
import Model.enums.Crops.SaplingEnum;
import Model.enums.Crops.SeedEnum;
import Model.enums.Shops.Products.BlackSmithProducts;
import Model.enums.Shops.Products.CarpenterShopProducts;
import Model.enums.machines.ArtisanProductDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Model.enums.TileContents.Seed;
import static java.util.Map.entry;

public enum CraftingItems implements ItemInterface {
    CherryBomb(List.of(new HashMap<>(Map.ofEntries(
            entry(BlackSmithProducts.COPPER_ORE, 4),
            entry(ArtisanProductDetails.Coal, 1)
    ))), 50, Skill.mining, 1),

    Bomb(List.of(new HashMap<>(Map.ofEntries(
            entry(BlackSmithProducts.IRON_ORE, 4),
            entry(ArtisanProductDetails.Coal, 1)
    ))), 50, Skill.mining, 2),

    MegaBomb(List.of(new HashMap<>(Map.ofEntries(
            entry(BlackSmithProducts.GOLD_ORE, 4),
            entry(ArtisanProductDetails.Coal, 1)
    ))), 50, Skill.mining, 3),

    Sprinkler(List.of(new HashMap<>(Map.ofEntries(
            entry(Minerals.COPPER, 1),
            entry(Minerals.IRON, 1)
    ))), -1, Skill.farming, 1),

    QualitySprinkler(List.of(new HashMap<>(Map.ofEntries(
            entry(Minerals.IRON, 1),
            entry(Minerals.GOLD, 1)
    ))), -1, Skill.farming, 2),

    IridiumSprinkler(List.of(new HashMap<>(Map.ofEntries(
            entry(Minerals.GOLD, 1),
            entry(Minerals.IRIDIUM, 1)
    ))), -1, Skill.farming, 3),

    CharcoalKlin(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 20),
            entry(Minerals.COPPER, 2)
    ))), -1, Skill.farming, 1),

    Furnace(List.of(new HashMap<>(Map.ofEntries(
            entry(BlackSmithProducts.COPPER_ORE, 20),
            entry(CarpenterShopProducts.STONE, 25)
    ))), -1, null, 0),

    Scarecrow(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 50),
            entry(ArtisanProductDetails.Coal, 1),
            entry(ForagingSeeds.FIBER, 20)
    ))), -1, null, 0),

    DeluxeScarecrow(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 50),
            entry(ArtisanProductDetails.Coal, 1),
            entry(ForagingSeeds.FIBER, 20),
            entry(Minerals.IRIDIUM, 1)
    ))), -1, Skill.farming, 2),

    BeeHouse(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 40),
            entry(ArtisanProductDetails.Coal, 8),
            entry(Minerals.IRON, 1)
    ))), -1, Skill.farming, 1),

    CheesePress(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 45),
            entry(CarpenterShopProducts.STONE, 45),
            entry(Minerals.COPPER, 1)
    ))), -1, Skill.farming, 2),

    Keg(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 30),
            entry(Minerals.COPPER, 1),
            entry(Minerals.IRON, 1)
    ))), -1, Skill.farming, 3),

    Loom(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 60),
            entry(ForagingSeeds.FIBER, 30)
    ))), -1, Skill.farming, 3),

    MayonnaiseMachine(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 15),
            entry(CarpenterShopProducts.STONE, 15),
            entry(Minerals.COPPER, 1)
    ))), -1, null, 0),

    OilMaker(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 100),
            entry(Minerals.GOLD, 1),
            entry(Minerals.IRON, 1)
    ))), -1, Skill.farming, 3),

    PreservesJar(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 50),
            entry(CarpenterShopProducts.STONE, 40),
            entry(ArtisanProductDetails.Coal, 8)
    ))), -1, Skill.farming, 2),

    Dehydrator(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 30),
            entry(CarpenterShopProducts.STONE, 20),
            entry(ForagingSeeds.FIBER, 30)
    ))), -1, null, 0),

    GrassStarter(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 1),
            entry(ForagingSeeds.FIBER, 1)
    ))), -1, null, 0),

    FishSmoker(List.of(new HashMap<>(Map.ofEntries(
            entry(CarpenterShopProducts.WOOD, 50),
            entry(Minerals.IRON, 3),
            entry(ArtisanProductDetails.Coal, 10)
    ))), -1, null, 0),

    MysticTreeSeed(List.of(new HashMap<>(Map.ofEntries(
            entry(SaplingEnum.ACORNS_SAPLING, 5),
            entry(SaplingEnum.MAPLE_SEEDS, 5),
            entry(SaplingEnum.PINE_CONES, 5),
            entry(SaplingEnum.MAHOGANY_SEEDS, 5)
    ))), 100, Skill.foraging, 4);

    private final List<HashMap<ItemInterface, Integer>> ingredients;
    private final int price;
    private final Skill skill;
    private final int Level;

    CraftingItems(List<HashMap<ItemInterface, Integer>> ingredients, int price, Skill skill, int Level) {
        this.ingredients = ingredients;
        this.price = price;
        this.skill = skill;
        this.Level = Level;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name();
    }

    public List<HashMap<ItemInterface, Integer>> getIngredients() {
        return ingredients;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getCurrentLevel() {
        return Level;
    }
}
