package core.GraphicView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.Model.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardUI implements Screen {
    private Label titleLabel;
    private Table table;
    private Stage stage;
    private SelectBox<String> selectBox;
    private ScrollPane scrollPane;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table(GameAssetManager.getDefaultSkin());
        table.setFillParent(true);
        table.top().pad(20);

        // Title
        titleLabel = new Label("---------- Scoreboard ----------", GameAssetManager.getDefaultSkin());
        titleLabel.setAlignment(Align.center);

        // SelectBox
        selectBox = new SelectBox<>(GameAssetManager.getDefaultSkin());
        selectBox.setItems("Score", "Username");
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updatePlayerList(); // re-sort and update table when changed
            }
        });

        // Header Row
        table.add(titleLabel).colspan(2).center().padBottom(20).row();
        table.add(new Label("Username", GameAssetManager.getDefaultSkin())).pad(10);
        table.add(selectBox).pad(10).row();
        table.add(new Label("Farming\nSkill", GameAssetManager.getDefaultSkin())).pad(10);
        table.add(new Label("Mining\nSkill", GameAssetManager.getDefaultSkin())).pad(10);
        table.add(new Label("Foraging\nSkill", GameAssetManager.getDefaultSkin())).pad(10);
        table.add(new Label("Fishing\nSkill", GameAssetManager.getDefaultSkin())).pad(10);
        table.add(new Label("Games Played", GameAssetManager.getDefaultSkin())).pad(10);
        table.add(new Label("Money", GameAssetManager.getDefaultSkin())).pad(10);
        table.add(new Label("Missions", GameAssetManager.getDefaultSkin())).pad(10);
        // Scrollable list of users
        scrollPane = new ScrollPane(new Table(), GameAssetManager.getDefaultSkin());
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).colspan(2).expand().fill().padTop(20);

        updatePlayerList();

        stage.addActor(table);
    }

    private void updatePlayerList() {
        Table content = new Table();
        ArrayList<User> players = sortPlayers();

        for (User user : players) {
            content.add(new Label(user.getUsername(), GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label(String.valueOf(user.getHighScore()), GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label(user.getFarmingSkill().toString(), GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label(user.getMiningSkill().toString(), GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label(user.getForagingSkill().toString(), GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label(user.getFishingSkill().toString(), GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label(user.getGamesPlayed()+"", GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label(user.getMoney()+"", GameAssetManager.getDefaultSkin())).pad(10);
            content.add(new Label("0", GameAssetManager.getDefaultSkin())).pad(10);
//            content.add(new Label(user.get))

        }

        scrollPane.setWidget(content);
    }

    private ArrayList<User> sortPlayers() {
        ArrayList<User> players = new ArrayList<>(App.getCurrentGame().getPlayers());

        String selected = selectBox.getSelected();
        if (selected.equals("Username")) {
            players.sort(Comparator.comparing(User::getUsername));
        } else if (selected.equals("Score")) {
            players.sort(Comparator.comparing(User::getHighScore).reversed());
        }
        //TODO
        return players;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // black background
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
