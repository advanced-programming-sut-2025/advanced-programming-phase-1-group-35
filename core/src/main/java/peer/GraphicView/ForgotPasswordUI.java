package peer.GraphicView;

import tracker.Controller.ForgotPasswordMenuController;
import common.Model.GameAssetManager;
import common.Model.User;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ForgotPasswordUI implements Screen {
    private Stage stage;
    private Skin skin;
    private final TextButton advanceButton;
    private final TextButton backButton;
    private final TextField answerTextField;
    private final TextField newPasswordTextField;
    private final Label title;
    private final Label questionLabel;
    private User user;
    public Table table;
    public ForgotPasswordMenuController controller;

    public ForgotPasswordUI(ForgotPasswordMenuController controller, User user) {
        this.user = user;
        this.controller = controller;
        this.skin = GameAssetManager.getDefaultSkin();
        this.title = new Label("forgot password", skin);
        this.advanceButton = new TextButton("change password", skin);
        advanceButton.setChecked(false);
        this.backButton = new TextButton("back", skin);
        backButton.setChecked(false);
        this.answerTextField = new TextField("", skin);
        answerTextField.setMessageText("Enter your answer");
        this.newPasswordTextField = new TextField("", skin);
        newPasswordTextField.setMessageText("Enter your new password");
        this.questionLabel = new Label(user.getSecurityQuestion().question,skin);
        this.table = new Table();
        controller.setView(this);
    }


    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        table.add(title);
        table.row().pad(15, 0 , 15 , 0);
        table.add(questionLabel);
        table.row().pad(15, 0 , 15 , 0);
        table.add(answerTextField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(newPasswordTextField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(advanceButton);
        table.row().pad(10, 0 , 10 , 0);
        table.add(backButton);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleForgetPassword();
        controller.handleGoBack();
    }

    @Override
    public void resize(int width, int height) {

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

    public TextButton getAdvanceButton() {
        return advanceButton;
    }
    public TextField getAnswerTextField() {
        return answerTextField;
    }
    public TextField getNewPasswordTextField() {
        return newPasswordTextField;
    }

    public User getUser() {
        return user;
    }

    public Stage getStage() {
        return stage;
    }

    public Button getBackButton() {
        return backButton;
    }
}
