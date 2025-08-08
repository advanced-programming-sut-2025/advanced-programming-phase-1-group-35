package core.Model;

import com.badlogic.gdx.graphics.Texture;
import core.Model.CropClasses.Crop;
import core.Model.enums.Crops.CropEnum;
import core.Model.enums.Crops.SeedEnum;
import core.Model.enums.Seasons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//under construction


public class SerializableCrop implements Serializable {
    public  CropEnum cropEnum;
    public  String name;
    public  SeedEnum source;
    public List<Integer> stages = new ArrayList<>();
    public  int totalHarvestTime;
    public  boolean oneTime;
    public  int regrowthTime;
    public  int baseSellPrice;
    public  boolean isEdible;
    public  int energy;
    public List<Seasons> season = new ArrayList<>();
    public  boolean canBecomeGiant;
    public boolean isForaging;
    public int currentState;
    public int daysSinceLastGrowth;
    public Point location;
    public boolean isGiant;
    public int daysSincePlanted = 0;
    public int price ;
    public int daysSinceWatered;
    public boolean isFertilized;


public SerializableCrop(Crop crop) {
 this.cropEnum = crop.getCropEnum();
 this.name = crop.getName();
 this.source = crop.getSource();
 this.stages = crop.getStages();
 this.totalHarvestTime = crop.getTotalHarvestTime();
 this.oneTime = crop.isOneTime();
 this.regrowthTime = crop.getRegrowthTime();
 this.baseSellPrice = crop.getBaseSellPrice();
 this.isEdible = crop.isEdible();
 this.energy = crop.getEnergy();
 this.season = crop.getSeasons();
 this.canBecomeGiant = crop.canBecomeGiant();
 this.isForaging = crop.isForaging();
 this.daysSincePlanted = crop.getDaysSincePlanted();
 this.price = crop.getPrice();
 this.daysSinceWatered = crop.getDaysSinceWatered();
 this.isFertilized = crop.isFertilized();
 this.currentState = crop.getCurrentState();
 this.isGiant = crop.isGiant();
 this.daysSinceLastGrowth = crop.getDaysSinceLastGrowth();
 this.location = crop.getCropTile().coordination;
}
public SerializableCrop() {}
}
