package GraphicView;

import Controller.InGameMenu.AnimalController;
import Model.GameAssetManager;
import Model.enums.animal.FishType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FishingUI implements Screen {
    private final GameMenuUI gameMenuUI;
    private final AnimalController animalController;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture fishTexture;
    private boolean isPerfectCatch;
    private float catchingProgress;
    private final FishType fish;
    private boolean isOver = false;

    private final float gameAreaX = 50;
    private final float gameAreaY = 170;
    private final float gameAreaWidth = Gdx.graphics.getWidth() - 100;

    private float playerBarX;
    private final float playerBarWidth = 100;

    private float fishX;
    private float fishTargetX;
    private float fishMoveTimer = 0f;

    private final float progressBarWidth = gameAreaWidth;

    public FishingUI(GameMenuUI gameMenuUI) {
        this.gameMenuUI = gameMenuUI;
        this.animalController = new AnimalController();
        this.fish = FishType.getRandomFish();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());

        font = new BitmapFont();
        font.setColor(Color.PURPLE);
        font.getData().setScale(2f);

        backgroundTexture = new Texture(Gdx.files.internal("assets/background/fishing.jpg"));
        fishTexture = fish.getTexture();

        isPerfectCatch = true;
        catchingProgress = 0.2f;
        playerBarX = gameAreaX + gameAreaWidth / 2f - playerBarWidth / 2f;
        fishX = gameAreaX + gameAreaWidth / 2f;
        fishTargetX = fishX;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.Q) {
                    gameMenuUI.toggleMiniGameMenu();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        update(delta);
        draw();
    }

    private void handleInput(float delta) {
        float playerBarSpeed = 250f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBarX += playerBarSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBarX -= playerBarSpeed * delta;
        }

        playerBarX = MathUtils.clamp(playerBarX, gameAreaX, gameAreaX + gameAreaWidth - playerBarWidth);
    }

    private void update(float delta) {
        fishMoveTimer += delta;
        if (fishMoveTimer >= 0.5f) {
            fishMoveTimer = 0;
            updateFishMovement();
        }

        fishX = MathUtils.lerp(fishX, fishTargetX, delta * 5f);

        boolean fishInBar = (fishX > playerBarX && fishX < playerBarX + playerBarWidth);

        if (fishInBar) {
            catchingProgress += 0.2f * delta;
        } else {
            catchingProgress -= 0.1f * delta;
            isPerfectCatch = false;
        }

        catchingProgress = MathUtils.clamp(catchingProgress, 0, 1);

        if (catchingProgress >= 1) {
            winGame();
        } else if (catchingProgress <= 0) {
            loseGame();
        }
    }

    private void updateFishMovement() {
        if (!isOver) {
            float moveAmount = 50;
            float direction = MathUtils.random(-1, 1);
            fishTargetX += direction * moveAmount;
            fishTargetX = MathUtils.clamp(fishTargetX, gameAreaX, gameAreaX + gameAreaWidth - fishTexture.getWidth());
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float gameAreaHeight = 60;
        batch.draw(fishTexture, fishX, gameAreaY + (gameAreaHeight - fishTexture.getHeight()) / 2);

        if (isPerfectCatch) {
            font.draw(batch, "PERFECT!", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() - 30);
        }
        font.draw(batch, "Fish: " + fish.getName(), 20, Gdx.graphics.getHeight() - 20);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        shapeRenderer.rect(gameAreaX, gameAreaY, gameAreaWidth, gameAreaHeight);

        shapeRenderer.setColor(0, 1, 0, 0.7f);
        shapeRenderer.rect(playerBarX, gameAreaY, playerBarWidth, gameAreaHeight);

        shapeRenderer.setColor(Color.DARK_GRAY);
        float progressBarX = gameAreaX;
        float progressBarY = gameAreaY + gameAreaHeight + 20;
        float progressBarHeight = 30;
        shapeRenderer.rect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);

        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(progressBarX, progressBarY, progressBarWidth * catchingProgress, progressBarHeight);

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act();
        stage.draw();
    }

    private void winGame() {
        animalController.fishing(fish, isPerfectCatch);
        showDialog("You caught some fish!");
        isOver = true;
    }

    private void loseGame() {
        showDialog("it got away!");
        isOver = true;
    }

    private void showDialog(String message) {
        Skin skin = GameAssetManager.getDefaultSkin();
        Dialog dialog = new Dialog("Fishing", skin);
        dialog.text(message);
        dialog.show(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        fishTexture.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
