package Model.enums;

import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    newGame("\\s*game\\s+new\\s+-u\\s+(?<user1>\\S+)(\\s+(?<user2>\\S+))?(\\s+(?<user3>\\S+))?\\s*"),
    chooseMap("\\s*game\\s+map\\s+(?<number>\\d+)(\\s+(?<type>\\d+))?\\s*"),
    loadGame("\\s*load\\s+game\\s*"),
    exitGame("\\s*exit\\s+game\\s*"),
    menuExit("menu\\s+exit"),
    deleteGame("\\s*force\\s+terminate\\s+game\\s*"),
    nextTurn("\\s*next\\s+turn\\s*"),
    getTime(""),
    getDate(""),
    getSeason(""),
    getTimeAndDate(""),
    getDayOfTheWeek(""),
    showWeather(""),
    weatherForecast(""),
    walk("\\s*walk\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*"),
    printMap("\\s*print\\s+map\\s+-l\\s+<\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*>\\s+-s\\s+(?<size>\\d+)"),
    helpReadingMap("\\s*help\\s+reading\\s+map\\s*"),
    showEnergy(""),
    showInventory(""),
    inventoryTrash(""),
    toolEquip("tools equip (?<toolName>\\S.*\\S)"),
    showCurrentTool("tools show current"),
    showAllTools("tools show available"),
    useTool("tools use -d (?<direction>\\S+)"),

















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



}
