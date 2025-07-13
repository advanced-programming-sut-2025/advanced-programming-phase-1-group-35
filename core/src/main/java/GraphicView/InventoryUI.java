package GraphicView;

import Model.App;
import Model.AssetManager;
import Model.CookingMaterial;
import Model.ItemInterface;
import Model.Food;
import Model.Tools.Tool;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InventoryUI {
    private Texture inventoryBackgroundTexture;
    private Texture trashCanIconTexture;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private Main game;
    private AssetManager assetManager;

    private static final int INVENTORY_ROWS = 3;
    private static final int INVENTORY_COLS = 12;
    private static final int ICON_SIZE = 64;
    private static final int CELL_PADDING = 10;
    private static final int TOTAL_WIDTH = INVENTORY_COLS * (ICON_SIZE + CELL_PADDING) - CELL_PADDING;
    private static final int TOTAL_HEIGHT = INVENTORY_ROWS * (ICON_SIZE + CELL_PADDING) - CELL_PADDING;
    private static final int TOP_PADDING = 50;
    private int startX, startY;

    public InventoryUI(Main game) {
        this.game = game;
        this.assetManager = new AssetManager();
        initialize();
    }

    private void initialize() {
        inventoryBackgroundTexture = new Texture(Gdx.files.internal("assets/shelf3.png"));
        trashCanIconTexture = new Texture(Gdx.files.internal("assets/inventory/Trash_Can.png"));
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.0f);
        glyphLayout = new GlyphLayout();
    }

    public void draw(SpriteBatch batch) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        startX = ((screenWidth - TOTAL_WIDTH) / 2);
        startY = (screenHeight - TOTAL_HEIGHT - TOP_PADDING) - 200;

        batch.draw(inventoryBackgroundTexture, startX - 20, startY - 20, TOTAL_WIDTH + 40, TOTAL_HEIGHT + 40);
        batch.draw(trashCanIconTexture, 1500, 600, ICON_SIZE, ICON_SIZE);
        drawInventoryItems(batch);
    }

    private void drawInventoryItems(SpriteBatch batch) {
//        if (App.getCurrentGame() == null || App.getCurrentGame().getPlayingUser() == null || App.getCurrentGame().getPlayingUser().backPack == null) {
//            return;
//        }
        // todo

        int currentItemIndex = 0;

        for (ItemInterface item : App.getLoggedInUser().backPack.items.keySet()) {
            if (currentItemIndex >= INVENTORY_COLS * INVENTORY_ROWS) {
                break;
            }

            Texture itemTexture = getItemTexture(item);
            int count = App.getLoggedInUser().backPack.items.get(item);

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
            }
            currentItemIndex++;
        }
    }

    private Texture getItemTexture(ItemInterface item) {
        if (item instanceof Tool tool) {
            switch (tool.getToolType()) {
                case AXE: return assetManager.axe;
                case FISHING_ROD: return assetManager.fishingRod;
                case HOE: return assetManager.hoe;
                case MILK_PAIL: return assetManager.milkPail;
                case PICKAXE: return assetManager.pickaxe;
                case SCYTHE: return assetManager.scythe;
                case SHEARS: return assetManager.shears;
                case WATERING_CAN: return assetManager.wateringCan;
            }
        } else if (item instanceof CookingMaterial ingredient) {
            switch (ingredient.getName().toUpperCase()) {
                case "AMARANTH": return assetManager.amaranth;
                case "APRICOT": return assetManager.apricot;
                case "BEET": return assetManager.beet;
                case "BLUEBERRY": return assetManager.blueberry;
                case "CARROT": return assetManager.carrot;
                case "CHEESE": return assetManager.cheese;
                case "COFFEE": return assetManager.coffee;
                case "CORN": return assetManager.corn;
                case "EGG": return assetManager.egg;
                case "EGGPLANT": return assetManager.eggplant;
                case "FIBER": return assetManager.fiber;
                case "FLOUNDER": return assetManager.flounder;
                case "HASH_BROWNS": return assetManager.hashbrowns;
                case "KALE": return assetManager.kale;
                case "MELON": return assetManager.melon;
                case "MIDNIGHT_CARP": return assetManager.midnightCarp;
                case "MILK": return assetManager.milk;
                case "OIL": return assetManager.oil;
                case "PARSNIP": return assetManager.parsnip;
                case "POTATO": return assetManager.potato;
                case "PUMPKIN": return assetManager.pumpkin;
                case "RADISH": return assetManager.radish;
                case "RED_CABBAGE": return assetManager.redCabbage;
                case "RICE": return assetManager.rice;
                case "SALMON": return assetManager.salmon;
                case "SARDINE": return assetManager.sardine;
                case "SUGAR": return assetManager.sugar;
                case "TOMATO": return assetManager.tomato;
                case "WHEAT": return assetManager.wheat;
            }
        } else if (item instanceof Food food) {
            switch (food.recipe) {
                case BAKED_FISH: return assetManager.bakedFish;
                case BREAD: return assetManager.bread;
                case COOKIE: return assetManager.cookie;
                case DISH_O_THE_SEA: return assetManager.dishOfTheSea;
                case FARMERS_LUNCH: return assetManager.farmersLunch;
                case FRIED_EGG: return assetManager.friedEgg;
                case FRUIT_SALAD: return assetManager.fruitSalad;
                case MAKI_ROLL: return assetManager.makiRoll;
                case MINERS_TREAT: return assetManager.minersTreat;
                case OMELET: return assetManager.omelet;
                case PANCAKES: return assetManager.pancakes;
                case PIZZA: return assetManager.pizza;
                case PUMPKIN_PIE: return assetManager.pumpkinPie;
                case RED_PLATE: return assetManager.redPlate;
                case SALAD: return assetManager.salad;
                case SALMON_DINNER: return assetManager.salmonDinner;
                case SEAFOAM_PUDDING: return assetManager.seafoamPudding;
                case SPAGHETTI: return assetManager.spaghetti;
                case SURVIVAL_BURGER: return assetManager.survivalBurger;
                case TORTILLA: return assetManager.tortilla;
                case TRIPLE_SHOT_ESPRESSO: return assetManager.tripleShotEspresso;
                case TROUT_SOUP: return assetManager.troutSoup;
                case VEGETABLE_MEDLEY: return assetManager.vegetableMedley;
            }
        }
        return null;
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
