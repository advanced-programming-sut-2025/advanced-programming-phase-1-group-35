package Model.CropClasses;

import Model.ItemInterface;
import Model.enums.Crops.Fruit;
import Model.enums.Crops.TreeEnum;

public class Sapling implements ItemInterface {
    private TreeEnum tree;
    public Sapling(TreeEnum tree) {
        this.tree = tree;
    }

    public TreeEnum getTree() {
        return tree;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }
}
