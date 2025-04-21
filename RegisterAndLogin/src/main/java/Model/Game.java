package Model;

import Model.enums.Seasons;

import java.util.ArrayList;

public class Game {
    private ArrayList<User> players;
    private User playingUser;
    private GameCalender gameCalender;
    private Weather weather;
    private ArrayList<Map> maps;
    private Seasons currentSeason;

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

}
