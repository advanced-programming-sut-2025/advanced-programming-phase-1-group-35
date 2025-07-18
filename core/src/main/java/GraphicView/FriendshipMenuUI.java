package GraphicView;

import Controller.InGameMenu.FriendshipMenuController;
import Model.*;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.List;

public class FriendshipMenuUI implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Skin skin;
    private FriendshipMenuController controller;
    private GameMenuUI gameMenuUI;
    private Table mainTable;
    private Table playersTable;
    private ScrollPane scrollPane;
    private TextField messageField;
    private Label statusLabel;
    private TextButton viewMessagesButton;
    private Window messagesWindow;
    private boolean hasNewMessages;

    private Inventory giftInventory;
    private TextField giftAmountField;
    private User selectedGiftRecipient;
    private TextButton sendGiftButton;

    public FriendshipMenuUI(GameMenuUI gameMenuUI) {
        this.gameMenuUI = gameMenuUI;
        this.controller = new FriendshipMenuController();
        initializeUI();
        checkNotifications(); // Check immediately when menu opens
    }

    private void initializeUI() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = GameAssetManager.getDefaultSkin();

        // Create main table
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setWidth(Gdx.graphics.getWidth() * 0.7f); // Take 70% of width
        stage.addActor(mainTable);

        // Title with notification badge
        Table titleTable = new Table();
        Label titleLabel = new Label("Friendship Menu", skin);
        titleLabel.setFontScale(1.5f);
        titleTable.add(titleLabel).padRight(10);

        // Notification indicator
        Label notificationBadge = new Label("", skin);
        notificationBadge.setColor(1, 0, 0, 1);
        titleTable.add(notificationBadge);
        mainTable.add(titleTable).padBottom(20).colspan(2).row();

        // Status label
        statusLabel = new Label("", skin);
        statusLabel.setColor(1, 0, 0, 1);
        mainTable.add(statusLabel).colspan(2).pad(5).row();

        // Players list
        Label playersLabel = new Label("Players Online:", skin);
        mainTable.add(playersLabel).colspan(2).left().padBottom(10).row();

        // Players scroll pane
        playersTable = new Table();
        scrollPane = new ScrollPane(playersTable, skin);
        scrollPane.setFadeScrollBars(false);
        mainTable.add(scrollPane).colspan(2).fill().height(300).padBottom(20).row();

        // Message input
        Table messageInputTable = new Table();
        messageInputTable.add(new Label("Message:", skin)).padRight(10);
        messageField = new TextField("", skin);
        messageInputTable.add(messageField).width(300);
        mainTable.add(messageInputTable).colspan(2).pad(10).row();

        // Buttons
        Table buttonsTable = new Table();
        buttonsTable.defaults().pad(10).width(150).height(50);

        // Send button
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendMessage();
            }
        });
        buttonsTable.add(sendButton);

        // View Messages button
        viewMessagesButton = new TextButton("View Messages", skin);
        viewMessagesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMessagesWindow();
            }
        });
        buttonsTable.add(viewMessagesButton);
        Label giftLabel = new Label("Gift Items:", skin);
        mainTable.add(giftLabel).colspan(2).left().padTop(20).row();

        // Initialize inventory for gifting
        giftInventory = new Inventory(Main.getGame(), stage, gameMenuUI.gameController);

        // Gift amount input
        Table giftInputTable = new Table();
        giftInputTable.add(new Label("Amount:", skin)).padRight(10);
        giftAmountField = new TextField("1", skin);
        giftAmountField.setMessageText("Enter amount");
        giftInputTable.add(giftAmountField).width(100);
        mainTable.add(giftInputTable).colspan(2).pad(10).row();

        // Send gift button
        sendGiftButton = new TextButton("Send Gift", skin);
        sendGiftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendGift();
            }
        });
        mainTable.add(sendGiftButton).colspan(2).pad(10).width(200).height(50);


        // Back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goBackToGameMenu();
            }
        });
        buttonsTable.add(backButton);

        mainTable.add(buttonsTable).colspan(2).pad(10);

        // Initialize messages window (hidden by default)
        createMessagesWindow();

        refreshPlayersList();
    }

    private void createMessagesWindow() {
        messagesWindow = new Window("Your Messages", skin);
        messagesWindow.setModal(true);
        messagesWindow.setMovable(false);
        messagesWindow.setSize(600, 400);
        messagesWindow.setPosition(
            (Gdx.graphics.getWidth() - messagesWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - messagesWindow.getHeight()) / 2
        );

        // Create messages table and scroll pane
        Table messagesTable = new Table();
        ScrollPane scrollPane = new ScrollPane(messagesTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        // Store references we'll need later
        messagesWindow.setUserObject(new Object[]{scrollPane, messagesTable});

        // Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messagesWindow.setVisible(false);
                hasNewMessages = false;
            }
        });

        // Add content to window
        messagesWindow.add(scrollPane).expand().fill().pad(10);
        messagesWindow.row();
        messagesWindow.add(closeButton).padBottom(10).padTop(10);

        stage.addActor(messagesWindow);
        messagesWindow.setVisible(false);
    }

    private void refreshMessagesWindow() {
        // Get the stored references
        Object[] windowObjects = (Object[]) messagesWindow.getUserObject();
        ScrollPane scrollPane = (ScrollPane) windowObjects[0];
        Table messagesTable = (Table) windowObjects[1];

        messagesTable.clear();

        User currentUser = App.getCurrentGame().getPlayingUser();
        List<Message> messages = currentUser.getMessages();

        // Header
        messagesTable.add(new Label("From", skin)).width(150).pad(5);
        messagesTable.add(new Label("Message", skin)).expandX().fillX().pad(5);
        messagesTable.row();
        messagesTable.add(new Label("════════════════════════════════", skin)).colspan(2).row();

        if (messages.isEmpty()) {
            messagesTable.add(new Label("No messages yet", skin)).colspan(2);
        } else {
            for (Message message : messages) {
                User sender = controller.getUserByID(message.getSenderID());
                String senderName = sender != null ? sender.getUsername() : "Unknown";

                // Message row
                Table messageRow = new Table();
                messageRow.add(new Label(senderName, skin)).width(150).pad(5);
                messageRow.add(new Label(message.getMessage(), skin)).expandX().fillX().pad(5).left();
                messagesTable.add(messageRow).expandX().fillX();
                messagesTable.row();
            }
        }

        // Clear notifications after viewing
        currentUser.setHasNewMessages(false);
        hasNewMessages = false;
    }

    private void showMessagesWindow() {
        refreshMessagesWindow();
        messagesWindow.setVisible(true);
    }

    private void refreshPlayersList() {
        playersTable.clear();

        // Header
        playersTable.add(new Label("Player", skin)).width(200).pad(5);
        playersTable.add(new Label("Action", skin)).width(100).pad(5);
        playersTable.row();
        playersTable.add(new Label("════════════════════════════════", skin)).colspan(2).row();

        // Players
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getID() != App.getCurrentGame().getPlayingUser().getID()) {
                addPlayerRow(player);
            }
        }
    }

    private void addPlayerRow(User player) {
        // Player name
        Label nameLabel = new Label(player.getUsername(), skin);
        Label xpLabel = new Label(String.format("%d",player.getFriendshipXPs().get(gameMenuUI.gameModel.getPlayingUser())),skin);
        playersTable.add(nameLabel).width(200).pad(5);
        playersTable.add(xpLabel).width(200).pad(5);

        // Talk button
        TextButton talkButton = new TextButton("Talk", skin);
        talkButton.setUserObject(player);
        talkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectPlayer(player);
            }
        });
        playersTable.add(talkButton).width(100).pad(5);

        playersTable.row();
    }

    private void selectPlayer(User player) {
        messageField.setUserObject(player);
        messageField.setText("");
        messageField.setMessageText("Message to " + player.getUsername());
        selectedGiftRecipient = player; // Set as gift recipient
    }

    private void sendGift() {
        if (selectedGiftRecipient == null) {
            statusLabel.setText("Please select a recipient first");
            statusLabel.setColor(1, 0, 0, 1);
            return;
        }

        ItemInterface selectedItem = giftInventory.getSelectedItem();
        if (selectedItem == null) {
            statusLabel.setText("Please select an item to gift");
            statusLabel.setColor(1, 0, 0, 1);
            return;
        }

        String amountText = giftAmountField.getText();
        try {
            int amount = Integer.parseInt(amountText);
            if (amount <= 0) {
                statusLabel.setText("Amount must be positive");
                statusLabel.setColor(1, 0, 0, 1);
                return;
            }

            Result result = controller.giftPlayer(
                selectedGiftRecipient.getUsername(),
                selectedItem.getName(),
                String.valueOf(amount)
            );

            if (result.isSuccess()) {
                statusLabel.setText("Gift sent successfully!");
                statusLabel.setColor(0, 1, 0, 1);
                giftInventory.refresh(); // Refresh inventory display
            } else {
                statusLabel.setText(result.toString());
                statusLabel.setColor(1, 0, 0, 1);
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid amount");
            statusLabel.setColor(1, 0, 0, 1);
        }
    }

    private void sendMessage() {
        User selectedPlayer = (User) messageField.getUserObject();
        if (selectedPlayer == null) {
            statusLabel.setText("Please select a player first");
            return;
        }

        String message = messageField.getText();
        if (message.isEmpty()) {
            statusLabel.setText("Please enter a message");
            return;
        }

        Result result = controller.talk(selectedPlayer.getUsername(), message);
        statusLabel.setText(result.toString());
        if (result.isSuccess()) {
            statusLabel.setColor(0, 1, 0, 1); // Green for success
            messageField.setText("");
        } else {
            statusLabel.setColor(1, 0, 0, 1); // Red for error
        }
    }

    private void checkNotifications() {
        User currentUser = App.getCurrentGame().getPlayingUser();
        hasNewMessages = currentUser.isHasNewMessages();

        if (hasNewMessages) {
            showDialog("notification", "New Messages has been received");
        }

        if (currentUser.isHasNewGift()) {
            showDialog("notification", "New Gift has been received");
        }
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        // Draw inventory on the right side
        batch.begin();
        // Position inventory on the right with some padding
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        giftInventory.drawOnRight(batch);
        batch.end();

        // Update notification badge
        Label notificationBadge = (Label)((Table)mainTable.getChild(0)).getChild(1);
        notificationBadge.setText(hasNewMessages ? "!" : "");
        notificationBadge.setVisible(hasNewMessages);
    }

    private void goBackToGameMenu() {
        if (gameMenuUI != null) {
            Main.getGame().setScreen(gameMenuUI);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if (messagesWindow != null) {
            messagesWindow.setPosition(
                (width - messagesWindow.getWidth()) / 2,
                (height - messagesWindow.getHeight()) / 2
            );
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        checkNotifications(); // Check again when screen is shown
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
