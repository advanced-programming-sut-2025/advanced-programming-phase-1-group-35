package core.GraphicView;

import core.Model.*;
import core.Controller.GameMenuController;
import core.Controller.InGameMenu.AnimalController;
import core.Controller.InGameMenu.ShopMenuController;
import core.Controller.InGameMenu.ToolsController;
import core.GraphicView.Game.GameMenuInputAdapter;
import core.GraphicView.Game.GameView;
import core.Model.*;
import core.Model.Tools.BackPack;
import core.Model.Tools.Tool;
import core.Model.animal.Animal;
import core.Model.animal.AnimalProduct;
import core.Model.enums.Buildings.AnimalHouseEnum;
import core.Model.enums.ToolTypes;
import core.Model.enums.animal.AnimalType;
import core.Controller.GameMenuController;
import core.Controller.InGameMenu.AnimalController;
import core.Controller.InGameMenu.ArtisanController;
import core.Controller.InGameMenu.ShopMenuController;
import core.Controller.InGameMenu.ToolsController;
import core.GraphicView.Game.GameMenuInputAdapter;
import core.GraphicView.Game.GameView;
import core.Model.*;
import core.Model.Tools.BackPack;
import core.Model.Tools.Tool;
import core.Model.animal.Animal;
import core.Model.animal.AnimalProduct;
import core.Model.enums.Buildings.AnimalHouseEnum;
import core.Model.enums.ToolTypes;
import core.Model.enums.animal.AnimalType;
import core.Model.machines.Keg;
import core.Model.machines.Machine;
import com.StardewValley.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.Controller.GameMenuController;
import core.Controller.InGameMenu.AnimalController;
import core.Controller.InGameMenu.ArtisanController;
import core.Controller.InGameMenu.ShopMenuController;
import core.Controller.InGameMenu.ToolsController;
import core.GraphicView.Game.GameMenuInputAdapter;
import core.GraphicView.Game.GameView;
import core.Model.*;
import core.Model.Tools.BackPack;
import core.Model.Tools.Tool;
import core.Model.animal.Animal;
import core.Model.animal.AnimalProduct;
import core.Model.enums.Buildings.AnimalHouseEnum;
import core.Model.enums.ToolTypes;
import core.Model.enums.animal.AnimalType;
import core.Model.machines.Keg;
import core.Model.machines.Machine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameMenuUI implements Screen {
    private GameView gameView;
    public Game gameModel;
    public GameMenuInputAdapter gameMenuInputAdapter;
    public GameMenuController gameController;
    private final AnimalController animalController;
    private boolean isSleeping = false;
    private float sleepAlpha = 0f;
    private float sleepTimer = 0f;
    private static final float SLEEP_DURATION = 2f;
    private static final float FADE_SPEED = 1.5f;
    private boolean advancingDay = false;
    private boolean isInInventory = false;
    private boolean isCook = false;
    private boolean isCheating = false;
    private boolean isMiniGaming = false;

    private SpriteBatch toolsBatch;
    private ShapeRenderer toolsShapeRenderer;
    private Texture toolsToggledPictureTexture;
    private final Texture axeTexture = new Texture(Gdx.files.internal("assets/tools/axe.png"));
    private final Texture wateringCanTexture = new Texture(Gdx.files.internal("assets/tools/watering_can.png"));
    private final Texture fishingRodTexture = new Texture(Gdx.files.internal("assets/tools/fishing_rod.png"));
    private final Texture scytheTexture = new Texture(Gdx.files.internal("assets/tools/scythe.png"));
    private final Texture hoeTexture = new Texture(Gdx.files.internal("assets/tools/hoe.png"));
    private final Texture milkPailTexture = new Texture(Gdx.files.internal("assets/tools/milk_pail.png"));
    private final Texture pickaxeTexture = new Texture(Gdx.files.internal("assets/tools/pickaxe.png"));
    private final Texture shearsTexture = new Texture(Gdx.files.internal("assets/tools/shears.png"));
    private Map<String, Rect> toolRects;
    private String equippedToolName = "hoe";
    private Stage stage;
    private Stage boxStage;
    private final ToolsController toolsController = new ToolsController();
    private boolean isToolsUIVisible = false;
    private static final int TOOLS_Y_OFFSET = 65;

    private InputMultiplexer mainMultiplexer;
    private InputAdapter hotkeyAdapter;

    public boolean buildingPlacementMode = false;
    private String buildingToPlace = null;
    private Texture barnTexture;
    private Texture coopTexture;

    private Map<Animal, Rect> animalRects = new HashMap<>();
    private Map<Animal, Float> pettedAnimals = new HashMap<>();
    private Texture heartTexture;
    private CheatUI cheatUI;

    private Animal animalToMove = null;
    private Point moveTarget = null;
    private float moveTimer = 0f;
    private static final float TILE_MOVE_SPEED = 0.2f;
    private Texture lightningTexture;

    public static boolean Crows = false;
    private float animationTime = 0f;
    private final float crowDuration = 6f;
    private final float fadeDuration = 1f;

    private ArtisanUI artisanUI;
    private ReactionUI reactionUI;

    private static class ChatMessage {
        String text;
        float timer;
        Label label;

        ChatMessage(String text, float timer, Label label) {
            this.text = text;
            this.timer = timer;
            this.label = label;
        }
    }
    private Array<ChatMessage> chatMessages = new Array<>();
    private Table chatTable;


    public ArtisanUI getArtisanUI() {
        return artisanUI;
    }

    public GameMenuUI(GameMenuController gameController, Game gameModel) {
        this.gameController = gameController;
        this.gameModel = gameModel;
        this.animalController = new AnimalController();
        initializeGame();
    }

    private void initializeGame() {
        gameView = new GameView(gameModel);
        gameMenuInputAdapter = new GameMenuInputAdapter(gameModel, gameController, this);
        gameMenuInputAdapter.gameMenuUI = this;

        toolsBatch = new SpriteBatch();
        toolsShapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());
        boxStage = new Stage(new ScreenViewport());
        toolsToggledPictureTexture = new Texture(Gdx.files.internal("assets/shelf.png"));
        toolRects = new HashMap<>();

        barnTexture = new Texture(Gdx.files.internal("assets/buildings/Barn.png"));
        coopTexture = new Texture(Gdx.files.internal("assets/buildings/Coop.png"));
        heartTexture = new Texture(Gdx.files.internal("assets/heart.png"));
        lightningTexture = new Texture(Gdx.files.internal("assets/light.png"));

        reactionUI = new ReactionUI(this);

        Table chatContainer = new Table();
        chatContainer.setFillParent(true);
        chatContainer.right();
        chatContainer.pad(10);
        chatTable = new Table();
        chatTable.setBackground(GameAssetManager.getDefaultSkin().getDrawable("window"));
        chatContainer.add(chatTable).width(400).height(600);
        boxStage.addActor(chatContainer);

        mainMultiplexer = new InputMultiplexer();

        hotkeyAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (buildingPlacementMode) return false;

                switch (keycode) {
                    case Input.Keys.G:
                        gameController.showNotification("notification test!");
                        return true;
                    case Input.Keys.R:
                        Main.getGame().setScreen(reactionUI);
                        return true;
                    case Input.Keys.O:
                        toggleArtisanUI(new Keg(new ArtisanController()));
                        return true;
                    case Input.Keys.T:
                        toggleToolsUI();
                        return true;
                    case Input.Keys.C:
                        toggleCookMenu();
                        return true;
                    case Input.Keys.ESCAPE:
                        toggleInventoryMenu();
                        return true;
                    case Input.Keys.M:
                        showBuildingSelectionDialog();
                        return true;
                    case Input.Keys.Z:
                        toggleCheatMenu();
                        return true;
                    case Input.Keys.F:
                        if (!gameController.isCloseToSea()) {
                            showDialog("Fishing Result", "You are not near to a sea!");
                        } else if (!App.getCurrentGame().getPlayingUser().getCurrentTool().getToolType().equals(ToolTypes.FISHING_ROD)) {
                            showDialog("Fishing Result", "you are not equipped by a fishing pole!");
                        } else {
                            toggleMiniGameMenu();
                        }
                        return true;
                    case Input.Keys.B:
                        try {
                            gameModel.getGameCalender().cheatTime(1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    Vector3 worldCoordinates = gameModel.camera.unproject(new Vector3(screenX, screenY, 0));
                    for (Map.Entry<Animal, Rect> entry : animalRects.entrySet()) {
                        if (entry.getValue().contains(worldCoordinates.x, worldCoordinates.y)) {
                            showAnimalInteractionDialog(entry.getKey());
                            return true;
                        }
                    }
                }
                return false;
            }
        };

        mainMultiplexer.addProcessor(stage);
        mainMultiplexer.addProcessor(boxStage);
        mainMultiplexer.addProcessor(hotkeyAdapter);
        mainMultiplexer.addProcessor(gameMenuInputAdapter);
        Gdx.input.setInputProcessor(mainMultiplexer);
    }

    public void showReactionForPlayer(Texture emojiTexture) {
        gameView.showReaction(emojiTexture);
    }

    public void showReactionForPlayer(Object reaction) {
        if (reaction instanceof Texture) {
            gameView.showReaction((Texture) reaction);
        } else if (reaction instanceof String) {
            addChatMessage((String) reaction);
        }
    }

    private void addChatMessage(String message) {
        String username = gameModel.getPlayingUser().getUsername();
        Label messageLabel = new Label(username + ": " + message, GameAssetManager.getDefaultSkin());
        messageLabel.setWrap(true);
        chatTable.add(messageLabel).width(280).align(Align.left).pad(5).row();
        chatMessages.add(new ChatMessage(message, 5f, messageLabel));
    }

    private void showBuildingSelectionDialog() {
        Skin skin = GameAssetManager.getDefaultSkin();
        Dialog dialog = new Dialog("Build Animal House", skin) {
            @Override
            protected void result(Object object) {
                if (object instanceof String) {
                    buildingToPlace = (String) object;
                    buildingPlacementMode = true;
                    showDialog("Placement Mode", "Click on the map to place the " + buildingToPlace + ".");
                }
            }
        };
        dialog.text("Which animal house would you like to build?");
        dialog.button("Barn", "Barn");
        dialog.button("Coop", "Coop");
        dialog.button("Cancel");
        dialog.show(stage);
    }

    private void showAnimalInteractionDialog(final Animal animal) {
        Skin skin = GameAssetManager.getDefaultSkin();
        new Dialog("Interact with " + animal.getName(), skin) {
            {
                text("What would you like to do?");
                button("Feed", "feed");
                button("Move", "move");
                button("Sell", "sell");
                button("Collect Product", "collect");
                button("Pet", "pet");
                button("Shepherd", "shepherd");
                button("Info", "info");
                button("Cancel", "cancel");
            }

            @Override
            protected void result(Object object) {
                switch (object.toString()) {
                    case "feed":
                        showDialog("Feed", animalController.feedByHay(animal.getName()).toString());
                        break;
                    case "move":
                        showMoveDialog(animal);
                        break;
                    case "sell":
                        showDialog("Sell", animalController.sellAnimal(animal.getName()).toString());
                        break;
                    case "collect":
                        showDialog("Collect", animalController.collectProducts(animal.getName()).toString());
                        break;
                    case "pet":
                        Result petResult = animalController.nazTheAnimal(animal.getName());
                        if(petResult.isSuccess()) {
                            pettedAnimals.put(animal, 5f);
                        }
                        showDialog("Pet", petResult.toString());
                        break;
                    case "shepherd":
                        showShepherdDialog(animal);
                        break;
                    case "info":
                        showAnimalInfoDialog(animal);
                        break;
                }
            }
        }.show(stage);
    }

    private void showMoveDialog(final Animal animal) {
        new Dialog("Move " + animal.getName(), GameAssetManager.getDefaultSkin()) {
            {
                text("Choose a direction to move 5 tiles:");
                button("Up", "up");
                button("Down", "down");
                button("Left", "left");
                button("Right", "right");
                button("Cancel", "cancel");
            }
            @Override
            protected void result(Object object) {
                if ("cancel".equals(object.toString())) return;
                int moveDistance = 5;
                Point currentLocation = animal.location;
                int targetX = currentLocation.x;
                int targetY = currentLocation.y;

                switch (object.toString()) {
                    case "up":
                        targetY += moveDistance;
                        break;
                    case "down":
                        targetY -= moveDistance;
                        break;
                    case "left":
                        targetX -= moveDistance;
                        break;
                    case "right":
                        targetX += moveDistance;
                        break;
                }
                animalToMove = animal;
                moveTarget = new Point(targetX, targetY);
            }
        }.show(stage);
    }

    private void showShepherdDialog(final Animal animal) {
        new Dialog("Shepherd " + animal.getName(), GameAssetManager.getDefaultSkin()) {
            {
                text("Choose a direction to shepherd 5 tiles:");
                button("Up", "up");
                button("Down", "down");
                button("Left", "left");
                button("Right", "right");
                button("Cancel", "cancel");
            }
            @Override
            protected void result(Object object) {
                int moveDistance = 5;
                Point currentLocation = animal.location;
                int newX = currentLocation.x;
                int newY = currentLocation.y;

                switch (object.toString()) {
                    case "up": newY += moveDistance; break;
                    case "down": newY -= moveDistance; break;
                    case "left": newX -= moveDistance; break;
                    case "right": newX += moveDistance; break;
                    default: return;
                }
                Result result = animalController.shepherdAnimal(animal.getName(), newX, newY);
                showDialog("Shepherd", result.toString());
            }
        }.show(stage);
    }

    private void showAnimalInfoDialog(final Animal animal) {
        AnimalType type = animal.getAnimalType();
        StringBuilder info = new StringBuilder();
        info.append("Type: ").append(type.name()).append("\n");
        info.append("Friendship: ").append(animal.getFriendship()).append("\n");
        info.append("Buying Price: ").append(type.getBuyingPrice()).append("g\n");
        info.append("House: ").append(type.getConfinement()).append("\n");
        info.append("Days between products: ").append(type.getProductionRate()).append("\n");
        info.append("Possible Products: \n");
        for(AnimalProduct product : type.getProducts()){
            info.append(" - ").append(product.getName()).append("\n");
        }
        showDialog(animal.getName() + " Info", info.toString());
    }

    public void handleBuildingPlacement(int screenX, int screenY) {
        Vector3 worldCoordinates = gameModel.camera.unproject(new Vector3(screenX, screenY, 0));
        int tileX = (int) (worldCoordinates.x / Main.TILE_SIZE);
        int tileY = (int) (worldCoordinates.y / Main.TILE_SIZE);
        Result result = animalController.buildAnimalHouse(buildingToPlace, tileX, tileY);
        showDialog(result.isSuccess() ? "Success" : "Error", result.toString());

        buildingPlacementMode = false;
        buildingToPlace = null;
    }

    private void renderBuildingPreview() {
        if (!buildingPlacementMode || buildingToPlace == null) return;

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();
        Vector3 worldCoordinates = gameModel.camera.unproject(new Vector3(mouseX, mouseY, 0));
        int tileX = (int) (worldCoordinates.x / Main.TILE_SIZE);
        int tileY = (int) (worldCoordinates.y / Main.TILE_SIZE);

        Texture previewTexture = null;
        int buildingWidthTiles = 0;
        int buildingHeightTiles = 0;

        if ("Barn".equals(buildingToPlace)) {
            previewTexture = barnTexture;
            buildingWidthTiles = AnimalHouseEnum.Barn.width;
            buildingHeightTiles = AnimalHouseEnum.Barn.height;
        } else if ("Coop".equals(buildingToPlace)) {
            previewTexture = coopTexture;
            buildingWidthTiles = AnimalHouseEnum.Coop.width;
            buildingHeightTiles = AnimalHouseEnum.Coop.height;
        }

        if (previewTexture != null) {
            gameView.getBatch().begin();
            gameView.getBatch().setColor(1f, 1f, 1f, 0.7f);
            gameView.getBatch().draw(previewTexture, tileX * Main.TILE_SIZE, tileY * Main.TILE_SIZE, buildingWidthTiles * Main.TILE_SIZE, buildingHeightTiles * Main.TILE_SIZE);
            gameView.getBatch().setColor(1f, 1f, 1f, 1f);
            gameView.getBatch().end();
        }
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(mainMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int i = chatMessages.size - 1; i >= 0; i--) {
            ChatMessage message = chatMessages.get(i);
            message.timer -= delta;
            if (message.timer <= 0) {
                message.label.remove();
                chatMessages.removeIndex(i);
            }
        }

        gameModel.update(delta);
        gameView.render(delta);
        updateAnimalMovement(delta);
        renderLightningEffects(delta);
        renderAnimals();
        renderPettedHearts(delta);
        gameMenuInputAdapter.update(delta);

        animationTime += delta;
        if (Crows) {
            animationTime += delta;

            if (animationTime >= crowDuration) {
                Crows = false;
                animationTime = 0;
            } else {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                TextureRegion currentFrame = gameView.getCrowAnimation().getKeyFrame(animationTime, true);

                float alpha = 1f;
                if (animationTime < fadeDuration) {
                    alpha = animationTime / fadeDuration;
                } else if (animationTime > crowDuration - fadeDuration) {
                    alpha = (crowDuration - animationTime) / fadeDuration;
                }

                gameView.getBatch().begin();
                gameView.getBatch().setColor(1f, 1f, 1f, alpha);
                gameView.getBatch().draw(
                    currentFrame,
                    Gdx.graphics.getWidth() / 4f,
                    Gdx.graphics.getHeight() / 4f,
                    Gdx.graphics.getWidth() / 2f,
                    Gdx.graphics.getHeight() / 2f
                );
                gameView.getBatch().setColor(1f, 1f, 1f, 1f);
                gameView.getBatch().end();
            }
        }

        if (isToolsUIVisible) {
            renderToolsUI();
        }

        renderBuildingPreview();

        if (isSleeping) {
            //... (sleep logic remains the same)
        }

        stage.act(delta);
        stage.draw();
        boxStage.act(delta);
        boxStage.draw();
    }

    private void updateAnimalMovement(float delta) {
        if (animalToMove == null || moveTarget == null) {
            return;
        }
        moveTimer += delta;
        if (moveTimer >= TILE_MOVE_SPEED) {
            moveTimer -= TILE_MOVE_SPEED;
            Point currentPos = animalToMove.location;

            if (currentPos.x == moveTarget.x && currentPos.y == moveTarget.y) {
                animalToMove = null;
                moveTarget = null;
                return;
            }

            if (currentPos.x < moveTarget.x) {
                currentPos.x++;
            } else if (currentPos.x > moveTarget.x) {
                currentPos.x--;
            }
            if (currentPos.y < moveTarget.y) {
                currentPos.y++;
            } else if (currentPos.y > moveTarget.y) {
                currentPos.y--;
            }
        }
    }

    private void renderLightningEffects(float delta) {
        SpriteBatch batch = gameView.getBatch();
        batch.begin();
        for (Tile[] row : gameModel.getMap().getTiles()) {
            for (Tile tile : row) {
                if (tile.getLightningTimer() > 0) {
                    float newTime = tile.getLightningTimer() - delta;
                    tile.setLightningTimer(newTime);
                    float x = tile.coordination.x * Main.TILE_SIZE;
                    float y = tile.coordination.y * Main.TILE_SIZE;
                    batch.draw(lightningTexture, x, y, Main.TILE_SIZE, Main.TILE_SIZE);
                    if (tile.getLightningTimer() <= 0) {
                        tile.setLightningTimer(0);
                    }
                }
            }
        }
        batch.end();
    }

    private void renderAnimals() {
        SpriteBatch batch = gameView.getBatch();
        batch.begin();
        for (Animal animal : gameModel.getPlayingUser().getFarm().animals) {
            Texture animalTexture = animal.getAnimalType().getTexture();
            float x = animal.location.x * Main.TILE_SIZE;
            float y = animal.location.y * Main.TILE_SIZE;
            batch.draw(animalTexture, x, y, Main.TILE_SIZE, Main.TILE_SIZE);
            animalRects.put(animal, new Rect(x, y, Main.TILE_SIZE, Main.TILE_SIZE));
        }
        batch.end();
    }

    private void renderPettedHearts(float delta) {
        SpriteBatch batch = gameView.getBatch();
        batch.begin();
        Iterator<Map.Entry<Animal, Float>> iterator = pettedAnimals.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Animal, Float> entry = iterator.next();
            Animal animal = entry.getKey();
            float timer = entry.getValue();

            timer -= delta;
            if(timer <= 0) {
                iterator.remove();
            } else {
                pettedAnimals.put(animal, timer);
                batch.draw(heartTexture, animal.location.x * Main.TILE_SIZE, (animal.location.y * Main.TILE_SIZE) + Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE);
            }
        }
        batch.end();
    }

    public void toggleToolsUI() {
        isToolsUIVisible = !isToolsUIVisible;
        if (isToolsUIVisible) {
            showToolsUIElements();
        } else {
            stage.clear();
        }
    }

    public void showToolsUIElements() {
        stage.clear();
        BackPack backPack = App.getCurrentGame().getPlayingUser().backPack;
        int xPos = (Gdx.graphics.getWidth() - toolsToggledPictureTexture.getWidth()) / 2 + 15;
        int toolY = TOOLS_Y_OFFSET + 2;
        int space = 65;
        int currentToolX = xPos;

        for (ItemInterface itemInterface : backPack.items.keySet()) {
            if (itemInterface instanceof Tool tool) {
                String toolName = tool.getName().toLowerCase();
                Texture currentTexture = null;
                switch (toolName) {
                    case "pickaxe": currentTexture = pickaxeTexture; break;
                    case "axe": currentTexture = axeTexture; break;
                    case "scythe": currentTexture = scytheTexture; break;
                    case "shears": currentTexture = shearsTexture; break;
                    case "watering_can": currentTexture = wateringCanTexture; break;
                    case "hoe": currentTexture = hoeTexture; break;
                    case "fishing_rod": currentTexture = fishingRodTexture; break;
                    case "milk_pail": currentTexture = milkPailTexture; break;
                }
                if (currentTexture != null) {
                    ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(currentTexture));
                    ImageButton button = new ImageButton(style);
                    int TOOL_ICON_SIZE = 45;
                    button.setBounds(currentToolX, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE);
                    final String finalToolName = toolName;
                    button.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            equippedToolName = finalToolName;
                            toolsController.toolEquip(equippedToolName);
                            Gdx.app.log("ToolsUI", "Equipped: " + equippedToolName);
                        }
                    });
                    stage.addActor(button);
                    toolRects.put(toolName, new Rect(currentToolX, toolY, TOOL_ICON_SIZE, TOOL_ICON_SIZE));
                    currentToolX += space;
                }
            }
        }
    }

    public void renderToolsUI() {
        toolsBatch.begin();
        float x = (Gdx.graphics.getWidth() - toolsToggledPictureTexture.getWidth()) / 2f;
        toolsBatch.draw(toolsToggledPictureTexture, x, TOOLS_Y_OFFSET - 10);
        toolsBatch.end();

        toolsShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        toolsShapeRenderer.setColor(Color.RED);
        Rect selectedRect = toolRects.get(equippedToolName);
        if (selectedRect != null) {
            toolsShapeRenderer.rect(selectedRect.x, selectedRect.y, selectedRect.width, selectedRect.height);
        }
        toolsShapeRenderer.end();
    }

    @Override
    public void dispose() {
        if (gameView != null) {
            //gameView.dispose();
        }
        if (toolsBatch != null) toolsBatch.dispose();
        if (toolsShapeRenderer != null) toolsShapeRenderer.dispose();
        if (toolsToggledPictureTexture != null) toolsToggledPictureTexture.dispose();
        axeTexture.dispose();
        wateringCanTexture.dispose();
        fishingRodTexture.dispose();
        scytheTexture.dispose();
        hoeTexture.dispose();
        milkPailTexture.dispose();
        pickaxeTexture.dispose();
        shearsTexture.dispose();
        if(heartTexture != null) heartTexture.dispose();
        if (stage != null) stage.dispose();
        if (boxStage != null) boxStage.dispose();
        if (barnTexture != null) barnTexture.dispose();
        if (coopTexture != null) coopTexture.dispose();
        if (lightningTexture != null) lightningTexture.dispose();
    }

    public void startSleepTransition() {
        isSleeping = true;
        sleepAlpha = 0f;
        sleepTimer = 0f;
        advancingDay = false;
    }

    public void toggleInventoryMenu() {
        if (Main.getGame().getScreen() == this) {
            this.isInInventory = true;
            mainMultiplexer.removeProcessor(gameMenuInputAdapter);
            mainMultiplexer.removeProcessor(hotkeyAdapter);
            Main.getGame().setScreen(new InventoryMenuUI(Main.getGame(), this));
            isToolsUIVisible = false;
        } else if (isInInventory) {
            isInInventory = false;
            Main.getGame().setScreen(this);
            mainMultiplexer.addProcessor(hotkeyAdapter);
            mainMultiplexer.addProcessor(gameMenuInputAdapter);
            Gdx.input.setInputProcessor(mainMultiplexer);
        }
    }

    public void toggleCookMenu() {
        if (Main.getGame().getScreen() == this) {
            this.isCook = true;
            mainMultiplexer.removeProcessor(gameMenuInputAdapter);
            mainMultiplexer.removeProcessor(hotkeyAdapter);
            Main.getGame().setScreen(new CookUI(Main.getGame(), this));
            isToolsUIVisible = false;
        } else if (isCook) {
            isCook = false;
            Main.getGame().setScreen(this);
            mainMultiplexer.addProcessor(hotkeyAdapter);
            mainMultiplexer.addProcessor(gameMenuInputAdapter);
            Gdx.input.setInputProcessor(mainMultiplexer);
        }
    }

    public void toggleMiniGameMenu() {
        if (Main.getGame().getScreen() == this) {
            this.isMiniGaming = true;
            mainMultiplexer.removeProcessor(gameMenuInputAdapter);
            mainMultiplexer.removeProcessor(hotkeyAdapter);
            Main.getGame().setScreen(new FishingUI(this));
            isToolsUIVisible = false;
        } else if (isMiniGaming) {
            isMiniGaming = false;
            Main.getGame().setScreen(this);
            mainMultiplexer.addProcessor(hotkeyAdapter);
            mainMultiplexer.addProcessor(gameMenuInputAdapter);
            Gdx.input.setInputProcessor(mainMultiplexer);
        }
    }

    public void toggleCheatMenu() {
        if (Main.getGame().getScreen() == this) {
            isCheating = true;
            mainMultiplexer.removeProcessor(gameMenuInputAdapter);
            mainMultiplexer.removeProcessor(hotkeyAdapter);
            if (cheatUI == null) {
                cheatUI = new CheatUI(gameController, this);
            }
            Main.getGame().setScreen(cheatUI);
            isToolsUIVisible = false;
        } else if (isCheating) {
            isCheating = false;
            Main.getGame().setScreen(this);
            mainMultiplexer.addProcessor(hotkeyAdapter);
            mainMultiplexer.addProcessor(gameMenuInputAdapter);
            Gdx.input.setInputProcessor(mainMultiplexer);
        }
    }

    public void showDialog(String title, String message) {
        Skin skin = GameAssetManager.getDefaultSkin();
        Dialog dialog = new Dialog(title, skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }

    public void toggleArtisanUI(Machine machine) {
        if(artisanUI == null){
            artisanUI = new ArtisanUI();
            artisanUI.setController(new ArtisanController());
            artisanUI.getController().setGameMenuUI(this);
            artisanUI.getController().setMachine(machine);
        }
        Main.getGame().setScreen(artisanUI);
    }

    @Override
    public void resize(int i, int i1) {
        gameModel.camera.viewportWidth = i;
        gameModel.camera.viewportHeight = i1;
        gameModel.camera.update();
        stage.getViewport().update(i, i1, true);
        boxStage.getViewport().update(i, i1, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    public void goToShopMenu() {
        ShopMenuController shopMenuController = new ShopMenuController(gameModel.getPlayingUser().getCurrentTile());
        ShopMenuUI shopMenuUI = new ShopMenuUI(shopMenuController, this);
        Main.getGame().setScreen(shopMenuUI);
    }

    public void goToFriendsMenu() {
        Main.getGame().setScreen(new FriendshipMenuUI(this));
    }

    public InputMultiplexer getMainMultiplexer() {
        return mainMultiplexer;
    }
    public Stage getStage(){
        return stage;
    }
}
