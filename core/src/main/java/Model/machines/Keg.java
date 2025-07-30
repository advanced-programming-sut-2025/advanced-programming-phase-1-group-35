package Model.machines;

import Controller.InGameMenu.ArtisanController;
import Model.enums.machines.ArtisanProductDetails;

public class Keg extends Machine {
    public Keg(ArtisanController controller) {
        super();
        super.controller = controller;
        super.products.add(ArtisanProductDetails.Juice);
        super.products.add(ArtisanProductDetails.Vinegar);
        super.products.add(ArtisanProductDetails.Coffee);
        super.products.add(ArtisanProductDetails.Mead);
        super.products.add(ArtisanProductDetails.PaleAle);
        super.products.add(ArtisanProductDetails.Wine);
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "Keg";
    }
}
