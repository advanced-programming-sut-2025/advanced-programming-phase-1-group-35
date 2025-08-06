package core.Model;

import core.Model.enums.Menu;

import java.util.ArrayList;

public class AppHolder {
    public ArrayList<User> users;
    public User loggedInUser;
    public boolean stayLoggedIn;

    /**
     * Constructor for AppHolder.
     * This class acts as a data container to gather all static state from the App class
     * for easy serialization to JSON.
     */
    public AppHolder() {
        // Before saving, ensure the current game state is associated with the logged-in user object.
        if (App.getLoggedInUser() != null && App.getCurrentGame() != null) {
            App.getLoggedInUser().setCurrentGame(App.getCurrentGame());
        }
        this.users = App.users;
        this.loggedInUser = App.getLoggedInUser();
        this.stayLoggedIn = App.isStayLoggedIn();
    }

    /**
     * Restores the application's state from this holder object.
     * This is called after deserializing the AppHolder from a JSON file.
     */
    public void restoreApp() {
        App.users = this.users;
        App.setLoggedInUser(this.loggedInUser);
        App.setStayLoggedIn(this.stayLoggedIn);

        // If a user was logged in, find them in the restored list and set their game as the current game.
        if (this.loggedInUser != null) {
            User restoredUser = App.findUserByUsername(this.loggedInUser.getUsername());
            if (restoredUser != null) {
                App.setCurrentGame(restoredUser.getCurrentGame());
            }
        }

        if (this.stayLoggedIn && this.loggedInUser != null) {
            App.setCurrentMenu(Menu.MainMenu);
        }
    }
}
