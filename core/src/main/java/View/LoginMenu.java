package View;

import Controller.LoginMenuController;
import Model.enums.LoginMenuCommands;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    LoginMenuController controller = new LoginMenuController();
    @Override
    public void check(Scanner scanner) throws IOException {
        Matcher matcher;
        String input = scan();
        if((matcher = LoginMenuCommands.register.getMatcher(input)) != null) {
            System.out.println(controller.registerUser(matcher.group("username") , matcher.group("password"),
                                                       matcher.group("passwordConfirm") , matcher.group("email"),
                                                       matcher.group("nickname") , matcher.group("gender") ));

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
