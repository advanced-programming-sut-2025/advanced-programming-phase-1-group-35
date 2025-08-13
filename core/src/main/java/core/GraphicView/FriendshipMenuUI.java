package core.GraphicView;

import core.Model.*;
import core.Controller.InGameMenu.FriendshipMenuController;
import core.Model.*;
import core.Model.TradeAndGift.Gift;
import core.Model.enums.Gender;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
    private boolean hasNewMessages;

    private Inventory giftInventory;
    private TextField giftAmountField;
    private User selectedGiftRecipient;
    private TextButton sendGiftButton;
    private Window chatHistoryWindow;
    private Table chatHistoryPlayersTable;
    private Table chatHistoryMessagesTable;
    private ScrollPane chatHistoryScrollPane;

    private int mode = 0; // 1 for generic selecting ;

    private Window giftHistoryWindow;
    private Table giftHistoryPlayersTable;
    private Table giftHistoryGiftsTable;
    private ScrollPane giftHistoryScrollPane;
    private TextField ratingField;

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

        // Create main table - now aligned to left
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.align(Align.left); // Align contents to left
        mainTable.setWidth(Gdx.graphics.getWidth() * 0.6f); // Take only 60% of width
        mainTable.padLeft(20); // Add some left padding
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

        Label giftLabel = new Label("Gift Items:", skin);
        mainTable.add(giftLabel).colspan(2).left().padTop(20).row();

        // Initialize inventory for gifting
        giftInventory = new Inventory(Main.getGame(), stage, gameMenuUI.gameController);
        giftInventory.setMode(1);

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
        createChatHistoryWindow();
        createGiftHistoryWindow();
        refreshPlayersList();
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
            if (!player.getUsername().equals(App.getCurrentGame().getPlayingUser().getUsername())) {
                addPlayerRow(player);
            }
        }
    }

    private void addPlayerRow(User player) {
        // Player name
        Label nameLabel = new Label(player.getUsername(), skin);
        int xp = player.getFriendshipXPs().get(gameMenuUI.gameModel.getPlayingUser()) == null ? 0 : player.getFriendshipXPs().get(gameMenuUI.gameModel.getPlayingUser());
        Label xpLabel = new Label(String.format("%d", xp), skin);
        playersTable.add(nameLabel).width(200).pad(5);
        playersTable.add(xpLabel).width(200).pad(5);

        // Select button
        TextButton selectButton = new TextButton("Select", skin);
        selectButton.setUserObject(player);
        selectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectPlayer(player);
            }
        });
        playersTable.add(selectButton).width(100).pad(5);

        // Action menu button (will show a popup with available actions)
        TextButton actionsButton = new TextButton("Actions", skin);
        actionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPlayerActionsMenu(player);
            }
        });
        playersTable.add(actionsButton).width(100).pad(5);

        // Chat History button
        TextButton historyButton = new TextButton("Chat", skin);
        historyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChatHistoryWindow();
                showChatWithPlayer(player);
            }
        });
        playersTable.add(historyButton).width(100).pad(5);

        // Gift History button
        TextButton giftHistoryButton = new TextButton("Gift History", skin);
        giftHistoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showGiftHistoryWindow();
                showGiftsFromPlayer(player);
            }
        });
        playersTable.add(giftHistoryButton).width(100).pad(5);

        playersTable.row();
    }

    private void showPlayerActionsMenu(User player) {
        Dialog actionsDialog = new Dialog("Actions for " + player.getUsername(), skin);

        // Hug button
        TextButton hugButton = new TextButton("Hug", skin);
        hugButton.setDisabled(!canHug(player));
        hugButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                performHug(player);
                actionsDialog.hide();
            }
        });
        actionsDialog.getContentTable().add(hugButton).width(200).pad(5).row();

        // Give Flower button
        TextButton flowerButton = new TextButton("Give Flower", skin);
        flowerButton.setDisabled(!canGiveFlower(player));
        flowerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                performGiveFlower(player);
                actionsDialog.hide();
            }
        });
        actionsDialog.getContentTable().add(flowerButton).width(200).pad(5).row();

        // Request Marriage button (only shown if conditions are met)
        if (canRequestMarriage(player)) {
            TextButton marryButton = new TextButton("Propose Marriage", skin);
            marryButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    performMarriageRequest(player);
                    actionsDialog.hide();
                }
            });
            actionsDialog.getContentTable().add(marryButton).width(200).pad(5).row();
        }

        // Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actionsDialog.hide();
            }
        });
        actionsDialog.getContentTable().add(closeButton).width(200).pad(5);

        actionsDialog.show(stage);
    }

    private boolean canHug(User player) {
        int xp = App.getCurrentGame().getPlayingUser().getFriendshipXPs().getOrDefault(player.getID(), 100);
        return (xp / 100 - 1) >= 2; // Level 2 friendship required
    }

    private boolean canGiveFlower(User player) {
        int xp = App.getCurrentGame().getPlayingUser().getFriendshipXPs().getOrDefault(player.getID(), 100);
        return (xp / 100 - 1) >= 3; // Level 3 friendship required
    }

    private void selectPlayer(User player) {
        messageField.setUserObject(player);
        messageField.setText("");
        messageField.setMessageText("Message to " + player.getUsername());
        selectedGiftRecipient = player; // Set as gift recipient
    }

    private boolean canRequestMarriage(User player) {
        User currentUser = App.getCurrentGame().getPlayingUser();
        int xp = currentUser.getFriendshipXPs().getOrDefault(player.getID(), 100);
        return (xp >= 400) && // At least 400 XP (level 4)
            currentUser.getGender() == Gender.male &&
            player.getGender() == Gender.female &&
            currentUser.getSpouse() == null &&
            player.getSpouse() == null;
    }

    private boolean hasMarriageRequest() {
        return App.getCurrentGame().getPlayingUser().getAskedMarriage() != null;
    }

    private User getMarriageRequester() {
        return App.getCurrentGame().getPlayingUser().getAskedMarriage();
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
        refreshPlayersList();
    }

    private void performHug(User player) {
        Result result = controller.hug(player.getUsername());
        statusLabel.setText(result.toString());
        if (result.isSuccess()) {
            statusLabel.setColor(0, 1, 0, 1); // Green for success
        } else {
            statusLabel.setColor(1, 0, 0, 1); // Red for error
        }
        refreshPlayersList();
    }

    private void performGiveFlower(User player) {
        Result result = controller.flower(player.getUsername());
        statusLabel.setText(result.toString());
        if (result.isSuccess()) {
            statusLabel.setColor(0, 1, 0, 1); // Green for success
        } else {
            statusLabel.setColor(1, 0, 0, 1); // Red for error
        }
        refreshPlayersList();
    }

    private void performMarriageRequest(User player) {
        Result result = controller.askMarriage(player.getUsername());
        statusLabel.setText(result.toString());
        if (result.isSuccess()) {
            statusLabel.setColor(0, 1, 0, 1);
        } else {
            statusLabel.setColor(1, 0, 0, 1);
        }
        refreshPlayersList();
    }

    private void showMarriageResponseDialog(User requester) {
        Dialog responseDialog = new Dialog("Marriage Proposal", skin);
        responseDialog.text(requester.getUsername() + " has proposed marriage to you!");

        // Accept button
        TextButton acceptButton = new TextButton("Accept", skin);
        acceptButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.acceptMarriageRequest(App.getCurrentGame().getPlayingUser());
                statusLabel.setText(result.toString());
                if (result.isSuccess()) {
                    statusLabel.setColor(0, 1, 0, 1);
                } else {
                    statusLabel.setColor(1, 0, 0, 1);
                }
                responseDialog.hide();
                refreshPlayersList();
            }
        });

        // Reject button
        TextButton rejectButton = new TextButton("Reject", skin);
        rejectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.rejectMarriageRequest(App.getCurrentGame().getPlayingUser());
                statusLabel.setText(result.toString());
                if (result.isSuccess()) {
                    statusLabel.setColor(0, 1, 0, 1);
                } else {
                    statusLabel.setColor(1, 0, 0, 1);
                }
                responseDialog.hide();
                refreshPlayersList();
            }
        });

        responseDialog.getContentTable().row();
        responseDialog.getContentTable().add(acceptButton).width(150).pad(10);
        responseDialog.getContentTable().add(rejectButton).width(150).pad(10);
        responseDialog.show(stage);
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
        refreshPlayersList();
    }

    private void checkNotifications() {
        User currentUser = App.getCurrentGame().getPlayingUser();
        hasNewMessages = currentUser.isHasNewMessages();

        if (hasNewMessages) {
            showDialog("Notification", "You have new messages!");
        }

        if (currentUser.isHasNewGift()) {
            showDialog("Notification", "You have received a new gift!");
        }

        // Check for marriage request
        if (hasMarriageRequest()) {
            User requester = getMarriageRequester();
            showMarriageResponseDialog(requester);
            // Reset the flag so it doesn't show repeatedly
            currentUser.setAskedMarriage(null);
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

        // Draw inventory on the right side
        batch.begin();
        // Position inventory on the right with some padding
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        giftInventory.drawOnRight(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

        // Update notification badge
        Label notificationBadge = (Label)((Table)mainTable.getChild(0)).getChild(1);
        notificationBadge.setText(hasNewMessages ? "!" : "");
        notificationBadge.setVisible(hasNewMessages);
    }

    private void createChatHistoryWindow() {
        chatHistoryWindow = new Window("Chat History", skin);
        chatHistoryWindow.setModal(true);
        chatHistoryWindow.setMovable(false);
        chatHistoryWindow.setSize(800, 500);
        chatHistoryWindow.setPosition(
            (Gdx.graphics.getWidth() - chatHistoryWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - chatHistoryWindow.getHeight()) / 2
        );

        // Create main table for the window
        Table mainTable = new Table();
        mainTable.pad(10);

        // Players list on left
        chatHistoryPlayersTable = new Table();
        ScrollPane playersScroll = new ScrollPane(chatHistoryPlayersTable, skin);
        playersScroll.setScrollingDisabled(false, true);
        playersScroll.setFadeScrollBars(false);

        // Messages on right
        chatHistoryMessagesTable = new Table();
        chatHistoryScrollPane = new ScrollPane(chatHistoryMessagesTable, skin);
        chatHistoryScrollPane.setFadeScrollBars(false);
        chatHistoryScrollPane.setScrollbarsOnTop(true);

        // Add both tables side by side
        mainTable.add(playersScroll).width(200).fillY();
        mainTable.add(chatHistoryScrollPane).expand().fill();

        // Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chatHistoryWindow.setVisible(false);
            }
        });

        chatHistoryWindow.add(mainTable).expand().fill().pad(10);
        chatHistoryWindow.row();
        chatHistoryWindow.add(closeButton).padBottom(10).padTop(10);

        stage.addActor(chatHistoryWindow);
        chatHistoryWindow.setVisible(false);
    }

    private void refreshChatHistoryWindow() {
        chatHistoryPlayersTable.clear();
        chatHistoryMessagesTable.clear();

        User currentUser = App.getCurrentGame().getPlayingUser();

        // Add player buttons
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getID() != currentUser.getID()) {
                TextButton playerButton = new TextButton(player.getUsername(), skin);
                playerButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        showChatWithPlayer(player);
                    }
                });
                chatHistoryPlayersTable.add(playerButton).width(180).pad(5);
                chatHistoryPlayersTable.row();
            }
        }
    }

    private void createGiftHistoryWindow() {
        giftHistoryWindow = new Window("Gift History", skin);
        giftHistoryWindow.setModal(true);
        giftHistoryWindow.setMovable(false);
        giftHistoryWindow.setSize(800, 500);
        giftHistoryWindow.setPosition(
            (Gdx.graphics.getWidth() - giftHistoryWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - giftHistoryWindow.getHeight()) / 2
        );

        // Create main table for the window
        Table mainTable = new Table();
        mainTable.pad(10);

        // Players list on left
        giftHistoryPlayersTable = new Table();
        ScrollPane playersScroll = new ScrollPane(giftHistoryPlayersTable, skin);
        playersScroll.setScrollingDisabled(false, true);
        playersScroll.setFadeScrollBars(false);

        // Gifts on right
        giftHistoryGiftsTable = new Table();
        giftHistoryScrollPane = new ScrollPane(giftHistoryGiftsTable, skin);
        giftHistoryScrollPane.setFadeScrollBars(false);
        giftHistoryScrollPane.setScrollbarsOnTop(true);

        // Rating input
        Table ratingTable = new Table();
        ratingTable.add(new Label("Rating (1-5):", skin)).padRight(10);
        ratingField = new TextField("", skin);
        ratingField.setMessageText("Enter rating");
        ratingTable.add(ratingField).width(100);

        TextButton rateButton = new TextButton("Rate Gift", skin);
        rateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rateSelectedGift();
            }
        });
        ratingTable.add(rateButton).padLeft(10);

        // Add both tables side by side
        mainTable.add(playersScroll).width(200).fillY();
        mainTable.add(giftHistoryScrollPane).expand().fill();
        mainTable.row();
        mainTable.add(ratingTable).colspan(2).padTop(10);

        // Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                giftHistoryWindow.setVisible(false);
            }
        });

        giftHistoryWindow.add(mainTable).expand().fill().pad(10);
        giftHistoryWindow.row();
        giftHistoryWindow.add(closeButton).padBottom(10).padTop(10);

        stage.addActor(giftHistoryWindow);
        giftHistoryWindow.setVisible(false);
    }

    private void showGiftHistoryWindow() {
        refreshGiftHistoryPlayers();
        giftHistoryWindow.setVisible(true);
    }

    private void refreshGiftHistoryPlayers() {
        giftHistoryPlayersTable.clear();
        giftHistoryGiftsTable.clear();

        User currentUser = App.getCurrentGame().getPlayingUser();

        // Add player buttons
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getID() != currentUser.getID()) {
                TextButton playerButton = new TextButton(player.getUsername(), skin);
                playerButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        showGiftsFromPlayer(player);
                    }
                });
                giftHistoryPlayersTable.add(playerButton).width(180).pad(5);
                giftHistoryPlayersTable.row();
            }
        }
    }

    private void showGiftsFromPlayer(User player) {
        giftHistoryGiftsTable.clear();

        User currentUser = App.getCurrentGame().getPlayingUser();
        List<Gift> gifts = currentUser.getGifts();

        // Header
        giftHistoryGiftsTable.add(new Label("Gifts from " + player.getUsername(), skin)).colspan(3).padBottom(10).row();
        giftHistoryGiftsTable.add(new Label("════════════════════════════════", skin)).colspan(3).row();

        boolean hasGifts = false;
        for (Gift gift : gifts) {
            if (gift.getSenderID() == player.getID() && gift.getRecipientID() == currentUser.getID()) {
                hasGifts = true;

                // Gift info
                String rating = gift.getRate() == -1 ? "Not rated yet" : "Rating: " + gift.getRate();
                Label giftLabel = new Label(
                    gift.getAmount() + " " + gift.getItemInterface().getName() +
                        " (" + rating + ")",
                    skin
                );

                // Select button for unrated gifts
                if (gift.getRate() == -1) {
                    TextButton selectButton = new TextButton("Select", skin);
                    selectButton.setUserObject(gift);
                    selectButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            ratingField.setUserObject(gift);
                            ratingField.setText("");
                        }
                    });

                    giftHistoryGiftsTable.add(giftLabel).left().padRight(10).padTop(5);
                    giftHistoryGiftsTable.add(selectButton).padTop(5).row();
                } else {
                    giftHistoryGiftsTable.add(giftLabel).left().colspan(2).padTop(5).row();
                }
            }
        }

        if (!hasGifts) {
            giftHistoryGiftsTable.add(new Label("No gifts from this player yet", skin)).colspan(3);
        }
    }

    private void rateSelectedGift() {
        Gift gift = (Gift) ratingField.getUserObject();
        if (gift == null) {
            statusLabel.setText("Please select a gift first");
            statusLabel.setColor(1, 0, 0, 1);
            return;
        }

        String ratingText = ratingField.getText();
        try {
            int rating = Integer.parseInt(ratingText);
            if (rating < 1 || rating > 5) {
                statusLabel.setText("Rating must be between 1 and 5");
                statusLabel.setColor(1, 0, 0, 1);
                return;
            }

            Result result = controller.rateGift(
                String.valueOf(gift.getID()),
                String.valueOf(rating)
            );

            if (result.isSuccess()) {
                statusLabel.setText("Gift rated successfully!");
                statusLabel.setColor(0, 1, 0, 1);
                // Refresh the view
                if (messageField.getUserObject() != null) {
                    User selectedPlayer = (User) messageField.getUserObject();
                    showGiftsFromPlayer(selectedPlayer);
                }
            } else {
                statusLabel.setText(result.toString());
                statusLabel.setColor(1, 0, 0, 1);
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid rating");
            statusLabel.setColor(1, 0, 0, 1);
        }
    }

    private void showChatWithPlayer(User player) {
        chatHistoryMessagesTable.clear();

        User currentUser = App.getCurrentGame().getPlayingUser();
        List<Message> messages = currentUser.getMessages();

        // Header
        chatHistoryMessagesTable.add(new Label("Chat with " + player.getUsername(), skin)).colspan(2).padBottom(10).row();
        chatHistoryMessagesTable.add(new Label("════════════════════════════════", skin)).colspan(2).row();

        boolean hasMessages = false;
        for (Message message : messages) {
            if ((message.getSenderID() == player.getID() && message.getReceiverID() == currentUser.getID()) ||
                (message.getSenderID() == currentUser.getID() && message.getReceiverID() == player.getID())) {

                hasMessages = true;
                String senderName = message.getSenderID() == currentUser.getID() ? "You" : player.getUsername();

                Label senderLabel = new Label(senderName + ":", skin);
                senderLabel.setColor(message.getSenderID() == currentUser.getID() ?
                    Color.BLUE : Color.GREEN);

                chatHistoryMessagesTable.add(senderLabel).left().padRight(10).padTop(5);
                chatHistoryMessagesTable.add(new Label(message.getMessage(), skin)).left().padTop(5).row();
            }
        }

        if (!hasMessages) {
            chatHistoryMessagesTable.add(new Label("No messages with this player yet", skin)).colspan(2);
        }

        // Scroll to bottom
        Gdx.app.postRunnable(() -> {
            chatHistoryScrollPane.setScrollPercentY(1);
        });
    }

    private void showChatHistoryWindow() {
        refreshChatHistoryWindow();
        chatHistoryWindow.setVisible(true);
    }

    private void goBackToGameMenu() {
        if (gameMenuUI != null) {
            Main.getGame().setScreen(gameMenuUI);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        refreshPlayersList(); // Refresh to show any changes in relationship status
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
