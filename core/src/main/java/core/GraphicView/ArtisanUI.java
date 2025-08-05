// ArtisanUI.java
package core.GraphicView;

import core.Controller.InGameMenu.ArtisanController;
import core.GraphicView.Game.GameView;
import core.Model.GameAssetManager;
import core.Model.ItemInterface;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class ArtisanUI implements Screen {
    private Stage stage;
    private Table beeHouseTable, cheesePressTable, kegTable;
    private Label beeHouseLabel, cheesePressLabel, kegLabel;
    private Label selectedItemLabel;
    private Image selectedItemTexture = new Image();
    private final ArrayList<Texture> items = new ArrayList<>();
    private ArtisanController controller;
    private ItemInterface selectedItem;
    private TextButton Craft , Back , QuickCraft , GetItem , Cancel;
    private Label craftState;
    private ProgressBar progressBar;

    public ArtisanUI() {
        this.controller = new ArtisanController();
        this.controller.setArtisanUI(this);
        initTables();
    }

    public void setController(ArtisanController controller) {
        this.controller = controller;
    }

    private void initTables() {
        beeHouseTable = createMachineTable();
        cheesePressTable = createMachineTable();
        kegTable = createMachineTable();

        beeHouseLabel = new Label("", GameAssetManager.getDefaultSkin());
        cheesePressLabel = new Label("", GameAssetManager.getDefaultSkin());
        kegLabel = new Label("", GameAssetManager.getDefaultSkin());

        beeHouseTable.add(beeHouseLabel).colspan(5).center().padBottom(20).row();
        cheesePressTable.add(cheesePressLabel).colspan(5).center().padBottom(20).row();
        kegTable.add(kegLabel).colspan(5).center().padBottom(20).row();

        selectedItemLabel = new Label("", GameAssetManager.getDefaultSkin());
        Craft = new TextButton("Craft", GameAssetManager.getDefaultSkin());
        craftState = new Label("machine is in rest mode", GameAssetManager.getDefaultSkin());
        craftState.setColor(Color.GREEN);

        Back = new TextButton("Back", GameAssetManager.getDefaultSkin());
        QuickCraft = new TextButton("Quick Craft", GameAssetManager.getDefaultSkin());
        GetItem = new TextButton("Get Item", GameAssetManager.getDefaultSkin());
        Cancel = new TextButton("Cancel", GameAssetManager.getDefaultSkin());

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle();
        barStyle.background = GameAssetManager.getDefaultSkin().newDrawable("white", Color.DARK_GRAY);
        barStyle.knob = GameAssetManager.getDefaultSkin().newDrawable("white", Color.CLEAR); // optional knob
        barStyle.knobBefore = GameAssetManager.getDefaultSkin().newDrawable("white", Color.GREEN);

        progressBar = new ProgressBar(0, 1, 0.01f, false, barStyle);
        progressBar.setAnimateDuration(0.25f);
        progressBar.setValue(0);
        progressBar.setVisible(false); // hide by default
        progressBar.setWidth(200); // optional


        beeHouseTable.setVisible(false);
        cheesePressTable.setVisible(false);
        kegTable.setVisible(true); // Default
    }

    private Table createMachineTable() {
        Table table = new Table(GameAssetManager.getDefaultSkin());
        table.setSize(800, 700);
        table.setTransform(true);
        table.setOrigin(Align.center);
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - table.getHeight()) / 2f);
        table.pad(30);
        table.defaults().space(10);
        table.setBackground("window");
        return table;
    }

    private void populateTable(Table table, List<Stack> stacks) {
        table.clearChildren();

        // Selected item preview
        table.add(selectedItemTexture).colspan(5).center().padBottom(20).row();
        table.add(selectedItemLabel).colspan(5).center().padBottom(20).row();

        // Recipe grid (5 items per row)
        int colCount = 0;
        for (Stack s : stacks) {
            table.add(s).size(64);
            if (++colCount % 5 == 0) table.row();
        }
        table.row().padTop(10);

        Table buttonRow = new Table();
        buttonRow.add(Craft).padRight(10);
        buttonRow.add(QuickCraft).padRight(10);
        buttonRow.add(GetItem);
        buttonRow.add(Cancel).padRight(10);
        table.add(buttonRow).colspan(5).center().padBottom(20).row();

        table.add(craftState).colspan(5).center().padBottom(10).row();
        table.add(progressBar).colspan(5).center().padBottom(20).row();

        table.add(Back).colspan(5).center().padBottom(20).row();
    }


    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        controller.setArtisanUI(this);
        List<Stack> recipeStacks = controller.getRecipes();

        if (kegTable.isVisible()) populateTable(kegTable, recipeStacks);
        else if (cheesePressTable.isVisible()) populateTable(cheesePressTable, recipeStacks);
        else if (beeHouseTable.isVisible()) populateTable(beeHouseTable, recipeStacks);

        stage.addActor(kegTable);
        stage.addActor(cheesePressTable);
        stage.addActor(beeHouseTable);
    }

    @Override public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        try {
            controller.renderButtons();
            controller.renderLabel();
            controller.renderTimer(delta);
        }catch (Exception e){}
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public Table getBeeHouseTable() { return beeHouseTable; }
    public Table getCheesePressTable() { return cheesePressTable; }
    public Table getKegTable() { return kegTable; }
    public Stage getStage() { return stage; }
    public ArtisanController getController() { return controller; }
    public ArrayList<Texture> getItems() { return items; }
    public Label getSelectedItemLabel() { return selectedItemLabel; }
    public void setSelectedItemLabel(Label label) { this.selectedItemLabel = label; }
    public Image getSelectedItemTexture() { return selectedItemTexture; }
    public void setSelectedItemTexture(Image texture) { this.selectedItemTexture = texture; }
    public ItemInterface getSelectedItem() { return selectedItem; }
    public void setSelectedItem(ItemInterface item) { this.selectedItem = item; }

    public Label getBeeHouseLabel() {
        return beeHouseLabel;
    }

    public void setBeeHouseLabel(Label beeHouseLabel) {
        this.beeHouseLabel = beeHouseLabel;
    }

    public void setBeeHouseTable(Table beeHouseTable) {
        this.beeHouseTable = beeHouseTable;
    }

    public Label getCheesePressLabel() {
        return cheesePressLabel;
    }

    public void setCheesePressLabel(Label cheesePressLabel) {
        this.cheesePressLabel = cheesePressLabel;
    }

    public void setCheesePressTable(Table cheesePressTable) {
        this.cheesePressTable = cheesePressTable;
    }

    public Label getKegLabel() {
        return kegLabel;
    }

    public void setKegLabel(Label kegLabel) {
        this.kegLabel = kegLabel;
    }

    public void setKegTable(Table kegTable) {
        this.kegTable = kegTable;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    // Inside ArtisanUI
    public void refreshSelectedItemDisplay(Image glow) {
        Table activeTable = getActiveTable();
        if (activeTable == null) return;
        activeTable.clearChildren();

        activeTable.add(selectedItemTexture).colspan(5).center().padBottom(20).row();
        activeTable.add(selectedItemLabel).colspan(5).center().padBottom(20).row();

        List<Stack> recipeStacks = controller.getRecipes();
        int colCount = 0;
        for (Stack s : recipeStacks) {
            activeTable.add(s).size(64);
            if (++colCount % 5 == 0) activeTable.row();
        }
        glow.setVisible(true);
    }

    private Table getActiveTable() {
        if(kegTable.isVisible()) return kegTable;
        if(cheesePressTable.isVisible()) return cheesePressTable;
        if(beeHouseTable.isVisible()) return beeHouseTable;
        return null;
    }

    public TextButton getCraft() {
        return Craft;
    }

    public void setCraft(TextButton craft) {
        Craft = craft;
    }

    public Label getCraftState() {
        return craftState;
    }

    public void setCraftState(Label craftState) {
        this.craftState = craftState;
    }

    public Button getBack() {
        return Back;
    }
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setBack(TextButton back) {
        Back = back;
    }

    public TextButton getGetItem() {
        return GetItem;
    }

    public void setGetItem(TextButton getItem) {
        GetItem = getItem;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextButton getQuickCraft() {
        return QuickCraft;
    }

    public void setQuickCraft(TextButton quickCraft) {
        QuickCraft = quickCraft;
    }

    public TextButton getCancel() {
        return Cancel;
    }

}

