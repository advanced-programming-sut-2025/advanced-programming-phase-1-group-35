package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static Skin defaultSkin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));

    public static Skin getDefaultSkin() {
        return defaultSkin;
    }

    public static void setDefaultSkin(Skin defaultSkin) {
        GameAssetManager.defaultSkin = defaultSkin;
    }
}
