package tracker;

import core.Controller.MainMenuController;
import core.GraphicView.MainMenuUI;
import core.Model.App;
import tracker.app.TrackerApp;
import tracker.app.TrackerListenerThread;
import tracker.controller.TrackerCLIController;

import java.io.IOException;
import java.util.Scanner;

public class TrackerMain {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        try {
            App.deserializeApp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (args.length < 1) {
            System.err.println("Usage: java tracker.TrackerMain <port>");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            TrackerApp.setListenerThread(new TrackerListenerThread(port));
            TrackerApp.startListening();
            System.out.println("Listening on port " + port);

            // Start the heartbeat service
            TrackerApp.startHealthCheckService();

        } catch (Exception e) {
            int port = Integer.parseInt(args[0]);
            System.err.println("Error starting tracker on port " + port + " : " + e.getMessage());
            return;
        }

        while (!TrackerApp.isEnded()) {
            String result = TrackerCLIController.processCommand(scanner.nextLine().trim());
            System.out.println(result);
        }
        scanner.close();
    }
}
