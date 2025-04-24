package Model.Tools;

public abstract class Tool {
    protected String name;
    protected int defaultEnergyCost;
    public abstract void reduceEnergy();

/*    protected static Tool currentTool;
    because of multiplayer it is best implemented in Player class*/
    @Override
    public String toString(){
        return name;
    }
}
