package peer.app;

import common.models.Message;
import peer.P2TConnectionController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class PeerApp {
    public static final int TIMEOUT_MILLIS = 1500; // Increased timeout for safety

    private static String peerIP;
    private static int peerPort;
    private static String trackerIP;
    private static int trackerPort;

    // Thread management
    private static P2TConnectionThread trackerConnectionThread;
    private static boolean exitFlag = false;

    public static boolean isEnded() {
        return exitFlag;
    }

    public static void initFromArgs(String[] args) throws Exception {
        trackerIP = "127.0.0.1";
        trackerPort = 2223;
        if(args != null && args.length == 2) {
            String[] peerAddress = args[0].split(":");
            if (peerAddress.length != 2) {
                throw new IllegalArgumentException("Invalid peer address format. Expected <ip:port>");
            }
            peerIP = peerAddress[0];
            peerPort = Integer.parseInt(peerAddress[1]);

            String[] trackerAddress = args[1].split(":");
            if (trackerAddress.length != 2) {
                throw new IllegalArgumentException("Invalid tracker address format. Expected <ip:port>");
            }
            trackerIP = trackerAddress[0];
            trackerPort = Integer.parseInt(trackerAddress[1]);
        }
        else{
            autoConfigure();
        }
        trackerConnectionThread = new P2TConnectionThread(new Socket(trackerIP, trackerPort));
    }

    private static void autoConfigure() throws Exception {
        try {
            peerIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            peerIP = "127.0.0.1"; // fallback to localhost
        }
        peerPort = findAvailablePort(50000, 51000);
    }

    private static int findAvailablePort(int minPort, int maxPort) throws Exception {
        for (int port = minPort; port <= maxPort; port++) {
            try (ServerSocket testSocket = new ServerSocket(port)) {
                return port;
            } catch (Exception e) {
                // Port in use, try next one
            }
        }
        throw new Exception("No available ports found in range " + minPort + "-" + maxPort);
    }

    public static void endAll() {
        exitFlag = true;
        if (trackerConnectionThread != null) {
            trackerConnectionThread.end();
        }
    }

    public static void connectTracker() {
        synchronized (PeerApp.class) {
            if (trackerConnectionThread == null || !trackerConnectionThread.isAlive()) {
                try {
                    if (trackerConnectionThread == null) {
                        trackerConnectionThread = new P2TConnectionThread(new Socket(trackerIP, trackerPort));
                    }
                    trackerConnectionThread.start();
                    System.out.println("Tracker connection thread started");
                } catch (Exception e) {
                    System.err.println("Failed to start tracker connection: " + e.getMessage());
                }
            } else {
                System.out.println("Tracker connection is already running");
            }
        }
    }

    /**
     * Call this method from your UI's "Refresh" button.
     * It sends a request to the tracker for the latest lobby list and updates the UI.
     * It runs in a new thread to avoid freezing the game.
     */
    public static void requestLobbyRefresh() {
        new Thread(() -> {
            if (trackerConnectionThread == null || !trackerConnectionThread.isAlive()) {
                System.err.println("Cannot refresh lobbies, not connected to tracker.");
                return;
            }

            System.out.println("Requesting lobby list refresh from tracker...");

            HashMap<String, Object> body = new HashMap<>();
            body.put("command", "get_lobbies");
            Message request = new Message(body, Message.Type.command);

            // This call will now work because of the fix in P2TConnectionThread
            Message response = trackerConnectionThread.sendAndWaitForResponse(request, TIMEOUT_MILLIS);

            if (response != null && "success".equals(response.getFromBody("response"))) {
                List<Map<String, Object>> lobbyMaps = response.getFromBody("lobbies");
                // Use the public method in the controller to update the UI
//                P2TConnectionController.notifyLobbyListUpdated(lobbyMaps);
                System.out.println("Successfully refreshed lobby list. Found " + (lobbyMaps != null ? lobbyMaps.size() : 0) + " lobbies.");
            } else {
                System.err.println("Failed to get lobby list from tracker (request timed out or failed).");
            }
        }).start();
    }


    public static String getPeerIP() {
        return peerIP;
    }

    public static int getPeerPort() {
        return peerPort;
    }

    public static P2TConnectionThread getP2TConnection() {
        return trackerConnectionThread;
    }
}
