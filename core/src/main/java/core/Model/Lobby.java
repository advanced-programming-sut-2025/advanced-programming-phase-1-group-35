package core.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Lobby implements Serializable {
    private final String id;
    private String lobbyName;
    private User host;
    private final List<User> players;
    private final List<Integer> mapNumbers;
    private LobbyStatus status;
    private String password; // FIX: Added for private lobbies

    public void setStatus(LobbyStatus lobbyStatus) {
        this.status = lobbyStatus;
    }

    public enum LobbyStatus {
        WAITING,
        IN_GAME
    }

    // FIX: Updated constructor for private lobbies
    public Lobby(String lobbyName, User host, String password) {
        this.id = UUID.randomUUID().toString();
        this.lobbyName = lobbyName;
        this.host = host;
        this.players = new ArrayList<>();
        this.mapNumbers = new ArrayList<>();
        this.status = LobbyStatus.WAITING;
        this.password = (password != null && !password.isEmpty()) ? password : null;
        addPlayer(host, 1);
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getLobbyName() { return lobbyName; }
    public User getHost() { return host; }
    public List<User> getPlayers() { return new ArrayList<>(players); }
    public LobbyStatus getStatus() { return status; }
    public List<Integer> getMapNumbers() { return new ArrayList<>(mapNumbers); }
    public String getPassword() { return password; } // FIX: Getter for password

    // --- Helper Methods ---
    public boolean isPrivate() {
        return this.password != null;
    }

    public boolean checkPassword(String input) {
        return Objects.equals(this.password, input);
    }

    // --- Player Management ---
    public boolean addPlayer(User player, int mapNumber) {
        if (!players.contains(player) && players.size() < 4) {
            players.add(player);
            mapNumbers.add(mapNumber);
            return true;
        }
        return false;
    }

    public void updatePlayerMap(User player, int mapNumber) {
        int index = players.indexOf(player);
        if (index != -1 && mapNumber >= 1 && mapNumber <= 3) {
            mapNumbers.set(index, mapNumber);
        }
    }

    public void removePlayer(User player) {
        int index = players.indexOf(player);
        if (index != -1) {
            players.remove(index);
            mapNumbers.remove(index);
        }
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
