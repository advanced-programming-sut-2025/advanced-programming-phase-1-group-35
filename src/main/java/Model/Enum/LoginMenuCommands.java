package Model.Enum;

import View.AppMenu;

import java.util.Scanner;

public enum LoginMenuCommands implements AppMenu {
    register(""),
    pickSecurityQuestion(""),
    login(""),
    forgotPassword(""),
    generateRandomPassword(""),
    ;


    private final String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }


    @Override
    public void check(Scanner scanner) {

    }
}
