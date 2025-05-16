package Model.Tools;

import Model.ItemInterface;
import Model.enums.ToolTypes;

public class Tool implements ItemInterface {
    public ToolTypes toolType;
    public double price;
    public int defaultEnergyCost;

    public Tool(double price, int defaultEnergyCost, ToolTypes toolName) {
        this.price = price;
        this.defaultEnergyCost = defaultEnergyCost;
        this.toolType = toolName;
    }
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
        return (int)price;
    }

    @Override
    public String getName() {
        return toolType.toString();
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
