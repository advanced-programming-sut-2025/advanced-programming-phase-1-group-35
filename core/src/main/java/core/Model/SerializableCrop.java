package core.Model;

import com.badlogic.gdx.graphics.Texture;
import core.Model.enums.Crops.CropEnum;
import core.Model.enums.Crops.SeedEnum;
import core.Model.enums.Seasons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//under construction


public class SerializableCrop implements Serializable {
    public char symbol = '&';
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
    public Tile cropTile;
    public boolean isGiant;
    public int daysSincePlanted = 0;
    public int price ;
    public int daysSinceWatered;
    public boolean isFertilized;
    public ItemInterface fertilizer;
    public Texture texture;


public SerializableCrop(){};
}
