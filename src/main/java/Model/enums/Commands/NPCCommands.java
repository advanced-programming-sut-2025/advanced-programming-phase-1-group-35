package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum NPCCommands {
    meetNPC("meet NPC (?<name>\\S+)"),
    giftNPC("gift NPC (?<name>\\S+) -i (?<item>\\S+)"),
    showAllFriendships("friendship NPC list"),
    showQuestList("quests list (?<name>\\S+)"),
    finishQuest("quests finish -i (?<index>\\S+) -n (?<name>\\S+)"),
    ;
    private final String pattern;

    NPCCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String command) {
        Matcher matcher = Pattern.compile(pattern).matcher(command);
        if (matcher.matches()) return matcher;
        return null;
    }
}
