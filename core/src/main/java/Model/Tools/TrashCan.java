package Model.Tools;

import Model.enums.ToolTypes;

public class TrashCan extends Tool {


    public TrashCan(String name) {
        super(1,1, ToolTypes.TRASH_CAN);
    }

    public void deleteItem() {

    }

    @Override
    public String getName() {
        return super.toolType.toString();
    }
}
