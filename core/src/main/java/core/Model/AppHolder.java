package core.Model;

import core.Model.enums.Menu;

import java.util.ArrayList;

public class AppHolder {
    public ArrayList<User> users;
    public User loggedInUser = null;
    public boolean stayLoggedIn = false;
    public AppHolder() {
        users = App.users;
        loggedInUser = App.getLoggedInUser();
        stayLoggedIn = App.isStayLoggedIn();
    }

    public void restoreApp() {
        App.users = users;
        App.setLoggedInUser(loggedInUser);
        App.setStayLoggedIn(stayLoggedIn);
        if(stayLoggedIn) {
            App.setCurrentMenu(Menu.MainMenu);
            App.setLoggedInUser(loggedInUser);
        }
    }
}
