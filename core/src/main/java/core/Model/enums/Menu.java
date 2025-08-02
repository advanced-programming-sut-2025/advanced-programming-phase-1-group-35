package core.Model.enums;

import core.View.*;
import core.View.InGameMenu.AnimalMenu;
import core.View.InGameMenu.CookingMenu;
import core.View.InGameMenu.ShopMenu;
import core.View.InGameMenu.TradeMenu;

import java.io.IOException;
import java.util.Scanner;

public enum Menu {
    MainMenu(new MainMenu()),
    LoginMenu(new LoginMenu()),
    ProfileMenu(new ProfileMenu()),
    ExitMenu(new ExitMenu()),
    ShopMenu(new ShopMenu()),
    AnimalMenu(new AnimalMenu()),
    CookingMenu(new CookingMenu()),
    TradeMenu(new TradeMenu())
    ;

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) throws IOException {
        this.menu.check(scanner);
    }

    public AppMenu getMenu() {
        return this.menu;
    }
}
