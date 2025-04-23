package View;

import Controller.MainMenuController;
import Model.enums.MainMenuCommands;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    MainMenuController controller = new MainMenuController();
    @Override
    public void check(Scanner scanner) throws IOException {
        Matcher matcher ;
        String input = scanner.nextLine();
        if((matcher = MainMenuCommands.menuEnter.getMatcher(input)) != null){
            System.out.println(controller.goToMenu(matcher.group("menuName")));
        }
        else if(MainMenuCommands.menuExit.getMatcher(input) != null){
            controller.exitMenu();
        }
        else if(MainMenuCommands.showCurrentMenu.getMatcher(input) != null){
            System.out.println(controller.showCurrentMenu());
        }
        else if(MainMenuCommands.logout.getMatcher(input) != null){
            System.out.println(controller.logout());
        }
        else {
            System.out.println("invalid command");
        }
    }
}
