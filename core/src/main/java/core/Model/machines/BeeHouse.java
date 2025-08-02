package core.Model.machines;

import core.Controller.InGameMenu.ArtisanController;
import core.Model.enums.machines.ArtisanProductDetails;

public class BeeHouse extends Machine {
    public BeeHouse(ArtisanController controller) {
        super();
        super.controller = controller;
        super.products.add(ArtisanProductDetails.Honey);
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "BeeHouse";
    }
}
