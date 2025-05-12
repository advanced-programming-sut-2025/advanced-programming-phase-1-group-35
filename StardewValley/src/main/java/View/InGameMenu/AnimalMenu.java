package View.InGameMenu;

import Controller.InGameMenu.AnimalController;
import Controller.InGameMenu.CookingController;
import Model.App;
import Model.enums.Commands.AnimalCommands;
import Model.enums.Commands.CookingCommands;
import Model.enums.Menu;
import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class AnimalMenu extends AppMenu {
    AnimalController controller = new AnimalController();

    public void check(Scanner scanner) {
        String command = scanner.nextLine().trim();
        Matcher matcher;
        while (!command.equals("exit")) {
            if ((matcher = AnimalCommands.buildAnimalHouse.getMatcher(command)) != null) {
                System.out.println(controller.buildAnimalHouse(matcher.group(1),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))));
            } else if ((matcher = AnimalCommands.buyAnimal.getMatcher(command)) != null) {
                System.out.println(controller.buyAnimal(matcher.group(1), matcher.group(2)));
            } else if ((matcher = AnimalCommands.nazAnimal.getMatcher(command)) != null) {
                System.out.println(controller.nazTheAnimal(matcher.group(1)));
            } else if ((matcher = AnimalCommands.showAllAnimals.getMatcher(command)) != null) {
                System.out.println(controller.seeAnimalsCondition());
            } else if ((matcher = AnimalCommands.shepherdAnimal.getMatcher(command)) != null) {
                System.out.println(controller.shepherdAnimal(matcher.group(1),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))));
            } else if ((matcher = AnimalCommands.feedHay.getMatcher(command)) != null) {
                System.out.println(controller.feedByHay(matcher.group(1)));
            } else if ((matcher = AnimalCommands.produces.getMatcher(command)) != null) {
                System.out.println(controller.produces());
            } else if ((matcher = AnimalCommands.collectProducts.getMatcher(command)) != null) {
                System.out.println(controller.collectProducts(matcher.group(1)));
            } else if ((matcher = AnimalCommands.sellAnimal.getMatcher(command)) != null) {
                System.out.println(controller.sellAnimal(matcher.group(1)));
            } else if ((matcher = AnimalCommands.fishing.getMatcher(command)) != null) {
                System.out.println(controller.fishing(matcher.group(1)));
            }
            else if (AnimalCommands.goBack.getMatcher(command) != null) {
                App.setCurrentMenu(Menu.GameMenu);
            }
            else {
                System.out.println("Invalid command");
            }
        }
    }
}
