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
    getTime("time"),
    getDate("date"),
    getSeason("season"),
    getTimeAndDate("datetime"),
    getDayOfTheWeek("day of the week"),
    cheatTime("cheat advance time (?<hour>\\d+)h"),
    cheatDate("cheat advance date (?<day>\\d+)d"),
    cheatThor("cheat\\s+Thor\\s+-l\\s+<\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*>\\s*"),
    showWeather("weather"),
    weatherForecast("weather\\s+forecast"),
    cheatWeatherSet("cheat\\s+weather\\s+set\\s+(?<type>\\S+)"),
    greenHouseBuild("greenhouse\\s+build"),//TODO
    walk("\\s*walk\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*"),
    printMap("\\s*print\\s+map\\s+-l\\s+<\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*>\\s+-s\\s+(?<size>\\d+)"),
    helpReadingMap("\\s*help\\s+reading\\s+map\\s*"),
    showEnergy("energy\\s+show"),
    cheatEnergySet("energy\\s+set\\s+-v\\s+(?<value>\\d+)"),
    cheatEnergyUnlimited("energy\\s+unlimited"),
    showInventory("\\s*show\\s+inventory\\s*"),
    inventoryTrash(""),
    ShowPlant("\\s*show\\s+plant\\s*-l\\s*(?<x>\\d+)\\s*(?<y>\\d+)\\s*"),
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
    talkPlayer("talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>.+?)"),
    talkHistory("talk\\s+history\\s+-u\\s+(?<username>\\S+)"),
    giftPlayer("gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>.+?)\\s+-a\\s+(?<amount>\\d+)"),
    friendshipStatus("friendship\\s+status\\s+-u\\s+(?<username>\\S+)"),
    giftHistory("gift\\s+history\\s+-u\\s+(?<username>\\S+)"),
    giftList("gift\\s+list"),
    rateGift("gift\\s+rate\\s+-i\\s+(?<id>\\d+)\\s+-r\\s+(?<rate>\\d+)"),
    hug("hug\\s+-u\\s+(?<username>\\S+)"),
    flower("flower\\s+-u\\s+(?<username>\\S+)"),
    askMarriage("ask\\s+\\s+marriage\\s+-u\\s+(?<username>\\S+)"),
    respondToMarriageRequest("respond\\s+(?<answer>accept|reject)"),
    showPlayerPosition("show position"),
    SetWeather("\\s*set\\s+weather\\s+-u\\s+(?<weather>\\S+)\\s*"),
    giveSeed("\\s*give\\s+seed\\s+(?<seed>\\S+)\\s*"),
    pickUpSeed("\\s*pick\\s+seed\\s+(?<direction>\\S+)\\s*"),
    pickItem("\\s*pick\\s+(?<itemName>\\S+)\\s+(?<direction>\\S+)\\s*"),
    showRecipes("\\s*show\\s+recipes\\s*"),
    CraftItem("\\s*crafting\\s+item\\s+(?<itemName>\\S+)\\s*"),
    CheatGetItem("\\s*cheat\\s+get\\s+(?<itemName>\\S+)\\s+(?<amount>\\d+)\\s*"),
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
