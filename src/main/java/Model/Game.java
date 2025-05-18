package Model;

import Model.NPCs.NPC;
import Model.enums.NPCs.NPCs;
import Model.enums.WeatherCondition;

import java.util.ArrayList;

public class Game {
    private int gameID;
    private int IDCounter = 1;
    private ArrayList<User> players = new ArrayList<>();
    private User playingUser;
    private GameCalender gameCalender = new GameCalender();
    private Weather weather = new Weather();
    private Map map = new Map();
    private ArrayList<NPC> npcs = new ArrayList<>();

    public Game(ArrayList<User> players, User playingUser) {
        this.players = players;
        this.playingUser = playingUser;
        this.gameID = IDCounter++;
        this.weather.setWeatherCondition(WeatherCondition.sunny);
        npcs.add(NPCs.Abigail.createNPC());
        npcs.add(NPCs.Sebastian.createNPC());
        npcs.add(NPCs.Lia.createNPC());
        npcs.add(NPCs.Robin.createNPC());
        npcs.add(NPCs.Harvey.createNPC());
    }


    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
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

    public GameCalender getGameCalender() {
        return gameCalender;
    }

    public void setGameCalender(GameCalender gameCalender) {
        this.gameCalender = gameCalender;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
