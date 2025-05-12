package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FarmingCommands {
    CraftInfo("\\s*craftinfo\\s*-n\\s*(?<craft_name>\\S+)"),
    Plant("\\s*plant\\s*-s\\s*(?<seed>\\S+)\\s*-d\\s*(?<direction>\\S+)\\s*)"),
    ShowPlant("\\s*showplant\\s*-l\\s*(?<x> \\d+)\\s*(?<y>\\d+)\\s*"),
    Fertilize("\\s*fertilize\\s*-f\\s*(?<fertilizer>\\S+)\\s*-d\\s*(?<direction>\\S+)\\s*"),
    HowMuchWater("\\s*howmuch\\s*water\\s*"),

    ;
    private String pattern;
    FarmingCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String command) {
        Matcher matcher = Pattern.compile(pattern).matcher(command);
        if(matcher.matches()) return matcher;
        return null;
    }
}
