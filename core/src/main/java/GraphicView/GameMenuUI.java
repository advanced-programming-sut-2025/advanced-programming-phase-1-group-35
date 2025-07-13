package GraphicView;

import Controller.GameMenuController;
import Controller.InGameMenu.ShopMenuController;
import GraphicView.Game.GameMenuInputAdapter;
import GraphicView.Game.GameView;
import Model.Game;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import java.io.IOException;


public class GameMenuUI implements Screen {
    private GameView gameView;
    public Game gameModel;
    public GameMenuInputAdapter gameMenuInputAdapter;
    public GameMenuController gameController;
    private boolean isSleeping = false;
    private float sleepAlpha = 0f;
    private float sleepTimer = 0f;
    private static final float SLEEP_DURATION = 2f; // seconds
    private static final float FADE_SPEED = 1.5f;   // speed of fading
    private boolean advancingDay = false;


    public GameMenuUI(GameMenuController gameController, Game gameModel) {
        this.gameController = gameController;
        this.gameModel = gameModel;
        initializeGame();
    }

    private void initializeGame() {
        gameView = new GameView(gameModel);
        gameMenuInputAdapter = new GameMenuInputAdapter(gameModel, gameController);
        Gdx.input.setInputProcessor(gameMenuInputAdapter);
        gameMenuInputAdapter.gameMenuUI = this;
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

            // Render black overlay
            gameView.getBatch().begin();
            gameView.getBatch().setColor(0f, 0f, 0f, sleepAlpha);
            gameView.getBatch().draw(gameView.getPixel(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            gameView.getBatch().setColor(1f, 1f, 1f, 1f);
            gameView.getBatch().end();
        }
    }

    public void startSleepTransition() {
        isSleeping = true;
        sleepAlpha = 0f;
        sleepTimer = 0f;
        advancingDay = false;
    }


    @Override
    public void resize(int i, int i1) {

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

    }

    public void goToShopMenu() {
        ShopMenuController shopMenuController = new ShopMenuController(gameModel.getMap().getTiles()[105][94]);
        ShopMenuUI shopMenuUI = new ShopMenuUI(shopMenuController, this);
        Main.getGame().setScreen(shopMenuUI);
    }

    // Other Screen methods
}
