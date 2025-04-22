package Model;

import Model.enums.Seasons;

import java.util.ArrayList;

public class Game {
    private static ArrayList<User> players;
    public static User playingUser;
    private static GameCalender gameCalender;
    private static Weather weather;
    private static ArrayList<Map> maps;
    private static Seasons currentSeason;

    public void setSeason(Seasons season){
        currentSeason = season;
    }
    public Seasons getSeason(){
        return currentSeason;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        Game.weather = weather;
    }

    public void makeRandomMaps() {

    }

}
