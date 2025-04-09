package Model.enums.NPCs;

import Model.NPCs.NPC;
import Model.NPCs.Quest;

public enum NPCs {
    Clint,
    Morris,
    Pierre,
    Willy,
    Marnie,
    Gus,
    Robin,

    Sebastian,
    Abigail,
    Harvey,
    Lia;

    public String name ;
    public Quest[] quests;
    public String job;

    public NPC createNPC(){}
}
