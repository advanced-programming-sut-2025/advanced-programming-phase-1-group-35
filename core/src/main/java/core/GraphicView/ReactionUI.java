package core.GraphicView;

import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.Model.GameAssetManager;

public class ReactionUI implements Screen {
    private Stage stage;
    private GameMenuUI gameMenuUI;
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Table emojiTable;
    private boolean isFirstPage = true;
    private TextField messageField;

    public ReactionUI(GameMenuUI gameMenuUI) {
        this.gameMenuUI = gameMenuUI;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table mainLayout = new Table();
        mainLayout.setFillParent(true);
        mainLayout.center();

        this.emojiTable = new Table();
        this.backgroundTexture = new Texture(Gdx.files.internal("assets/emoji/background.jpg"));

        loadEmojiPage();

        TextButton changePageButton = new TextButton("Next Page", GameAssetManager.getDefaultSkin());
        changePageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isFirstPage = !isFirstPage;
                loadEmojiPage();
            }
        });

        messageField = new TextField("", GameAssetManager.getDefaultSkin());
        messageField.setMessageText("Type a message...");
        TextButton sendButton = new TextButton("Send", GameAssetManager.getDefaultSkin());
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String message = messageField.getText();
                if (message != null && !message.trim().isEmpty()) {
                    gameMenuUI.showReactionForPlayer(message);
                    Main.getGame().setScreen(gameMenuUI);
                }
            }
        });

        Table textInputTable = new Table();
        textInputTable.add(messageField).width(300).padRight(10);
        textInputTable.add(sendButton);

        mainLayout.add(emojiTable).row();
        mainLayout.add(changePageButton).padTop(20).row();
        mainLayout.add(textInputTable).padTop(20);

        stage.addActor(mainLayout);
    }

    private void loadEmojiPage() {
        emojiTable.clearChildren();

        int start = isFirstPage ? 1 : 11;
        int end = isFirstPage ? 10 : 20;
        final int EMOJIS_PER_ROW = 5;

        for (int i = start; i <= end; i++) {
            String texturePath = "assets/emoji/" + i + ".png";
            Texture emojiTexture = new Texture(Gdx.files.internal(texturePath));
            addEmojiButton(emojiTable, emojiTexture);

            if (i % EMOJIS_PER_ROW == 0 && i != end) {
                emojiTable.row();
            }
        }
    }

    private void addEmojiButton(Table table, final Texture emojiTexture) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(emojiTexture));
        ImageButton button = new ImageButton(style);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuUI.showReactionForPlayer(emojiTexture);
                Main.getGame().setScreen(gameMenuUI);
            }
        });
        table.add(button).pad(10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(0.2f, 0.2f, 0.2f, 1f);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);
        batch.end();

        stage.act(delta);
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
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        backgroundTexture.dispose();
    }
}
