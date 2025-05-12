package View.InGameMenu;

import Controller.InGameMenu.ShopMenuController;
import Model.Shops.Shop;
import Model.enums.ShopMenuCommands;
import View.AppMenu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu extends AppMenu {
    ShopMenuController controller = new ShopMenuController();

    public void setShop(Shop shop) {
        controller.setShop(shop);
    }
    @Override
    public void check(Scanner scanner) throws IOException {
        String input = ShopMenu.scan();
        Matcher matcher;
        if((matcher = ShopMenuCommands.showAllProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAllProducts());
        }
        else if((matcher = ShopMenuCommands.showAvailableProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAvailableProducts());
        }
        else if((matcher = ShopMenuCommands.purchase.getMatcher(input)) != null) {
            System.out.println(controller.purchase(matcher.group("name"), matcher.group("count")));
        }
        else if((matcher = ShopMenuCommands.cheatAddMoney.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddMoney(matcher.group("count")));
        }
        else if((matcher = ShopMenuCommands.goBack.getMatcher(input)) != null) {
            System.out.println(controller.goBack());
        }
        else
            System.out.println("Invalid input");
    }
}
