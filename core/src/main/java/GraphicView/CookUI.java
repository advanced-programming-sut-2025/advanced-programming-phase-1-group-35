package GraphicView;

import Controller.InGameMenu.CookingController;
import Model.*;
import Model.enums.CookingRecipes;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

public class CookUI implements Screen {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture toggledPictureTexture;
    private boolean isFridgeVisible = false;
    private final int y = 65;
    private AssetManager assetManager;
    private Map<CookingRecipes, Texture> recipeTextures;
    private Map<CookingRecipes, Rect> recipeRects;

    private Texture refrigeratorIconTexture;
    private Rect refrigeratorRect;
    private Texture fridgeShelfTexture;
    private static final int FRIDGE_SHELF_COLS = 12;
    private static final int FRIDGE_SHELF_ICON_SIZE = 64;

    private Stage stage;
    private Main game;
    private final CookingController cookingController;
    private GameMenuUI gameMenuUI; // Reference to GameMenuUI

    private static final int RECIPES_PER_ROW = 12;
    private static final int RECIPE_ICON_SIZE = 49;
    private static final int RECIPE_PADDING_X = 15;
    private static final int RECIPE_PADDING_Y = 15;

    private Texture backgroundTexture;

    public CookUI(Main game, GameMenuUI gameMenuUI) {
        this.game = game;
        this.gameMenuUI = gameMenuUI;
        cookingController = new CookingController();
        cookingController.showCookingRecipes();
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
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());

        assetManager = new AssetManager();

        backgroundTexture = new Texture(Gdx.files.internal("assets/background/cook.jpg"));
        toggledPictureTexture = new Texture(Gdx.files.internal("assets/shelf2.png"));
        refrigeratorIconTexture = new Texture(Gdx.files.internal("assets/refrigerator.png"));
        fridgeShelfTexture = new Texture(Gdx.files.internal("assets/shelf.png"));
        refrigeratorRect = new Rect(Gdx.graphics.getWidth() - 500, 100, 80, 80);

        recipeTextures = new HashMap<>();
        recipeRects = new HashMap<>();

        for (CookingRecipes recipe : CookingRecipes.values()) {
            Texture texture = getRecipeTexture(recipe);
            if (texture != null) {
                recipeTextures.put(recipe, texture);
                recipeRects.put(recipe, new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE));
            }
        }

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.C) {
                    gameMenuUI.toggleCookMenu();
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int libGdxY = Gdx.graphics.getHeight() - screenY;
                if (refrigeratorRect.contains(screenX, libGdxY)) {
                    isFridgeVisible = !isFridgeVisible;
                    return true;
                }

                if (button == Input.Buttons.LEFT) {
                    for (Map.Entry<CookingRecipes, Rect> entry : recipeRects.entrySet()) {
                        if (entry.getValue().contains(screenX, libGdxY)) {
                            Result result = cookingController.cook(entry.getKey().name());
                            if (!result.isSuccess()) {
                                showDialog("Error", result.toString());
                                return false;
                            }
                            return true;
                        }
                    }
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
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(refrigeratorIconTexture, refrigeratorRect.x, refrigeratorRect.y, refrigeratorRect.width, refrigeratorRect.height);

        float shelfX = (Gdx.graphics.getWidth() - toggledPictureTexture.getWidth()) / 2f;
        batch.draw(toggledPictureTexture, shelfX, y - 10);
        drawRecipes();
        if (isFridgeVisible) {
            drawFridgeContents();
        }
        batch.end();
        stage.draw();
    }

    private void drawFridgeContents() {
        if (App.getCurrentGame().getPlayingUser() == null || App.getCurrentGame().getPlayingUser().backPack == null) {
            return;
        }

        float shelfWidth = FRIDGE_SHELF_COLS * (FRIDGE_SHELF_ICON_SIZE + 10);
        float shelfX = (Gdx.graphics.getWidth() - shelfWidth) / 2f;
        float shelfY = Gdx.graphics.getHeight() / 4f;

        batch.draw(fridgeShelfTexture, shelfX, shelfY, shelfWidth, FRIDGE_SHELF_ICON_SIZE + 20);

        int currentItemIndex = 0;
        for (ItemInterface item : App.getCurrentGame().getPlayingUser().backPack.refrigerator) {
            if (currentItemIndex >= FRIDGE_SHELF_COLS) {
                break;
            }

            Texture itemTexture = getItemTexture(item);

            if (itemTexture != null) {
                float itemX = shelfX + (currentItemIndex * (FRIDGE_SHELF_ICON_SIZE + 10)) + 10;
                float itemY = shelfY + 10;
                batch.draw(itemTexture, itemX, itemY, FRIDGE_SHELF_ICON_SIZE, FRIDGE_SHELF_ICON_SIZE);
            }
            currentItemIndex++;
        }
    }

    private void drawRecipes() {
        if (App.getCurrentGame() == null || App.getCurrentGame().getPlayingUser() == null || App.getCurrentGame().getPlayingUser().learnedRecipes == null) {
            return;
        }

        float shelfWidth = toggledPictureTexture.getWidth();
        float shelfX = (Gdx.graphics.getWidth() - shelfWidth) / 2f;
        float rowContentWidth = (RECIPES_PER_ROW * RECIPE_ICON_SIZE) + ((RECIPES_PER_ROW - 1) * RECIPE_PADDING_X);
        float startRecipeX = (shelfX + (shelfWidth - rowContentWidth) / 2f) - 4;

        CookingRecipes[] allRecipes = CookingRecipes.values();
        for (int i = 0; i < 24 && i < allRecipes.length; i++) {
            CookingRecipes recipe = allRecipes[i];
            Texture recipeTexture = recipeTextures.get(recipe);
            Rect recipeRect = recipeRects.get(recipe);
            if (recipeTexture != null && recipeRect != null) {
                boolean isLearned = App.getCurrentGame().getPlayingUser().learnedRecipes.contains(recipe);
                if (!isLearned) {
                    batch.setColor(0.5f, 0.5f, 0.5f, 1.0f);
                }
                int row = i / RECIPES_PER_ROW;
                int col = i % RECIPES_PER_ROW;
                float recipeX = startRecipeX + (col * (RECIPE_ICON_SIZE + RECIPE_PADDING_X));
                float recipeY = y + (row * (RECIPE_ICON_SIZE + RECIPE_PADDING_Y));
                batch.draw(recipeTexture, recipeX, recipeY, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
                batch.setColor(Color.WHITE);
                recipeRect.set(recipeX, recipeY, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
            }
        }
    }

    private Texture getItemTexture(ItemInterface item) {
        if (item instanceof Food food) {
            return getRecipeTexture(food.recipe);
        } else if (item instanceof CookingMaterial material) {
            switch (material.getName().toUpperCase()) {
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
        }
        return null;
    }

    private Texture getRecipeTexture(CookingRecipes recipe) {
        switch (recipe) {
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
        return null;
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        toggledPictureTexture.dispose();
        refrigeratorIconTexture.dispose();
        fridgeShelfTexture.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        stage.dispose();

    }
}
