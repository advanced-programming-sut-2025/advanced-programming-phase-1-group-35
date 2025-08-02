package core.Model.machines;

import core.Controller.InGameMenu.ArtisanController;
import core.Model.enums.machines.ArtisanProductDetails;

public class Keg extends Machine {
    public Keg(ArtisanController controller) {
        super();
        super.controller = controller;
        super.products.add(ArtisanProductDetails.Juice);
        super.products.add(ArtisanProductDetails.Vinegar);
        super.products.add(ArtisanProductDetails.Coffee);
        super.products.add(ArtisanProductDetails.Mead);
        super.products.add(ArtisanProductDetails.Pale_Ale);
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
