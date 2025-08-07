package peer;

import com.badlogic.gdx.Gdx;
import common.models.Message;
import core.Model.App;
import core.Model.Lobby;
import core.Model.User;
import peer.app.PeerApp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P2TConnectionController {
    private static final List<LobbyUpdateListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public static void addLobbyUpdateListener(LobbyUpdateListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public static void removeLobbyUpdateListener(LobbyUpdateListener listener) {
        listeners.remove(listener);
    }

    public static void notifyLobbyListUpdated(List<Map<String, Object>> lobbyMaps) {
        List<Lobby> lobbies = new ArrayList<>();
        if (lobbyMaps != null) {
            for (Map<String, Object> map : lobbyMaps) {
                Lobby lobby = lobbyFromMap(map);
                if (lobby != null) {
                    lobbies.add(lobby);
                }
            }
        }

        System.out.println("Client UI is being updated with " + lobbies.size() + " lobbies.");
        // Notify all registered listeners on the main LibGDX thread
        for (LobbyUpdateListener listener : new ArrayList<>(listeners)) {
            if (listener != null) {
                Gdx.app.postRunnable(() -> listener.onLobbyListUpdated(lobbies));
            }
        }
    }

    public static Message handleCommand(Message message) {
        String command = message.getFromBody("command");
        switch (command) {
            case "status":
                return status();
            case "onlineUsers":
                return updateOnlineUsers(message);
            case "lobby_list_update":
                handleLobbyListUpdate(message);
                return null;
            case "lobby_state_update":
                handleLobbyStateUpdate(message);
                return null;
            case "begin_game":
                handleGameStart(message);
                return null;
            default:
                System.out.println("Unknown command from tracker: " + command);
                return null;
        }
    }

    private static void handleGameStart(Message message) {
        Map<String, Object> lobbyMap = message.getFromBody("lobby");
        Lobby finalLobby = lobbyFromMap(lobbyMap);

        if (finalLobby == null) {
            System.err.println("Received a null or invalid lobby for game start.");
            return;
        }

        System.out.println("Client received BEGIN_GAME command for lobby: " + finalLobby.getLobbyName());
        for (LobbyUpdateListener listener : new ArrayList<>(listeners)) {
            listener.onGameStarting(finalLobby);
        }
    }

    private static User userFromMap(Map<String, Object> map) {
        if (map == null) return null;
        String username = (String) map.get("username");
        if (username == null) return null;
        return App.findUserByUsername(username);
    }

    private static Lobby lobbyFromMap(Map<String, Object> map) {
        if (map == null) return null;

        String lobbyId = (String) map.get("id");
        String lobbyName = (String) map.get("lobbyName");
        User host = userFromMap((Map<String, Object>) map.get("host"));
        List<Map<String, Object>> playerMaps = (List<Map<String, Object>>) map.get("players");
        List<Number> mapNumbers = (List<Number>) map.get("mapNumbers");
        // FIX: Get the password from the map (can be null)
        String password = (String) map.get("password");

        if (lobbyId == null || lobbyName == null || host == null || playerMaps == null || mapNumbers == null) {
            System.err.println("Received incomplete lobby data from server.");
            return null;
        }

        // FIX: Use the new constructor that accepts a password
        Lobby lobby = new Lobby(lobbyName, host, password);

        try {
            Field idField = Lobby.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(lobby, lobbyId);

            Field hostField = Lobby.class.getDeclaredField("host");
            hostField.setAccessible(true);
            hostField.set(lobby, host);

            // FIX: Ensure the password field is correctly set via reflection as well
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
            System.err.println("FATAL: Could not reconstruct Lobby object due to reflection error.");
            e.printStackTrace();
            return null;
        }

        return lobby;
    }

    private static void handleLobbyListUpdate(Message message) {
        List<Map<String, Object>> lobbyMaps = message.getFromBody("lobbies");
        List<Lobby> lobbies = new ArrayList<>();
        if (lobbyMaps != null) {
            for (Map<String, Object> map : lobbyMaps) {
                Lobby lobby = lobbyFromMap(map);
                if (lobby != null) {
                    lobbies.add(lobby);
                }
            }
        }

        for (LobbyUpdateListener listener : new ArrayList<>(listeners)) {
            Gdx.app.postRunnable(() -> listener.onLobbyListUpdated(lobbies));
        }
    }

    private static void handleLobbyStateUpdate(Message message) {
        Map<String, Object> lobbyMap = message.getFromBody("lobby");
        Lobby lobby = lobbyFromMap(lobbyMap);

        if (lobby == null) {
            System.err.println("Received a null or invalid lobby state update.");
            return;
        }

        for (LobbyUpdateListener listener : new ArrayList<>(listeners)) {
            Gdx.app.postRunnable(() -> listener.onLobbyStateUpdated(lobby));
        }
    }

    private static Message updateOnlineUsers(Message message) {
        App.onlineUsers.clear();
        ArrayList<String> names = message.getFromBody("onlineUsers");
        for(String name : names){
            User user = App.findUserByUsername(name);
            if (user != null) {
                App.onlineUsers.add(user);
            }
        }
        return new Message(new HashMap<>() , Message.Type.response);
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
