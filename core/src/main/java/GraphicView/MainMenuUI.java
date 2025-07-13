package GraphicView;

import Controller.MainMenuController;
import Model.GameAssetManager;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class MainMenuUI implements Screen {
    private final MainMenuController controller;
    private final Label title;
    private final TextButton preGameButton;
    private final TextButton logoutButton;
    private final TextButton exitButton;
    private final TextButton profileButton;
    private final TextButton testButton;
    private final Table table;
    private Stage stage;
    private Skin skin;


    public MainMenuUI(MainMenuController mainMenuController) {
        skin = GameAssetManager.getDefaultSkin();
        this.controller = mainMenuController;
        title = new Label("Main Menu", skin);
        preGameButton = new TextButton("Pre Game", skin);
        preGameButton.setChecked(false);
        logoutButton = new TextButton("Logout", skin);
        logoutButton.setChecked(false);
        exitButton = new TextButton("Exit", skin);
        exitButton.setChecked(false);
        profileButton = new TextButton("Profile", skin);
        profileButton.setChecked(false);
        testButton = new TextButton("Cook", skin);
        testButton.setChecked(false);
        table = new Table(skin);
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        controller.setStage(stage);

        table.setFillParent(true);
        table.center();
        table.add(title);
        table.row().pad(15 , 0 , 10 , 0);
        table.add(preGameButton);
        table.row().pad(15 , 0 , 10 , 0);
        table.add(profileButton);
        table.row().pad(15 , 0 , 10 , 0);
        table.add(logoutButton);
        table.row().pad(15 , 0 , 10 , 0);
        table.add(exitButton);
        table.row().pad(15 , 0 , 10 , 0);
        table.add(testButton);
        table.row().pad(15 , 0 , 10 , 0);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.goToMenu("what");
        controller.logout();
        try {
            controller.exitMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        testButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getGame().setScreen(new InventoryMenuUI(Main.getGame()));
            }
        });
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }
    public TextButton getPreGameButton() {
        return preGameButton;
    }
    public TextButton getExitButton() {
        return exitButton;
    }
    public TextButton getProfileButton() {
        return profileButton;
    }

}

// TODO: remove test bottom
