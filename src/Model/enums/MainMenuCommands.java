package Model.enums;

import View.AppMenu;

import java.util.Scanner;

public enum MainMenuCommands implements AppMenu {
    menuEnter(""),
    menuExit(""),
    showCurrentMenu(""),
    logout(""),
    ;


    private final String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }


    @Override
    public void check(Scanner scanner) {

    }
}
