package core.GraphicView;

import common.models.Message;
import core.Controller.GameMenuController;
import core.Controller.LoginMenuController;
import core.Controller.MainMenuController;
import core.Model.App;
import core.Model.GameAssetManager;
import core.Model.Result;
import core.Model.User;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import peer.app.P2TConnectionThread;
import peer.app.PeerApp;
import tracker.app.PeerConnectionThread;

import java.util.ArrayList;
import java.util.HashMap;

public class PregameMenuUI implements Screen {
    private Stage stage;
    private Skin skin;
    private MainMenuController mainMenuController;
    private GameMenuController gameController;
    private Table mainTable;
    private TextButton newGameBtn, loadGameBtn, deleteGameBtn, backButton;
    ArrayList<Integer> mapNumbers = new ArrayList<>();

    // Main layout
    private Table rootTable;
    private Cell<Table> mainContentCell;

    // Game creation UI elements
    private Table creationTable;
    private TextField usernameField;
    private TextButton addPlayerBtn, advanceBtn;
    private Label playersLabel;
    private SelectBox<Integer> farmTypeSelect;
    private ScrollPane playersScroll;
    private Table playersTable;

    public PregameMenuUI(MainMenuController mainMenuController) {
        this.stage = new Stage(new ScreenViewport());
        this.skin = GameAssetManager.getDefaultSkin();
        this.mainMenuController = mainMenuController;
        this.gameController = new GameMenuController();

        setupLayout();
        mainTable = createMainMenuTable();
        mainContentCell.setActor(mainTable);

        Gdx.input.setInputProcessor(stage);
    }

    private void setupLayout() {
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Left panel for online players
        Table onlinePlayersTable = createOnlinePlayersTable();
        rootTable.add(onlinePlayersTable).width(250).growY().pad(15);

        // A vertical line to separate the panels
        Image separator = new Image(skin.newDrawable("white", 0, 0, 0, 0.75f));
        rootTable.add(separator).width(2).growY().padTop(20).padBottom(20);

        // Right panel for the main content
        mainContentCell = rootTable.add((Table)null).grow().pad(15);
    }

    private Table createOnlinePlayersTable() {
        HashMap<String , Object> body = new HashMap<>();
        body.put("command", "onlineUsers");
        PeerApp.getP2TConnection().sendAndWaitForResponse(new Message(body, Message.Type.command), 500);
        Table table = new Table(skin);
        table.top().left();
        table.pad(10);

        Label title = new Label("Online Players", skin, "title");
        table.add(title).padBottom(20).row();

        Table playersList = new Table(skin);
        // Assumes App.onlineUsers is a static ArrayList<User>
        ArrayList<User> onlineUsers = App.onlineUsers;
        if (onlineUsers != null && !onlineUsers.isEmpty()) {
            for (User user : onlineUsers) {
                playersList.add(new Label(user.getUsername(), skin)).left().padBottom(5).row();
            }
        } else {
            playersList.add(new Label("No players online", skin)).left().row();
        }

        ScrollPane scrollPane = new ScrollPane(playersList, skin);
        scrollPane.setFadeScrollBars(false);
        table.add(scrollPane).grow();

        return table;
    }

