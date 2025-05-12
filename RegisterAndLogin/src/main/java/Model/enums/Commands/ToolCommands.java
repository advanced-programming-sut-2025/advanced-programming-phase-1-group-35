package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ToolCommands {
    toolEquip("tools equip (?<toolName>\\S.*\\S)"),
    showCurrentTool("tools show current"),
    showAllTools("tools show available"),
    upgradeTool("tools upgrade (?<toolName>\\S+)"),
    useTool("tools use -d (?<direction>\\d)"),
    ;

    private final String pattern;

    ToolCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String command) {
        Matcher matcher = Pattern.compile(pattern).matcher(command);
        if (matcher.matches()) return matcher;
        return null;
    }
}
