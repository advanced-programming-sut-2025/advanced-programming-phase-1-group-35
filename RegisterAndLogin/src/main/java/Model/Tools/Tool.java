package Model.Tools;

import Model.ItemInterface;
import Model.enums.ToolTypes;

public abstract class Tool {
    protected ToolTypes toolName;
    protected int defaultEnergyCost;

    public Tool(String name) {
        super(name);
    }

    public abstract void reduceEnergy();
    private double price;

    public ToolTypes getToolName() {
        return toolName;
    }

    public void setToolName(ToolTypes toolName) {
        this.toolName = toolName;
    }

    public int getDefaultEnergyCost() {
        return defaultEnergyCost;
    }

    public void setDefaultEnergyCost(int defaultEnergyCost) {
        this.defaultEnergyCost = defaultEnergyCost;
    }

    public int getPrice() {
        return 0;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
