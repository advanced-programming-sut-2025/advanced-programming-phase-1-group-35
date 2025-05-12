package Model.Tools;

import Model.ItemInterface;
import Model.enums.ToolTypes;

public abstract class Tool implements ItemInterface {
    public ToolTypes toolType;
    public double price;
    public int defaultEnergyCost;

    public Tool(double price, int defaultEnergyCost, ToolTypes toolName) {
        this.price = price;
        this.defaultEnergyCost = defaultEnergyCost;
        this.toolType = toolName;
    }

    public abstract void reduceEnergy();

    public ToolTypes getToolType() {
        return toolType;
    }

    public void setToolType(ToolTypes toolType) {
        this.toolType = toolType;
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
