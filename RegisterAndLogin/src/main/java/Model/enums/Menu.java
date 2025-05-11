package Model.enums;

import View.*;
import View.InGameMenu.ShopMenu;

import java.io.IOException;
import java.util.Scanner;

public enum Menu {
    MainMenu(new MainMenu()),
    LoginMenu(new LoginMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu()),
    ExitMenu(new ExitMenu()),

    ShopMenu(new ShopMenu()),
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
