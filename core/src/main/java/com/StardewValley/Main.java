package com.StardewValley;

import core.Controller.LoginMenuController;
import core.Controller.MainMenuController;
import core.GraphicView.MainMenuUI;
import core.GraphicView.SignUpUI;
import core.Model.App;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main game;
    private static SpriteBatch batch;
    private static OrthographicCamera camera;
    public static int TILE_SIZE = 5;

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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
