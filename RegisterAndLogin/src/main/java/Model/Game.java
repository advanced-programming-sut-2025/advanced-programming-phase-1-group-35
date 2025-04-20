package Model;

import java.util.ArrayList;

public class Game {
    private ArrayList<User> players;
    private User playingUser;
    private GameCalender gameCalender;
    private Weather weather;
    private Map map;

    public Game(ArrayList<User> players, User playingUser) {
        this.players = players;
        this.playingUser = playingUser;
    }


    public void makeRandomMaps() {

    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public User getPlayingUser() {
        return playingUser;
    }

    public void setPlayingUser(User playingUser) {
        this.playingUser = playingUser;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
