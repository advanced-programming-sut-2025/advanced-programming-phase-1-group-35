package GraphicView;

import Controller.InGameMenu.TradeMenuController;
import Model.*;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TradeMenuUI implements Screen {
    private enum TradeMode {
        REQUEST,
        OFFER
    }

    private SpriteBatch batch;
    private Stage stage;
    private Main game;
    private GameMenuUI gameMenuUI;
    private TradeMenuController tradeController;

    private Texture backgroundTexture;

    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private ScrollPane playerScrollPane;
    private ScrollPane inventoryScrollPane;
    private ScrollPane targetInventoryScrollPane;

    private Table playerTable;
    private Table inventoryTable;
    private Table targetInventoryTable;
    private Table mainTable;

    private TextField amountField;
    private TextField priceField;
    private TextField targetAmountField;

    private SelectBox<String> tradeTypeSelectBox;
    private SelectBox<String> itemTypeSelectBox;
    private SelectBox<String> targetItemSelectBox;

    private Label statusLabel;

    private TradeMode currentMode = TradeMode.REQUEST;
    private User selectedPlayer;
    private ItemInterface selectedItem;
    private ItemInterface selectedTargetItem;

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private static final int ICON_SIZE = 64;
    private static final int PADDING = 10;

    private Inventory inventory;

    Skin skin;

    public TradeMenuUI(Main game, GameMenuUI gameMenuUI) {
        this.game = game;
        this.gameMenuUI = gameMenuUI;
        this.tradeController = new TradeMenuController();
        this.tradeController.setUser(App.getCurrentGame().getPlayingUser());
        skin = GameAssetManager.getDefaultSkin();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());

        // Load textures
        backgroundTexture = new Texture(Gdx.files.internal("assets/background/trade.jpg"));

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.2f);
        glyphLayout = new GlyphLayout();

        // Create UI elements
        createMainTable();
        createPlayerSelection();
        createInventorySelection();
        createTradeControls();

        // Set up input handling
        InputMultiplexer mainMultiplexer = gameMenuUI.getMainMultiplexer();
        mainMultiplexer.addProcessor(stage);
        mainMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    gameMenuUI.toggleTradeMenu();
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setInputProcessor(mainMultiplexer);
    }

    private void createMainTable() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20);
        stage.addActor(mainTable);

        // Mode selection buttons
        TextButton requestBtn = new TextButton("Request", skin);
        TextButton offerBtn = new TextButton("Offer", skin);

        requestBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentMode = TradeMode.REQUEST;
                updateTradeControls();
            }
        });

        offerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentMode = TradeMode.OFFER;
                updateTradeControls();
            }
        });

        mainTable.add(requestBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padRight(PADDING);
        mainTable.add(offerBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).row();
    }

    private void createPlayerSelection() {
        playerTable = new Table();
        playerTable.defaults().pad(PADDING);

        // Add player buttons
        ArrayList<User> players = App.getCurrentGame().getPlayers();
        for (User player : players) {
            if (player.equals(App.getCurrentGame().getPlayingUser())) continue;

            TextButton playerBtn = new TextButton(player.getUsername(), new Skin(Gdx.files.internal("skin/uiskin.json")));
            playerBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedPlayer = player;
                    updateTargetInventory();
                    statusLabel.setText("Selected player: " + player.getUsername());
                }
            });

            playerTable.add(playerBtn).width(200).height(BUTTON_HEIGHT).row();
        }

        playerScrollPane = new ScrollPane(playerTable);
        playerScrollPane.setFadeScrollBars(false);

        mainTable.add(playerScrollPane).colspan(2).height(200).width(400).row();
    }

    private void createInventorySelection() {
        // Player inventory
        inventoryTable = new Table();
        inventoryTable.defaults().pad(PADDING);

        Map<ItemInterface, Integer> items = App.getCurrentGame().getPlayingUser().getBackPack().items;
        for (ItemInterface item : items.keySet()) {
            TextButton itemBtn = new TextButton(item.getName() + " (" + items.get(item) + ")", new Skin(Gdx.files.internal("skin/uiskin.json")));
            itemBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedItem = item;
                    statusLabel.setText("Selected item: " + item.getName());
                }
            });

            inventoryTable.add(itemBtn).width(200).height(BUTTON_HEIGHT).row();
        }

        inventoryScrollPane = new ScrollPane(inventoryTable);
        inventoryScrollPane.setFadeScrollBars(false);

        // Target player inventory (initially empty)
        targetInventoryTable = new Table();
        targetInventoryScrollPane = new ScrollPane(targetInventoryTable);
        targetInventoryScrollPane.setFadeScrollBars(false);

        mainTable.add(inventoryScrollPane).height(300).width(300);
        mainTable.add(targetInventoryScrollPane).height(300).width(300).row();
    }

    private void updateTargetInventory() {
        targetInventoryTable.clear();

        if (selectedPlayer != null) {
            Map<ItemInterface, Integer> items = selectedPlayer.getBackPack().items;
            for (ItemInterface item : items.keySet()) {
                TextButton itemBtn = new TextButton(item.getName() + " (" + items.get(item) + ")", new Skin(Gdx.files.internal("skin/uiskin.json")));
                itemBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        selectedTargetItem = item;
                        statusLabel.setText("Selected target item: " + item.getName());
                    }
                });

                targetInventoryTable.add(itemBtn).width(200).height(BUTTON_HEIGHT).row();
            }
        }
    }

    private void createTradeControls() {
        // Trade type selection
        tradeTypeSelectBox = new SelectBox<>(skin);
        tradeTypeSelectBox.setItems("Item for Item", "Item for Money");

        // Item type selection
        itemTypeSelectBox = new SelectBox<>(skin);
        itemTypeSelectBox.setItems("Food", "Material", "Tool");

        // Target item selection
        targetItemSelectBox = new SelectBox<>(skin);
        targetItemSelectBox.setItems("Food", "Material", "Tool");

        // Amount fields
        amountField = new TextField("1", skin);
        priceField = new TextField("2", skin);
        targetAmountField = new TextField("1", skin);

        // Status label
        statusLabel = new Label("Select a player and items to trade", skin);
        statusLabel.setAlignment(Align.center);

        // Send button
        TextButton sendBtn = new TextButton("Send Trade", skin);
        sendBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendTradeRequest();
            }
        });

        // Back button
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuUI.toggleTradeMenu();
            }
        });

        // Add controls to table
        Table controlsTable = new Table();
        controlsTable.defaults().pad(PADDING);

        controlsTable.add(tradeTypeSelectBox).width(200).height(BUTTON_HEIGHT).row();
        controlsTable.add(itemTypeSelectBox).width(200).height(BUTTON_HEIGHT).row();
        controlsTable.add(new Label("Amount:", new Skin(Gdx.files.internal("skin/uiskin.json")))).row();
        controlsTable.add(amountField).width(200).height(BUTTON_HEIGHT).row();

        controlsTable.add(targetItemSelectBox).width(200).height(BUTTON_HEIGHT).row();
        controlsTable.add(new Label("Target Amount:", new Skin(Gdx.files.internal("skin/uiskin.json")))).row();
        controlsTable.add(targetAmountField).width(200).height(BUTTON_HEIGHT).row();

        controlsTable.add(new Label("Price:", new Skin(Gdx.files.internal("skin/uiskin.json")))).row();
        controlsTable.add(priceField).width(200).height(BUTTON_HEIGHT).row();

        controlsTable.add(statusLabel).width(400).height(50).row();
        controlsTable.add(sendBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padTop(20).row();
        controlsTable.add(backBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padTop(10);

        mainTable.add(controlsTable).colspan(2).row();

        updateTradeControls();
    }

    private void updateTradeControls() {
        boolean isItemForItem = tradeTypeSelectBox.getSelected().equals("Item for Item");

        targetItemSelectBox.setVisible(isItemForItem);
        targetAmountField.setVisible(isItemForItem);
        priceField.setVisible(!isItemForItem);

        statusLabel.setText(currentMode == TradeMode.REQUEST ? "Request Mode" : "Offer Mode");
    }

    private void sendTradeRequest() {
        if (selectedPlayer == null) {
            statusLabel.setText("Please select a player first!");
            return;
        }

        if (selectedItem == null) {
            statusLabel.setText("Please select an item to trade!");
            return;
        }

        try {
            String type = tradeTypeSelectBox.getSelected().equals("Item for Item") ? "item" : "cash";
            String itemName = selectedItem.getName();
            String amount = amountField.getText();
            String price = priceField.getText();
            String targetItemName = selectedTargetItem != null ? selectedTargetItem.getName() : null;
            String targetAmount = targetAmountField.getText();

            Result result = tradeController.requestTrade(
                selectedPlayer.getUsername(),
                type,
                itemName,
                amount,
                price,
                targetItemName,
                targetAmount
            );

            if (result.isSuccess()) {
                statusLabel.setText("Trade request sent successfully!");
            } else {
                statusLabel.setText("Error: " + result.toString());
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
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
        InputMultiplexer mainMultiplexer = gameMenuUI.getMainMultiplexer();
        mainMultiplexer.removeProcessor(stage);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        font.dispose();
    }
}
