package View.InGameMenu;

import tracker.Controller.InGameMenu.CookingController;
import common.Model.enums.Commands.CookingCommands;
import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CookingMenu extends AppMenu {
    CookingController controller = new CookingController();

    public void check(Scanner scanner) {
        String command = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = CookingCommands.showCookingRecipes.getMatcher(command)) != null) {
            System.out.println(controller.showCookingRecipes());
        } else if ((matcher = CookingCommands.cook.getMatcher(command)) != null) {
            System.out.println(controller.cook(matcher.group(1)));
        } else if ((matcher = CookingCommands.eatFood.getMatcher(command)) != null) {
            System.out.println(controller.eatFood(matcher.group(1)));
        } else {
            System.out.println("Invalid command");
        }
    }
}
