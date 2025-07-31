package tracker.Controller;

import peer.GraphicView.LoginUI;
import peer.GraphicView.MainMenuUI;
import peer.GraphicView.PregameMenuUI;
import peer.GraphicView.ProfileMenuUI;
import common.Model.App;
import common.Model.enums.Menu;
import common.Model.Result;
import com.StardewValley.Main;

import java.io.IOException;

public class MainMenuController extends Controller {
    public MainMenuUI view ;
    public void setView(MainMenuUI view) {
        this.view = view;
    }
    public Result showCurrentMenu() {
        return new Result(true , "main menu");
    }

    public Result logout() {
        if(!view.getLogoutButton().isChecked())return null;
        App.setCurrentMenu(Menu.LoginMenu);
        App.setStayLoggedIn(false);
        App.setLoggedInUser(null);
        Main.getGame().getScreen().dispose();
        Main.getGame().setScreen(new LoginUI(new LoginMenuController()));
        return new Result(true , "redirecting to login menu");
    }

    public Result goToMenu(String menuString) {
        if(view.getPreGameButton().isChecked()){
            Main.getGame().getScreen().dispose();
            Main.getGame().setScreen(new PregameMenuUI(new MainMenuController()));
            menuString = "game menu";
        }
        else if(view.getProfileButton().isChecked()){
            Main.getGame().getScreen().dispose();
            Main.getGame().setScreen(new ProfileMenuUI(new MainMenuController()));
            menuString = "profile menu";
        }
        else return null;
        Menu menu = switch (menuString) {
            case "login menu" -> Menu.LoginMenu;
            case "profile menu" -> Menu.ProfileMenu;
            default -> null;
        };
        App.setCurrentMenu(menu);
        return new Result(true , "Redirecting to " + menuString);
    }

    public void exitMenu() throws IOException {
        if(!view.getExitButton().isChecked())return;
        if(!App.isStayLoggedIn()) {
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
        }
        App.serializeApp();
        System.exit(0);
    }
}
