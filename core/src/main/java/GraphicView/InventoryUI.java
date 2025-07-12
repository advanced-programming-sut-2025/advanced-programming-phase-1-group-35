package GraphicView;

import Model.App;
import Model.AssetManager;
import Model.CookingMaterial;
import Model.ItemInterface;
import Model.Food;
import Model.Tools.Tool;
import Model.enums.CookingRecipes;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InventoryUI implements Screen {
    private SpriteBatch batch;
    private Texture inventoryBackgroundTexture;
    private boolean isInventoryVisible = false;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private Stage stage;
    private Main game;
    private AssetManager assetManager = new AssetManager();

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
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        inventoryBackgroundTexture = new Texture(Gdx.files.internal("assets/shelf3.png"));

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.0f);
        glyphLayout = new GlyphLayout();

        startX = ((Gdx.graphics.getWidth() - TOTAL_WIDTH) / 2);
        startY = (Gdx.graphics.getHeight() - TOTAL_HEIGHT - TOP_PADDING);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    isInventoryVisible = !isInventoryVisible;
                    return true;
                }
                return false;
            }
        }));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        if (isInventoryVisible) {
            batch.begin();
            batch.draw(inventoryBackgroundTexture, startX - 20, startY - 20, TOTAL_WIDTH + 40, TOTAL_HEIGHT + 40);
            drawInventoryItems();
            batch.end();
        }
        stage.draw();
    }

    private void drawInventoryItems() {
//        if (App.getCurrentGame() == null || App.getCurrentGame().getPlayingUser() == null || App.getCurrentGame().getPlayingUser().backPack == null) {
//            return;
//        } // todo

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
            if (ingredient.getName().equalsIgnoreCase("AMARANTH")) {
                return assetManager.amaranth;
            } else if (ingredient.getName().equalsIgnoreCase("APRICOT")) {
                return assetManager.apricot;
            } else if (ingredient.getName().equalsIgnoreCase("BEET")) {
                return assetManager.beet;
            } else if (ingredient.getName().equalsIgnoreCase("BLUEBERRY")) {
                return assetManager.blueberry;
            } else if (ingredient.getName().equalsIgnoreCase("CARROT")) {
                return assetManager.carrot;
            } else if (ingredient.getName().equalsIgnoreCase("CHEESE")) {
                return assetManager.cheese;
            } else if (ingredient.getName().equalsIgnoreCase("COFFEE")) {
                return assetManager.coffee;
            } else if (ingredient.getName().equalsIgnoreCase("CORN")) {
                return assetManager.corn;
            } else if (ingredient.getName().equalsIgnoreCase("EGG")) {
                return assetManager.egg;
            } else if (ingredient.getName().equalsIgnoreCase("EGGPLANT")) {
                return assetManager.eggplant;
            } else if (ingredient.getName().equalsIgnoreCase("FIBER")) {
                return assetManager.fiber;
            } else if (ingredient.getName().equalsIgnoreCase("FLOUNDER")) {
                return assetManager.flounder;
            } else if (ingredient.getName().equalsIgnoreCase("HASH_BROWNS")) {
                return assetManager.hashbrowns;
            } else if (ingredient.getName().equalsIgnoreCase("KALE")) {
                return assetManager.kale;
            } else if (ingredient.getName().equalsIgnoreCase("MELON")) {
                return assetManager.melon;
            } else if (ingredient.getName().equalsIgnoreCase("MIDNIGHT_CARP")) {
                return assetManager.midnightCarp;
            } else if (ingredient.getName().equalsIgnoreCase("MILK")) {
                return assetManager.milk;
            } else if (ingredient.getName().equalsIgnoreCase("OIL")) {
                return assetManager.oil;
            } else if (ingredient.getName().equalsIgnoreCase("PARSNIP")) {
                return assetManager.parsnip;
            } else if (ingredient.getName().equalsIgnoreCase("POTATO")) {
                return assetManager.potato;
            } else if (ingredient.getName().equalsIgnoreCase("PUMPKIN")) {
                return assetManager.pumpkin;
            } else if (ingredient.getName().equalsIgnoreCase("RADISH")) {
                return assetManager.radish;
            } else if (ingredient.getName().equalsIgnoreCase("RED_CABBAGE")) {
                return assetManager.redCabbage;
            } else if (ingredient.getName().equalsIgnoreCase("RICE")) {
                return assetManager.rice;
            } else if (ingredient.getName().equalsIgnoreCase("SALMON")) {
                return assetManager.salmon;
            } else if (ingredient.getName().equalsIgnoreCase("SARDINE")) {
                return assetManager.sardine;
            } else if (ingredient.getName().equalsIgnoreCase("SUGAR")) {
                return assetManager.sugar;
            } else if (ingredient.getName().equalsIgnoreCase("TOMATO")) {
                return assetManager.tomato;
            } else if (ingredient.getName().equalsIgnoreCase("WHEAT")) {
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
        }
        return null;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        startX = (width - TOTAL_WIDTH) / 2;
        startY = Gdx.graphics.getHeight() - TOTAL_HEIGHT - TOP_PADDING;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        inventoryBackgroundTexture.dispose();
        font.dispose();
    }
}
