package GraphicView;

import Controller.LoginMenuController;
import Model.GameAssetManager;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginUI implements Screen {
    private Stage stage;
    private Skin skin;
    private final TextButton advanceButton;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextButton forgotPasswordButton;
    private final TextButton goToSignUpButton;
    private final Label title;
    public Table table;
    public LoginMenuController controller;

    public LoginUI(LoginMenuController controller) {
        this.controller = controller;
        this.skin = GameAssetManager.getDefaultSkin();
        this.advanceButton = new TextButton("Login", skin);
        advanceButton.setChecked(false);
        this.title = new Label("Login Menu", skin);
        this.usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter your username");
        this.passwordField = new TextField("", skin);
        passwordField.setMessageText("Enter your password");
        this.forgotPasswordButton = new TextButton("Forgot Password", skin);
        this.forgotPasswordButton.setChecked(false);
        this.goToSignUpButton = new TextButton("back to signup", skin);
        this.goToSignUpButton.setChecked(false);
        this.table = new Table();
        controller.setView(this);
    }


    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        controller.setStage(stage);

        table.setFillParent(true);
        table.center();
        table.add(title);
        table.row().pad(15, 0 , 15 , 0);
        table.add(usernameField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(passwordField).width(600);
        table.row().pad(15, 0 , 10 , 0);
        table.add(advanceButton).width(300);
        table.row().pad(10, 0 , 10 , 0);
        table.add(forgotPasswordButton).width(300);
        table.row().pad(10, 0 , 10 , 0);
        table.add(goToSignUpButton).width(300);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

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
