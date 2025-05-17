package Model.Tools;

import Model.enums.ToolTypes;

public class WateringCan extends Tool {
    private int capacity = 55;

    public WateringCan(String name) {
        // TODO when making this object in shop section
        super(1,1, ToolTypes.WATERING_CAN);
    }


    @Override
    public String getName() {
        return super.toolType.toString();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
