package core.GraphicView;

import com.badlogic.gdx.graphics.Texture;
import core.Controller.GameMenuController;
import core.Controller.LoginMenuController;
import core.Model.App;
import core.Model.GameAssetManager;
import core.Model.Result;
import core.Model.enums.SecurityQuestions;
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
    private final TextButton testButton;
    private final TextButton login1Button;
    private final TextButton login2Button;
    private final TextButton login3Button;
    private final TextButton login4Button;
    private final Image bg = new Image(new Texture(Gdx.files.internal("firstMenubg.png")));
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
        testButton = new TextButton("Test", skin);
        testButton.setChecked(false);
        testButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMenuController gameController = new GameMenuController();
                int[] mapTypes = {1,2,3,3};
                Result result = null;
                    result = gameController.createNewGame(App.getLoggedInUser().getUsername(), "player2", "player3", "player4", mapTypes);
                gameController.init();
                App.setCurrentGame(gameController.getGame());
                System.out.println(result.toString());
            }
        });
        login1Button = new TextButton("Login 1", skin);
        login1Button.setChecked(false);
        login1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.login("player1", "Sadeghi12!", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        login2Button = new TextButton("Login 2", skin);
        login2Button.setChecked(false);
        login2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.login("player2", "Sadeghi12!", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        login3Button = new TextButton("Login 3", skin);
        login3Button.setChecked(false);
        login3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.login("player3", "Sadeghi12!", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        login4Button = new TextButton("Login 4", skin);
        login4Button.setChecked(false);
        login4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.login("player4", "Sadeghi12!", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
//        table.add(title).right().center();
        table.row().pad(15, 0 , 15 , 0);
        table.add(usernameField).width(600).right();
        table.row().pad(10, 0 , 10 , 0);
        table.add(passwordField).width(600).right();
        table.row().pad(10, 0 , 10 , 0);
        table.add(confirmPasswordField).width(600).right();
        table.row().pad(10, 0 , 10 , 0);
        table.add(emailField).width(600).right();
        table.row().pad(10, 0 , 10 , 0);
        table.add(randomPasswordButton).width(300).right();
        table.row().pad(15, 0 , 10 , 0);
        table.add(genderBox).width(150).right();
        table.row().pad(10, 0 , 10 , 0).expandX();
        table.add(securityQuestionBox).width(600).right();
        table.row().pad(10, 0 , 10 , 0);
        table.add(securityAnswerField).width(600).right();
        table.row().pad(10, 0 , 10 , 0);
        table.add(advanceButton).width(300).right();
        table.row().pad(10, 0 , 10 , 0);
        table.add(loginButton).width(300).right().row();
        table.add(testButton).width(300).right();
        table.row().pad(10, 0 , 10 , 150);
        table.add(login1Button).width(300).right();
        table.pad(10, 0 , 10 , 0);
        table.add(login2Button).width(300).right();
        table.row().pad(10, 0 , 10 , 150);
        table.add(login3Button).width(300).right();
        table.pad(10, 0 , 10 , 0);
        table.add(login4Button).width(300).right();
        bg.setFillParent(true);
        stage.addActor(bg);
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
            controller.checkRandom();
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

    public TextButton getGenerateRandomPasswordButton() {
        return randomPasswordButton;
    }
}
