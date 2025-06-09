package Model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regexes {
    Username("[a-zA-Z0-9-]+"),
    Email("(?<name>(?!.*\\.{2})[a-zA-Z0-9](?:[a-zA-Z0-9._-]*[a-zA-Z0-9])?)@(?<domain>[a-zA-Z0-9-]+).(?<TLD>[a-zA-Z0-9-]{2,})"),
    SpecialCharacters("?><,\"';:\\/|\\]\\[\\}\\{\\\\+=)(*&^%$#!"),
    Password("[" + SpecialCharacters.regex + "0-9A-Za-z]+"),
    LowerCase("abcdefghijklmnopqrstuvwxyz"),
    UpperCase("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    Number("0123456789"),
    ;
    private final String regex;
    Regexes(String regex) {
        this.regex = regex;
    }
    public Matcher getMatcher(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
    public String getRegex() {
        return regex;
    }
}
