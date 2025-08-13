package core.GraphicView;

import core.Model.*;
import core.Controller.GameMenuController;
import core.Controller.InGameMenu.AnimalController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.Model.*;
import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Seed;
import core.Model.enums.Crops.CropEnum;
import core.Model.enums.Crops.SeedEnum;

import java.io.IOException;

import java.util.Objects;

public class CheatUI implements Screen {

    private Stage stage;
    private Skin skin;
    private TextArea commandOutput;
    private TextField commandInput;
    private TextButton closeButton;
    private GameMenuController gameController;
    private GameMenuUI gameMenuUI;
    private AnimalController animalController;

    public CheatUI(GameMenuController gameController, GameMenuUI gameMenuUI) {
        this.gameController = gameController;
        this.gameMenuUI = gameMenuUI;
        this.animalController = new AnimalController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = GameAssetManager.getDefaultSkin();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        commandOutput = new TextArea("", skin);
        commandOutput.setDisabled(true);
        ScrollPane scrollPane = new ScrollPane(commandOutput, skin);
        scrollPane.setFadeScrollBars(false);

        commandInput = new TextField("", skin);
        commandInput.setMessageText("Enter cheat command...");

        closeButton = new TextButton("X", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuUI.toggleCheatMenu();
            }
        });

        Table titleTable = new Table();
        titleTable.add(new Label("Cheat Terminal", skin)).expandX().center();
        titleTable.add(closeButton).top().right();

        table.add(titleTable).width(600).row();
        table.add(scrollPane).width(600).height(400).pad(10).colspan(2).row();
        table.add(commandInput).width(600).pad(10).colspan(2);

        stage.addActor(table);

        commandInput.setTextFieldListener((textField, c) -> {
            if (c == '\n' || c == '\r') {
                String command = commandInput.getText();
                commandInput.setText("");
                executeCommand(command);
            }
        });
    }

    private void executeCommand(String command) {
        commandOutput.appendText("> " + command + "\n");
        String[] commandParts = command.split("\\s+");
        Result result = null;
        if (commandParts[0].equals("cheat") && commandParts[1].equals("set") && commandParts[2].equals("friendship")) {
            result = animalController.cheatFriendshipAnimal(commandParts[3], Integer.parseInt(commandParts[4]));
        } else if (commandParts[0].equals("cheat") && commandParts[1].equals("set" ) && commandParts[2].equals("energy")) {
            result = new GameMenuController().cheatEnergySet(commandParts[3]);
        } else if (commandParts[0].equals("cheat") && commandParts[1].equals("thor")) {
            result = Weather.hitTileWithThunder(Objects.requireNonNull(Map.getTileWithCoordination(commandParts[2], commandParts[3])));
        }
        else if(command.equals("go to next day")){
            try {
            result = App.getCurrentGame().getGameCalender().goToNextDay();
            } catch (IOException e){
                e.printStackTrace();
            };
        }
        else if(command.equals("giant")){
            result = spawnGiantCrop();
        }
        else if(command.equals("mixed")){
            result = plantMixedSeed();
        }
        else if(command.equals("fertilize")){
            App.getCurrentGame().getPlayingUser().getCurrentTile().setFertilized(true);
            result = new Result(true, "fertilized");
        }
        else if(command.equals("water")){
            App.getCurrentGame().getPlayingUser().getCurrentTile().setWatered(true);
            result = new Result(true, "watered");
        }
        else if(command.contains("info")){
            String cropName = command.substring(command.indexOf(" ") + 1);
            result = new Result(true, gameController.showCropInfo(cropName));
        }
        if (result == null) {
            commandOutput.setText("invalid command!\n");
            return;
        }
        commandOutput.appendText(result.toString() + "\n");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
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
    }

    private Result spawnGiantCrop(){
        App.getCurrentGame().getPlayingUser().getBackPack().items.put(SeedEnum.POWDERMELON,1);
        Tile[][] map = App.getCurrentGame().getMap().getTiles();

        App.getCurrentGame().getMap().getCrops().add(new Crop(CropEnum.POWDERMELON,
            map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x]
                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y]));

        App.getCurrentGame().getMap().getCrops().add(new Crop(CropEnum.POWDERMELON,
            map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x+1]
                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y]));

        App.getCurrentGame().getMap().getCrops().add(new Crop(CropEnum.POWDERMELON,
            map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x+1]
                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y+1]));

        map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x]
            [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y].setPlanted(new Crop(CropEnum.POWDERMELON,
            map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x]
                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y]));

        map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x+1]
            [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y].setPlanted(new Crop(CropEnum.POWDERMELON,
            map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x+1]
                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y]));

        map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x+1]
            [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y+1].setPlanted(new Crop(CropEnum.POWDERMELON,
            map[App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x+1]
                [App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y+1]));
        return gameController.plantSeed("POWDERMELON","up");
    }
    private Result plantMixedSeed(){
        return gameController.plantSeed("mixed seed","here");
    }
}
