package Model;

import Model.enums.Menu;

import java.util.ArrayList;

public class AppHolder {
    public ArrayList<User> users;
    public ArrayList<Game> games;
    public User loggedInUser = null;
    public boolean stayLoggedIn = false;
    public Menu currentMenu = Menu.LoginMenu;
    public AppHolder() {
        users = App.users;
        games = App.games;
        loggedInUser = App.getLoggedInUser();
        stayLoggedIn = App.isStayLoggedIn();
        currentMenu = App.getCurrentMenu();
        for (Game game : games) {
            for (int i = 0; i < game.getMap().getTiles().length; i++) {
                for (int j = 0; j < game.getMap().getTiles()[i].length; j++) {
                    game.getMap().getTiles()[i][j].setOwner(null);
                }
            }
        }
        for (User user : users) {
            user.setCurrentGame(null);
            user.setCurrentPoint(new Point(user.getCurrentTile().getCoordination().x,
                    user.getCurrentTile().getCoordination().y));
            user.setCurrentTile(null);
        }
    }

    public void restoreApp() {
        App.setCurrentMenu(currentMenu);
        App.setLoggedInUser(loggedInUser);
        App.setStayLoggedIn(stayLoggedIn);
        for (User user : users) {
            user.setCurrentGame(getCurrentGameByID(user.getCurrentGameID()));
            user.setCurrentTile(user.getCurrentGame().getMap().
                    getTiles()[user.getCurrentPoint().x][user.getCurrentPoint().y]);
        }
        for (Game game : games) {
            for (int i = 0; i < game.getMap().getTiles().length; i++) {
                for (int j = 0; j < game.getMap().getTiles()[i].length; j++) {
                    game.getMap().getTiles()[i][j].setOwner(getUserByID(
                            game.getMap().getTiles()[i][j].getOwnerID()
                    ));
                }
            }
        }
        App.users = users;
        App.games = games;
    }

    public Game getCurrentGameByID(int id){
        for (Game game : games) {
            if (game.getGameID() == id) {
                return game;
            }
        }
        return null;
    }
    public User getUserByID(int id){
        for (User user : users) {
            if(id == user.getID()){
                return user;
            }
        }
        return null;
    }
}
