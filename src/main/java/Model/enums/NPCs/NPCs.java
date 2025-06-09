package Model.enums.NPCs;

import Model.NPCs.NPC;
import Model.NPCs.Quest;

import java.util.ArrayList;

public enum NPCs {
    //shop manager
    Clint,
    Morris,
    Pierre,
    Willy,
    Marnie,
    Gus,
    //quest
    Sebastian,
    Abigail,
    Harvey,
    Lia,
    //shop and quest
    Robin;

    public NPC createNPC() {
        if (hasQuests()) {
            return new NPC(this.name(), getJob(), quests(), favorites(), getSymbol());
        } else {
            return new NPC(this.name(), getJob(), new ArrayList<>(), new ArrayList<>(), getSymbol());
        }
    }

    private boolean hasQuests() {
        return switch (this) {
            case Sebastian, Abigail, Harvey, Lia, Robin -> true;
            default -> false;
        };
    }

    private String getJob() {
        // You can assign jobs here if needed
        return switch (this) {
            case Clint -> "Blacksmith";
            case Morris -> "JojaMart Manager";
            case Pierre -> "Shopkeeper";
            case Willy -> "Fisherman";
            case Marnie -> "Rancher";
            case Gus -> "Bartender";
            default -> "Villager";
        };
    }

    private ArrayList<String> favorites() {
        ArrayList<String> favorites = new ArrayList<>();
        switch (this) {
            case Sebastian:
                favorites.add("Wool");
                favorites.add("Pumpkin Pie");
                favorites.add("Pizza");
                break;
            case Abigail:
                favorites.add("Rock");
                favorites.add("Iron Ore");
                favorites.add("Coffee");
                break;
            case Harvey:
                favorites.add("Coffee");
                favorites.add("Pickles");
                favorites.add("Wine");
                break;
            case Lia:
                favorites.add("Salad");
                favorites.add("Grape");
                favorites.add("Wine");
                break;
            case Robin:
                favorites.add("Spaghetti");
                favorites.add("Wood");
                favorites.add("Iron Ingot");
                break;
        }
        return favorites;
    }

    private ArrayList<Quest> quests() {
        ArrayList<Quest> quests = new ArrayList<>();
        switch (this) {
            case Sebastian:
                quests.add(new Quest("50 units of iron", "2 diamonds"));
                quests.add(new Quest("Pumpkin Pie", "5000 gold coins"));
                quests.add(new Quest("150 units of stone", "50 quartz"));
                break;
            case Abigail:
                quests.add(new Quest("1 gold bar", "1 friendship level"));
                quests.add(new Quest("1 pumpkin", "500 gold coins"));
                quests.add(new Quest("50 wheat", "1 automatic iridium sprinkler"));
                break;
            case Harvey:
                quests.add(new Quest("12 of any plant", "750 gold coins"));
                quests.add(new Quest("1 salmon", "1 friendship level"));
                quests.add(new Quest("1 bottle of wine", "5 salads"));
                break;
            case Lia:
                quests.add(new Quest("10 hardwood", "500 gold coins"));
                quests.add(new Quest("1 salmon", "Salmon dinner recipe"));
                quests.add(new Quest("200 wood", "3 deluxe scarecrows"));
                break;
            case Robin:
                quests.add(new Quest("80 wood", "1000 gold coins"));
                quests.add(new Quest("10 iron bars", "3 beehives"));
                quests.add(new Quest("1000 wood", "25000 gold coins"));
                break;
        }
        return quests;
    }

    private char getSymbol() {
        return switch (this) {
            case Sebastian -> 'S';
            case Abigail -> 'A';
            case Harvey -> 'H';
            case Lia -> 'L';
            case Robin -> 'R';
            default -> 'V';
        };
    }

}
