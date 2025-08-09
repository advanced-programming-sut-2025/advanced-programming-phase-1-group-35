package core.GraphicView;

import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.models.Message;
import core.Controller.GameMenuController;
import core.Controller.MainMenuController;
import core.Model.*;
import peer.LobbyUpdateListener;
import peer.P2TConnectionController;
import peer.app.PeerApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PregameMenuUI implements Screen, LobbyUpdateListener {
    private final Stage stage;
    private final Skin skin;
    private final MainMenuController mainMenuController;

    private final Table rootTable;
    private final Cell<Table> mainContentCell;

    private Lobby currentLobby = null;
    private List<Lobby> availableLobbies = new ArrayList<>();

    private Table lobbiesContainer;
    private Table lobbyPlayersTable;
    private Table lobbyViewTable;

    public PregameMenuUI(MainMenuController mainMenuController) {
        this.stage = new Stage(new ScreenViewport());
        this.skin = GameAssetManager.getDefaultSkin();
        this.mainMenuController = mainMenuController;
        this.rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        mainContentCell = rootTable.add((Table) null).grow().pad(15);
        P2TConnectionController.addLobbyUpdateListener(this);
        Gdx.input.setInputProcessor(stage);
    }

    private void showLobbyListUI() {
        currentLobby = null;
        Table lobbyListTable = new Table(skin);
        lobbyListTable.pad(20f);
        Label title = new Label("Game Lobbies", skin, "title");
        lobbyListTable.add(title).padBottom(30).row();
        lobbiesContainer = new Table();
        ScrollPane scrollPane = new ScrollPane(lobbiesContainer, skin);
        lobbyListTable.add(scrollPane).grow().padBottom(20).row();
        refreshLobbyListView();
        TextButton createLobbyButton = new TextButton("Create New Lobby", skin);
        TextButton backButton = new TextButton("Back to Main Menu", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);
        Table buttonTable = new Table();
        buttonTable.add(createLobbyButton).width(250).pad(10);
        buttonTable.add(refreshButton).width(150).pad(10);
        lobbyListTable.add(buttonTable).padBottom(10).row();
        lobbyListTable.add(backButton).width(300);
        createLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showCreateLobbyDialog();
            }
        });
        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PeerApp.requestLobbyRefresh();
            }
        });
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBackToMainMenu();
            }
        });
        mainContentCell.setActor(lobbyListTable);
        PeerApp.requestLobbyRefresh();
    }

    private void refreshLobbyListView() {
        if (lobbiesContainer == null) return;
        lobbiesContainer.clear();
        lobbiesContainer.defaults().pad(5);
        if (availableLobbies.isEmpty()) {
            lobbiesContainer.add(new Label("No active lobbies. Why not create one?", skin));
        } else {
            for (Lobby lobby : availableLobbies) {
                String lobbyText = String.format("%s (%d/4)", lobby.getLobbyName(), lobby.getPlayers().size());
                if (lobby.isPrivate()) {
                    lobbyText = "🔒 " + lobbyText;
                }
                Label lobbyLabel = new Label(lobbyText, skin);

                TextButton joinButton = new TextButton("Join", skin);
                joinButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (lobby.isPrivate()) {
                            showPasswordPromptDialog(lobby);
                        } else {
                            showMapSelectionDialog("Join Lobby", (mapNumber) -> joinLobby(lobby.getId(), mapNumber, null));
                        }
                    }
                });
                lobbiesContainer.add(lobbyLabel).growX().padRight(20);
                lobbiesContainer.add(joinButton).width(100).row();
            }
        }
    }

    private void showLobbyViewUI() {
        if (currentLobby == null) return;
        lobbyViewTable = new Table(skin);
        lobbyViewTable.pad(20f);
        Label lobbyTitleLabel = new Label(currentLobby.getLobbyName(), skin, "title");
        lobbyViewTable.add(lobbyTitleLabel).padBottom(30).row();
        lobbyPlayersTable = new Table();
        updateLobbyPlayersList();
        ScrollPane scrollPane = new ScrollPane(lobbyPlayersTable, skin);
        lobbyViewTable.add(scrollPane).grow().padBottom(20).row();
        Table buttonTable = new Table();
        User currentUser = App.getLoggedInUser();
        if (currentUser.equals(currentLobby.getHost())) {
            TextButton startGameButton = new TextButton("Start Game", skin);
            buttonTable.add(startGameButton).width(250).pad(10);
            startGameButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    startGame();
                }
            });
        }
        TextButton leaveLobbyButton = new TextButton("Leave Lobby", skin);
        buttonTable.add(leaveLobbyButton).width(250).pad(10);
        leaveLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                leaveLobby(currentLobby.getId());
                showLobbyListUI();
            }
        });
        lobbyViewTable.add(buttonTable).row();
        mainContentCell.setActor(lobbyViewTable);
    }

    private void updateLobbyPlayersList() {
        if (lobbyPlayersTable == null || currentLobby == null) return;
        lobbyPlayersTable.clear();
        lobbyPlayersTable.defaults().pad(5).align(Align.left);
        List<User> players = currentLobby.getPlayers();
        List<Integer> mapNumbers = currentLobby.getMapNumbers();
        User currentUser = App.getLoggedInUser();
        for (int i = 0; i < players.size(); i++) {
            User player = players.get(i);
            int mapNumber = (i < mapNumbers.size()) ? mapNumbers.get(i) : 1;
            String labelText = String.format("%s (Map %d)", player.getUsername(), mapNumber);
            if (player.equals(currentLobby.getHost())) {
                labelText += " (Host)";
            }
            Table playerRow = new Table();
            playerRow.add(new Label(labelText, skin)).expandX().align(Align.left);
            if (player.equals(currentUser)) {
                TextButton changeMapButton = new TextButton("Change", skin);
                playerRow.add(changeMapButton).width(100).padLeft(20);
                changeMapButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        showMapSelectionDialog("Change Your Map", (newMap) -> updateMapSelection(newMap));
                    }
                });
            }
            lobbyPlayersTable.add(playerRow).growX().row();
        }
    }

    private void showCreateLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin);
        dialog.pad(40);
        Table content = dialog.getContentTable();
        content.defaults().pad(5);

        content.add(new Label("Lobby Name:", skin));
        TextField lobbyNameField = new TextField(App.getLoggedInUser().getUsername() + "'s Game", skin);
        content.add(lobbyNameField).width(250).row();

        CheckBox privateCheckbox = new CheckBox(" Private Lobby", skin);
        content.add(privateCheckbox).colspan(2).left().padTop(10).row();

        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        final Label passwordLabel = new Label("Password:", skin);

        passwordLabel.setVisible(false);
        passwordField.setVisible(false);

        content.add(passwordLabel).left();
        content.add(passwordField).width(250).row();

        privateCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean isChecked = privateCheckbox.isChecked();
                passwordLabel.setVisible(isChecked);
                passwordField.setVisible(isChecked);
            }
        });

        TextButton confirmButton = new TextButton("Create", skin);
        TextButton cancelButton = new TextButton("Cancel", skin);
        dialog.getButtonTable().add(confirmButton).width(120);
        dialog.getButtonTable().add(cancelButton).width(120);

        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String lobbyName = lobbyNameField.getText().trim();
                String password = privateCheckbox.isChecked() ? passwordField.getText() : null;
                if (!lobbyName.isEmpty()) {
                    if (privateCheckbox.isChecked() && (password == null || password.isEmpty())) {
                        showDialog("Error", "Private lobbies require a password.");
                        return;
                    }
                    dialog.hide();
                    createLobby(lobbyName, password);
                } else {
                    showDialog("Invalid Name", "Lobby name cannot be empty.");
                }
            }
        });
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        dialog.show(stage);
    }

    private void showPasswordPromptDialog(Lobby lobby) {
        Dialog dialog = new Dialog("Password Required", skin);
        dialog.pad(40);
        dialog.getContentTable().add(new Label("This lobby is private. Please enter the password:", skin)).row();
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        dialog.getContentTable().add(passwordField).width(300).padTop(10).row();

        TextButton confirmButton = new TextButton("Join", skin);
        dialog.getButtonTable().add(confirmButton).width(120);
        TextButton cancelButton = new TextButton("Cancel", skin);
        dialog.getButtonTable().add(cancelButton).width(120);

        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String password = passwordField.getText();
                dialog.hide();
                showMapSelectionDialog("Join Lobby", (mapNumber) -> joinLobby(lobby.getId(), mapNumber, password));
            }
        });
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        dialog.show(stage);
    }

    private void showMapSelectionDialog(String title, final MapSelectionCallback callback) {
        Dialog dialog = new Dialog(title, skin);
        dialog.pad(40);
        dialog.getContentTable().add(new Label("Select a map type:", skin)).row();
        Table mapButtons = new Table();
        mapButtons.defaults().width(100).pad(10);
        for (int i = 1; i <= 3; i++) {
            final int mapNumber = i;
            TextButton mapButton = new TextButton("Map " + mapNumber, skin);
            mapButtons.add(mapButton);
            mapButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    dialog.hide();
                    callback.onMapSelected(mapNumber);
                }
            });
        }
        dialog.getContentTable().add(mapButtons).padTop(10).row();
        TextButton cancelButton = new TextButton("Cancel", skin);
        dialog.getButtonTable().add(cancelButton).width(120);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        dialog.show(stage);
    }

    // --- Network Communication ---

    private void createLobby(String lobbyName, String password) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "create_lobby");
        body.put("lobby_name", lobbyName);
        if (password != null && !password.isEmpty()) {
            body.put("password", password);
        }
        Message request = new Message(body, Message.Type.command);
        if (PeerApp.getP2TConnection() != null) {
            PeerApp.getP2TConnection().sendMessage(request);
        }
    }

    private void joinLobby(String lobbyId, int mapNumber, String password) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "join_lobby");
        body.put("lobby_id", lobbyId);
        body.put("map_number", mapNumber);
        if (password != null) {
            body.put("password", password);
        }
        Message request = new Message(body, Message.Type.command);
        if (PeerApp.getP2TConnection() != null) {
            PeerApp.getP2TConnection().sendMessage(request);
        }
    }

    private void leaveLobby(String lobbyId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "leave_lobby");
        body.put("lobby_id", lobbyId);
        Message request = new Message(body, Message.Type.command);
        if (PeerApp.getP2TConnection() != null) {
            PeerApp.getP2TConnection().sendMessage(request);
        }
    }

    private void updateMapSelection(int mapNumber) {
        if (currentLobby == null) return;
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "update_map_selection");
        body.put("lobby_id", currentLobby.getId());
        body.put("map_number", mapNumber);
        Message request = new Message(body, Message.Type.command);
        if (PeerApp.getP2TConnection() != null) {
            PeerApp.getP2TConnection().sendMessage(request);
        }
    }

    private void startGame() {
        if (currentLobby == null) return;
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "start_game");
        body.put("lobby_id", currentLobby.getId());
        Message request = new Message(body, Message.Type.command);
        if (PeerApp.getP2TConnection() != null) {
            PeerApp.getP2TConnection().sendMessage(request);
        }
    }

    // --- Listener Implementation ---

    @Override
    public void onLobbyListUpdated(List<Lobby> lobbies) {
        if (lobbies == null) return;
        this.availableLobbies = lobbies;
        if (currentLobby == null) {
            Gdx.app.postRunnable(this::refreshLobbyListView);
        }
    }

    @Override
    public void onLobbyStateUpdated(Lobby lobby) {
        if (lobby == null) return;
        this.currentLobby = lobby;
        Gdx.app.postRunnable(() -> {
            if (mainContentCell.getActor() != lobbyViewTable) {
                showLobbyViewUI();
            } else {
                updateLobbyPlayersList();
            }
        });
    }

    @Override
    public void onGameStarting(Lobby finalLobbyState) {
        Gdx.app.postRunnable(() -> {
            User host = App.getLoggedInUser();
            List<String> otherPlayerNames = finalLobbyState.getPlayers().stream()
                .filter(p -> !p.getUsername().equals(host.getUsername()))
                .map(User::getUsername)
                .collect(Collectors.toList());
            int[] mapTypes = finalLobbyState.getMapNumbers().stream().mapToInt(i -> i).toArray();
            GameMenuController gameController = new GameMenuController();
            Result result = gameController.createNewGame(
                host.getUsername(),
                otherPlayerNames.size() <= 0 ? null : otherPlayerNames.get(0),
                otherPlayerNames.size() <= 1 ? null : otherPlayerNames.get(1),
                otherPlayerNames.size() <= 2 ? null : otherPlayerNames.get(2),
                mapTypes
            );
            System.out.println(result);
            gameController.init();
            App.setCurrentGame(gameController.getGame());
        });
    }

    // --- Helper & Lifecycle Methods ---

    private void goBackToMainMenu() {
        dispose();
        Main.getGame().setScreen(new MainMenuUI(mainMenuController));
    }

    public void showDialog(String title, String message) {
        new Dialog(title, skin, "dialog").text(message).button("OK").show(stage);
    }

    @Override
    public void show() {
        showLobbyListUI();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        P2TConnectionController.removeLobbyUpdateListener(this);
        stage.dispose();
    }

    private interface MapSelectionCallback {
        void onMapSelected(int mapNumber);
    }
}
