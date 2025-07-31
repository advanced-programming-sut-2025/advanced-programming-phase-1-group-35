package common.Model.enums.NPCs;

import common.Model.NPCs.NPC;
import common.Model.NPCs.Quest;
import common.Model.Point;
import com.badlogic.gdx.graphics.Texture;

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
    Sebastian(120 , 110 , "npc/sebastianSimple.png"),
    Abigail(125 , 110 , "npc/AbigailSimple.png"),
    Harvey(130 , 110 , "npc/HarveySimple.png"),
    Leah(135 , 110 , "npc/LeahSimple.png"),
    //shop and quest
    Robin(140 , 110 , "npc/RobinSimple.png"),;

    public int x , y;
    public String spriteAddress ;

    NPCs (){

    }

    NPCs(int x , int y, String spriteAddress){
        this.x = x;
        this.y = y;
        this.spriteAddress = spriteAddress;
    }

    public NPC createNPC() {
        NPC npc ;
        if (hasQuests()) {
            npc = new NPC(this.name(), getJob(), quests(), favorites(), getSymbol());
        } else {
            npc = new NPC(this.name(), getJob(), new ArrayList<>(), new ArrayList<>(), getSymbol());
        }
        if(spriteAddress != null){
            npc.location = new Point(x, y);
            npc.texture = new Texture(spriteAddress);
        }
        return npc;
    }

    private boolean hasQuests() {
        return switch (this) {
            case Sebastian, Abigail, Harvey, Leah, Robin -> true;
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
            case Leah:
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
            case Leah:
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
            case Leah -> 'L';
            case Robin -> 'R';
            default -> 'V';
        };
    }

}
