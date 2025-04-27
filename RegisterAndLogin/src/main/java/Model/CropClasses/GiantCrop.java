package Model.CropClasses;

import Model.Tile;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.PlantAble;

public class GiantCrop implements PlantAble{
    private Crop crop;
    private Tile[] tiles = new Tile[4];
    public GiantCrop(Crop crop,Tile[] tiles){
        this.crop = crop;
        this.tiles = tiles;
    }
}
