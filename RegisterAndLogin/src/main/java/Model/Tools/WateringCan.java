package Model.Tools;

import Model.enums.ToolTypes;

public class WateringCan extends Tool {

    public WateringCan(String name) {
        // TODO when making this object in shop section
        super(1,1, ToolTypes.WATERING_CAN);
    }

    @Override
    public void reduceEnergy() {

    }

    @Override
    public String getName() {
        return super.toolName.toString();
    }
}
