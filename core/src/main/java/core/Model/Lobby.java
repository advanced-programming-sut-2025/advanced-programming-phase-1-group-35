package core.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a game lobby where players can gather before starting a game.
 * This object is designed to be sent over the network, so it is Serializable.
 */
public class Lobby implements Serializable {
    private final String id;
    private String lobbyName;
    private User host;
    private final List<User> players;
    private final List<Integer> mapNumbers; // Corresponds to each player's farm choice
    private LobbyStatus status;

    public enum LobbyStatus {
        WAITING,
        IN_GAME
    }

    public Lobby(String lobbyName, User host) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID for each lobby
        this.lobbyName = lobbyName;
        this.host = host;
        this.players = new ArrayList<>();
        this.mapNumbers = new ArrayList<>();
        this.status = LobbyStatus.WAITING;
        addPlayer(host, 1); // The host is the first player, default farm type 1
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getLobbyName() { return lobbyName; }
    public User getHost() { return host; }
    public List<User> getPlayers() { return new ArrayList<>(players); } // Return a copy for safety
    public LobbyStatus getStatus() { return status; }
    public List<Integer> getMapNumbers() { return new ArrayList<>(mapNumbers); }

    // --- Setters ---
    public void setLobbyName(String lobbyName) { this.lobbyName = lobbyName; }
    public void setStatus(LobbyStatus status) { this.status = status; }

    // --- Player Management ---

    /**
     * Adds a player to the lobby if it's not full and the player isn't already in it.
     * @param player The user to add.
     * @param mapNumber The farm type selected by the player.
     * @return true if the player was added, false otherwise.
     */
    public boolean addPlayer(User player, int mapNumber) {
        if (!players.contains(player) && players.size() < 4) {
            players.add(player);
            mapNumbers.add(mapNumber);
            return true;
        }
        return false;
    }

    /**
     * FIX: Updates an existing player's map selection.
     * @param player The player to update.
     * @param mapNumber The new map number.
     */
    public void updatePlayerMap(User player, int mapNumber) {
        int index = players.indexOf(player);
        if (index != -1 && mapNumber >= 1 && mapNumber <= 3) {
            mapNumbers.set(index, mapNumber);
        }
    }

    /**
     * Removes a player from the lobby. If the host leaves, a new host is assigned.
     * @param player The user to remove.
     */
    public void removePlayer(User player) {
        int index = players.indexOf(player);
        if (index != -1) {
            players.remove(index);
            mapNumbers.remove(index);
        }
        // If the host leaves, assign the next player as the new host.
        // If the lobby becomes empty, it should be removed by the tracker.
        if (host.equals(player) && !players.isEmpty()) {
            host = players.get(0);
        }
    }

    public boolean isFull() {
        return players.size() >= 4;
    }

    // --- Overrides ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Lobby lobby = (Lobby) obj;
        return id.equals(lobby.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
