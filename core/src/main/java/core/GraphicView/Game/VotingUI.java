package core.GraphicView.Game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import core.Model.App;
import core.Model.GameAssetManager;
import core.Model.User;

public class VotingUI implements Screen{
    private Stage stage;
    private Table table;
    private Label title;


    private void initTable() {
        table = new Table();
        table.setFillParent(true);
        stage = new Stage();
        title = new Label("Voting Menu", GameAssetManager.getDefaultSkin());
        table.add(title).pad(10).expandX().fillX().row();
        for(User user : App.getCurrentGame().getPlayers()){
            table.add(new TextButton(user.getUsername(), GameAssetManager.getDefaultSkin())).pad(10).row();
        }

        stage.addActor(table);
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
