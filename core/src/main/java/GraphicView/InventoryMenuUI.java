package GraphicView;

import Model.Rect;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InventoryMenuUI implements Screen {
    private enum MenuState {
        INVENTORY,
        SKILLS,
        SOCIAL,
        MAP
    }

    private SpriteBatch batch;
    private Stage stage;
    private Main game;
    private MenuState currentState;
    private boolean isInventoryMenuVisible = false;

    private Texture inventoryButtonTexture;
    private Texture skillsButtonTexture;
    private Texture socialButtonTexture;
    private Texture mapButtonTexture;
    private Texture menuBackgroundTexture; // Added for the background

    private Rect inventoryRect;
    private Rect skillsRect;
    private Rect socialRect;
    private Rect mapRect;

    private InventoryUI inventoryPanel;
    // private SkillsUI skillsPanel;
    // private SocialUI socialPanel;
    // private MapUI mapPanel;

    private static final int BUTTON_SIZE = 64;
    private static final int BUTTON_PADDING = 10;
    private static final int START_X = 500;
    private static final int START_Y = 840;

    public InventoryMenuUI(Main game) {
        this.game = game;
        this.currentState = MenuState.INVENTORY;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());

        inventoryButtonTexture = new Texture(Gdx.files.internal("assets/inventory/inventory.png"));
        skillsButtonTexture = new Texture(Gdx.files.internal("assets/inventory/skills.png"));
        socialButtonTexture = new Texture(Gdx.files.internal("assets/inventory/social.png"));
        mapButtonTexture = new Texture(Gdx.files.internal("assets/inventory/map.png"));
        menuBackgroundTexture = new Texture(Gdx.files.internal("assets/background/inventory.jpg"));

        inventoryRect = new Rect(START_X, START_Y, BUTTON_SIZE, BUTTON_SIZE);
        skillsRect = new Rect(START_X + BUTTON_SIZE + BUTTON_PADDING, START_Y, BUTTON_SIZE, BUTTON_SIZE);
        socialRect = new Rect(START_X + 2 * (BUTTON_SIZE + BUTTON_PADDING), START_Y, BUTTON_SIZE, BUTTON_SIZE);
        mapRect = new Rect(START_X + 3 * (BUTTON_SIZE + BUTTON_PADDING), START_Y, BUTTON_SIZE, BUTTON_SIZE);

        inventoryPanel = new InventoryUI(game, stage);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    isInventoryMenuVisible = !isInventoryMenuVisible;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (!isInventoryMenuVisible) return false;

                Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                float x = stageCoords.x;
                float y = stageCoords.y;

                if (inventoryRect.contains(x, y)) {
                    currentState = MenuState.INVENTORY;
                } else if (skillsRect.contains(x, y)) {
                    currentState = MenuState.SKILLS;
                } else if (socialRect.contains(x, y)) {
                    currentState = MenuState.SOCIAL;
                } else if (mapRect.contains(x, y)) {
                    currentState = MenuState.MAP;
                }

                return false;
            }
        }, stage));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        if (isInventoryMenuVisible) {
            batch.begin();
            // Draw the background image first so other elements are on top
            batch.draw(menuBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            batch.draw(inventoryButtonTexture, inventoryRect.x, inventoryRect.y, inventoryRect.width, inventoryRect.height);
            batch.draw(skillsButtonTexture, skillsRect.x, skillsRect.y, skillsRect.width, skillsRect.height);
            batch.draw(socialButtonTexture, socialRect.x, socialRect.y, socialRect.width, socialRect.height);
            batch.draw(mapButtonTexture, mapRect.x, mapRect.y, mapRect.width, mapRect.height);

            switch (currentState) {
                case INVENTORY:
                    inventoryPanel.draw(batch);
                    break;
                case SKILLS:
                    break;
                case SOCIAL:
                    break;
                case MAP:
                    break;
            }
            batch.end();
        }
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
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        inventoryButtonTexture.dispose();
        skillsButtonTexture.dispose();
        socialButtonTexture.dispose();
        mapButtonTexture.dispose();
        menuBackgroundTexture.dispose(); // Dispose the new background texture
        inventoryPanel.dispose();
    }
}
