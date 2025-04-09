package Model.enums;

import View.AppMenu;

import java.util.Scanner;

public enum GameMenuCommands implements AppMenu {
    newGame(""),
    chooseMap(""),
    loadGame(""),
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


    @Override
    public void check(Scanner scanner) {

    }
}
