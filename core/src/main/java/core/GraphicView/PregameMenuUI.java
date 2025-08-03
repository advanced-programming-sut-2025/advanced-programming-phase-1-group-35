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
import core.Controller.MainMenuController;
import core.Model.*;
import peer.LobbyUpdateListener;
import peer.P2TConnectionController;
import peer.app.PeerApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PregameMenuUI implements Screen, LobbyUpdateListener {
    private final Stage stage;
    private final Skin skin;
    private final MainMenuController mainMenuController;

    private final Table rootTable;
    private final Cell<Table> mainContentCell;

    // UI State
    private Lobby currentLobby = null;
    private List<Lobby> availableLobbies = new ArrayList<>();

    // UI Components
    private Table lobbiesContainer;
    private Table lobbyPlayersTable;
    private Table lobbyViewTable; // Keep a reference to check which view is active

    public PregameMenuUI(MainMenuController mainMenuController) {
        this.stage = new Stage(new ScreenViewport());
        this.skin = GameAssetManager.getDefaultSkin();
        this.mainMenuController = mainMenuController;

        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        mainContentCell = rootTable.add((Table) null).grow().pad(15);

        // Register this UI screen to listen for network updates.
        P2TConnectionController.addLobbyUpdateListener(this);

        Gdx.input.setInputProcessor(stage);
    }

    // --- UI Creation and Refresh Logic ---

    private void showLobbyListUI() {
        currentLobby = null;
        Table lobbyListTable = new Table(skin);
        lobbyListTable.pad(20f);

        Label title = new Label("Game Lobbies", skin, "title");
        lobbyListTable.add(title).padBottom(30).row();

        lobbiesContainer = new Table();
        ScrollPane scrollPane = new ScrollPane(lobbiesContainer, skin);
        lobbyListTable.add(scrollPane).grow().padBottom(20).row();

        refreshLobbyListView(); // Populate with current data

        TextButton createLobbyButton = new TextButton("Create New Lobby", skin);
        TextButton backButton = new TextButton("Back to Main Menu", skin);

        // FIX: Add the Refresh Button
        TextButton refreshButton = new TextButton("Refresh", skin);

        Table buttonTable = new Table();
        buttonTable.add(createLobbyButton).width(250).pad(10);
        buttonTable.add(refreshButton).width(150).pad(10); // Add the button to the table

        lobbyListTable.add(buttonTable).padBottom(10).row();
        lobbyListTable.add(backButton).width(300);

        createLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showCreateLobbyDialog();
            }
        });

        // FIX: Add the listener for the refresh button
        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Call the method from PeerApp to request a manual refresh
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
        // Automatically refresh when the screen is shown
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
                Label lobbyLabel = new Label(
                    String.format("%s (%d/4)", lobby.getLobbyName(), lobby.getPlayers().size()),
                    skin
                );
                TextButton joinButton = new TextButton("Join", skin);
                joinButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        joinLobby(lobby.getId());
                    }
                });
                lobbiesContainer.add(lobbyLabel).growX().padRight(20);
                lobbiesContainer.add(joinButton).width(100).row();
            }
        }
    }

    private void showLobbyViewUI() {
        if (currentLobby == null) return;

        lobbyViewTable = new Table(skin); // Assign to the class member
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
                    showDialog("Info", "Game would start now!");
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

        for (User player : currentLobby.getPlayers()) {
            String labelText = player.getUsername();
            if (player.equals(currentLobby.getHost())) {
                labelText += " (Host)";
            }
            lobbyPlayersTable.add(new Label(labelText, skin)).row();
        }
    }

    private void showCreateLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin);
        dialog.pad(40);
        dialog.getContentTable().add(new Label("Enter lobby name:", skin)).row();
        TextField lobbyNameField = new TextField(App.getLoggedInUser().getUsername() + "'s Game", skin);
        dialog.getContentTable().add(lobbyNameField).width(300).padTop(10).row();

        TextButton confirmButton = new TextButton("Create", skin);
        dialog.getButtonTable().add(confirmButton).width(120);
        TextButton cancelButton = new TextButton("Cancel", skin);
        dialog.getButtonTable().add(cancelButton).width(120);

        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String lobbyName = lobbyNameField.getText().trim();
                if (!lobbyName.isEmpty()) {
                    dialog.hide();
                    createLobby(lobbyName);
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

    // --- Network Communication ---

    private void createLobby(String lobbyName) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "create_lobby");
        body.put("lobby_name", lobbyName);
        Message request = new Message(body, Message.Type.command);
        if (PeerApp.getP2TConnection() != null) {
            PeerApp.getP2TConnection().sendMessage(request);
        }
    }

    private void joinLobby(String lobbyId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "join_lobby");
        body.put("lobby_id", lobbyId);
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

    // --- Listener Implementation ---

    @Override
    public void onLobbyListUpdated(List<Lobby> lobbies) {
        if (lobbies == null) return;
        this.availableLobbies = lobbies;
        // Only refresh the list view if we are not currently inside a lobby
        if (currentLobby == null) {
            // Ensure this runs on the main UI thread
            Gdx.app.postRunnable(this::refreshLobbyListView);
        }
    }

    @Override
    public void onLobbyStateUpdated(Lobby lobby) {
        if (lobby == null) return;
        this.currentLobby = lobby;
        // Ensure this runs on the main UI thread
        Gdx.app.postRunnable(() -> {
            // If we aren't already in the lobby view, switch to it.
            // Otherwise, just refresh the player list.
            if (mainContentCell.getActor() != lobbyViewTable) {
                showLobbyViewUI();
            } else {
                updateLobbyPlayersList();
            }
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
        // Unregister the listener to prevent memory leaks.
        P2TConnectionController.removeLobbyUpdateListener(this);
        stage.dispose();
    }
}
