package Controller;

import Model.App;
import Model.enums.Menu;
import Model.Result;

import java.io.IOException;

public class MainMenuController {
    public Result showCurrentMenu() {
        return new Result(true , "main menu");
    }

    public Result logout() {
        App.setCurrentMenu(Menu.LoginMenu);
        App.setStayLoggedIn(false);
        App.setLoggedInUser(null);
        return new Result(true , "redirecting to login menu");
    }

    public Result goToMenu(String menuString) {
        Menu menu = switch (menuString) {
            case "game menu" -> Menu.GameMenu;
            case "login menu" -> Menu.LoginMenu;
            case "profile menu" -> Menu.ProfileMenu;
            default -> null;
        };
        if(menu == null) {
            return new Result(false, "Invalid menu");
        }
        App.setCurrentMenu(menu);
        return new Result(true , "Redirecting to " + menuString);
    }

    public void exitMenu() throws IOException {
        if(!App.isStayLoggedIn()) {
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
        }
        App.serializeApp();
        App.setCurrentMenu(Menu.ExitMenu);
    }
}
