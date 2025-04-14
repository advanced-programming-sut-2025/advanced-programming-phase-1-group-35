package Model.enums;

import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements AppMenu {
    register("\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+(?<passwordConfirm>\\S+))?\\s+" +
            "-n\\s+(?<nickname>.+?)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*"),
    login("\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+(?<stay>-stay-logged-in))?\\s*"),
    forgotPassword("\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*"),
    showCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    menuExit("\\s*menu\\s+exit\\s*"),
    ;


    private final String regex;

    LoginMenuCommands(String regex) {
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
