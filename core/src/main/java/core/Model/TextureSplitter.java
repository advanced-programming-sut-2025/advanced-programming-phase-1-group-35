package core.Model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;

public class TextureSplitter {
    private TextureRegion[][] regions;

    public TextureSplitter(String imagePath, int rows, int cols) {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        int tileWidth = texture.getWidth() / cols;
        int tileHeight = texture.getHeight() / rows;
        regions = TextureRegion.split(texture, tileWidth, tileHeight);
    }

    public TextureRegion getRegion(int row, int col) {
        return regions[row][col];
    }

    public TextureRegion[] getRegionsAs1DArray() {
        TextureRegion[] flatRegions = new TextureRegion[regions.length * regions[0].length];
        int index = 0;
        for (int row = 0; row < regions.length; row++) {
            for (int col = 0; col < regions[row].length; col++) {
                flatRegions[index++] = regions[row][col];
            }
        }
        return flatRegions;
    }

    public void dispose() {
        // Remember to dispose the original texture when done
        regions[0][0].getTexture().dispose();
    }
}
