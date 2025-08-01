package peer.app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class PeerApp {
    public static final int TIMEOUT_MILLIS = 500;

    private static String peerIP;
    private static int peerPort;
    private static String trackerIP;
    private static int trackerPort;
    private static String sharedFolderPath;

    // Thread management
    private static P2TConnectionThread trackerConnectionThread;
    private static boolean exitFlag = false;

    public static boolean isEnded() {
        return exitFlag;
    }

    public static void initFromArgs(String[] args) throws Exception {
        trackerIP = "127.0.0.1";
        trackerPort = 2000;
        if(args != null && args.length == 2) {
            // 1. Parse self address (ip:port)
            String[] peerAddress = args[0].split(":");
            if (peerAddress.length != 2) {
                throw new IllegalArgumentException("Invalid peer address format. Expected <ip:port>");
            }
            peerIP = peerAddress[0];
            peerPort = Integer.parseInt(peerAddress[1]);

            // 2. Parse tracker address (ip:port)
            String[] trackerAddress = args[1].split(":");
            if (trackerAddress.length != 2) {
                throw new IllegalArgumentException("Invalid tracker address format. Expected <ip:port>");
            }
            trackerIP = trackerAddress[0];
            trackerPort = Integer.parseInt(trackerAddress[1]);
            // 4. Create tracker connection thread
        }
        else{
            autoConfigure();
        }
        trackerConnectionThread = new P2TConnectionThread(new Socket(trackerIP, trackerPort));
    }

    private static void autoConfigure() throws Exception {
        // Get local IP address
        try {
            peerIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            peerIP = "127.0.0.1"; // fallback to localhost
        }

        // Find an available port
        peerPort = findAvailablePort(50000, 51000); // Search between 50000-51000
    }

    private static int findAvailablePort(int minPort, int maxPort) throws Exception {
        for (int port = minPort; port <= maxPort; port++) {
            try (ServerSocket testSocket = new ServerSocket(port)) {
                // If we get here, the port was available
                return port;
            } catch (Exception e) {
                // Port in use, try next one
            }
        }
        throw new Exception("No available ports found in range " + minPort + "-" + maxPort);
    }

    public static void endAll() {
        exitFlag = true;

        // 1. End tracker connection
        if (trackerConnectionThread != null) {
            trackerConnectionThread.end();
        }
    }

    public static void connectTracker() {
        // Check if thread exists and not running, then Start thread
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

    public static String getSharedFolderPath() {
        return sharedFolderPath;
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

