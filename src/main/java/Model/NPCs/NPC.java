package Model.NPCs;

import java.util.ArrayList;

public class NPC {
    public final String name;
    public ArrayList<String> favorites = new ArrayList<>();
    public ArrayList<Quest> quests = new ArrayList<>();
    public String job;
    public int friendshipPoint;
    public int friendshipLevel;

    public NPC(String name, String job, ArrayList<Quest> quests, ArrayList<String> favorites) {
        this.name = name;
        this.job = job;
        this.quests = quests;
        this.favorites = favorites;
        this.friendshipPoint = 0;
        this.friendshipLevel = 1;
    }

    public void updateFriendLevel() {
        this.friendshipLevel = Math.min(3, (int) Math.floor((double) friendshipPoint / 200));
    }
}
