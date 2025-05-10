package Model.CropClasses;

import Model.enums.Crops.Fruit;
import Model.enums.Crops.TreeEnum;

public class Sapling {
    private TreeEnum tree;
    public Sapling(TreeEnum tree) {
        this.tree = tree;
    }

    public TreeEnum getTree() {
        return tree;
    }
}
