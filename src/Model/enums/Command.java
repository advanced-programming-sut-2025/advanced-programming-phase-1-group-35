package Model.enums;

import java.util.regex.Matcher;

public interface Command {
    String getRegex();

    default Matcher getMather(String input) {
        return null;
    }
}
