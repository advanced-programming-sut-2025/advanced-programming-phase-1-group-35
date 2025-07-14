package GraphicView;

import Controller.InGameMenu.ShopMenuController;
import Model.GameAssetManager;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class ShopMenuUI implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Skin skin;
    private ShopMenuController shopController;
    private GameMenuUI gameMenuUI;
    private Table mainTable;
    private Table itemsTable;
    private ScrollPane scrollPane;
    private TextField amountField;
    private Label moneyLabel;
    private Label statusLabel;

    public ShopMenuUI(ShopMenuController shopController, GameMenuUI gameMenuUI) {
        this.shopController = shopController;
        this.gameMenuUI = gameMenuUI;
        initializeUI();
    }

    private void initializeUI() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load skin and font
        skin = GameAssetManager.getDefaultSkin();
        font = new BitmapFont();

        // Create main table
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        // Money display
        moneyLabel = new Label("Money: " + gameMenuUI.gameModel.getPlayingUser().getMoney(), skin);
        mainTable.add(moneyLabel).pad(10).row();

        // Status label
        statusLabel = new Label("", skin);
        statusLabel.setColor(1, 0, 0, 1); // Red color for error messages
        mainTable.add(statusLabel).pad(5).row();

        // Create scrollable items table
        itemsTable = new Table();
        scrollPane = new ScrollPane(itemsTable, skin);
        scrollPane.setFadeScrollBars(false);
        mainTable.add(scrollPane).expand().fill().pad(10).row();

        // Amount input field
        Table inputTable = new Table();
        amountField = new TextField("1", skin);
        inputTable.add(new Label("Amount:", skin)).padRight(10);
        inputTable.add(amountField).width(100);
        mainTable.add(inputTable).pad(10).row();

        // Back button
        TextButton backButton = new TextButton("Back to Game", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(gameMenuUI.gameMenuInputAdapter);
                // Set the screen back to game menu
                if (gameMenuUI.gameModel != null) {
                    Main.getGame().setScreen(gameMenuUI);
                }
            }
        });
        mainTable.add(backButton).width(200).height(50).pad(10);

        refreshShopItems();
    }

    private void refreshShopItems() {
        itemsTable.clear();

        // Add header row
        itemsTable.add(new Label("Item", skin)).width(150).pad(5);
        itemsTable.add(new Label("Price", skin)).width(100).pad(5);
        itemsTable.add(new Label("Limit", skin)).width(100).pad(5);
        itemsTable.add(new Label("Action", skin)).width(150).pad(5);
        itemsTable.row();

        itemsTable.add(new Label("════════════════════════════════", skin)).colspan(4).row();

        // Add shop items
        for (var product : shopController.shop.getProducts()) {
            // Item name
            Label nameLabel = new Label(product.getName(), skin);
            itemsTable.add(nameLabel).width(150).pad(5);

            // Price (with seasonal adjustment)
            int price = product.getPrice();
            if (product.getSeason() != null &&
                !product.getSeason().equals(gameMenuUI.gameModel.getGameCalender().getSeason())) {
                price = (int)(price * 1.5);
            }
            itemsTable.add(new Label(String.valueOf(price), skin)).width(100).pad(5);

            // Daily limit (remaining)
            String limitText = (product.getDailyLimit() - product.getDailyBoughtCount()) + "/" + product.getDailyLimit();
            itemsTable.add(new Label(limitText, skin)).width(100).pad(5);

            // Buy button
            TextButton buyButton = new TextButton("Buy", skin);
            buyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        String amountText = amountField.getText();
                        var result = shopController.purchase(product.getName(), amountText);
                        statusLabel.setText(result.toString());
                        if (result.isSuccess()) {
                            statusLabel.setColor(0, 1, 0, 1); // Green for success
                            refreshShopItems();
                            updateMoneyDisplay();
                        } else {
                            statusLabel.setColor(1, 0, 0, 1); // Red for error
                        }
                    } catch (IOException e) {
                        statusLabel.setText("Error during purchase: " + e.getMessage());
                        statusLabel.setColor(1, 0, 0, 1);
                    }
                }
            });
            itemsTable.add(buyButton).width(150).pad(5);

            itemsTable.row();
        }
    }

    private void updateMoneyDisplay() {
        moneyLabel.setText("Money: " + gameMenuUI.gameModel.getPlayingUser().getMoney());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        font.dispose();
        skin.dispose();
    }

    // Other required Screen methods
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
