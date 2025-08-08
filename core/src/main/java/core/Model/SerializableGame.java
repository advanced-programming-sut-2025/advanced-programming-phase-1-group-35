package core.Model;

import core.Model.CropClasses.Crop;
import core.Model.enums.WeatherCondition;
import java.io.Serializable;
import java.time.LocalDateTime; // For GameCalender
import java.util.ArrayList;

public class SerializableGame implements Serializable {
    public int gameID;
    // Store player IDs/usernames instead of full User objects to avoid circularity
    public ArrayList<Integer> playerIDs; // List of IDs of players in this game
    public int playingUserID; // ID of the currently playing user
    public SerializableMap map; // Use the serializable map DTO

    // Serializable versions of GameCalender and Weather
//    public LocalDateTime gameDateTime;
    public String currentSeasonName; // Store season as a string or enum name
    public int dayPassedFromSeason;
    public String weatherConditionName; // Store weather as a string or enum name
    public String tomorrowWeatherConditionName;

    // Default constructor for Gson deserialization
    public SerializableGame() {}

    // Constructor to convert a live Game object into a SerializableGame
    public SerializableGame(Game game) {
        this.gameID = game.getGameID();

        this.playerIDs = new ArrayList<>();
        for (User player : game.getPlayers()) {
            this.playerIDs.add(player.getID());
        }
        this.playingUserID = game.getPlayingUser().getID();

        // Convert live Map to SerializableMap
        this.map = new SerializableMap(game.getMap());

        // Copy GameCalender data
//        this.gameDateTime = game.getGameCalender().getGameDateTime();
        this.currentSeasonName = game.getGameCalender().getSeason().name();
        this.dayPassedFromSeason = game.getGameCalender().getDayPassedFromSeason(); // Assuming dayPassedFromSeason is public or has a getter

        // Copy Weather data
        this.weatherConditionName = game.getWeather().getWeatherCondition().name();
        this.tomorrowWeatherConditionName = game.getWeather().getTomorrowCondition().name();

    }
}
