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
    // A thread-safe list to hold UI listeners
    private static final List<LobbyUpdateListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public static void addLobbyUpdateListener(LobbyUpdateListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public static void removeLobbyUpdateListener(LobbyUpdateListener listener) {
        listeners.remove(listener);
    }

    public static Message handleCommand(Message message) {
        String command = message.getFromBody("command");
        switch (command) {
            case "status":
                return status();
            case "onlineUsers":
                return updateOnlineUsers(message);
            // --- New Broadcast Handlers ---
            case "lobby_list_update":
                handleLobbyListUpdate(message);
                return null; // No response needed for a broadcast
            case "lobby_state_update":
                handleLobbyStateUpdate(message);
                return null; // No response needed for a broadcast
            default:
                System.out.println("Unknown command from tracker: " + command);
                return null;
        }
    }

    /**
     * Finds a user instance from the application's central user list.
     * This is important for ensuring that we are using a single object for each user.
     */
    private static User userFromMap(Map<String, Object> map) {
        if (map == null) return null;
        String username = (String) map.get("username");
        if (username == null) return null;
        // Assuming an App.findUserByUsername method exists to get the canonical User object.
        // If it doesn't, you would need to implement it in your App class.
        return App.findUserByUsername(username);
    }

    /**
     * Reconstructs a Lobby object from a Map, which is how it arrives after JSON deserialization.
     * This method is complex because the Lobby class is not designed to be created from a data map.
     * It uses Java Reflection to forcefully set private and final fields (like 'id' and the player list)
     * to ensure the client's Lobby object is an exact mirror of the server's object.
     *
     * @param map The map of data representing the lobby.
     * @return A fully reconstructed Lobby object, or null if reconstruction fails.
     */
    private static Lobby lobbyFromMap(Map<String, Object> map) {
        if (map == null) return null;

        // 1. Extract all necessary data from the map.
        String lobbyId = (String) map.get("id");
        String lobbyName = (String) map.get("lobbyName");
        User host = userFromMap((Map<String, Object>) map.get("host"));
        List<Map<String, Object>> playerMaps = (List<Map<String, Object>>) map.get("players");
        // Numbers from JSON are often Doubles, so we get a list of Numbers and convert to int.
        List<Number> mapNumbers = (List<Number>) map.get("mapNumbers");

        if (lobbyId == null || lobbyName == null || host == null || playerMaps == null || mapNumbers == null) {
            System.err.println("Received incomplete lobby data from server.");
            return null;
        }

        // 2. Create a temporary Lobby object. Its internal state will be incorrect initially.
        Lobby lobby = new Lobby(lobbyName, host);

        // 3. Use Reflection to overwrite the fields to match the server's state.
        try {
            // Overwrite the final 'id' field.
            Field idField = Lobby.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(lobby, lobbyId);

            // Overwrite the 'host' field to be certain it's correct.
            Field hostField = Lobby.class.getDeclaredField("host");
            hostField.setAccessible(true);
            hostField.set(lobby, host);

            // Get access to the internal 'players' list and clear it.
            Field playersField = Lobby.class.getDeclaredField("players");
            playersField.setAccessible(true);
            List<User> internalPlayersList = (List<User>) playersField.get(lobby);
            internalPlayersList.clear();

            // Get access to the internal 'mapNumbers' list and clear it.
            Field mapNumbersField = Lobby.class.getDeclaredField("mapNumbers");
            mapNumbersField.setAccessible(true);
            List<Integer> internalMapNumbersList = (List<Integer>) mapNumbersField.get(lobby);
            internalMapNumbersList.clear();

            // 4. Repopulate the internal lists with the correct data from the server.
            for (int i = 0; i < playerMaps.size(); i++) {
                User player = userFromMap(playerMaps.get(i));
                if (player != null) {
                    internalPlayersList.add(player);
                    internalMapNumbersList.add(mapNumbers.get(i).intValue());
                }
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("FATAL: Could not reconstruct Lobby object due to reflection error. " +
                "The Lobby class structure may have changed.");
            e.printStackTrace();
            return null; // Can't recover from this.
        }

        return lobby;
    }


    private static void handleLobbyListUpdate(Message message) {
        // This method now correctly converts the list of maps into a list of Lobby objects.
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

        System.out.println("Client received lobby list update with " + lobbies.size() + " lobbies.");
        // Notify all registered listeners on the main LibGDX thread
        for (LobbyUpdateListener listener : new ArrayList<>(listeners)) {
            Gdx.app.postRunnable(() -> listener.onLobbyListUpdated(lobbies));
        }
    }

    private static void handleLobbyStateUpdate(Message message) {
        // This method now correctly converts the single map into a Lobby object.
        Map<String, Object> lobbyMap = message.getFromBody("lobby");
        Lobby lobby = lobbyFromMap(lobbyMap);

        if (lobby == null) {
            System.err.println("Received a null or invalid lobby state update.");
            return;
        }

        System.out.println("Client received state update for lobby: " + lobby.getLobbyName());
        for (LobbyUpdateListener listener : new ArrayList<>(listeners)) {
            Gdx.app.postRunnable(() -> listener.onLobbyStateUpdated(lobby));
        }
    }

    private static Message updateOnlineUsers(Message message) {
        System.out.println("Updating online users");
        App.onlineUsers.clear();
        ArrayList<String> names = message.getFromBody("onlineUsers");
        for(String name : names){
            User user = App.findUserByUsername(name);
            if (user != null) {
                App.onlineUsers.add(user);
            }
        }
        System.out.println("online users updated");
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
