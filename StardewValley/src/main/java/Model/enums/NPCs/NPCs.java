package Model.enums.NPCs;

import Model.NPCs.NPC;
import Model.NPCs.Quest;

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
    public String name ;
    public Quest[] quests;
    public String job;

    public NPC createNPC(){
        return null;
    }
}
