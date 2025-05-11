package View.InGameMenu;

import Controller.InGameMenu.ShopMenuController;
import Model.Shops.Shop;
import View.AppMenu;

import java.io.IOException;
import java.util.Scanner;

public class ShopMenu extends AppMenu {
    ShopMenuController controller = new ShopMenuController();

    public void setShop(Shop shop) {
        controller.setShop(shop);
    }
    @Override
    public void check(Scanner scanner) throws IOException {

    }
}
