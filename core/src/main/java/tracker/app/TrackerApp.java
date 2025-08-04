package tracker.app;

import core.Model.App;
import core.Model.Lobby;
import core.Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrackerApp {
    public static final int TIMEOUT_MILLIS = 500;
    private static final List<PeerConnectionThread> connections = Collections.synchronizedList(new ArrayList<>());
    private static final List<Lobby> lobbies = Collections.synchronizedList(new ArrayList<>());
    private static boolean exitFlag = false;
    private static TrackerListenerThread listenerThread;
    private static HealthCheckThread healthCheckThread; // Add this

    public static PeerConnectionThread getConnectionByIpPort(String ip, int port) {
        for (PeerConnectionThread connection : connections) {
            if(connection.getOtherSideIP().equals(ip) && connection.getOtherSidePort() == port) {
                return connection;
            }
        }
        return null;
    }

    public static boolean isEnded() {
        return exitFlag;
    }

    public static void setListenerThread(TrackerListenerThread listenerThread) {
        TrackerApp.listenerThread = listenerThread;
    }

    public static List<PeerConnectionThread> getConnections() {
        return new ArrayList<>(connections);
    }

    public static void startListening() {
        if (listenerThread != null && !listenerThread.isAlive()) {
            listenerThread.start();
        } else {
            throw new IllegalStateException("Listener thread is already running or not set.");
        }
    }

    public static void endAll() {
        exitFlag = true;
        if (healthCheckThread != null) {
            healthCheckThread.interrupt(); // Stop the health check thread
        }
        for (PeerConnectionThread connection : connections)
            connection.end();
        connections.clear();
        lobbies.clear();
        listenerThread.interrupt();
        System.exit(0);
    }

    public static synchronized void removePeerConnection(PeerConnectionThread peerConnectionThread) {
        if (peerConnectionThread != null && connections.contains(peerConnectionThread)) {
            User user = peerConnectionThread.user;
            if (user != null) {
                // Remove user from online list
                App.onlineUsers.remove(user);
                System.out.println("User '" + user.getUsername() + "' logged out.");

                // Remove user from any lobby they were in
                findLobbyWithUser(user).ifPresent(lobby -> {
                    System.out.println("User '" + user.getUsername() + "' was in lobby '" + lobby.getLobbyName() + "'. Removing them.");
                    lobby.removePlayer(user);
                    if (lobby.getPlayers().isEmpty()) {
                        System.out.println("Lobby '" + lobby.getLobbyName() + "' is now empty and will be removed.");
                        removeLobby(lobby);
                    } else {
                        // The broadcast to other players is handled in the controller
                    }
                });
            }

            connections.remove(peerConnectionThread);
            peerConnectionThread.end();
        }
    }

    public static void addPeerConnection(PeerConnectionThread peerConnectionThread) {
        if (peerConnectionThread != null)
            connections.add(peerConnectionThread);
    }

    // --- Health Check Methods ---

    /**
     * This is the method called by the HealthCheckThread.
     * It iterates through all connected peers and pings them.
     */
    public static void checkConnections() {
        System.out.println("🩺 Running health check on " + connections.size() + " peers...");

        // Iterate over a copy of the list to avoid ConcurrentModificationException,
        // as refreshStatus() can trigger the removal of an element from the original list.
        List<PeerConnectionThread> connectionsCopy = new ArrayList<>(connections);

        for (PeerConnectionThread connection : connectionsCopy) {
            try {
                // refreshStatus() already has a timeout and will throw an exception on failure.
                // The catch block inside refreshStatus handles the removal logic.
                connection.refreshStatus();
            } catch (IOException e) {
                // This catch block is just for logging purposes here.
                // The actual removal happens inside refreshStatus().
                System.out.println("Health check confirmed a dead connection has been handled for peer: " + connection.getOtherSideIP());
            }
        }
    }

    public static void startHealthCheckService() {
        if (healthCheckThread == null || !healthCheckThread.isAlive()) {
            healthCheckThread = new HealthCheckThread();
            healthCheckThread.start();
        }
    }


    // --- Lobby Management ---

    public static void addLobby(Lobby lobby) {
        if (lobby != null) {
            lobbies.add(lobby);
        }
    }

    public static void removeLobby(Lobby lobby) {
        if (lobby != null) {
            lobbies.remove(lobby);
        }
    }

    public static Optional<Lobby> findLobbyById(String id) {
        return lobbies.stream()
            .filter(lobby -> lobby.getId().equals(id))
            .findFirst();
    }

    public static Optional<Lobby> findLobbyWithUser(User user) {
        if (user == null) return Optional.empty();
        return lobbies.stream()
            .filter(lobby -> lobby.getPlayers().stream().anyMatch(p -> p.equals(user)))
            .findFirst();
    }

    public static List<Lobby> getLobbies() {
        return new ArrayList<>(lobbies); // Return a copy for thread safety
    }
}
