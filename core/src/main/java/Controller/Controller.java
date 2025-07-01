package Controller;

import Model.GameAssetManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Controller {
    protected Stage stage;

    public void showErrorDialog(String title, String message) {
        Skin skin = GameAssetManager.getDefaultSkin();
        Dialog dialog = new Dialog(title, skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
