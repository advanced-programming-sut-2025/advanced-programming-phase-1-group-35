package peer;

import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import common.models.Message;
import core.GraphicView.GameMenuUI;
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

    /**
     * NEW: Public method to process a list of lobby data and notify the UI.
     * This can be called from anywhere in the client application.
     * @param lobbyMaps A list of maps, where each map is the raw data for a lobby from the server.
     */
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
            // --- Broadcast Handlers ---
            case "lobby_list_update":
                handleLobbyListUpdate(message);
                return null; // No response needed for a broadcast
            case "lobby_state_update":
                handleLobbyStateUpdate(message);
                return null; // No response needed for a broadcast
            case "begin_game":
                handleGameStart(message);
                return null;
            case "start_Vote":
                notifyVoting(message);
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
            // The UI's onGameStarting method already uses postRunnable
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

        if (lobbyId == null || lobbyName == null || host == null || playerMaps == null || mapNumbers == null) {
            System.err.println("Received incomplete lobby data from server.");
            return null;
        }

        Lobby lobby = new Lobby(lobbyName, host);

        try {
            Field idField = Lobby.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(lobby, lobbyId);

            Field hostField = Lobby.class.getDeclaredField("host");
            hostField.setAccessible(true);
            hostField.set(lobby, host);

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
            System.err.println("FATAL: Could not reconstruct Lobby object due to reflection error. " +
                "The Lobby class structure may have changed.");
            e.printStackTrace();
            return null;
        }

        return lobby;
    }


    private static void handleLobbyListUpdate(Message message) {
        List<Map<String, Object>> lobbyMaps = message.getFromBody("lobbies");
        notifyLobbyListUpdated(lobbyMaps);
    }

    private static void handleLobbyStateUpdate(Message message) {
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
    public static void notifyVoting(Message message) {
        String text = "voting for " + message.getFromBody("user") + " was started";
        if(Main.getGame().getScreen().getClass().equals(GameMenuUI.class)){
            ((GameMenuUI)Main.getGame().getScreen()).gameController.showNotification(text);
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
