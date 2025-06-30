package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CookingCommands {
    placeItemInFridge("cooking refrigerator put (?<item>\\S+)"),
    pickItemFromFridge("cooking refrigerator pick (?<item>\\S+)"),
    showCookingRecipes("cooking show recipes"),
    cook("cooking prepare (?<recipeName>\\S+)"),
    eatFood("eat (?<foodName>\\S+)"),
    goBack("go back"),
    ;

    private final String pattern;

    CookingCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String command) {
        Matcher matcher = Pattern.compile(pattern).matcher(command);
        if (matcher.matches()) return matcher;
        return null;
    }
}
