package Model.enums;

import View.AppMenu;

import java.util.Scanner;

public enum ProfileMenuCommands implements AppMenu {
    changeUsername(""),
    changePassword(""),
    changeEmail(""),
    changeNickname(""),
    showUserInfo(""),
    ;


    private final String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }


    @Override
    public void check(Scanner scanner) {

    }
}
