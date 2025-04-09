package Model.enums;

import View.*;

import java.util.Scanner;

public enum Menu {
    MainMenu(new MainMenu()),
    LoginMenu(new LoginMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu()),
    ExitMenu(new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