    private Table createMainMenuTable() {
        Table table = new Table();
        table.pad(20);

        // Title
        Label title = new Label("Pregame Menu", skin, "title");
        table.add(title).colspan(2).padBottom(30).row();

        // New Game Button
        newGameBtn = new TextButton("Start New Game", skin);
        table.add(newGameBtn).colspan(2).width(300).padBottom(15).row();

        // Load Game Button
        loadGameBtn = new TextButton("Load Last Saved Game", skin);
        table.add(loadGameBtn).colspan(2).width(300).padBottom(15).row();

        // Delete Game Button
        deleteGameBtn = new TextButton("Delete Game", skin);
        table.add(deleteGameBtn).colspan(2).width(300).padBottom(30).row();

        // Back Button
        backButton = new TextButton("Back to Main Menu", skin);
        table.add(backButton).colspan(2);

        // Button listeners
        newGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showGameCreationUI();
            }
        });

        loadGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Placeholder for load game functionality
                showDialog("Info", "Load game functionality will be implemented later");
            }
        });

        deleteGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Placeholder for delete game functionality
                showDialog("Info", "Delete game functionality will be implemented later");
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBackToMainMenu();
            }
        });
        return table;
    }

    private void showGameCreationUI() {
        // Create game creation UI
        creationTable = new Table();
        creationTable.pad(20);

        // Title
        Label title = new Label("Create New Game", skin, "title");
        creationTable.add(title).colspan(2).padBottom(20).row();

        // Username input
        Table inputTable = new Table(skin);
        inputTable.defaults().pad(5);

        inputTable.add(new Label("Add Player:", skin)).left().row();

        usernameField = new TextField("", skin);
        inputTable.add(usernameField).growX().row();

        addPlayerBtn = new TextButton("Add Player", skin);
        inputTable.add(addPlayerBtn).right().row();

        creationTable.add(inputTable).colspan(2).growX().padBottom(15).row();

        // Farm type selection
        Table farmTable = new Table(skin);
        farmTable.defaults().pad(5);

        farmTable.add(new Label("Select Farm Type (1-3):", skin)).left().row();

        farmTypeSelect = new SelectBox<>(skin);
        farmTypeSelect.setItems(1, 2, 3);
        farmTypeSelect.setSelected(1);
        farmTable.add(farmTypeSelect).left().row();

        creationTable.add(farmTable).colspan(2).growX().padBottom(15).row();

        // Players list
        playersLabel = new Label("Players in Game:", skin);
        creationTable.add(playersLabel).colspan(2).left().padBottom(5).row();

        playersTable = new Table(skin);
        playersTable.defaults().pad(5);

        // Add the current user as the first player
        addPlayerToTable(App.getLoggedInUser().getUsername(), 1);

        playersScroll = new ScrollPane(playersTable, skin);
        playersScroll.setFadeScrollBars(false);
        creationTable.add(playersScroll).colspan(2).grow().padBottom(15).row();

        // Advance button
        advanceBtn = new TextButton("Start Game", skin);
        creationTable.add(advanceBtn).colspan(2).width(300).padTop(20).row();

        // Back button
        TextButton creationBackBtn = new TextButton("Back to Pregame Menu", skin);
        creationTable.add(creationBackBtn).colspan(2).padTop(10);

        // Button listeners
        addPlayerBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                if (username.isEmpty()) {
                    showDialog("Error", "Please enter a username");
                    return;
                }

                try {
                    User user = new LoginMenuController().getUser(username);
                    if (user == null) {
                        showDialog("Error", "User not found");
                        return;
                    }

                    if (user.equals(App.getLoggedInUser())) {
                        showDialog("Error", "Cannot add yourself again");
                        return;
                    }

                    // Check if player is already added
                    for (Actor child : playersTable.getChildren()) {
                        if (child instanceof Label) {
                            Label label = (Label) child;
                            if (label.getText().toString().contains(username)) {
                                showDialog("Error", "Player already added");
                                return;
                            }
                        }
                    }

                    int farmType = farmTypeSelect.getSelected();
                    addPlayerToTable(username, farmType);
                    mapNumbers.add(farmType);
                    usernameField.setText("");

                } catch (Exception e) {
                    showDialog("Error", "An error occurred: " + e.getMessage());
                }
            }
        });

        advanceBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    // Get all added players (excluding the first one which is the current user)
                    String player1 = null, player2 = null, player3 = null, player4 = null;
                    int index = 0;

                    for (Actor child : playersTable.getChildren()) {
                        if (child instanceof Label) {
                            Label label = (Label) child;
                            String text = label.getText().toString();
                            if (text.startsWith("- ")) {
                                String username = text.substring(2).split(" \\(")[0];
                                if (index == 0) {
                                    player1 = username;
                                } else if (index == 1) {
                                    player2 = username;
                                } else if (index == 2) {
                                    player3 = username;
                                } else if (index == 3) {
                                    player4 = username;
                                }
                                index++;
                            }
                        }
                    }

                    // Create the game
                    int[] mapTypes = {1,1,1,1};
                    for (int i = 0; i < mapNumbers.size(); i++) {
                        mapTypes[i] = mapNumbers.get(i);
                    }
                    Result result = gameController.createNewGame(player2, player3, player4,mapTypes);
                    if (result.isSuccess()) {
                        // Game created successfully, proceed to game screen
                        Gdx.app.postRunnable(() -> {
                            dispose();
                            gameController.init();
                        });
                    } else {
                        showDialog("Error", result.toString());
                    }
                } catch (Exception e) {
                    showDialog("Error", "Failed to create game: " + e.getMessage());
                }
            }
        });

        creationBackBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Go back to main pregame menu by switching the actor in the cell
                mainContentCell.setActor(mainTable);
            }
        });

        // Set the new creation table as the main content
        mainContentCell.setActor(creationTable);
    }

    private void addPlayerToTable(String username, int farmType) {
        playersTable.add(new Label("- " + username + " (Farm " + farmType + ")", skin)).left().row();
        playersTable.row();
    }

    private void goBackToMainMenu() {
        dispose();
        Main.getGame().setScreen(new MainMenuUI(mainMenuController));
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
