package Model.Tools;

import Model.enums.ToolTypes;

public class FishingPole extends Tool {

    public FishingPole(String name) {
        // TODO when making this object in shop section
        super(1,1, ToolTypes.FISHING_ROD);
    }

    @Override
    public void reduceEnergy() {

    }

    @Override
    public String getName() {
        return super.toolName.toString();
    }
}
