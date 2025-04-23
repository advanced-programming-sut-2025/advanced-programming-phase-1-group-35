package Model.enums.Crops;

public enum Trees {
    //will be added
    ;

    private final String Name;
    private final String Source;
    private final String Stages;
    private final int TotalHarvestTime;
    private final String Friut;
    private final String FruitHarvestCycle;
    private final String FruitBaseSellPrice;
    private final String IsFruitEdible;
    private final String FruitEnergy;
    private final String FruitBaseHealth;
    private final String Season;
    private boolean isForaging;
    Trees(String Name, String Source, String Stages,int totalHarvestTime, String fruit,String FruitHarvestCycle,String FruitBaseSellPrice,String IsFruitEdible,String FruitEnergy, String fruitBaseHealth, String Season ) {
        this.Name = Name;
        this.Source = Source;
        this.Stages = Stages;
        this.TotalHarvestTime = totalHarvestTime;
        this.Friut = fruit;
        this.FruitHarvestCycle = FruitHarvestCycle;
        this.FruitBaseSellPrice = FruitBaseSellPrice;
        this.IsFruitEdible = IsFruitEdible;
        this.FruitEnergy = FruitEnergy;
        this.FruitBaseHealth = fruitBaseHealth;
        this.Season = Season;


    }
}
