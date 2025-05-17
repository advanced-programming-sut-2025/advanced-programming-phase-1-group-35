package Model.NPCs;

import Model.App;
import Model.Point;

import java.util.ArrayList;
import java.util.Random;

public class NPC {
    public final String name;
    public ArrayList<String> favorites = new ArrayList<>();
    public ArrayList<Quest> quests = new ArrayList<>();
    public String job;
    public int friendshipPoint;
    public int friendshipLevel;
    public char symbol;
    public Point location;

    public NPC(String name, String job, ArrayList<Quest> quests, ArrayList<String> favorites, char symbol) {
        this.name = name;
        this.job = job;
        this.quests = quests;
        this.favorites = favorites;
        this.friendshipPoint = 0;
        this.friendshipLevel = 1;
        this.location = getRandomLocation();
        this.symbol = symbol;
        App.getCurrentGame().getMap().changeTileSymbol(App.getCurrentGame().getMap().getTiles()[location.x][location.y], symbol, symbol);
    }

    public void updateFriendLevel() {
        this.friendshipLevel = Math.min(3, (int) Math.floor((double) friendshipPoint / 200));
    }

    public Point getRandomLocation() {
        Random rand = new Random();
        return new Point(rand.nextInt(41) + 103, rand.nextInt(5) + 106);
    }

    public Point getLocation() {
        return location;
    }

    public char getSymbol() {
        return symbol;
    }
}
