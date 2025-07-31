package peer.GraphicView;

import tracker.Controller.MainMenuController;
import tracker.Controller.ProfileMenuController;
import common.Model.App;
import common.Model.GameAssetManager;
import common.Model.User;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class ProfileMenuUI implements Screen {
    private Stage stage;
    private Skin skin;
    private ProfileMenuController controller;
    private User user;
    private Table mainTable;
    private Label usernameLabel, nicknameLabel, highScoreLabel, gamesPlayedLabel, emailLabel;
    private TextField usernameField, emailField, passwordField;
    private TextButton changeUsernameBtn, changeEmailBtn, changePasswordBtn;
    private TextButton backButton;
    private MainMenuController mainMenuController;

    public ProfileMenuUI(MainMenuController mainMenuController) {
        this.stage = new Stage(new ScreenViewport());
        this.skin = GameAssetManager.getDefaultSkin();
        this.controller = new ProfileMenuController();
        this.user = App.getLoggedInUser();
        this.mainMenuController = mainMenuController;

        createUI();
        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20);
        stage.addActor(mainTable);

        // Title
        Label title = new Label("Profile Information", skin, "title");
        mainTable.add(title).colspan(2).padBottom(30).row();
        backButton = new TextButton("Back to Main Menu", skin);
        mainTable.row().pad(30, 0, 0, 0);
        mainTable.add(backButton).colspan(2);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBackToMainMenu();
            }
        });

        // Current User Info
        createInfoSection();

        // Change Username Section
        createUsernameSection();

        // Change Email Section
        createEmailSection();

        // Change Password Section
        createPasswordSection();

        updateUserInfo();
    }
    private void goBackToMainMenu() {
        dispose();
        Main.getGame().setScreen(new MainMenuUI(mainMenuController));
    }

    private void createInfoSection() {
        Table infoTable = new Table(skin);
        infoTable.defaults().pad(5);

        infoTable.add(new Label("Username:", skin)).left();
        usernameLabel = new Label("", skin);
        infoTable.add(usernameLabel).left().row();

        infoTable.add(new Label("Nickname:", skin)).left();
        nicknameLabel = new Label("", skin);
        infoTable.add(nicknameLabel).left().row();

        infoTable.add(new Label("High Score:", skin)).left();
        highScoreLabel = new Label("", skin);
        infoTable.add(highScoreLabel).left().row();

        infoTable.add(new Label("Games Played:", skin)).left();
        gamesPlayedLabel = new Label("", skin);
        infoTable.add(gamesPlayedLabel).left().row();

        infoTable.add(new Label("Email:", skin)).left();
        emailLabel = new Label("", skin);
        infoTable.add(emailLabel).left().row();

        mainTable.add(infoTable).colspan(2).growX().padBottom(30).row();
    }

    private void createUsernameSection() {
        Table usernameTable = new Table(skin);
        usernameTable.defaults().pad(5);

        usernameTable.add(new Label("Change Username:", skin)).left().row();

        usernameField = new TextField("", skin);
        usernameTable.add(usernameField).growX().row();

        changeUsernameBtn = new TextButton("Change", skin);
        usernameTable.add(changeUsernameBtn).right().row();

        mainTable.add(usernameTable).growX().padBottom(15).row();

        changeUsernameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newUsername = usernameField.getText();
                var result = controller.changeUsername(newUsername);
                showDialog(result.isSuccess() ? "Success" : "Error", result.toString());
                if (result.isSuccess()) {
                    updateUserInfo();
                    usernameField.setText("");
                }
            }
        });
    }

    private void createEmailSection() {
        Table emailTable = new Table(skin);
        emailTable.defaults().pad(5);

        emailTable.add(new Label("Change Email:", skin)).left().row();

        emailField = new TextField("", skin);
        emailTable.add(emailField).growX().row();

        changeEmailBtn = new TextButton("Change", skin);
        emailTable.add(changeEmailBtn).right().row();

        mainTable.add(emailTable).growX().padBottom(15).row();

        changeEmailBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String newEmail = emailField.getText();
                var result = controller.changeEmail(newEmail);
                showDialog(result.isSuccess() ? "Success" : "Error", result.toString());
                if (result.isSuccess()) {
                    updateUserInfo();
                    emailField.setText("");
                }
            }
        });
    }

    private void createPasswordSection() {
        Table passwordTable = new Table(skin);
        passwordTable.defaults().pad(5);

        passwordTable.add(new Label("Change Password:", skin)).left().row();

        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordTable.add(passwordField).growX().row();

        changePasswordBtn = new TextButton("Change", skin);
        passwordTable.add(changePasswordBtn).right().row();

        mainTable.add(passwordTable).growX().padBottom(15).row();

        changePasswordBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // In a real app, you'd want to verify old password first
                String newPassword = passwordField.getText();
                try {
                    // Note: This needs old password verification - simplified for example
                    var result = controller.changePassword("", newPassword, null);
                    showDialog(result.isSuccess() ? "Success" : "Error", result.toString());
                    if (result.isSuccess()) {
                        passwordField.setText("");
                    }
                } catch (IOException e) {
                    showDialog("Error", "Failed to change password");
                }
            }
        });
    }

    private void updateUserInfo() {
        usernameLabel.setText(user.getUsername());
        nicknameLabel.setText(user.getNickname());
        highScoreLabel.setText(String.valueOf(user.getHighScore()));
        gamesPlayedLabel.setText(String.valueOf(user.getGamesPlayed()));
        emailLabel.setText(user.getEmail());
    }

    public void showDialog(String title, String message) {
        Skin skin = GameAssetManager.getDefaultSkin();
        Dialog dialog = new Dialog(title, skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

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

    public void dispose() {
        stage.dispose();
    }
}
