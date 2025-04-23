package Model.enums;

import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements AppMenu {
    newGame("\\s*game\\s+new\\s+-u\\s+(?<user1>\\S+)(\\s+(?<user2>\\S+))?(\\s+(?<user2>\\S+))?\\s*"),
    chooseMap("\\s*game\\s+map\\s+(?<number>\\d+)(\\s+(?<type>\\d+))?\\s*"),
    loadGame("\\s*load\\s+game\\s*"),
    saveGame(""),
    exitGame(""),
    deleteGame(""),
    nextTurn(""),
    getTime(""),
    getDate(""),
    getSeason(""),
    getTimeAndDate(""),
    getDayOfTheWeek(""),
    showWeather(""),
    weatherForecast(""),
    walk(""),
    printMap(""),
    helpReadingMap(""),
    showEnergy(""),
    showInventory(""),
    inventoryTrash(""),
















    ;


    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        java.util.regex.Matcher matcher = Pattern.compile(this.regex).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }


    @Override
    public void check(Scanner scanner) {

    }
}
