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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.StardewValley.Main;
import Controller.InGameMenu.ToolsController;

public class toolsUI implements Screen {

    private SpriteBatch batch;
    private Texture toggledPictureTexture;
    private final Texture axeTexture = new Texture(Gdx.files.internal("assets/tools/axe.png"));
    private final Texture wateringCanTexture = new Texture(Gdx.files.internal("assets/tools/watering_can.png"));
    private final Texture fishingRodTexture = new Texture(Gdx.files.internal("assets/tools/fishing_rod.png"));
    private final Texture scytheTexture = new Texture(Gdx.files.internal("assets/tools/scythe.png"));
    private final Texture hoeTexture = new Texture(Gdx.files.internal("assets/tools/hoe.png"));
    private final Texture milkPailTexture = new Texture(Gdx.files.internal("assets/tools/milk_pail.png"));
    private final Texture pickaxeTexture = new Texture(Gdx.files.internal("assets/tools/pickaxe.png"));
    private final Texture shearsTexture = new Texture(Gdx.files.internal("assets/tools/shears.png"));

    private final int TOOL_ICON_SIZE = 45;

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
    private ToolsController toolsController;

    public toolsUI(Main game) {
        this.game = game;
        this.toolsController = new ToolsController();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        toggledPictureTexture = new Texture(Gdx.files.internal("assets/toolsShelf.png"));

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
                        //toolsController.toolEquip("pickaxe");
                        Gdx.app.log("toolsUI", "Equipped Pickaxe");
                    } else if (axeRect != null && axeRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        //toolsController.toolEquip("axe");
                        Gdx.app.log("toolsUI", "Equipped Axe");
                    } else if (scytheRect != null && scytheRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        //toolsController.toolEquip("scythe");
                        Gdx.app.log("toolsUI", "Equipped Scythe");
                    } else if (shearsRect != null && shearsRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        //toolsController.toolEquip("shears");
                        Gdx.app.log("toolsUI", "Equipped Shears");
                    } else if (wateringCanRect != null && wateringCanRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        //toolsController.toolEquip("watering_can");
                        Gdx.app.log("toolsUI", "Equipped Watering_Can");
                    } else if (hoeRect != null && hoeRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        //toolsController.toolEquip("hoe");
                        Gdx.app.log("toolsUI", "Equipped Hoe");
                    } else if (fishingRodRect != null && fishingRodRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        //toolsController.toolEquip("fishing_rod");
                        Gdx.app.log("toolsUI", "Equipped Fishing_Rod");
                    } else if (milkPailRect != null && milkPailRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                        //toolsController.toolEquip("milk_pail");
                        Gdx.app.log("toolsUI", "Equipped Milk_Pail");
                    }
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
        }
        stage.draw();
    }

    private void drawTools() {
        BackPack backPack = App.getLoggedInUser().backPack;
        int i = 520;
        int toolY = y + 2;
        int space = 65;

        for (ItemInterface itemInterface : backPack.items.keySet()) {
            if (itemInterface instanceof Tool) {
                Tool tool = (Tool) itemInterface;
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
                        currentTexture = shearsTexture; //
                        i += space;
                        break;
                    case "watering_can":
                        currentTexture = wateringCanTexture; //
                        i += space;
                        break;
                    case "hoe":
                        currentTexture = hoeTexture; //
                        i += space;
                        break;
                    case "fishing_rod":
                        currentTexture = fishingRodTexture; //
                        i += space;
                        break;
                    case "milk_pail":
                        currentTexture = milkPailTexture; //
                        i += space;
                        break;
                }

                if (currentTexture != null) {
                    // Calculate the scaling factor for the current texture
                    float scaleX = (float) TOOL_ICON_SIZE / currentTexture.getWidth();
                    float scaleY = (float) TOOL_ICON_SIZE / currentTexture.getHeight();
                    // Use the smaller scale factor to ensure it fits within the square
                    float scale = Math.min(scaleX, scaleY);

                    // Calculate the new width and height based on the uniform scale
                    float scaledWidth = currentTexture.getWidth() * scale;
                    float scaledHeight = currentTexture.getHeight() * scale;

                    // Calculate offset to center the scaled image within the TOOL_ICON_SIZE square
                    float offsetX = (TOOL_ICON_SIZE - scaledWidth) / 2;
                    float offsetY = (TOOL_ICON_SIZE - scaledHeight) / 2;

                    batch.draw(currentTexture, i + offsetX, toolY + offsetY, scaledWidth, scaledHeight);

                    // Rect still needs to cover the entire logical slot for clicking
                    switch (toolName) {
                        case "pickaxe":
                            pickaxeRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                        case "axe":
                            axeRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                        case "scythe":
                            scytheRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                        case "shears":
                            shearsRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                        case "watering_can":
                            wateringCanRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                        case "hoe":
                            hoeRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                        case "fishing_rod":
                            fishingRodRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                        case "milk_pail":
                            milkPailRect = new Rect(i, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                            break;
                    }
                }
            }
        }
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
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
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
