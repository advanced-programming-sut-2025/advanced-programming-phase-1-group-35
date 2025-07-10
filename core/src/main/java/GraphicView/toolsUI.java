package GraphicView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer; // For combining stage input with custom input
import com.badlogic.gdx.Screen; // Implementing Screen interface
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // For drawing the texture
import com.badlogic.gdx.scenes.scene2d.Stage; // A minimal Stage is still useful for input handling
import com.badlogic.gdx.utils.viewport.ScreenViewport; // For the Stage

import com.StardewValley.Main;

public class toolsUI implements Screen {

    private SpriteBatch batch;
    private Texture toggledPictureTexture;
    private boolean isPictureVisible = false;

    private Stage stage;
    private Main game;

    public toolsUI(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport()); // Initialize stage
        toggledPictureTexture = new Texture(Gdx.files.internal("assets/toolsShelf.png"));
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.T) {
                    isPictureVisible = !isPictureVisible; // Toggle visibility state
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

        // Only draw the picture if it's visible
        if (isPictureVisible) {
            batch.begin();
            // Draw the picture centered on the screen
            float x = (Gdx.graphics.getWidth() - toggledPictureTexture.getWidth()) / 2f;
            float y = 65;
            batch.draw(toggledPictureTexture, x, y);
            batch.end();
        }

        // Draw the stage (if any actors were added, they would be drawn here)
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update the stage's viewport to handle resizing
        stage.getViewport().update(width, height, true);
        // If the picture needs to be repositioned on resize, calculate here (already done in render)
    }

    @Override
    public void pause() {
        // Called when the game is paused (e.g., when minimized on mobile)
    }

    @Override
    public void resume() {
        // Called when the game is resumed from a paused state
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen
        // Dispose resources that are unique to this screen
        dispose(); // Call the dispose method to clean up
    }

    @Override
    public void dispose() {
        // Called when the screen is destroyed
        batch.dispose();
        toggledPictureTexture.dispose();
        stage.dispose(); // Dispose the stage
        Gdx.app.log("ToolsUI", "Disposed.");
    }
}
