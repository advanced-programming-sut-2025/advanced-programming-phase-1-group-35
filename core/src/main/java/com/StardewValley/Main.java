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
    public static int TILE_SIZE = 40;
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
        game.setScreen(new SignUpUI(new LoginMenuController()));

        try {
            App.deserializeApp();
            if(App.isStayLoggedIn()){
                game.setScreen(new MainMenuUI(new MainMenuController()));
            } else {
                System.out.println("No user logged in. Loading SignUp/Login screen.");
                game.setScreen(new SignUpUI(new LoginMenuController()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
