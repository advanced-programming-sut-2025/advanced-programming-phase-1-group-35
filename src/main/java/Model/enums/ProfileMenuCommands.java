package Model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    changeUsername("change\\s+username\\s+-u\\s+(?<username>\\S+)"),
    changePassword("change\\s+password\\s+-p\\s+(?<password>\\.+?)\\s+-o\\s+(?<oldPassword>.+?)"),
    changeEmail("change\\s+email\\s+-e\\s+(?<email>\\S+)"),
    changeNickname("change\\s+nickname\\s+-n\\s+(?<nickname>\\S+)"),
    showUserInfo("show\\s+user\\s+info"),
    exit("menu\\s+exit"),
    ;


    private final String regex;

    ProfileMenuCommands(String regex) {
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
