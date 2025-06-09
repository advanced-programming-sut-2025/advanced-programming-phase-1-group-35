package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CraftingCommands {
    ArtisanUse("\\s*artisan\\s+use\\s+(?<artisan_name>\\S+)\\s+(?<item1_name>\\S+)\\s*"),
    ArtisanGet("\\s*artisan\\s+get\\s+(?<artisan_name>\\S+)\\s+"),

    ;
    private final String pattern;

    CraftingCommands(String pattern){
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
