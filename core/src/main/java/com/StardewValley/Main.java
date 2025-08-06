package com.StardewValley;

import core.Controller.GameMenuController;
import core.Controller.InGameMenu.CraftingController;
import core.Controller.LoginMenuController;
import core.Controller.MainMenuController;
import core.GraphicView.CraftingUI;
import core.GraphicView.GameMenuUI;
import core.GraphicView.MainMenuUI;
import core.GraphicView.SignUpUI;
import core.Model.App;
import core.Model.GameAssetManager;
import core.Model.User;
import core.Model.enums.CraftingRecipes;
import core.Model.enums.Gender;
import core.Model.enums.SecurityQuestions;
import core.View.AppView;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import peer.app.PeerApp;

import java.io.IOException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main game;
    private static SpriteBatch batch;
    private static OrthographicCamera camera;
    public static int TILE_SIZE = 5;
    public static String[] arguments ;
    public static Thread peerThread;

    public static void main(String[] args) {
        arguments = args;
    }

    public static Main getGame() {
        return game;
    }

    public static void setGame(Main game) {
        Main.game = game;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public static OrthographicCamera getCamera() {
        return camera;
    }

    public static void setCamera(OrthographicCamera camera) {
        Main.camera = camera;
    }

    @Override
    public void create() {
        game = this;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        try {
            App.deserializeApp();
            // This logic is for automatically continuing a game if the user was logged in.
            if (App.isStayLoggedIn() && App.getLoggedInUser() != null && App.getCurrentGame() != null) {
                System.out.println("Continuing saved game for " + App.getLoggedInUser().getUsername());
                game.setScreen(new GameMenuUI(new GameMenuController(), App.getCurrentGame()));
            } else if (App.isStayLoggedIn() && App.getLoggedInUser() != null) {
                System.out.println("Welcome back! Loading Main Menu.");
                game.setScreen(new MainMenuUI(new MainMenuController()));
            } else {
                System.out.println("No user logged in. Loading SignUp/Login screen.");
                game.setScreen(new SignUpUI(new LoginMenuController()));
            }
        } catch (IOException e) {
            System.err.println("Could not load saved data. Starting fresh.");
            game.setScreen(new SignUpUI(new LoginMenuController()));
        }

        handleConnection();
    }

    private void handleConnection() {
        try {
            PeerApp.initFromArgs(arguments);
            PeerApp.connectTracker();
//            PeerApp.startListening();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing peer: " + e.getMessage());
            return;
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }
}
