package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static Skin defaultSkin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));

    public static Skin getDefaultSkin() {
        return defaultSkin;
    }

    public static Texture dirt = new Texture(Gdx.files.internal("flooring/dirt.png"));
    public static Texture grass = new Texture(Gdx.files.internal("flooring/grass.png"));
    public static Texture pathway = new Texture(Gdx.files.internal("flooring/pathway.png"));
    public static Texture water = new Texture(Gdx.files.internal("flooring/water.png"));
    public static Texture rock = new Texture(Gdx.files.internal("rock/boulder.png"));
    public static Texture cabin = new Texture(Gdx.files.internal("buildings/cabin.png"));
    public static Texture greenhouse = new Texture(Gdx.files.internal("buildings/greenhouse.png"));

    public static void setDefaultSkin(Skin defaultSkin) {
        GameAssetManager.defaultSkin = defaultSkin;
    }
}
