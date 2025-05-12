package Model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    showAllProducts("show\\s+all\\s+products"),
    showAvailableProducts("show\\s+available\\s+products"),
    purchase("purchase\\s+(?<name>.+?)\\s+-n\\s+(?<count>\\d+)"),
    cheatAddMoney("cheat\\s+add\\s+(?<count>\\d+)"),
    goBack("go\\s+back"),

    ;

    private final String regex;

    ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
