package View.InGameMenu;

import Controller.InGameMenu.CookingController;
import Model.App;
import Model.enums.Commands.CookingCommands;
import Model.enums.Menu;
import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CookingMenu extends AppMenu {
    CookingController controller = new CookingController();

    public void check(Scanner scanner) {
        String command = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = CookingCommands.placeItemInFridge.getMatcher(command)) != null) {
            System.out.println(controller.placeItemInFridge(matcher.group(1)));
        } else if ((matcher = CookingCommands.pickItemFromFridge.getMatcher(command)) != null) {
            System.out.println(controller.pickItemFromFridge(matcher.group(1)));
        } else if ((matcher = CookingCommands.showCookingRecipes.getMatcher(command)) != null) {
            System.out.println(controller.showCookingRecipes());
        } else if ((matcher = CookingCommands.cook.getMatcher(command)) != null) {
            System.out.println(controller.cook(matcher.group(1)));
        } else if ((matcher = CookingCommands.eatFood.getMatcher(command)) != null) {
            System.out.println(controller.eatFood(matcher.group(1)));
        } else if ((CookingCommands.goBack.getMatcher(command)) != null) {
            App.setCurrentMenu(Menu.GameMenu);
        } else {
            System.out.println("Invalid command");
        }
    }
}
