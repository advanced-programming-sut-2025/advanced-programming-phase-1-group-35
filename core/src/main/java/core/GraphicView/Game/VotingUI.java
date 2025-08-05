package core.GraphicView.Game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import common.models.Message;
import core.GraphicView.GameMenuUI;
import core.Model.App;
import core.Model.GameAssetManager;
import core.Model.User;
import peer.app.PeerApp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VotingUI implements Screen{
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
        stage = new Stage();
        title = new Label("Voting Menu", GameAssetManager.getDefaultSkin());
        table.add(title).pad(10).expandX().fillX().row();
        users = new SelectBox<>(GameAssetManager.getDefaultSkin());
        List<User> list = App.getCurrentGame().getPlayers();
        users.setItems(list.toArray(new User[0]));


        users.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                selectedUser = users.getSelected();
            }
        });
        start = new TextButton("start",GameAssetManager.getDefaultSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedUser != null) {
                    System.out.println("Selected user: " + selectedUser.getUsername());
                }
            }
        });
        table.add(users);
        table.add(start);
        stage.addActor(table);
    }
    public Message NotifyVoting(){
        HashMap<String,Object> body = new HashMap<>();
        body.put("command","start_Vote");
        body.put("user",selectedUser);
        Message message = new Message(body,Message.Type.command);
        return message;
    }
    private void handleVoting(){

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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
}
