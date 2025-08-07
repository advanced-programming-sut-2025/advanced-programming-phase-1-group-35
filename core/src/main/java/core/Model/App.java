package core.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.Model.enums.Menu;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<User> onlineUsers = new ArrayList<>();
    public static ArrayList<Game> games = new ArrayList<>();
    private static Game currentGame = null;
    private static User loggedInUser = null;
    private static boolean stayLoggedIn = false;
    private static Menu currentMenu = Menu.LoginMenu;

    /**
     * Serializes the entire application state, including users and the current game, into a JSON file.
     * It uses the AppHolder class to gather all necessary data.
     *
     * @throws IOException if there is an error writing to the file.
     */
    public static void serializeApp() throws IOException {
        AppHolder appHolder = new AppHolder();
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(User.class, new UserTypeAdapter())
            .create();

        try (FileWriter writer = new FileWriter("app.json")) {
            gson.toJson(appHolder, writer);
            System.out.println("Application data and game state saved successfully.");
        }
    }

    /**
     * Deserializes the application state from a JSON file.
     * It restores users, login status, and the saved game for the logged-in user.
     *
     * @throws IOException if there is an error reading from the file.
     */
    public static void deserializeApp() throws IOException {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserTypeAdapter())
            .create();

        try (FileReader reader = new FileReader("app.json")) {
            AppHolder appHolder = gson.fromJson(reader, AppHolder.class);
            if (appHolder != null) {
                appHolder.restoreApp();
                System.out.println("Application data and game state loaded successfully.");
            }
        } catch (IOException e) {
            System.err.println("Save file not found. Starting with a fresh state.");
        }
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean stayLoggedIn) {
        App.stayLoggedIn = stayLoggedIn;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }

    public static User findUserByUsername(String name) {
        if (users == null || name == null) return null;
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
