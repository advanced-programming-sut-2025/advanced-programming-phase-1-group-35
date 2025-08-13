package peer;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import common.models.Message;
import core.Controller.InGameMenu.FriendshipMenuController;
import core.Model.*;
import core.Model.Serializables.SerializableTile;
import core.Model.Tools.BackPack;
import peer.app.PeerApp;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P2TConnectionController {
    // A thread-safe list to hold UI listeners
    private static final List<LobbyUpdateListener> lobbyListeners = Collections.synchronizedList(new ArrayList<>());
    // A separate list for game state listeners
    private static final List<GameStateUpdateListener> gameStateListeners = Collections.synchronizedList(new ArrayList<>());

    public static void addLobbyUpdateListener(LobbyUpdateListener listener) {
        if (!lobbyListeners.contains(listener)) {
            lobbyListeners.add(listener);
        }
    }

    public static void removeLobbyUpdateListener(LobbyUpdateListener listener) {
        lobbyListeners.remove(listener);
    }

    public static void addGameStateUpdateListener(GameStateUpdateListener listener) {
        if (!gameStateListeners.contains(listener)) {
            gameStateListeners.add(listener);
        }
    }

    public static void removeGameStateUpdateListener(GameStateUpdateListener listener) {
        gameStateListeners.remove(listener);
    }

    public static Message handleCommand(Message message) {
        String command = message.getFromBody("command");
        switch (command) {
            case "status":
                return status();
            case "lobby_list_update":
                handleLobbyListUpdate(message);
                return null;
            case "lobby_state_update":
                handleLobbyStateUpdate(message);
                return null;
            case "begin_game":
                handleGameStart(message);
                return null;
            case "game_state_broadcast":
                handleGameStateBroadcast(message);
                return null;
            default:
                System.out.println("Unknown command from tracker: " + command);
                return null;
        }
    }

    private static void handleGameStateBroadcast(Message message) {
        String username = message.getFromBody("username");
        Map<String, Object> payload = message.getFromBody("payload");
        if (username == null || payload == null) return;
        Game currentGame = App.getCurrentGame();
        if (currentGame == null) return;
        User userToUpdate = currentGame.getPlayer(username);
        if (userToUpdate == null) {
            System.err.println("Received update for a user not in the current game: " + username);
            return;
        }

        String field = (String) payload.get("field");
        Object value = payload.get("value");
        Gdx.app.postRunnable(() -> {
            List<GameStateUpdateListener> listenersCopy = new ArrayList<>(gameStateListeners);
            switch (field) {
                case "money":
                    userToUpdate.setMoney(((Number) value).intValue());
                    for (GameStateUpdateListener listener : listenersCopy) {
                        listener.onPlayerMoneyUpdated(userToUpdate, ((Number) value).intValue());
                    }
                    break;
                case "energy":
                    userToUpdate.getEnergy().setEnergyAmount(((Number) value).intValue());
                    for (GameStateUpdateListener listener : listenersCopy) {
                        listener.onPlayerEnergyUpdated(userToUpdate, ((Number) value).intValue());
                    }
                    break;
                case "position":
                    Map<String, Number> pos = (Map<String, Number>) value;
                    float x = pos.get("x").floatValue();
                    float y = pos.get("y").floatValue();
                    userToUpdate.getCurrentPoint().first = x;
                    userToUpdate.getCurrentPoint().second = y;
                    userToUpdate.setCurrentTile(currentGame.getMap().getTiles()[(int) x][(int) y]);
                    for (GameStateUpdateListener listener : listenersCopy) {
                        listener.onPlayerPositionUpdated(userToUpdate, x, y);
                    }
                    break;
                case "income":
                    userToUpdate.setIncome(((Number) value).intValue());
                    break;
                case "movingDirection":
                    userToUpdate.setMovingDirection(((Number) value).intValue());
                    break;
                case "spouse":
                    userToUpdate.setSpouse(App.findUserByID(((Number) value).intValue()));
                    break;
                case "askedMarriage":
                    userToUpdate.setAskedMarriage(App.findUserByID(((Number) value).intValue()));
                    break;
                case "tile" :
                    Gson gson = new Gson();
                    SerializableTile sTile = gson.fromJson((String) value, SerializableTile.class);
                    Tile tile = SerializableTile.deserializeTile(sTile);
                    App.getCurrentGame().getMap().getTiles()[tile.coordination.x][tile.coordination.y] = tile;
                    break;
                case "reaction":
                    Map<String, String> reactionData = (Map<String, String>) value;
                    String type = reactionData.get("type");
                    String content = reactionData.get("content");
                    for (GameStateUpdateListener listener : listenersCopy) {
                        listener.onPlayerReaction(userToUpdate, type, content);
                    }
                    break;
                case "email":
                    userToUpdate.setEmail((String) value);
                    break;
                case "username":
                    userToUpdate.setUsername((String) value);
                    break;
                case "password":
                    userToUpdate.setPassword((String) value);
                    break;
                case "backpack":
                    userToUpdate.setBackPack(new Gson().fromJson((String)value, BackPack.class));
                    break;
                case "passTime":
                    try {
                        currentGame.getGameCalender().updateTimeAndDateAndSeasonAfterTurns();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "shopUpdate":
                    System.out.println((String)value);
                    HashMap<String , Object> values = new Gson().fromJson((String)value, HashMap.class);
                    currentGame.getShopByName((String) values.get("shop")).getItemByName((String) values.get("item")).setDailyBoughtCount(getIntFromHashMap(values, "stock"));
                    break;
                case "increaseFriendXP" :
                    HashMap<String , Object> values2 = new Gson().fromJson((String)value, HashMap.class) ;
                    FriendshipMenuController.increaseMutualXP(App.findUserByID(getIntFromHashMap(values2, "sender")),
                        App.findUserByID(getIntFromHashMap(values2, "receiver")), getIntFromHashMap(values2, "xp"));
                    break;
            }
        });
    }

    public static int getIntFromHashMap(HashMap<String , Object> hashMap, String field) {
        return (int)((double)((Double) hashMap.get(field)));
    }

    private static void handleGameStart(Message message) {
        Map<String, Object> lobbyMap = message.getFromBody("lobby");
        Lobby finalLobby = lobbyFromMap(lobbyMap);
        if (finalLobby == null) return;
        for (LobbyUpdateListener listener : new ArrayList<>(lobbyListeners)) {
            listener.onGameStarting(finalLobby);
        }
    }

    private static Lobby lobbyFromMap(Map<String, Object> map) {
        if (map == null) return null;
        String lobbyId = (String) map.get("id");
        String lobbyName = (String) map.get("lobbyName");
        User host = userFromMap((Map<String, Object>) map.get("host"));
        List<Map<String, Object>> playerMaps = (List<Map<String, Object>>) map.get("players");
        List<Number> mapNumbers = (List<Number>) map.get("mapNumbers");
        String password = (String) map.get("password");
        if (lobbyId == null || lobbyName == null || host == null || playerMaps == null || mapNumbers == null) return null;
        Lobby lobby = new Lobby(lobbyName, host, password);
        try {
            Field idField = Lobby.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(lobby, lobbyId);
            Field hostField = Lobby.class.getDeclaredField("host");
            hostField.setAccessible(true);
            hostField.set(lobby, host);
            Field passwordField = Lobby.class.getDeclaredField("password");
            passwordField.setAccessible(true);
            passwordField.set(lobby, password);
            Field playersField = Lobby.class.getDeclaredField("players");
            playersField.setAccessible(true);
            List<User> internalPlayersList = (List<User>) playersField.get(lobby);
            internalPlayersList.clear();
            Field mapNumbersField = Lobby.class.getDeclaredField("mapNumbers");
            mapNumbersField.setAccessible(true);
            List<Integer> internalMapNumbersList = (List<Integer>) mapNumbersField.get(lobby);
            internalMapNumbersList.clear();
            for (int i = 0; i < playerMaps.size(); i++) {
                User player = userFromMap(playerMaps.get(i));
                if (player != null) {
                    internalPlayersList.add(player);
                    internalMapNumbersList.add(mapNumbers.get(i).intValue());
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return lobby;
    }

    private static User userFromMap(Map<String, Object> map) {
        if (map == null) return null;
        String username = (String) map.get("username");
        if (username == null) return null;
        return App.findUserByUsername(username);
    }

    private static void handleLobbyListUpdate(Message message) {
        List<Map<String, Object>> lobbyMaps = message.getFromBody("lobbies");
        List<Lobby> lobbies = new ArrayList<>();
        if (lobbyMaps != null) {
            for (Map<String, Object> map : lobbyMaps) {
                Lobby lobby = lobbyFromMap(map);
                if (lobby != null) lobbies.add(lobby);
            }
        }
        for (LobbyUpdateListener listener : new ArrayList<>(lobbyListeners)) {
            Gdx.app.postRunnable(() -> listener.onLobbyListUpdated(lobbies));
        }
    }

    private static void handleLobbyStateUpdate(Message message) {
        Map<String, Object> lobbyMap = message.getFromBody("lobby");
        Lobby lobby = lobbyFromMap(lobbyMap);
        if (lobby == null) return;
        for (LobbyUpdateListener listener : new ArrayList<>(lobbyListeners)) {
            Gdx.app.postRunnable(() -> listener.onLobbyStateUpdated(lobby));
        }
    }

    public static Message status() {
        HashMap<String , Object> map = new HashMap<>();
        map.put("command", "status");
        map.put("response", "ok");
        map.put("peer", PeerApp.getPeerIP());
        map.put("listen_port", PeerApp.getPeerPort());
        return new Message(map, Message.Type.response);
    }
}
