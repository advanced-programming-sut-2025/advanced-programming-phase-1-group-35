package Model.Tools;

import Model.enums.ToolTypes;

public class FishingPole extends Tool {
    private String name ;

    public FishingPole(String name , int price) {
        super(price,1, ToolTypes.FISHING_ROD);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
