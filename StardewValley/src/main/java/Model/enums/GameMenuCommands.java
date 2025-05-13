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
    showEnergy("energy\\s+show"),
    cheatEnergySet("energy\\s+set\\s+-v\\s+(?<value>\\d+)"),
    cheatEnergyUnlimited("energy\\s+unlimited"),
    showInventory(""),
    inventoryTrash(""),
    showCropInfo("\\s*show\\s+crop\\s+info\\s+(?<cropName>\\S+)\\s*"),
    plantSeed("\\s*plant\\s+seed\\s+(?<seed>\\S+)\\s+(?<direction>\\S+)\\s*"),
    fertilize("\\s*fertilize\\s+-f\\s+(?<fertilizer>\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*"),
    harvest("\\s*harvest\\s+(?<direction>\\S+)\\s*"),
    GoToNextDay("\\s*go\\s+to\\s+next\\s+day\\s*"),
    buildABuilding("build\\s+-l\\s+<\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*>"),
    placeAnimal("animal\\s+place\\s+-l\\s+<\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*>"),
    goToShopMenu("go\\s+to\\s+shop\\s+menu"),
    sellProduct("sell\\s+(?<name>.+?)(\\s+-n\\s+(?<count>\\d+))?"),
    goToCookingMenu("go\\s+to\\s+cooking\\s+menu"),
    goToAnimalMenu("go\\s+to\\s+animal\\s+menu"),




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
