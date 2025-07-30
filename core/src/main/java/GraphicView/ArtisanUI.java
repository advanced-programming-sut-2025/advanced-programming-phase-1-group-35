package GraphicView;

import Controller.InGameMenu.ArtisanController;
import Model.Item;
import Model.enums.machines.ArtisanProductDetails;
import Model.machines.BeeHouse;
import Model.machines.Cheese_Press;
import Model.machines.Keg;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class ArtisanUI implements Screen {
    private Stage stage;
    private Table BeeHouseTable;
    private Table CheesePressTable;
    private Table KegTable;
    private Label label;
    private ArrayList<Texture> Items;
    private ArtisanController controller;
    private Keg keg = new Keg(controller);
    private BeeHouse beeHouse = new BeeHouse(controller);
    private Cheese_Press cheesePress = new Cheese_Press(controller);
    public ArtisanUI() {
        BeeHouseTable = new Table();
        CheesePressTable = new Table();
        KegTable = new Table();
        BeeHouseTable.setFillParent(true);
        CheesePressTable.setFillParent(true);
        KegTable.setFillParent(true);
        BeeHouseTable.add(label);
        CheesePressTable.add(label);
        KegTable.add(label);
        Items = new ArrayList<>();
        this.controller = new ArtisanController(this);
        BeeHouseTable.setVisible(false);
        CheesePressTable.setVisible(false);
        KegTable.setVisible(false);
    }

    public Table getBeeHouseTable() {
        return BeeHouseTable;
    }

    public Table getCheesePressTable() {
        return CheesePressTable;
    }

    public ArtisanController getController() {
        return controller;
    }

    public ArrayList<Texture> getItems() {
        return Items;
    }

    public Table getKegTable() {
        return KegTable;
    }

    public Label getLabel() {
        return label;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        KegTable = new Table(KegTable.getSkin());
        KegTable.setSize(800, 700);
        KegTable.setTransform(true);
        KegTable.setOrigin(Align.center);
        KegTable.setPosition(
            (Gdx.graphics.getWidth() - KegTable.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - KegTable.getHeight()) / 2f
        );
        KegTable.pad(30);
        KegTable.defaults().space(10);
        KegTable.setBackground("window");

        label.setAlignment(Align.center);
        KegTable.add(label).colspan(5).center().padBottom(20).row();

        BeeHouseTable = new Table(BeeHouseTable.getSkin());
        BeeHouseTable.setSize(800, 700);
        BeeHouseTable.setTransform(true);
        BeeHouseTable.setOrigin(Align.center);
        BeeHouseTable.setPosition(
            (Gdx.graphics.getWidth() - BeeHouseTable.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - BeeHouseTable.getHeight()) / 2f
        );
        BeeHouseTable.pad(30);
        BeeHouseTable.defaults().space(10);
        BeeHouseTable.setBackground("window");

        label.setAlignment(Align.center);
        BeeHouseTable.add(label).colspan(5).center().padBottom(20).row();

        CheesePressTable = new Table(CheesePressTable.getSkin());
        CheesePressTable.setSize(800, 700);
        CheesePressTable.setTransform(true);
        CheesePressTable.setOrigin(Align.center);
        CheesePressTable.setPosition(
            (Gdx.graphics.getWidth() - CheesePressTable.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - CheesePressTable.getHeight()) / 2f
        );
        CheesePressTable.pad(30);
        CheesePressTable.defaults().space(10);
        CheesePressTable.setBackground("window");

        label.setAlignment(Align.center);
        CheesePressTable.add(label).colspan(5).center().padBottom(20).row();
        int colcount = 0;
        List<Stack> list = controller.getRecipes();
        if (getKegTable().isVisible()) {
            for (Stack s : list) {
                getKegTable().add(s).size(64);
                colcount++;
                if (colcount == 5) {
                    getKegTable().row();
                    colcount = 0;
                }
            }
        }
        if (getCheesePressTable().isVisible()) {
            for (Stack s : list) {
                getCheesePressTable().add(s).size(64);
                colcount++;
                if (colcount == 5) {
                    getCheesePressTable().row();
                    colcount = 0;
                }
            }
        }
        if (getBeeHouseTable().isVisible()) {
            for (Stack s : list) {
                getCheesePressTable().add(s).size(64);
                colcount++;
                if (colcount == 5) {
                    getBeeHouseTable().row();
                    colcount = 0;
                }
            }
        }
    }
    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
