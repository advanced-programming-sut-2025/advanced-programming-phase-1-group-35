package Model.enums.Crops;

import Model.CropClasses.Sapling;
import Model.CropClasses.Tree;
import Model.ItemInterface;

public enum SaplingEnum implements ItemInterface {
    APRICOT_SAPLING(TreeEnum.APRICOT_TREE),
    CHERRY_SAPLING(TreeEnum.CHERRY_TREE),
    BANANA_SAPLING(TreeEnum.BANANA_TREE),
    MANGO_SAPLING(TreeEnum.MANGO_TREE),
    ORANGE_SAPLING(TreeEnum.ORANGE_TREE),
    PEACH_SAPLING(TreeEnum.PEACH_TREE),
    APPLE_SAPLING(TreeEnum.APPLE_TREE),
    POMEGRANATE_SAPLING(TreeEnum.POMEGRANATE_TREE),
    ACORNS_SAPLING(TreeEnum.OAK_TREE),
    MAPLE_SEEDS(TreeEnum.MAPLE_TREE),
    PINE_CONES(TreeEnum.PINE_TREE),
    MAHOGANY_SEEDS(TreeEnum.MAHOGANY_TREE),
    MUSHROOM_TREE_SEEDS(TreeEnum.MUSHROOM_TREE),
    MYSTIC_TREE_SEEDS(TreeEnum.MYSTIC_TREE);

    private TreeEnum tree;

    SaplingEnum(TreeEnum tree) {
        this.tree = tree;
    }

    public TreeEnum getTree() {
        return tree;
    }

    @Override
    public int getPrice() {
        return this.tree.getFruitSellPrice();
    }

    @Override
    public String getName() {
        return this.name();
    }
}
