package core.GraphicView;

import com.StardewValley.Main;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TradeMenuUI implements Screen {
    public enum MenuState {
        MAIN,
        REQUEST,
        OFFER,
    }

    private SpriteBatch batch;
    private Stage stage;
    private Main game ;
    private GameMenuUI gameMenuUI;
    private MenuState currentState;

    private Texture background;

    private TradeInventory inventory;
    private static final int BUTTON_SIZE = 100;
    private static final int START_X = 480;
    private static final int START_Y = 810;

    public TradeMenuUI(Main game, GameMenuUI gameMenuUI) {
        this.game = game;
        this.gameMenuUI = gameMenuUI;
        this.currentState = MenuState.MAIN;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());

        background = new Texture("background/trade.png");
    }

    @Override
    public void render(float v) {

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




}
