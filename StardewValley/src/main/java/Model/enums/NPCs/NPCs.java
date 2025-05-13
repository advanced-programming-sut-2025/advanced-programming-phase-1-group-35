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
    Robin,
    ;
    public String name;
    public Quest[] quests;
    public String job;

    public NPC createNPC() {
        if (name.equals(Sebastian.name) || name.equals(Abigail.name)
                || name.equals(Harvey.name) || name.equals(Lia.name) || name.equals(Robin.name)) {
            return new NPC(name, job, quests(), favorites());
        } else {
            return new NPC(name, job, new ArrayList<Quest>(), new ArrayList<String>());
        }
    }

    private ArrayList<String> favorites() {
        ArrayList<String> favorites = new ArrayList<>();
        switch (this.name()) {
            case "Sebastian":
                favorites.add("Wool");
                favorites.add("Pumpkin Pie");
                favorites.add("Pizza");
                break;
            case "Abigail":
                favorites.add("Rock");
                favorites.add("Iron Ore");
                favorites.add("Coffee");
            case "Harvey":
                favorites.add("Coffee");
                favorites.add("Pickles");
                favorites.add("Wine");
                break;
            case "Lia":
                favorites.add("Salad");
                favorites.add("Grape");
                favorites.add("Wine");
                break;
            case "Robin":
                favorites.add("Spaghetti");
                favorites.add("Wood");
                favorites.add("Iron Ingot");
                break;
            default:
                favorites.add("");
                break;
        }
        return favorites;
    }

    private ArrayList<Quest> quests() {
        ArrayList<Quest> quests = new ArrayList<>();
        switch (this.name()) {
            case "Sebastian":
                quests.add(new Quest("50 units of iron", "2 diamonds"));
                quests.add(new Quest("pumpkin pie", "5000 gold coins"));
                quests.add(new Quest("150 units of stone", "50 quartz"));
                break;
            case "Abigail":
                quests.add(new Quest("a gold bar", "1 friendship level"));
                quests.add(new Quest("a pumpkin", "500 gold coins"));
                quests.add(new Quest("50 wheat", "1 automatic iridium sprinkler"));
                break;
            case "Harvey":
                quests.add(new Quest("12 of any plant", "750 gold coins"));
                quests.add(new Quest("a salmon fish", "1 friendship level"));
                quests.add(new Quest("a bottle of wine", "5 salads"));
                break;
            case "Lia":
                quests.add(new Quest("10 hardwood", "500 gold coins"));
                quests.add(new Quest("a salmon fish", "Salmon dinner cooking recipe"));
                quests.add(new Quest("200 wood", "3 deluxe scarecrow"));
                break;
            case "Robin":
                quests.add(new Quest("80 wood", "1000 gold coins"));
                quests.add(new Quest("10 iron bars", "3 beehives"));
                quests.add(new Quest("1000 wood", "25000 gold coins"));
                break;
            default:
                break;
        }
        return quests;
    }
}
