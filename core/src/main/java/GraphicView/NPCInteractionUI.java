package GraphicView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.StardewValley.Main;
import Controller.InGameMenu.NPCController;
import Model.Game;
import Model.GameAssetManager;
import Model.ItemInterface;
import Model.NPCs.NPC;
import Model.NPCs.Quest;
import Model.Result;

public class NPCInteractionUI implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private Skin skin;
    private final NPCController controller;
    private final GameMenuUI gameMenuUI;
    private final NPC npc;

    private Table mainTable;
    private Label statusLabel;
    private Label friendshipLabel;

    private Inventory giftInventory;

    public NPCInteractionUI(GameMenuUI gameMenuUI, NPC npc) {
        this.gameMenuUI = gameMenuUI;
        this.controller = new NPCController();
        this.npc = npc;
        initializeUI();
    }

    private void initializeUI() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = GameAssetManager.getDefaultSkin();

        // Main table, aligned to the left
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.align(Align.topLeft);
        mainTable.pad(20);
        stage.addActor(mainTable);

        // Title
        Label titleLabel = new Label("Interacting with " + npc.name, skin, "title");
        mainTable.add(titleLabel).colspan(2).padBottom(20).row();

        // NPC Information Section
        Table infoTable = new Table(skin);
        infoTable.defaults().align(Align.left).pad(5);

        // Friendship Points and Level
        friendshipLabel = new Label("", skin);
        infoTable.add(friendshipLabel).row();

        // Favorites
        infoTable.add(new Label("Loves:", skin)).padTop(10).row();
        Table favoritesTable = new Table();
        for (String favorite : npc.favorites) {
            favoritesTable.add(new Label("- " + favorite, skin)).left().row();
        }
        infoTable.add(favoritesTable).padBottom(10).row();


        // Quests
        infoTable.add(new Label("Quests:", skin)).padTop(10).row();
        Table questsTable = new Table();
        if (npc.quests.isEmpty()) {
            questsTable.add(new Label("No active quests.", skin)).left();
        } else {
            for (Quest quest : npc.quests) {
                questsTable.add(new Label(String.format("'%s' for %s reward", quest.getRequest(), quest.getReward()), skin)).left().row();
            }
        }
        infoTable.add(questsTable).row();
        mainTable.add(infoTable).expandY().top().padRight(20);


        // Status label for feedback
        statusLabel = new Label("", skin);
        statusLabel.setColor(1, 0, 0, 1); // Red for errors by default
        mainTable.add(statusLabel).colspan(2).pad(10).row();

        // Gifting Section
        Label giftLabel = new Label("Gift an Item:", skin);
        mainTable.add(giftLabel).colspan(2).left().padTop(20).row();

        // Inventory for gifting
        // The inventory is drawn separately on the right side of the screen
        giftInventory = new Inventory(Main.getGame(), stage, gameMenuUI.gameController);
        giftInventory.setMode(1); // Set to selection mode

        // Send Gift Button
        TextButton sendGiftButton = new TextButton("Send Gift", skin);
        sendGiftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendGift();
            }
        });

        // Back Button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goBackToGameMenu();
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(sendGiftButton).width(200).height(50).pad(10);
        buttonTable.add(backButton).width(200).height(50).pad(10);
        mainTable.add(buttonTable).colspan(2).padTop(20);

        // Initial data refresh
        refreshNPCInfo();
    }

    /**
     * Updates the friendship label with the latest data from the NPC object.
     */
    private void refreshNPCInfo() {
        npc.updateFriendLevel(); // Ensure level is up-to-date
        friendshipLabel.setText(String.format("Friendship: %d / %d (Level %d)",
            npc.friendshipPoint, (npc.friendshipLevel + 1) * 200, npc.friendshipLevel));
    }


    /**
     * Handles the logic for sending a selected item as a gift to the NPC.
     */
    private void sendGift() {
        ItemInterface selectedItem = giftInventory.getSelectedItem();
        if (selectedItem == null) {
            statusLabel.setText("Please select an item to gift.");
            statusLabel.setColor(1, 0, 0, 1); // Red
            return;
        }

        Result result = controller.sendGift(npc.name, selectedItem.getName());

        if (result.isSuccess()) {
            statusLabel.setText(result.toString());
            statusLabel.setColor(0, 1, 0, 1); // Green
            giftInventory.refresh(); // Update inventory display
            refreshNPCInfo(); // Update friendship points display
        } else {
            statusLabel.setText(result.toString());
            statusLabel.setColor(1, 0, 0, 1); // Red
        }
    }


    private void goBackToGameMenu() {
        if (gameMenuUI != null) {
            Main.getGame().setScreen(gameMenuUI);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the inventory on the right side of the screen
        batch.begin();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        giftInventory.drawOnRight(batch);
        batch.end();

        // Act and draw the main UI stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        refreshNPCInfo();
        giftInventory.refresh();
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
        batch.dispose();
    }
}
