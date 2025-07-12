package GraphicView;

import Model.App;
import Model.ItemInterface;
import Model.Rect;
import Model.Tools.BackPack;
import Model.Tools.Tool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.StardewValley.Main;
import Controller.InGameMenu.ToolsController;

public class toolsUI implements Screen {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture toggledPictureTexture;
    private final Texture axeTexture = new Texture(Gdx.files.internal("assets/tools/axe.png"));
    private final Texture wateringCanTexture = new Texture(Gdx.files.internal("assets/tools/watering_can.png"));
    private final Texture fishingRodTexture = new Texture(Gdx.files.internal("assets/tools/fishing_rod.png"));
    private final Texture scytheTexture = new Texture(Gdx.files.internal("assets/tools/scythe.png"));
    private final Texture hoeTexture = new Texture(Gdx.files.internal("assets/tools/hoe.png"));
    private final Texture milkPailTexture = new Texture(Gdx.files.internal("assets/tools/milk_pail.png"));
    private final Texture pickaxeTexture = new Texture(Gdx.files.internal("assets/tools/pickaxe.png"));
    private final Texture shearsTexture = new Texture(Gdx.files.internal("assets/tools/shears.png"));

    private Rect axeRect;
    private Rect wateringCanRect;
    private Rect fishingRodRect;
    private Rect scytheRect;
    private Rect hoeRect;
    private Rect milkPailRect;
    private Rect pickaxeRect;
    private Rect shearsRect;

    private boolean isPictureVisible = false;
    private final int y = 65;

    private Stage stage;
    private Main game;
    private final ToolsController toolsController;
    private String equippedToolName = "hoe";

    public toolsUI(Main game) {
        this.game = game;
        toolsController = new ToolsController();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());
        toggledPictureTexture = new Texture(Gdx.files.internal("assets/shelf.png"));

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.T) {
                    isPictureVisible = !isPictureVisible;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (isPictureVisible && button == Input.Buttons.LEFT) {
                    if (pickaxeRect != null && pickaxeRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "pickaxe";
                        Gdx.app.log("toolsUI", "Equipped Pickaxe");
                    } else if (axeRect != null && axeRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "axe";
                        Gdx.app.log("toolsUI", "Equipped Axe");
                    } else if (scytheRect != null && scytheRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "scythe";
                        Gdx.app.log("toolsUI", "Equipped Scythe");
                    } else if (shearsRect != null && shearsRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "shears";
                        Gdx.app.log("toolsUI", "Equipped Shears");
                    } else if (wateringCanRect != null && wateringCanRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "watering_can";
                        Gdx.app.log("toolsUI", "Equipped Watering Can");
                    } else if (hoeRect != null && hoeRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "hoe";
                        Gdx.app.log("toolsUI", "Equipped Hoe");
                    } else if (fishingRodRect != null && fishingRodRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "fishing_rod";
                        Gdx.app.log("toolsUI", "Equipped Fishing Rod");
                    } else if (milkPailRect != null && milkPailRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        equippedToolName = "milk_pail";
                        Gdx.app.log("toolsUI", "Equipped Milk Pail");
                    }
                    toolsController.toolEquip(equippedToolName);
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

        if (isPictureVisible) {
            batch.begin();
            float x = (Gdx.graphics.getWidth() - toggledPictureTexture.getWidth()) / 2f;
            batch.draw(toggledPictureTexture, x, y - 10);
            drawTools();
            batch.end();
            drawToolBorder();
        }

        stage.draw();
    }

    private void drawTools() {
        BackPack backPack = App.getLoggedInUser().backPack;
        int i = 520;
        int toolY = y + 2;
        int space = 65;

        for (ItemInterface itemInterface : backPack.items.keySet()) {
            if (itemInterface instanceof Tool tool) {
                String toolName = tool.getName().toLowerCase();
                Texture currentTexture = null;

                switch (toolName) {
                    case "pickaxe":
                        currentTexture = pickaxeTexture;
                        i += space;
                        break;
                    case "axe":
                        currentTexture = axeTexture;
                        i += space;
                        break;
                    case "scythe":
                        currentTexture = scytheTexture;
                        i += space;
                        break;
                    case "shears":
                        currentTexture = shearsTexture;
                        i += space;
                        break;
                    case "watering_can":
                        currentTexture = wateringCanTexture;
                        i += space;
                        break;
                    case "hoe":
                        currentTexture = hoeTexture;
                        i += space;
                        break;
                    case "fishing_rod":
                        currentTexture = fishingRodTexture;
                        i += space;
                        break;
                    case "milk_pail":
                        currentTexture = milkPailTexture;
                        i += space;
                        break;
                }

                if (currentTexture != null) {
                    int TOOL_ICON_SIZE = 45;
                    float scaleX = (float) TOOL_ICON_SIZE / currentTexture.getWidth();
                    float scaleY = (float) TOOL_ICON_SIZE / currentTexture.getHeight();
                    float scale = Math.min(scaleX, scaleY);

                    float scaledWidth = currentTexture.getWidth() * scale;
                    float scaledHeight = currentTexture.getHeight() * scale;
                    float offsetX = (TOOL_ICON_SIZE - scaledWidth) / 2;
                    float offsetY = (TOOL_ICON_SIZE - scaledHeight) / 2;

                    batch.draw(currentTexture, i + offsetX, toolY + offsetY, scaledWidth, scaledHeight);

                    Rect rect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                    switch (toolName) {
                        case "pickaxe":
                            pickaxeRect = rect;
                            break;
                        case "axe":
                            axeRect = rect;
                            break;
                        case "scythe":
                            scytheRect = rect;
                            break;
                        case "shears":
                            shearsRect = rect;
                            break;
                        case "watering_can":
                            wateringCanRect = rect;
                            break;
                        case "hoe":
                            hoeRect = rect;
                            break;
                        case "fishing_rod":
                            fishingRodRect = rect;
                            break;
                        case "milk_pail":
                            milkPailRect = rect;
                            break;
                    }
                }
            }
        }
    }

    private void drawToolBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        Rect selectedRect = null;

        switch (equippedToolName) {
            case "pickaxe":
                selectedRect = pickaxeRect;
                break;
            case "axe":
                selectedRect = axeRect;
                break;
            case "scythe":
                selectedRect = scytheRect;
                break;
            case "shears":
                selectedRect = shearsRect;
                break;
            case "watering_can":
                selectedRect = wateringCanRect;
                break;
            case "hoe":
                selectedRect = hoeRect;
                break;
            case "fishing_rod":
                selectedRect = fishingRodRect;
                break;
            case "milk_pail":
                selectedRect = milkPailRect;
                break;
        }

        if (selectedRect != null) {
            shapeRenderer.rect(selectedRect.x, selectedRect.y, selectedRect.width, selectedRect.height);
        }

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        toggledPictureTexture.dispose();
        axeTexture.dispose();
        wateringCanTexture.dispose();
        fishingRodTexture.dispose();
        scytheTexture.dispose();
        hoeTexture.dispose();
        milkPailTexture.dispose();
        pickaxeTexture.dispose();
        shearsTexture.dispose();
        stage.dispose();
    }
}
