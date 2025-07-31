package peer.GraphicView;

import tracker.Controller.GameMenuController;
import tracker.Controller.InGameMenu.CookingController;
import common.Model.*;
import common.Model.Tools.Tool;
import common.Model.animal.AnimalProduct;
import common.Model.animal.Fish;
import common.Model.enums.animal.FishType;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Texture inventoryBackgroundTexture;
    private Texture trashCanIconTexture;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private Main game;
    private AssetManager assetManager;
    private Stage stage;
    public GameMenuController gameMenuController;

    private Map<Rect, ItemInterface> itemRects;
    private Rect trashCanRect;

    private int mode = 0 ; // 1 for generic selecting ;

    private static final int INVENTORY_ROWS = 3;
    private static final int INVENTORY_COLS = 12;
    private static final int ICON_SIZE = 64;
    private static final int CELL_PADDING = 10;
    private static final int TOTAL_WIDTH = INVENTORY_COLS * (ICON_SIZE + CELL_PADDING) - CELL_PADDING;
    private static final int TOTAL_HEIGHT = INVENTORY_ROWS * (ICON_SIZE + CELL_PADDING) - CELL_PADDING;
    private static final int TOP_PADDING = 50;
    private int startX, startY;

    ItemInterface clickedItem;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    private enum InventoryAction {
        SELL,
        MOVE_TO_TRASH,
        MOVE_TO_FRIDGE,
        CANCEL, EAT
    }

    public Inventory(Main game, Stage stage, GameMenuController gameMenuController) {
        this.game = game;
        this.stage = stage;
        this.assetManager = new AssetManager();
        this.gameMenuController = gameMenuController;
        initialize();
    }

    private void initialize() {
        inventoryBackgroundTexture = new Texture(Gdx.files.internal("assets/shelf3.png"));
        trashCanIconTexture = new Texture(Gdx.files.internal("assets/inventory/Trash_Can.png"));
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.0f);
        glyphLayout = new GlyphLayout();

        itemRects = new HashMap<>();
        trashCanRect = new Rect(1560, 600, ICON_SIZE * 1.7f, ICON_SIZE * 1.7f);

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float stageX = x;
                float stageY = y;

                for (Map.Entry<Rect, ItemInterface> entry : itemRects.entrySet()) {
                    Rect rect = entry.getKey();
                    clickedItem = entry.getValue();
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
        amountField.setMessageText("amount");
        Dialog dialog = new Dialog("Item Action: " + item.getName(), skin) {
            @Override
            protected void result(Object object) {
                if (object instanceof InventoryAction action) {
                    switch (action) {
                        case MOVE_TO_TRASH:
                            if (!(item instanceof Tool)) {
                                App.getCurrentGame().getPlayingUser().backPack.items.remove(item);
                            } else {
                                showDialog("Error", "cant remove a tool!");
                            }
                            break;
                        case MOVE_TO_FRIDGE:
                            if ((item instanceof Food) || (item instanceof CookingMaterial)) {
                                App.getCurrentGame().getPlayingUser().backPack.refrigerator.add(item);
                                App.getCurrentGame().getPlayingUser().backPack.items.remove(item);
                            } else {
                                showDialog("Error", "you cant move this to the fridge!");
                            }
                            break;
                        case EAT:
                            if ((item instanceof Food)) {
                                new CookingController().eatFood(item.getName());
                            } else {
                                showDialog("Error", "you cant eat this item!");
                            }
                            break;
                        case SELL:
                            Result checkAmount = checkAmount(amountField, item);
                            if(!checkAmount.isSuccess()){
                                showDialog("Error", checkAmount.toString());
                            }
                            else {
                                Result result = gameMenuController.Sell(item.getName(), checkAmount.toString());
                                if(result.isSuccess()){
                                    showDialog("Success", result.toString());
                                }
                                else {
                                    showDialog("Error", result.toString());
                                }
                            }
                            break;
                        case CANCEL:
                            break;
                    }
                }
            }
        };
        dialog.text("What would you like to do with " + item.getName() + "?");
        if(mode == 0) {
            dialog.button("To Trash", InventoryAction.MOVE_TO_TRASH);
            dialog.button("To Fridge", InventoryAction.MOVE_TO_FRIDGE);
            dialog.button("Eat", InventoryAction.EAT);
            dialog.button("Sell", InventoryAction.SELL);
        }
        dialog.add(amountField).width(100);
        dialog.button("Cancel", InventoryAction.CANCEL) ;
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
        batch.draw(trashCanIconTexture, trashCanRect.x, trashCanRect.y, trashCanRect.width, trashCanRect.height);
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
    public void drawOnRight(SpriteBatch batch) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Position inventory on the right with some padding
        startX = screenWidth - TOTAL_WIDTH - 50; // 50 pixels from right edge
        startY = (screenHeight - TOTAL_HEIGHT) / 2; // Center vertically

        // Adjust trash can position for this layout
        trashCanRect.x = startX + TOTAL_WIDTH - ICON_SIZE * 1.7f;
        trashCanRect.y = startY - ICON_SIZE * 1.7f - 20;

        batch.draw(inventoryBackgroundTexture, startX - 20, startY - 20, TOTAL_WIDTH + 40, TOTAL_HEIGHT + 40);
        batch.draw(trashCanIconTexture, trashCanRect.x, trashCanRect.y, trashCanRect.width, trashCanRect.height);
        drawInventoryItems(batch);
    }
    public ItemInterface getSelectedItem() {
        // Return the currently selected item (you'll need to track this)
        return clickedItem;
    }

    public void refresh() {
        // Refresh the inventory display
        itemRects.clear();
    }

    public Texture getItemTexture(ItemInterface item) {
        if (item instanceof Tool tool) {
            switch (tool.getToolType()) {
                case AXE:
                    return assetManager.axe;
                case FISHING_ROD:
                    return assetManager.fishingRod;
                case HOE:
                    return assetManager.hoe;
                case MILK_PAIL:
                    return assetManager.milkPail;
                case PICKAXE:
                    return assetManager.pickaxe;
                case SCYTHE:
                    return assetManager.scythe;
                case SHEARS:
                    return assetManager.shears;
                case WATERING_CAN:
                    return assetManager.wateringCan;
            }
        } else if (item instanceof CookingMaterial ingredient) {
            switch (ingredient.getName().toUpperCase()) {
                case "AMARANTH":
                    return assetManager.amaranth;
                case "APRICOT":
                    return assetManager.apricot;
                case "BEET":
                    return assetManager.beet;
                case "BLUEBERRY":
                    return assetManager.blueberry;
                case "CARROT":
                    return assetManager.carrot;
                case "CHEESE":
                    return assetManager.cheese;
                case "COFFEE":
                    return assetManager.coffee;
                case "CORN":
                    return assetManager.corn;
                case "EGG":
                    return assetManager.egg;
                case "EGGPLANT":
                    return assetManager.eggplant;
                case "FIBER":
                    return assetManager.fiber;
                case "FLOUNDER":
                    return assetManager.flounder;
                case "HASH_BROWNS":
                    return assetManager.hashbrowns;
                case "KALE":
                    return assetManager.kale;
                case "MELON":
                    return assetManager.melon;
                case "MIDNIGHT_CARP":
                    return assetManager.midnightCarp;
                case "MILK":
                    return assetManager.milk;
                case "OIL":
                    return assetManager.oil;
                case "PARSNIP":
                    return assetManager.parsnip;
                case "POTATO":
                    return assetManager.potato;
                case "PUMPKIN":
                    return assetManager.pumpkin;
                case "RADISH":
                    return assetManager.radish;
                case "RED_CABBAGE":
                    return assetManager.redCabbage;
                case "RICE":
                    return assetManager.rice;
                case "SALMON":
                    return assetManager.salmon;
                case "SARDINE":
                    return assetManager.sardine;
                case "SUGAR":
                    return assetManager.sugar;
                case "TOMATO":
                    return assetManager.tomato;
                case "WHEAT":
                    return assetManager.wheat;
            }
        } else if (item instanceof Food food) {
            switch (food.recipe) {
                case BAKED_FISH:
                    return assetManager.bakedFish;
                case BREAD:
                    return assetManager.bread;
                case COOKIE:
                    return assetManager.cookie;
                case DISH_O_THE_SEA:
                    return assetManager.dishOfTheSea;
                case FARMERS_LUNCH:
                    return assetManager.farmersLunch;
                case FRIED_EGG:
                    return assetManager.friedEgg;
                case FRUIT_SALAD:
                    return assetManager.fruitSalad;
                case MAKI_ROLL:
                    return assetManager.makiRoll;
                case MINERS_TREAT:
                    return assetManager.minersTreat;
                case OMELET:
                    return assetManager.omelet;
                case PANCAKES:
                    return assetManager.pancakes;
                case PIZZA:
                    return assetManager.pizza;
                case PUMPKIN_PIE:
                    return assetManager.pumpkinPie;
                case RED_PLATE:
                    return assetManager.redPlate;
                case SALAD:
                    return assetManager.salad;
                case SALMON_DINNER:
                    return assetManager.salmonDinner;
                case SEAFOAM_PUDDING:
                    return assetManager.seafoamPudding;
                case SPAGHETTI:
                    return assetManager.spaghetti;
                case SURVIVAL_BURGER:
                    return assetManager.survivalBurger;
                case TORTILLA:
                    return assetManager.tortilla;
                case TRIPLE_SHOT_ESPRESSO:
                    return assetManager.tripleShotEspresso;
                case TROUT_SOUP:
                    return assetManager.troutSoup;
                case VEGETABLE_MEDLEY:
                    return assetManager.vegetableMedley;
            }
        } else if (item instanceof AnimalProduct animalProduct) {
            switch (animalProduct.getName()) {
                case "dinosaur egg":
                    return assetManager.dinosaurEgg;
                case "duck egg":
                    return assetManager.duckEgg;
                case "duck feather":
                    return assetManager.duckFeather;
                case "egg":
                    return assetManager.egg;
                case "goat milk":
                    return assetManager.goatMilk;
                case "large egg":
                    return assetManager.largeEgg;
                case "large goat milk":
                    return assetManager.largeGoatMilk;
                case "large milk":
                    return assetManager.largeMilk;
                case "milk":
                    return assetManager.milk;
                case "rabbit foot":
                    return assetManager.rabbitsFoot;
                case "truffle":
                    return assetManager.truffle;
                case "wool":
                    return assetManager.wool;
            }
        } else if (item instanceof Fish fish) {
            for (FishType fishType : FishType.values()) {
                if (fishType.getName().equalsIgnoreCase(fish.getName())) {
                    return fishType.getTexture();
                }
            }
        }
        return null;
    }

    public void dispose() {
        if (inventoryBackgroundTexture != null) {
            inventoryBackgroundTexture.dispose();
        }
        if (trashCanIconTexture != null) {
            trashCanIconTexture.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }
}
