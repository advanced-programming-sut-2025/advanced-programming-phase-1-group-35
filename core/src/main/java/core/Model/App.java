package core.Model;

import core.Model.enums.Menu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import com.fatboyindustrial.gsonjavatime.LocalDateTimeConverter;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

public class App {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Game> games = new ArrayList<>();
    private static Game currentGame = null;
    private static User loggedInUser = null;
    private static boolean stayLoggedIn = false;
    private static Menu currentMenu = Menu.LoginMenu;

    public static void serializeApp() throws IOException { // to save the progress
        AppHolder appHolder = new AppHolder();
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(User.class , new UserTypeAdapter())
            .create();

        // Serialize to JSON
        try (FileWriter writer = new FileWriter("app.json")) {
            gson.toJson(appHolder, writer);
        }
    }
    public static void deserializeApp() throws IOException{ // to open a save
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(User.class , new UserTypeAdapter())
            .create();

        try (FileReader reader = new FileReader("app.json")) {
            AppHolder appHolder = gson.fromJson(reader, AppHolder.class);
            if (appHolder != null) {
                appHolder.restoreApp();
            }
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
}
