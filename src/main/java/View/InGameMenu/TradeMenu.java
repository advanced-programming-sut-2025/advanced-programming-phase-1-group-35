package View.InGameMenu;

import Controller.InGameMenu.TradeMenuController;
import Model.App;
import View.AppMenu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu extends AppMenu {
    TradeMenuController controller = new TradeMenuController();
    public void check(Scanner scanner) throws IOException {
        controller.setUser(App.getCurrentGame().getPlayingUser());
        String input = TradeMenu.scan();
        Matcher matcher ;

    }
}
