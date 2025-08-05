package tracker.controller;

import common.models.Message;
import core.Model.App;
import core.Model.Lobby;
import core.Model.User;
import tracker.app.PeerConnectionThread;
import tracker.app.TrackerApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TrackerConnectionController {
    public static Message handleCommand(Message message, PeerConnectionThread peerConnectionThread) throws IOException {
        String command = message.getFromBody("command");
        User user = peerConnectionThread.user;
        System.out.println("Parsing command: " + command + " from user: " + (user != null ? user.getUsername() : "N/A"));

        switch (command) {
            case "login":
                return login(message, peerConnectionThread);
            case "onlineUsers":
                return onlineUsers(message);
            case "get_lobbies":
                return getLobbies();
            case "create_lobby":
                return createLobby(message, user);
            case "join_lobby":
                return joinLobby(message, user);
            case "leave_lobby":
                return leaveLobby(message, user);
            case "start_game":
                return createSuccessResponse("start_game_response", new HashMap<>());
            case "start_Vote":
                return NotifyVoting(message.getFromBody("user"));
            default:
                System.err.println("Unknown command received: " + command);
                return createErrorResponse(command, "Unknown command.");
        }
    }

    public static Message login(Message message, PeerConnectionThread peerConnectionThread) {
        for (User user : App.users) {
            if(user.getUsername().equals(message.getFromBody("username"))){
                if (!App.onlineUsers.contains(user)) {
                    App.onlineUsers.add(user);
                }
                peerConnectionThread.user = user;
            }
        }

        // FIX: Proactively send a lobby list update to the newly logged-in user.
        // This ensures they see existing lobbies immediately without needing a manual refresh.
        HashMap<String, Object> updateBody = new HashMap<>();
        updateBody.put("command", "lobby_list_update");
        updateBody.put("lobbies", TrackerApp.getLobbies());
        Message updateMessage = new Message(updateBody, Message.Type.command);
        peerConnectionThread.sendMessage(updateMessage);
        System.out.println("Sent initial lobby list to newly logged-in user: " + peerConnectionThread.user.getUsername());


        // Send the standard synchronous response to confirm the login was successful.
        HashMap<String , Object> body = new HashMap<>();
        body.put("command", "login_response");
        body.put("response", "success");
        return new Message(body, Message.Type.response);
    }

    public static Message onlineUsers(Message message) {
        HashMap<String , Object> body = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        for (User onlineUser : App.onlineUsers) {
            names.add(onlineUser.getUsername());
        }
        body.put("command", "onlineUsers_response");
        body.put("onlineUsers", names);
        return new Message(body, Message.Type.response);
    }

    private static Message getLobbies() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("lobbies", TrackerApp.getLobbies());
        System.out.println("Sending " + TrackerApp.getLobbies().size() + " lobbies to a client.");
        return createSuccessResponse("get_lobbies_response", body);
    }

    private static Message createLobby(Message message, User host) {
        String lobbyName = message.getFromBody("lobby_name");
        if (host == null) {
            return createErrorResponse("create_lobby_response", "User must be logged in to create a lobby.");
        }
        if (TrackerApp.findLobbyWithUser(host).isPresent()) {
            return createErrorResponse("create_lobby_response", "You are already in another lobby.");
        }

        Lobby newLobby = new Lobby(lobbyName, host);
        TrackerApp.addLobby(newLobby);
        System.out.println("User '" + host.getUsername() + "' created lobby '" + lobbyName + "'");

        broadcastLobbyListUpdate();
        broadcastLobbyUpdate(newLobby);

        HashMap<String, Object> body = new HashMap<>();
        body.put("lobby", newLobby);
        return createSuccessResponse("create_lobby_response", body);
    }

    private static Message joinLobby(Message message, User user) {
        String lobbyId = message.getFromBody("lobby_id");
        if (user == null) {
            return createErrorResponse("join_lobby_response", "User must be logged in to join a lobby.");
        }

        Optional<Lobby> lobbyOpt = TrackerApp.findLobbyById(lobbyId);
        if (lobbyOpt.isEmpty()) {
            return createErrorResponse("join_lobby_response", "Lobby not found.");
        }

        Lobby lobby = lobbyOpt.get();
        if (lobby.isFull()) {
            return createErrorResponse("join_lobby_response", "Lobby is full.");
        }

        int mapNumber = message.body.containsKey("map_number") ? message.getIntFromBody("map_number") : 1;
        if (lobby.addPlayer(user, mapNumber)) {
            System.out.println("User '" + user.getUsername() + "' joined lobby '" + lobby.getLobbyName() + "'");
            broadcastLobbyUpdate(lobby);
            broadcastLobbyListUpdate();
            HashMap<String, Object> body = new HashMap<>();
            body.put("lobby", lobby);
            return createSuccessResponse("join_lobby_response", body);
        } else {
            return createErrorResponse("join_lobby_response", "Failed to join lobby. You might already be in it.");
        }
    }

    private static Message leaveLobby(Message message, User user) {
        String lobbyId = message.getFromBody("lobby_id");
        if (user == null) {
            return createErrorResponse("leave_lobby_response", "User not logged in.");
        }

        Optional<Lobby> lobbyOpt = TrackerApp.findLobbyById(lobbyId);
        if (lobbyOpt.isEmpty()) {
            return createErrorResponse("leave_lobby_response", "Lobby not found.");
        }

        Lobby lobby = lobbyOpt.get();
        lobby.removePlayer(user);
        System.out.println("User '" + user.getUsername() + "' left lobby '" + lobby.getLobbyName() + "'");

        if (lobby.getPlayers().isEmpty()) {
            System.out.println("Lobby '" + lobby.getLobbyName() + "' is now empty and has been removed.");
            TrackerApp.removeLobby(lobby);
            broadcastLobbyListUpdate();
        } else {
            broadcastLobbyUpdate(lobby);
            broadcastLobbyListUpdate();
        }
        return createSuccessResponse("leave_lobby_response", new HashMap<>());
    }

    private static void broadcastLobbyListUpdate() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "lobby_list_update");
        body.put("lobbies", TrackerApp.getLobbies());
        Message updateMessage = new Message(body, Message.Type.command);

        System.out.println("Broadcasting full lobby list update to all clients.");
        for (PeerConnectionThread connection : new ArrayList<>(TrackerApp.getConnections())) {
            connection.sendMessage(updateMessage);
        }
    }

    private static void broadcastLobbyUpdate(Lobby lobby) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "lobby_state_update");
        body.put("lobby", lobby);
        Message updateMessage = new Message(body, Message.Type.command);

        List<User> playersInLobby = lobby.getPlayers();
        System.out.println("Broadcasting state of lobby '" + lobby.getLobbyName() + "' to " + playersInLobby.size() + " players.");

        for (PeerConnectionThread connection : new ArrayList<>(TrackerApp.getConnections())) {
            if (connection.user != null && playersInLobby.contains(connection.user)) {
                connection.sendMessage(updateMessage);
            }
        }
    }

    private static Message createSuccessResponse(String command, Map<String, Object> body) {
        body.put("command", command);
        body.put("response", "success");
        return new Message(new HashMap<>(body), Message.Type.response);
    }

    private static Message createErrorResponse(String command, String errorMessage) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", command);
        body.put("response", "error");
        body.put("message", errorMessage);
        return new Message(body, Message.Type.response);
    }
    private static Message NotifyVoting(User selectedUser) {
        HashMap<String,Object> body = new HashMap<>();
        body.put("command","start_Vote");
        body.put("user", selectedUser.getUsername());
        Message message = new Message(body,Message.Type.command);
        return message;
    }

}
