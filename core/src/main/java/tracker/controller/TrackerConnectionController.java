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
            case "update_map_selection":
                return updateUserMapSelection(message, user);
            case "start_game":
                return startGame(message, user);
                return createSuccessResponse("start_game_response", new HashMap<>());
            case "start_Vote":
                return NotifyVoting(message.getFromBody("user"));
            default:
                System.err.println("Unknown command received: " + command);
                return createErrorResponse(command, "Unknown command.");
        }
    }

    private static Message updateUserMapSelection(Message message, User user) {
        String lobbyId = message.getFromBody("lobby_id");
        int mapNumber = message.getIntFromBody("map_number");

        Optional<Lobby> lobbyOpt = TrackerApp.findLobbyById(lobbyId);
        if (lobbyOpt.isEmpty() || user == null) {
            return createErrorResponse("update_map_response", "Lobby not found or user not logged in.");
        }

        Lobby lobby = lobbyOpt.get();
        lobby.updatePlayerMap(user, mapNumber);
        System.out.println("User '" + user.getUsername() + "' in lobby '" + lobby.getLobbyName() + "' updated map to " + mapNumber);

        broadcastLobbyUpdate(lobby);
        return createSuccessResponse("update_map_response", new HashMap<>());
    }

    private static Message startGame(Message message, User user) {
        String lobbyId = message.getFromBody("lobby_id");
        Optional<Lobby> lobbyOpt = TrackerApp.findLobbyById(lobbyId);

        if (lobbyOpt.isEmpty() || user == null) {
            return createErrorResponse("start_game_response", "Lobby not found or user not logged in.");
        }

        Lobby lobby = lobbyOpt.get();
        if (!lobby.getHost().equals(user)) {
            return createErrorResponse("start_game_response", "Only the host can start the game.");
        }

        lobby.setStatus(Lobby.LobbyStatus.IN_GAME);
        System.out.println("Host '" + user.getUsername() + "' started game in lobby '" + lobby.getLobbyName() + "'.");

        broadcastLobbyUpdate(lobby);
        broadcastGameStart(lobby);

        return createSuccessResponse("start_game_response", new HashMap<>());
    }

    private static void broadcastGameStart(Lobby lobby) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "begin_game");
        body.put("lobby", lobby);
        Message updateMessage = new Message(body, Message.Type.command);

        System.out.println("Broadcasting BEGIN GAME for lobby '" + lobby.getLobbyName() + "'.");

        for (PeerConnectionThread connection : new ArrayList<>(TrackerApp.getConnections())) {
            if (connection.user != null && lobby.getPlayers().contains(connection.user)) {
                connection.sendMessage(updateMessage);
            }
        }
    }

    // --- Existing Methods (Unchanged) ---

    public static Message login(Message message, PeerConnectionThread peerConnectionThread) {
        for (User user : App.users) {
            if(user.getUsername().equals(message.getFromBody("username"))){
                if (!App.onlineUsers.contains(user)) {
                    App.onlineUsers.add(user);
                }
                peerConnectionThread.user = user;
            }
        }

        HashMap<String, Object> updateBody = new HashMap<>();
        updateBody.put("command", "lobby_list_update");
        updateBody.put("lobbies", TrackerApp.getLobbies());
        Message updateMessage = new Message(updateBody, Message.Type.command);
        peerConnectionThread.sendMessage(updateMessage);

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

        if (lobby.getPlayers().isEmpty()) {
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
