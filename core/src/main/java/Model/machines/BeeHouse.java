package Model.machines;

import Controller.InGameMenu.ArtisanController;
import Model.enums.machines.ArtisanProductDetails;

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
