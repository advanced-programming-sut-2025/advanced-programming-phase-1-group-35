package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    requestTrade("trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>cash|item)\\s+-i\\s+(?<item>.+?)\\s+" +
            "(?<amount>\\d+)(\\s+-p\\s+(?<price>\\d+))?(\\s+-ti\\s+(?<tItem>.+?)\\s+-ta\\s+(?<tAmount>\\d+))?"),
    ;

    private final String pattern;

    TradeMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String command) {
        Matcher matcher = Pattern.compile(pattern).matcher(command);
        if (matcher.matches()) return matcher;
        return null;
    }
}
