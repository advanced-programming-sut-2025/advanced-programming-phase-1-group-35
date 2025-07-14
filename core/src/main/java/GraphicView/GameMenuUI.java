package GraphicView;

import Controller.GameMenuController;
import GraphicView.Game.GameMenuInputAdapter;
import GraphicView.Game.GameView;
import Model.Game;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import Model.App;
import Model.ItemInterface;
import Model.Rect;
import Model.Tools.BackPack;
import Model.Tools.Tool;
import Controller.InGameMenu.ToolsController;
import com.badlogic.gdx.graphics.Color;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GameMenuUI implements Screen {
    private GameView gameView;
    public Game gameModel;
    public GameMenuInputAdapter gameMenuInputAdapter;
    public GameMenuController gameController;
    private boolean isSleeping = false;
    private float sleepAlpha = 0f;
    private float sleepTimer = 0f;
    private static final float FADE_SPEED = 1.5f;
    private boolean advancingDay = false;
    private boolean isInInventory = false;
    private boolean isCook = false;

    private SpriteBatch toolsBatch;
    private ShapeRenderer toolsShapeRenderer; // Renamed
    private Texture toolsToggledPictureTexture; // Renamed
    private final Texture axeTexture = new Texture(Gdx.files.internal("assets/tools/axe.png"));
    private final Texture wateringCanTexture = new Texture(Gdx.files.internal("assets/tools/watering_can.png"));
    private final Texture fishingRodTexture = new Texture(Gdx.files.internal("assets/tools/fishing_rod.png"));
    private final Texture scytheTexture = new Texture(Gdx.files.internal("assets/tools/scythe.png"));
    private final Texture hoeTexture = new Texture(Gdx.files.internal("assets/tools/hoe.png"));
    private final Texture milkPailTexture = new Texture(Gdx.files.internal("assets/tools/milk_pail.png"));
    private final Texture pickaxeTexture = new Texture(Gdx.files.internal("assets/tools/pickaxe.png"));
    private final Texture shearsTexture = new Texture(Gdx.files.internal("assets/tools/shears.png"));
    private Map<String, Rect> toolRects;
    private String equippedToolName = "hoe";
    private Stage toolsStage;
    private final ToolsController toolsController = new ToolsController();
    private boolean isToolsUIVisible = false;
    private static final int TOOLS_Y_OFFSET = 65;


    public GameMenuUI(GameMenuController gameController, Game gameModel) {
        this.gameController = gameController;
        this.gameModel = gameModel;
        initializeGame();
    }

    private void initializeGame() {
        gameView = new GameView(gameModel);
        gameMenuInputAdapter = new GameMenuInputAdapter(gameModel, gameController);
        gameMenuInputAdapter.gameMenuUI = this;

        toolsBatch = new SpriteBatch();
        toolsShapeRenderer = new ShapeRenderer();
        toolsStage = new Stage(new ScreenViewport());
        toolsToggledPictureTexture = new Texture(Gdx.files.internal("assets/shelf.png"));
        toolRects = new HashMap<>();
        showToolsUIElements();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.T) {
                    toggleToolsUI();
                    return true;
                }
                return false;
            }
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (isToolsUIVisible) {
                    return toolsStage.touchDown(screenX, screenY, pointer, button);
                }
                return false;
            }
        });
        multiplexer.addProcessor(gameMenuInputAdapter);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameModel.update(delta);
        gameView.render();
        gameMenuInputAdapter.update(delta);

        if (isSleeping) {
            sleepTimer += delta;
            if (!advancingDay && sleepAlpha < 1f) {
                sleepAlpha = Math.min(1f, sleepAlpha + delta * FADE_SPEED);
                if (sleepAlpha >= 1f) {
                    try {
                        gameModel.getGameCalender().goToNextDay();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    advancingDay = true;
                }
            } else if (advancingDay && sleepAlpha > 0f) {
                sleepAlpha = Math.max(0f, sleepAlpha - delta * FADE_SPEED);
                if (sleepAlpha <= 0f) {
                    isSleeping = false;
                    advancingDay = false;
                    sleepTimer = 0f;
                }
            }

            gameView.getBatch().begin();
            gameView.getBatch().setColor(0f, 0f, 0f, sleepAlpha);
            gameView.getBatch().draw(gameView.getPixel(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            gameView.getBatch().setColor(1f, 1f, 1f, 1f);
            gameView.getBatch().end();
        }

        if (isToolsUIVisible) {
            renderToolsUI();
        }
    }

    public void startSleepTransition() {
        isSleeping = true;
        sleepAlpha = 0f;
        sleepTimer = 0f;
        advancingDay = false;
    }

    public void toggleInventoryMenu() {
        if (Main.getGame().getScreen() == this) {
            this.isInInventory = true;
            Main.getGame().setScreen(new InventoryMenuUI(Main.getGame(), this));
            isToolsUIVisible = false;
        } else if (isInInventory) {
            isInInventory = false;
            Main.getGame().setScreen(this);
            InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
            if (!multiplexer.getProcessors().contains(toolsStage, true)) {
                multiplexer.addProcessor(toolsStage);
            }
            Gdx.input.setInputProcessor(multiplexer);
        }
    }

    public void toggleCookMenu() {
        if (Main.getGame().getScreen() == this) {
            this.isCook = true;
            Main.getGame().setScreen(new CookUI(Main.getGame(), this));
            isToolsUIVisible = false;
        } else if (isCook) {
            isCook = false;
            Main.getGame().setScreen(this);
            InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
            if (!multiplexer.getProcessors().contains(toolsStage, true)) {
                multiplexer.addProcessor(toolsStage);
            }
            Gdx.input.setInputProcessor(multiplexer);
        }
    }

    public void toggleToolsUI() {
        isToolsUIVisible = !isToolsUIVisible;
        InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();

        if (isToolsUIVisible) {
            toolsStage.clear();
            showToolsUIElements();
            if (!multiplexer.getProcessors().contains(toolsStage, true)) {
                multiplexer.addProcessor(toolsStage);
            }
        } else {
            multiplexer.removeProcessor(toolsStage);
        }
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void showToolsUIElements() {
        toolsStage.clear();

        BackPack backPack = App.getCurrentGame().getPlayingUser().backPack;
        int xPos = (Gdx.graphics.getWidth() - toolsToggledPictureTexture.getWidth()) / 2 + 15;
        int toolY = TOOLS_Y_OFFSET + 2;
        int space = 65;
        int currentToolX = xPos;

        for (ItemInterface itemInterface : backPack.items.keySet()) {
            if (itemInterface instanceof Tool tool) {
                String toolName = tool.getName().toLowerCase();
                Texture currentTexture = null;

                switch (toolName) {
                    case "pickaxe": currentTexture = pickaxeTexture; break;
                    case "axe": currentTexture = axeTexture; break;
                    case "scythe": currentTexture = scytheTexture; break;
                    case "shears": currentTexture = shearsTexture; break;
                    case "watering_can": currentTexture = wateringCanTexture; break;
                    case "hoe": currentTexture = hoeTexture; break;
                    case "fishing_rod": currentTexture = fishingRodTexture; break;
                    case "milk_pail": currentTexture = milkPailTexture; break;
                }

                if (currentTexture != null) {
                    ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(currentTexture));
                    ImageButton button = new ImageButton(style);

                    int TOOL_ICON_SIZE = 45;
                    button.setBounds(currentToolX, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);

                    final String finalToolName = toolName;
                    button.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            equippedToolName = finalToolName;
                            toolsController.toolEquip(equippedToolName);
                            Gdx.app.log("ToolsUI", "Equipped: " + equippedToolName);
                        }
                    });
                    toolsStage.addActor(button);
                    toolRects.put(toolName, new Rect(currentToolX, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE));
                    currentToolX += space;
                }
            }
        }
    }

    public void renderToolsUI() {
        toolsBatch.begin();
        float x = (Gdx.graphics.getWidth() - toolsToggledPictureTexture.getWidth()) / 2f;
        toolsBatch.draw(toolsToggledPictureTexture, x, TOOLS_Y_OFFSET - 10);
        toolsBatch.end();

        toolsShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        toolsShapeRenderer.setColor(Color.RED);
        Rect selectedRect = toolRects.get(equippedToolName);
        if (selectedRect != null) {
            toolsShapeRenderer.rect(selectedRect.x, selectedRect.y, selectedRect.width, selectedRect.height);
        }
        toolsShapeRenderer.end();

        toolsStage.act(Gdx.graphics.getDeltaTime());
        toolsStage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        gameModel.camera.viewportWidth = i;
        gameModel.camera.viewportHeight = i1;
        gameModel.camera.update();
        toolsStage.getViewport().update(i, i1, true);
        float x = (Gdx.graphics.getWidth() - toolsToggledPictureTexture.getWidth()) / 2f;
        int currentToolX = (int)x + 20;
        int toolY = TOOLS_Y_OFFSET + 2;
        int space = 65;
        int TOOL_ICON_SIZE = 45;

        for (com.badlogic.gdx.scenes.scene2d.Actor actor : toolsStage.getActors()) {
            if (actor instanceof ImageButton) {
                actor.setBounds(currentToolX, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                currentToolX += space;
            }
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (gameView != null) {
            //gameView.dispose();
        }
        if (toolsBatch != null) toolsBatch.dispose();
        if (toolsShapeRenderer != null) toolsShapeRenderer.dispose();
        if (toolsToggledPictureTexture != null) toolsToggledPictureTexture.dispose();
        axeTexture.dispose();
        wateringCanTexture.dispose();
        fishingRodTexture.dispose();
        scytheTexture.dispose();
        hoeTexture.dispose();
        milkPailTexture.dispose();
        pickaxeTexture.dispose();
        shearsTexture.dispose();
        if (toolsStage != null) toolsStage.dispose();
    }
}
