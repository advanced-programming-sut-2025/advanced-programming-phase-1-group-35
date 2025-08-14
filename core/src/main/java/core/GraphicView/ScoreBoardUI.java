package core.GraphicView;

import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.Model.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardUI implements Screen {
    private Stage stage;
    private Table mainTable;
    private SelectBox<String> sortSelectBox;
    private ScrollPane scrollPane;
    private GameMenuUI gameMenuUI;

    public ScoreBoardUI(GameMenuUI gameMenuUI) {
        this.gameMenuUI = gameMenuUI;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        buildUI();
    }

    private void buildUI() {
        Skin skin = GameAssetManager.getDefaultSkin();

        // Main container
        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.top().pad(20);

        // Title
        Label titleLabel = new Label("Scoreboard", skin, "title");
        titleLabel.setAlignment(Align.center);
        mainTable.add(titleLabel).colspan(9).center().padBottom(20).row();

        // Sort select box
        mainTable.add(new Label("Sort by:", skin)).right().pad(5);
        sortSelectBox = new SelectBox<>(skin);
        sortSelectBox.setItems("Score", "Username");
        sortSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updatePlayerList();
            }
        });
        mainTable.add(sortSelectBox).left().pad(5).colspan(8).row();

        // Create one inner table that has both header and data
        Table scoreboardTable = new Table(skin);

        // Header row
        String[] headers = {
            "Username", "Score", "Farming", "Mining", "Foraging",
            "Fishing", "Games Played", "Money", "Missions"
        };
        for (String header : headers) {
            Label headerLabel = new Label(header, skin);
            headerLabel.setAlignment(Align.center);
            scoreboardTable.add(headerLabel).pad(8);
        }
        scoreboardTable.row();

        // ScrollPane
        scrollPane = new ScrollPane(scoreboardTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setOverscroll(false, false);
        mainTable.add(scrollPane).colspan(9).expand().fill().padTop(10).row();

        // Exit button
        TextButton exitButton = new TextButton("Back", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getGame().setScreen(gameMenuUI);
            }
        });
        mainTable.add(exitButton).colspan(9).center().padTop(15);

        stage.addActor(mainTable);

        // Keep a reference to the scoreboardTable for updates
        this.scoreboardTable = scoreboardTable;

        updatePlayerList();
    }

    private Table scoreboardTable;

    private void updatePlayerList() {
        Skin skin = GameAssetManager.getDefaultSkin();
        scoreboardTable.clearChildren();

        // Re-add header row
        String[] headers = {
            "Username", "Score", "Farming", "Mining", "Foraging",
            "Fishing", "Games Played", "Money", "Missions"
        };
        for (String header : headers) {
            Label headerLabel = new Label(header, skin);
            headerLabel.setAlignment(Align.center);
            scoreboardTable.add(headerLabel).pad(8);
        }
        scoreboardTable.row();

        // Add player rows
        ArrayList<User> players = sortPlayers();
        for (User user : players) {
            scoreboardTable.add(new Label(user.getUsername(), skin)).pad(5);
            scoreboardTable.add(new Label(String.valueOf(user.getHighScore()), skin)).pad(5);
            scoreboardTable.add(new Label(user.getFarmingSkill().toString(), skin)).pad(5);
            scoreboardTable.add(new Label(user.getMiningSkill().toString(), skin)).pad(5);
            scoreboardTable.add(new Label(user.getForagingSkill().toString(), skin)).pad(5);
            scoreboardTable.add(new Label(user.getFishingSkill().toString(), skin)).pad(5);
            scoreboardTable.add(new Label(String.valueOf(user.getGamesPlayed()), skin)).pad(5);
            scoreboardTable.add(new Label(String.valueOf(user.getMoney()), skin)).pad(5);
            scoreboardTable.add(new Label("0", skin)).pad(5); // Placeholder for Missions
            scoreboardTable.row();
        }
    }


    private ArrayList<User> sortPlayers() {
        ArrayList<User> players = new ArrayList<>(App.getCurrentGame().getPlayers());
        String selected = sortSelectBox.getSelected();
        if (selected.equals("Username")) {
            players.sort(Comparator.comparing(User::getUsername));
        } else if (selected.equals("Score")) {
            players.sort(Comparator.comparing(User::getHighScore).reversed());
        }
        return players;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
