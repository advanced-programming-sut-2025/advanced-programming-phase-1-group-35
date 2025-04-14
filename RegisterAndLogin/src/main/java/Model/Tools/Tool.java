package Model.Tools;

public abstract class Tool {
    protected String name;
    protected int defaultEnergyCost;
    public abstract void reduceEnergy();
    private double price;

    @Override
    public String toString(){
        return name;
    }
}
