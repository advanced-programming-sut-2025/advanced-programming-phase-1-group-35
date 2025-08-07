package core.GraphicView.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.models.Message;
import core.GraphicView.GameMenuUI;
import core.Model.App;
import core.Model.GameAssetManager;
import core.Model.User;
import peer.app.PeerApp;

import java.util.HashMap;
import java.util.List;

public class VotingUI implements Screen {
    private Stage stage;
    private Table table;
    private Label title;
    private SelectBox<User> users;
    private User selectedUser;
    private TextButton start;
    private GameMenuUI gameMenuUI;

    public GameMenuUI getGameMenuUI() {
        return gameMenuUI;
    }

    public void setGameMenuUI(GameMenuUI gameMenuUI) {
        this.gameMenuUI = gameMenuUI;
    }

    private void initTable() {
        table = new Table();
        table.setFillParent(true);

        title = new Label("Voting Menu", GameAssetManager.getDefaultSkin());

        users = new SelectBox<>(GameAssetManager.getDefaultSkin());
        List<User> list = App.getCurrentGame().getPlayers();
        users.setItems(list.toArray(new User[0]));

        users.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedUser = users.getSelected();
            }
        });

        start = new TextButton("Start Vote", GameAssetManager.getDefaultSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleVoting();
            }
        });

        table.add(title).pad(10).expandX().fillX().row();
        table.add(users).pad(10).expandX().fillX().row();
        table.add(start).pad(10).width(150).height(40).row();

        stage.addActor(table);
    }

    private void handleVoting() {
        if (selectedUser != null) {
            PeerApp.getP2TConnection().sendMessage(NotifyVoting());
            showNotification("Vote started for " + selectedUser.getUsername());
        } else {
            showNotification("Please select a user first!");
        }
    }

    public Message NotifyVoting() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "start_Vote");
        body.put("user", selectedUser);
        return new Message(body, Message.Type.command);
    }

    private void showNotification(String message) {
        Label notificationLabel = new Label(message, GameAssetManager.getDefaultSkin());
        notificationLabel.setAlignment(Align.center);

        Table notificationTable = new Table(GameAssetManager.getDefaultSkin());
        notificationTable.setBackground(GameAssetManager.getDefaultSkin().newDrawable("white", com.badlogic.gdx.graphics.Color.DARK_GRAY));
        notificationTable.add(notificationLabel).width(300).pad(10);
        notificationTable.pack();

        notificationTable.setPosition(
            Gdx.graphics.getWidth() / 2f - notificationTable.getWidth() / 2f,
            Gdx.graphics.getHeight() - notificationTable.getHeight() - 20
        );

        notificationTable.getColor().a = 0;
        stage.addActor(notificationTable);

        notificationTable.addAction(Actions.sequence(
            Actions.fadeIn(0.3f),
            Actions.delay(2f),
            Actions.fadeOut(0.5f),
            Actions.removeActor()
        ));
    }

    // ─────────────────────────────────────────────────────────────────────
    // Screen Lifecycle Methods
    // ─────────────────────────────────────────────────────────────────────

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        initTable();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
