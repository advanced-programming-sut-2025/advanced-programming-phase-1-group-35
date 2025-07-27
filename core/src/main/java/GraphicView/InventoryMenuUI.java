package GraphicView;

import Model.Rect;
import com.StardewValley.Main;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    private GameMenuUI gameMenuUI;
    private MenuState currentState;

    private Texture inventoryButtonTexture;
    private Texture skillsButtonTexture;
    private Texture socialButtonTexture;
    private Texture mapButtonTexture;
    private Texture menuBackgroundTexture;

    private Inventory inventoryPanel;
    private Skills skillsPanel;

    private static final int BUTTON_SIZE = 100;
    private static final int START_X = 480;
    private static final int START_Y = 810;

    public InventoryMenuUI(Main game, GameMenuUI gameMenuUI) {
        this.game = game;
        this.gameMenuUI = gameMenuUI;
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

        ImageButton inventoryBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(inventoryButtonTexture)));
        ImageButton skillsBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(skillsButtonTexture)));
        ImageButton socialBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(socialButtonTexture)));
        ImageButton mapBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(mapButtonTexture)));

        inventoryBtn.setBounds(START_X, START_Y, BUTTON_SIZE, BUTTON_SIZE);
        skillsBtn.setBounds(START_X + 60, START_Y, BUTTON_SIZE, BUTTON_SIZE);
        socialBtn.setBounds(START_X + 2 * (60), START_Y, BUTTON_SIZE, BUTTON_SIZE);
        mapBtn.setBounds(START_X + 3 * (60), START_Y, BUTTON_SIZE, BUTTON_SIZE);

        inventoryBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = MenuState.INVENTORY;
                skillsPanel.setVisible(false);
            }
        });
        skillsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = MenuState.SKILLS;
                skillsPanel.setVisible(true);
            }
        });
        socialBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = MenuState.SOCIAL;
                skillsPanel.setVisible(false);
            }
        });
        mapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = MenuState.MAP;
                skillsPanel.setVisible(false);
            }
        });

        stage.addActor(inventoryBtn);
        stage.addActor(skillsBtn);
        stage.addActor(socialBtn);
        stage.addActor(mapBtn);

        inventoryPanel = new Inventory(game, stage, gameMenuUI.gameController);
        skillsPanel = new Skills(stage);

        InputMultiplexer mainMultiplexer = gameMenuUI.getMainMultiplexer();
        mainMultiplexer.addProcessor(stage);
        mainMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    gameMenuUI.toggleInventoryMenu();
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setInputProcessor(mainMultiplexer);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        batch.begin();
        batch.draw(menuBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        InputMultiplexer mainMultiplexer = gameMenuUI.getMainMultiplexer();
        mainMultiplexer.removeProcessor(stage);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        inventoryButtonTexture.dispose();
        skillsButtonTexture.dispose();
        socialButtonTexture.dispose();
        mapButtonTexture.dispose();
        menuBackgroundTexture.dispose();
        if (inventoryPanel != null) {
            inventoryPanel.dispose();
        }
    }
}
