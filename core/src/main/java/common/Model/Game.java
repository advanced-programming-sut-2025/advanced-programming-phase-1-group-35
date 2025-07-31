package common.Model;

import tracker.Controller.InGameMenu.NPCController;
import common.Model.NPCs.NPC;
import common.Model.enums.WeatherCondition;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;

public class Game {
    private int gameID;
    private int IDCounter = 1;
    private ArrayList<User> players = new ArrayList<>();
    private User playingUser;
    private GameCalender gameCalender = new GameCalender();
    private Weather weather = new Weather();
    private Map map ;
    private ArrayList<NPC> npcs = new ArrayList<>();
    public OrthographicCamera camera = new OrthographicCamera();
    public NPCController npcController = new NPCController();

    public Game(ArrayList<User> players, User playingUser) {
        map = new Map();
        this.players = players;
        this.playingUser = playingUser;
        this.gameID = IDCounter++;
        this.weather.setWeatherCondition(WeatherCondition.sunny);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(playingUser.getCurrentPoint().first, playingUser.getCurrentPoint().second, 0);
        npcController = new NPCController();
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

    public void update(float deltaTime) {
        Pair<Float, Float> playerPos = playingUser.getCurrentPoint();
        float playerX = playerPos.first * Main.TILE_SIZE;
        float playerY = playerPos.second * Main.TILE_SIZE;

        float camX = camera.position.x;
        float camY = camera.position.y;

        float viewHalfWidth = camera.viewportWidth / 2;
        float viewHalfHeight = camera.viewportHeight / 2;

        float border = Main.TILE_SIZE * 2; // 2-tile margin from edge

        // Horizontal movement
        if (playerX < camX - viewHalfWidth + border) {
            camX = playerX + viewHalfWidth - border;
        } else if (playerX > camX + viewHalfWidth - border) {
            camX = playerX - viewHalfWidth + border;
        }

        // Vertical movement
        if (playerY < camY - viewHalfHeight + border) {
            camY = playerY + viewHalfHeight - border;
        } else if (playerY > camY + viewHalfHeight - border) {
            camY = playerY - viewHalfHeight + border;
        }

        camX = Math.max(viewHalfWidth, Math.min(camX, 300 * Main.TILE_SIZE - viewHalfWidth));
        camY = Math.max(viewHalfHeight, Math.min(camY, 250 * Main.TILE_SIZE - viewHalfHeight));

        camera.position.set(camX, camY, 0);
        camera.update();
    }
}
