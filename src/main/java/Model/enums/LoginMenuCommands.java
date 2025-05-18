package Model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    register("\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+(?<passwordConfirm>\\S+))?\\s+" +
            "-n\\s+(?<nickname>.+?)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*"),
    pickQuestion("\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>\\d)\\s+-a\\s+(?<answer>.+?)\\s+-c\\s+(?<answerConfirm>.+?)"),
    login("\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+(?<stay>-stay-logged-in))?\\s*"),
    forgotPassword("\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*"),
    answerQuestion("\\s*answer\\s+-a\\s+(?<answer>.+?)\\s*"),
    showCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    menuExit("\\s*menu\\s+exit\\s*"),
    ;


    private final String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }

}
