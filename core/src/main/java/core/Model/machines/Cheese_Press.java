package core.Model.machines;

import core.Controller.InGameMenu.ArtisanController;
import core.Model.enums.machines.ArtisanProductDetails;

public class Cheese_Press extends Machine {
    public Cheese_Press(ArtisanController controller) {
        super();
        super.controller = controller;
        super.products.add(ArtisanProductDetails.Cheese);
        super.products.add(ArtisanProductDetails.goat_Cheese);
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "Cheese_Press";
    }
}
