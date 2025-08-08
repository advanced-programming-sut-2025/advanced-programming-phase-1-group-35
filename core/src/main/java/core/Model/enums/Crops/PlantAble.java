package core.Model.enums.Crops;

import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Seed;
import core.Model.CropClasses.Tree;
import core.Model.ItemInterface;
import core.Model.Tile;

public interface PlantAble extends ItemInterface {

    static PlantAble getPlantAbleByName(String plantedItemName, Tile tile) {
        for(CropEnum cropEnum : CropEnum.values()) {
            if(cropEnum.name().equals(plantedItemName)) {
                return new Crop(cropEnum,tile);
            }
        }
        for (TreeEnum treeEnum : TreeEnum.values()) {
            if(treeEnum.name().equals(plantedItemName)) {
                return new Tree(treeEnum,tile);
            }
        }
        if(plantedItemName.toLowerCase().contains("seed")) {
            return new Seed(ForagingSeeds.getRandomForagingSeed());
        }
        return null;
    }
}
