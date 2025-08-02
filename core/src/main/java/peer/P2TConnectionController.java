package peer;

import com.badlogic.gdx.Gdx;
import common.models.Message;
import core.Model.App;
import core.Model.Lobby;
import core.Model.User;
import peer.app.PeerApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    private static void handleLobbyListUpdate(Message message) {
        List<Lobby> lobbies = message.getFromBody("lobbies");
        System.out.println("Client received lobby list update with " + lobbies.size() + " lobbies.");
        // Notify all registered listeners on the main LibGDX thread
        for (LobbyUpdateListener listener : new ArrayList<>(listeners)) {
            Gdx.app.postRunnable(() -> listener.onLobbyListUpdated(lobbies));
        }
    }

    private static void handleLobbyStateUpdate(Message message) {
        Lobby lobby = message.getFromBody("lobby");
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
            for(User user : App.users){
                if(user.getUsername().equals(name)){
                    App.onlineUsers.add(user);
                }
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
