package Model;

import java.util.ArrayList;

import Model.Enum.Menu;


public class App {
    public static final ArrayList<User> users = new ArrayList<>();
    public static final ArrayList<Game> games = new ArrayList<>();
    private static User loggedInUser = null;
    private static Menu currentMenu = Menu.LoginMenu;
}
