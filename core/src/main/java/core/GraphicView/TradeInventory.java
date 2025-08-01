package core.GraphicView;

import core.Model.*;
import core.Controller.GameMenuController;
import core.Controller.InGameMenu.TradeMenuController;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import core.Model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TradeInventory {
    private Texture inventoryBackgroundTexture;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private Main game;
    private AssetManager assetManager;
    private Stage stage;
    public GameMenuController gameMenuController;

    public SelectBox playerSelectBox;
    private Inventory inventory ;
    private Table table;

    private Map<Rect, ItemInterface> itemRects;

    private static final int INVENTORY_ROWS = 3;
    private static final int INVENTORY_COLS = 12;
    private static final int ICON_SIZE = 64;
    private static final int CELL_PADDING = 10;
    private static final int TOTAL_WIDTH = INVENTORY_COLS * (ICON_SIZE + CELL_PADDING) - CELL_PADDING;
    private static final int TOTAL_HEIGHT = INVENTORY_ROWS * (ICON_SIZE + CELL_PADDING) - CELL_PADDING;
    private static final int TOP_PADDING = 50;
    private int startX, startY;

    TradeMenuController tradeMenuController;

    private enum InventoryAction {
        OFFER,
        CANCEL
    }

    public TradeInventory(Main game, Stage stage, GameMenuController gameMenuController) {
        this.game = game;
        this.stage = stage;
        this.assetManager = new AssetManager();
        this.gameMenuController = gameMenuController;
        this.inventory = new Inventory(game,stage,gameMenuController);
        table = new Table();
        TradeMenuController tradeMenuController = new TradeMenuController();
        initialize();
        checkForRequests();
    }

    private void checkForRequests() {
        if(App.getCurrentGame().getPlayingUser().isHasNewTradeRequest()){
            showDialog("Notification", "You have new trade requests");
            App.getCurrentGame().getPlayingUser().setHasNewTradeRequest(false);
        }
    }

    private void initialize() {
        inventoryBackgroundTexture = new Texture(Gdx.files.internal("assets/shelf3.png"));
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.0f);
        glyphLayout = new GlyphLayout();

        playerSelectBox.setItems(App.users);
        playerSelectBox.setSelectedIndex(0);

        table.setFillParent(true);
        table.center();
        table.addActor(playerSelectBox);
        stage.addActor(table);

        itemRects = new HashMap<>();

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float stageX = x;
                float stageY = y;

                for (Map.Entry<Rect, ItemInterface> entry : itemRects.entrySet()) {
                    Rect rect = entry.getKey();
                    ItemInterface clickedItem = entry.getValue();
                    if (rect.contains(stageX, stageY)) {
                        Gdx.app.log("InventoryUI", "Item clicked: " + clickedItem.getName());
                        showItemActionDialog(clickedItem);
                        return;
                    }
                }
            }
        });
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

    public void showItemActionDialog(final ItemInterface item) {
        Skin skin = GameAssetManager.getDefaultSkin();
        TextField amountField = new TextField("", skin);
        TextField priceField = new TextField("", skin);
        amountField.setMessageText("amount");
        priceField.setMessageText("price");
        Dialog dialog = new Dialog("Item Action: " + item.getName(), skin) {
            @Override
            protected void result(Object object) {
                if (object instanceof InventoryAction action) {
                    switch (action) {
                        case OFFER:
                            Result checkAmntResult = checkAmount(amountField, item);
                            int price = 0;
                            try{
                                price = Integer.parseInt(priceField.getText());
                            }
                            catch (Exception e){
                                showDialog("Error","Invalid price");
                            }
                            if (checkAmntResult.isSuccess() ) {
                                User them = App.getCurrentGame().getPlayers().get(playerSelectBox.getSelectedIndex());
                                try {
                                    Result result = tradeMenuController.requestTrade(them.getUsername(),
                                        "cash",item.getName(),checkAmntResult.toString(),
                                        String.format("%d",price),null,null);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            else {
                                showDialog("Error", checkAmntResult.toString());
                            }
                        case CANCEL:
                            break;
                    }
                }
            }
        };
        dialog.text("What would you like to do with " + item.getName() + "?");
        dialog.button("Cancel", InventoryAction.CANCEL) ;
        dialog.button("Send Offer", InventoryAction.OFFER);
        dialog.show(stage);
    }

    public Result checkAmount(TextField amountField, ItemInterface item) { //TODO: handle ItemAmount check
        int amount ;
        try{
            amount = Integer.parseInt(amountField.getText());
        }
        catch(NumberFormatException e){
            return new Result(false, "Invalid amount!");
        }
        if(amount > App.getCurrentGame().getPlayingUser().getBackPack().items.get(item)){
            return new Result(false, "amount is greater than what you have");
        };
        return new Result(true, String.format("%d", amount));
    }

    public void draw(SpriteBatch batch) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        startX = ((screenWidth - TOTAL_WIDTH) / 2);
        startY = (screenHeight - TOTAL_HEIGHT - TOP_PADDING) - 200;

        batch.draw(inventoryBackgroundTexture, startX - 20, startY - 20, TOTAL_WIDTH + 40, TOTAL_HEIGHT + 40);
        drawInventoryItems(batch);
    }

    private void drawInventoryItems(SpriteBatch batch) {
        if (App.getCurrentGame() == null || App.getCurrentGame().getPlayingUser() == null || App.getCurrentGame().getPlayingUser().backPack == null) {
            return;
        }
        itemRects.clear();
        int currentItemIndex = 0;
        for (ItemInterface item : App.getCurrentGame().getPlayingUser().backPack.items.keySet()) {
            if (currentItemIndex >= INVENTORY_COLS * INVENTORY_ROWS) {
                break;
            }
            Texture itemTexture = getItemTexture(item);
            int count = App.getCurrentGame().getPlayingUser().backPack.items.get(item);
            if (itemTexture != null) {
                int col = currentItemIndex % INVENTORY_COLS;
                int row = currentItemIndex / INVENTORY_COLS;
                float itemX = (startX + col * (ICON_SIZE + CELL_PADDING)) - 10;
                float itemY = (startY + row * (ICON_SIZE + CELL_PADDING)) - 16;
                batch.draw(itemTexture, itemX, itemY, ICON_SIZE, ICON_SIZE);
                String countString = String.valueOf(count);
                glyphLayout.setText(font, countString);
                float textX = itemX + ICON_SIZE - glyphLayout.width - 5;
                float textY = itemY + glyphLayout.height;
                font.draw(batch, countString, textX, textY);
                itemRects.put(new Rect(itemX, itemY, ICON_SIZE, ICON_SIZE), item);
            }
            currentItemIndex++;
        }
    }

    private Texture getItemTexture(ItemInterface item) {
        return inventory.getItemTexture(item);
    }

    public void dispose() {
        if (inventoryBackgroundTexture != null) {
            inventoryBackgroundTexture.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }
}
