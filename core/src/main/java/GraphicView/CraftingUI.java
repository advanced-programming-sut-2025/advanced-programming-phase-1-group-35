package GraphicView;

import Controller.InGameMenu.CraftingController;
import Model.User;
import Model.enums.CraftingRecipes;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class CraftingUI implements Screen {
private Stage stage;
private Table table;
private Label title;
private ArrayList<CraftingRecipes> knownRecipes = new ArrayList<CraftingRecipes>();
private CraftingController controller;
private Image selectedRecipe;
private Label selectedRecipeLabel;
private TextButton Back;
private TextButton Craft;


public CraftingUI(Skin skin, User user, CraftingController controller) {
    this.knownRecipes = user.getCraftingRecipes();
    this.stage = new Stage();
    this.table = new Table(skin);
    this.title = new Label("what you craftin?",skin);
    this.Back = new TextButton("Back", skin);
    this.Craft = new TextButton("Craft", skin);
    this.selectedRecipe = new Image();
    this.selectedRecipeLabel = new Label("", skin);
    this.controller = controller;
    controller.setCraftingUI(this);
}

    public Label getSelectedRecipeLabel() {
        return selectedRecipeLabel;
    }

    public void setSelectedRecipeLabel(Label selectedRecipeLabel) {
        this.selectedRecipeLabel = selectedRecipeLabel;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        table.pad(20);


        title.setAlignment(Align.center);
        table.add(title).padBottom(20).colspan(5).center().row();


        selectedRecipe.setSize(64, 64);
        table.add(selectedRecipe).colspan(5).padBottom(30).center().row();
        table.add(selectedRecipeLabel).colspan(5).padBottom(30).center().row();

        int colCount = 0;
        for (ImageButton ib : controller.showRecipes()) {
            table.add(ib).pad(10).size(64);
            colCount++;
            if (colCount == 5) {
                table.row();
                colCount = 0;
            }
        }

        TextButton craftButton = new TextButton("Craft", table.getSkin());
        controller.getCraftingUI().setCraft(craftButton); // Store if needed
        table.row().padTop(30);
        table.add(craftButton).colspan(5).center().pad(10).height(40).width(120).row();
        table.add(Back).colspan(5).center().pad(10).height(40).width(120);
        table.setBackground("window");

        stage.addActor(table);
    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.handleButtons();
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

    public TextButton getBack() {
        return Back;
    }

    public CraftingController getController() {
        return controller;
    }

    public TextButton getCraft() {
        return Craft;
    }

    public ArrayList<CraftingRecipes> getKnownRecipes() {
        return knownRecipes;
    }

    public Image getSelectedRecipe() {
        return selectedRecipe;
    }

    public Stage getStage() {
        return stage;
    }

    public Table getTable() {
        return table;
    }

    public Label getTitle() {
        return title;
    }

    public void setCraft(TextButton craft) {
        Craft = craft;
    }
}
