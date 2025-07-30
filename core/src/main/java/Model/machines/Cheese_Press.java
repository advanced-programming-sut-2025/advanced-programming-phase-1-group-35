package Model.machines;

import Controller.InGameMenu.ArtisanController;
import Model.enums.machines.ArtisanProductDetails;

public class Cheese_Press extends Machine {
    public Cheese_Press(ArtisanController controller) {
        super();
        super.controller = controller;
        super.products.add(ArtisanProductDetails.Cheese);
        super.products.add(ArtisanProductDetails.goatCheese);
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
