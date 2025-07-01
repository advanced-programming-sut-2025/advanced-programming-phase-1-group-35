package GraphicView;

import Controller.LoginMenuController;
import Model.GameAssetManager;
import Model.enums.SecurityQuestions;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class SignUpUI implements Screen {
    private Stage stage;
    private Skin skin;
    private final TextButton advanceButton;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextButton randomPasswordButton;
    private final SelectBox genderBox;
    private final SelectBox securityQuestionBox;
    private final TextField securityAnswerField;
    private final TextField confirmPasswordField;
    private final TextField emailField;
    private final TextButton loginButton;
    private final Label title;
    public Table table;
    private final LoginMenuController controller;

    public SignUpUI(LoginMenuController controller) {
        this.controller = controller;
        skin = GameAssetManager.getDefaultSkin();
        this.advanceButton = new TextButton("Signup", skin);
        advanceButton.setChecked(false);
        this.title = new Label("Signup Menu", skin);
        this.usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter your username");
        this.passwordField = new TextField("", skin);
        passwordField.setMessageText("Enter your password");
        this.emailField = new TextField("", skin);
        emailField.setMessageText("Enter your email");
        this.confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setMessageText("Confirm your password");
        this.table = new Table();
        this.randomPasswordButton = new TextButton("Random Password", skin);
        randomPasswordButton.setChecked(false);
        this.genderBox = new SelectBox(skin);
        this.genderBox.setItems("Male", "Female");
        this.securityQuestionBox = new SelectBox(skin);
        this.securityQuestionBox.setItems(SecurityQuestions.Question1.question, SecurityQuestions.Question2.question, SecurityQuestions.Question3.question, SecurityQuestions.Question4.question);
        this.securityAnswerField = new TextField("", skin);
        securityAnswerField.setMessageText("Enter your answer to the question");
        this.loginButton = new TextButton("Login", skin);
        loginButton.setChecked(false);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getGame().getScreen().dispose();
                Main.getGame().setScreen(new LoginUI(new LoginMenuController()));
            }
        });
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
        table.row().pad(15, 0 , 15 , 0);
        table.add(usernameField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(passwordField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(confirmPasswordField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(emailField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(randomPasswordButton).width(300);
        table.row().pad(15, 0 , 10 , 0);
        table.add(genderBox).width(150);
        table.row().pad(10, 0 , 10 , 0);
        table.add(securityQuestionBox).width(800);
        table.row().pad(10, 0 , 10 , 0);
        table.add(securityAnswerField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(advanceButton).width(300);
        table.row().pad(10, 0 , 10 , 0);
        table.add(loginButton).width(300);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        try {
            controller.registerUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public TextField getUsernameField() {
        return usernameField;
    }
    public TextField getPasswordField() {
        return passwordField;
    }
    public TextButton getRandomPasswordButton() {
        return randomPasswordButton;
    }
    public SelectBox getGenderBox() {
        return genderBox;
    }
    public SelectBox getSecurityQuestionBox() {
        return securityQuestionBox;
    }
    public TextField getSecurityAnswerField() {
        return securityAnswerField;
    }

    public TextField getEmailField() {
        return emailField;
    }
    public TextField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public TextButton getAdvanceButton() {
        return advanceButton;
    }
}
