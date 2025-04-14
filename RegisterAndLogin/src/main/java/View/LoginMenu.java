package View;

import Controller.LoginMenuController;
import Controller.MainMenuController;
import Model.enums.LoginMenuCommands;
import Model.enums.MainMenuCommands;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu implements AppMenu {
    LoginMenuController controller = new LoginMenuController();
    @Override
    public void check(Scanner scanner) throws IOException {
        Matcher matcher;
        String input = scanner.nextLine();
        if((matcher = LoginMenuCommands.register.getMatcher(input)) != null) {
            System.out.println(controller.registerUser(matcher.group("username") , matcher.group("password"),
                                                       matcher.group("passwordConfirm") , matcher.group("email"),
                                                       matcher.group("nickname") , matcher.group("gender") , scanner));

        }
        else if((matcher = LoginMenuCommands.login.getMatcher(input)) != null) {
            System.out.println(controller.login(matcher.group("username") , matcher.group("password"),
                                                matcher.group("stay")));
        }
        else if(LoginMenuCommands.menuExit.getMatcher(input) != null) {
            controller.exitMenu();
        }
        else if(LoginMenuCommands.showCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else{
            System.out.println("invalid command");
        }
    }
}
