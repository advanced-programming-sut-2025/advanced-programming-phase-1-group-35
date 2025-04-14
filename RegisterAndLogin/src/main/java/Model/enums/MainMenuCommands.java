package Model.enums;

import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements AppMenu {
    menuEnter("\\s*menu\\s+enter\\s+(?<menuName>.+?)\\s*"),
    menuExit("\\s*menu\\s+exit\\s*"),
    showCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    logout("\\s*logout\\s*"),
    ;


    private final String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
    @Override
    public void check(Scanner scanner) {

    }
}
