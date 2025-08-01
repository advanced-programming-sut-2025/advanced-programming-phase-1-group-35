package peer.app;

import java.io.IOException;
import java.net.Socket;
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

        // 3. Set shared folder path
        sharedFolderPath = args[2];

        // Create shared folder if it doesn't exist
        java.io.File folder = new java.io.File(sharedFolderPath);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new Exception("Could not create shared folder: " + sharedFolderPath);
            }
        }

        // 4. Create tracker connection thread
        trackerConnectionThread = new P2TConnectionThread(new Socket(trackerIP, trackerPort));
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

