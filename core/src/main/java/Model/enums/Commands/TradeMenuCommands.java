package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    requestTrade("trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>cash|item)\\s+-i\\s+(?<item>.+?)\\s+" +
            "(?<amount>\\d+)(\\s+-p\\s+(?<price>\\d+))?(\\s+-ti\\s+(?<tItem>.+?)\\s+-ta\\s+(?<tAmount>\\d+))?"),
    listTradeRequests("trade\\s+list\\s+all"),
    listUnAnsweredTradeRequests("trade\\s+list"),
    tradeHistory("trade\\s+history\\s+-u\\s+(?<username>\\S+)"),
    respondToTrade("trade\\s+response\\s+(?<answer>-accept|-reject)\\s+-i\\s+(?<id>\\d+)"),
    goBack("go\\s+back"),
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
