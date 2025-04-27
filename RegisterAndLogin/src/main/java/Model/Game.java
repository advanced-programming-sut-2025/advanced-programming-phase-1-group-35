package Model;

import Model.enums.Seasons;

import java.util.ArrayList;

public class Game {
    private ArrayList<User> players = new ArrayList<>();
    private User playingUser;
    private GameCalender gameCalender;
    private Weather weather;
    private Map map = new Map();
    private Seasons currentSeason;

    public Game(ArrayList<User> players, User playingUser) {
        this.players = players;
        this.playingUser = playingUser;
    }


    public void setSeason(Seasons season){
        this.currentSeason = season;
    }
    public Seasons getSeason(){
        return this.currentSeason;
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


}
