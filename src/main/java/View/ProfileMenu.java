package View;

import Controller.ProfileMenuController;
import Model.App;
import Model.enums.Menu;
import Model.enums.ProfileMenuCommands;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends AppMenu {
    ProfileMenuController controller = new ProfileMenuController();
    @Override
    public void check(Scanner scanner) throws IOException {
        Matcher matcher;
        String input = scan();
        if((matcher = ProfileMenuCommands.changeUsername.getMatcher(input)) != null) {
            System.out.println(controller.changeUsername(matcher.group("username")));
        }
        else if((matcher = ProfileMenuCommands.changePassword.getMatcher(input)) != null) {
            System.out.println(controller.changePassword(matcher.group("password"), matcher.group("newPassword")));
        }
        else if((matcher = ProfileMenuCommands.changeEmail.getMatcher(input)) != null) {
            System.out.println(controller.changeEmail(matcher.group("email")));
        }
        else if((matcher = ProfileMenuCommands.changeNickname.getMatcher(input)) != null) {
            System.out.println(controller.changeNickname(matcher.group("nickname")));
        }
        else if((matcher = ProfileMenuCommands.showUserInfo.getMatcher(input)) != null) {
            System.out.println(controller.showUserInfo());
        }
        else if((matcher = ProfileMenuCommands.exit.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.MainMenu);
            System.out.println("redirecting to main menu");
        }
        else {
            System.out.println("Invalid input");
        }
    }
}
