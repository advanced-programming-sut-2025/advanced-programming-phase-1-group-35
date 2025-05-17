package Model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AnimalCommands {
    buildAnimalHouse("build -a (?<buildingName>\\S+) -l (?<x>\\d+) (?<y>\\d+)"),
    buyAnimal("buy animal -a (?<animal>\\S+) -n (?<name>\\S+)"),
    nazAnimal("pet -n (?<name>\\S+)"),
    showAllAnimals("animals"),
    shepherdAnimal("shepherd animals -n (?<animalName>\\S+) -l (?<x>\\d+) (?<y>\\d+)"),
    feedHay("feed hay -n (?<animalName>\\S+)"),
    produces("produces"),
    collectProducts("collect produce -n (?<name>\\S+)"),
    sellAnimal("sell animal -n (?<name>\\S+)"),
    fishing("fishing -p (?<pole>\\S+)"),
    goBack("go back"),
    ;

    private final String pattern;

    AnimalCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String command) {
        Matcher matcher = Pattern.compile(pattern).matcher(command);
        if (matcher.matches()) return matcher;
        return null;
    }
}
