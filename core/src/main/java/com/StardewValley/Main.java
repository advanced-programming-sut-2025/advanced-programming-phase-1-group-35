package com.StardewValley;

import Controller.InGameMenu.CraftingController;
import Controller.LoginMenuController;
import GraphicView.CraftingUI;
import GraphicView.SignUpUI;
import Model.App;
import Model.GameAssetManager;
import Model.User;
import Model.enums.CraftingRecipes;
import Model.enums.Gender;
import Model.enums.SecurityQuestions;
import View.AppView;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main game;
    private static SpriteBatch batch;
    private static OrthographicCamera camera;

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
