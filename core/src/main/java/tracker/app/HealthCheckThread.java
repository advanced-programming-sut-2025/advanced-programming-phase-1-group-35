package tracker.app;

import java.io.IOException;

public class HealthCheckThread extends Thread {
    // Check connections every 30 seconds
    private static final long CHECK_INTERVAL_MS = 30000;

    public HealthCheckThread() {
        // Set as a daemon thread so it doesn't prevent the JVM from exiting
        setDaemon(true);
    }

    @Override
    public void run() {
        System.out.println("❤️ Heartbeat service started. Checking peer health periodically.");
        while (!TrackerApp.isEnded()) {
            try {
                Thread.sleep(CHECK_INTERVAL_MS);
                TrackerApp.checkConnections();
            } catch (InterruptedException e) {
                System.out.println("Heartbeat service interrupted and stopping.");
                break;
            } catch (Exception e) {
                System.err.println("❌ An error occurred during the periodic health check: " + e.getMessage());
            }
        }
    }
}
