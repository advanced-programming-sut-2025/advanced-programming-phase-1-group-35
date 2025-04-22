package Model;

import Model.enums.Menu;

import java.util.ArrayList;

public class AppHolder {
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Game> games = new ArrayList<>();
    public User loggedInUser = null;
    public boolean stayLoggedIn = false;
    public Menu currentMenu = Menu.LoginMenu;
    public AppHolder() {
        users = App.users;
        games = App.games;
        loggedInUser = App.getLoggedInUser();
        stayLoggedIn = App.isStayLoggedIn();
        currentMenu = App.getCurrentMenu();
    }

    public void restoreApp() {
        App.setCurrentMenu(currentMenu);
        App.setLoggedInUser(loggedInUser);
        App.setStayLoggedIn(stayLoggedIn);
        App.users = users;
        App.games = games;
    }
}
