package tracker;

import tracker.app.TrackerApp;
import tracker.app.TrackerListenerThread;
import tracker.controller.TrackerCLIController;

import java.io.IOException;
import java.util.Scanner;

public class TrackerMain {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java tracker.TrackerMain <port>");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            TrackerApp.setListenerThread(new TrackerListenerThread(port));
            TrackerApp.startListening();
            System.out.println("Listening on port " + port);
        } catch (Exception e) {
            System.err.println("Error starting tracker: " + e.getMessage());
            return;
        }

        while (!TrackerApp.isEnded()) {
            String result = TrackerCLIController.processCommand(scanner.nextLine().trim());
            System.out.println(result);
        }
        scanner.close();
    }
}
