package GraphicView.Game;

import Model.*;
import Model.CropClasses.Crop;
import Model.CropClasses.Tree;
import Model.Point;
import Model.enums.Crops.CropEnum;
import Model.enums.Crops.TreeEnum;
import Model.enums.TileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Controller.Controller;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.StardewValley.Main;

public class GameView {
    private final Game game;
    private SpriteBatch batch;
    private BitmapFont smallFont;
    private Map<String, TextureRegion> textures;
    private Texture pixel;
    private TextureAtlas playerAtlas;
    private Label coordinateLabel;
    public Table infoTable;
    private final ArrayList<Animation<TextureRegion>> playerAnimations = new ArrayList<>();
    private int moveDirection = 0;
    private float stateTime = 0f;
    private Map<Point, Integer> staticTreeDecorations = new HashMap<>();
    private FrameBuffer treeBuffer;
    private TextureRegion treeRegion;
    private boolean treesRendered = false;
    private SpriteBatch treeBatch;

    public GameView(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        coordinateLabel = new Label();
        treeBatch = new SpriteBatch();
        loadTextures();
        loadFont();
    }

    private void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/stardew-valley.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        smallFont = generator.generateFont(parameter);
        generator.dispose();
    }

    private void loadTextures() {
        textures = new HashMap<>();

        for (TileType id : TileType.values()) {
            String path = id.getIconPath();
            textures.put(id.name(), new TextureRegion(new Texture(Gdx.files.internal(path))));
        }
        for (CropEnum cropEnum : CropEnum.values()) {
            for (int i = 1; i <= cropEnum.getStages().size(); i++) { // or a fixed max stage
                String path = "crops/" + Controller.formatUpperSnakeCase(cropEnum.getName()) + "_Stage_" + i + ".png";
                if(cropEnum.isForaging()) {path = "crops/" + Controller.formatUpperSnakeCase(cropEnum.getName()) + ".png";
                    textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
                    break;}
                textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
            }
        }

        String[] seasons = {"Spring","Summer","Fall","Winter"};
        for(TreeEnum treeEnum : TreeEnum.values()) {
            for (int i = 1; i <= treeEnum.getStages().size(); i++) {
                String path = "trees/" + Controller.formatUpperSnakeCase(treeEnum.getName()) + "_Stage_" + i + ".png";
                if(i == treeEnum.getStages().size()) {
                    for(String season : seasons) {
                            path = "trees/" + Controller.formatUpperSnakeCase(treeEnum.getName()) + "_Stage_" + i + "_" + season + ".png";
                        try {
                            textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
                        }catch(Exception e) {
                            textures.put("Debug.png", new TextureRegion(new Texture(Gdx.files.internal("Debug.png"))));
                        }
                    }
                }
                        try {
                textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
                        }catch(Exception e) {
                            textures.put("Debug.png", new TextureRegion(new Texture(Gdx.files.internal("Debug.png"))));
                        }
            }
        }
//        Tree test = new Tree(TreeEnum.APPLE_TREE);
//        test.setTile(App.getCurrentGame().getPlayingUser().getCurrentTile());
//        App.getCurrentGame().getMap().getTrees().add(test);

        // TODO : load other things
//        for (ItemDescriptionId id : ItemDescriptionId.values()) {
//            String path = id.getIconPath();
//            textures.put(id.name(), new TextureRegion(new Texture(Gdx.files.internal(path))));
//        }
//        for (CarrotStages cs : CarrotStages.values()) {
//            String path = cs.getIconPath();
//            textures.put(cs.name(), new TextureRegion(new Texture(Gdx.files.internal(path))));
//        }

        playerAtlas = new TextureAtlas(Gdx.files.internal("charachters/sprites_player.atlas"));

        for (int i = 14; i > 9; i--) {
            Array<TextureRegion> walkFrames = new Array<>();
            if (i == 14) {
                for (int j = 0; j < 4; j++) {
                    String region = "player_" + 13 + "_" + 0;
                    walkFrames.add(playerAtlas.findRegion(region));
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    String region = "player_" + i + "_" + j;
                    walkFrames.add(playerAtlas.findRegion(region));
                }
            }
            playerAnimations.add(new Animation<>(0.15f, walkFrames, Animation.PlayMode.LOOP));
        }

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        pixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public void render() {
        batch.setProjectionMatrix(game.camera.combined);
        batch.begin();
        renderTiles();
        renderPlayer();
        renderCoordinates();

        batch.end();
    }

    private void renderTiles() {
        Tile[][] tiles = game.getMap().getTiles();

        float camX = game.camera.position.x;
        float camY = game.camera.position.y;
        float viewportWidth = game.camera.viewportWidth;
        float viewportHeight = game.camera.viewportHeight;

        int tileSize = Main.TILE_SIZE;

        float cameraLeft = camX - viewportWidth / 2;
        float cameraBottom = camY - viewportHeight / 2;

        int startX = Math.max(0, (int) (cameraLeft / tileSize) - 2);
        int startY = Math.max(0, (int) (cameraBottom / tileSize) - 2);
        int endX = Math.min(tiles.length, (int) ((camX + viewportWidth / 2) / tileSize) + 2);
        int endY = Math.min(tiles[0].length, (int) ((camY + viewportHeight / 2) / tileSize) + 2);
        Map<Point, Tile> outsideTiles = new HashMap<>();

//        FrameBuffer treeBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, worldWidth, worldHeight, false);

        // Render base tiles
        for (int x = startX; x < tiles.length; x++) {
            for (int y = startY; y < tiles[0].length; y++) {
                Tile id = tiles[x][y];
                if (id != null) {
                    float drawX = x * tileSize - cameraLeft;
                    float drawY = y * tileSize - cameraBottom;
                    if (id.getTileType().equals(TileType.OutSideFarm)) {
                        outsideTiles.put(new Point(x, y), id);
                    }
                    //TODO: also render crops
                    if (id.getPlanted() != null && id.getPlanted().getClass().equals(Crop.class)) {
                        Crop GrowingCrop = (Crop) id.getPlanted();

                        if (GrowingCrop != null && GrowingCrop.getDaysSinceWatered() > 1) {
                            batch.setColor(0.7f, 0.7f, 0.7f, 1f);
                        } else {
                            batch.setColor(1f, 1f, 1f, 1f);
                        }

                    } else if (id.getPlanted() != null && id.getPlanted().getClass().equals(Tree.class)) {
                        Tree GrowingTree = (Tree) id.getPlanted();

                        if (GrowingTree != null && GrowingTree.getDaysSinceWatered() > 1) {
                            batch.setColor(0.7f, 0.7f, 0.7f, 1f);
                        } else {
                            batch.setColor(1f, 1f, 1f, 1f);
                        }
                    }
                    TextureRegion texture = textures.get(id.getTileType().name());
                    if (texture != null) {
                        batch.draw(texture, drawX, drawY, tileSize, tileSize);
                        TextureRegion layer1 = id.getTexture();
                        texture = textures.get(id.getTileType().name());
                        if (texture != null) {
                            batch.draw(texture, drawX, drawY, tileSize, tileSize);
                        }
                        if (layer1 != null) {
                            batch.draw(layer1, drawX, drawY, tileSize, tileSize);
                        }
                    }
                }
            }
        }


 //failed to put decorative trees maybe will return to it later

//        if (!treesRendered) {
//            int worldWidth = tiles.length * tileSize;
//            int worldHeight = tiles[0].length * tileSize;
//
//            // Initialize treeBuffer here, only once
//            if (treeBuffer == null) {
//                treeBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, worldWidth, worldHeight, false);
//            }
//
//            // Populate staticTreeDecorations if it's empty (only once during first render)
//            if (staticTreeDecorations.isEmpty()) {
//                Random random = new Random();
//                for (Map.Entry<Point, Tile> entry : outsideTiles.entrySet()) {
//                    Point tileCoord = entry.getKey();
//                    // Example: 10% chance to place a decorative tree
//                    if (random.nextFloat() < 0.1f) {
//                        staticTreeDecorations.put(tileCoord, random.nextInt(AssetManager.trees.length));
//                    }
//                }
//            }
//
//            treeBuffer.begin();
//            Gdx.gl.glClearColor(0, 0, 0, 0); // transparent background
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//            // Set projection matrix for treeBatch when drawing to the FrameBuffer
//            treeBatch.setProjectionMatrix(new com.badlogic.gdx.graphics.OrthographicCamera(worldWidth, worldHeight).combined);
//            treeBatch.begin();
//
//            for (Map.Entry<Point, Tile> entry : outsideTiles.entrySet()) {
//                Point tileCoord = entry.getKey();
//                int x = tileCoord.x;
//                int y = tileCoord.y;
//
//                float drawX = x * tileSize;
//                float drawY = y * tileSize;
//
//                int treeIndex = staticTreeDecorations.getOrDefault(tileCoord, -1);
//                if (treeIndex >= 0) {
//                    TextureRegion decoTree = new TextureRegion(AssetManager.trees[treeIndex]);
//
//                    float treeWidth = tileSize * 2f;
//                    float treeHeight = tileSize * 3f;
//
//                    float adjustedX = drawX - (treeWidth - tileSize) / 2f;
//                    float adjustedY = drawY;
//
//                    treeBatch.draw(decoTree, adjustedX, adjustedY, treeWidth, treeHeight);
//                }
//            }
//            treeBatch.end();
//            treeBuffer.end();
//
//            treeRegion = new TextureRegion(treeBuffer.getColorBufferTexture());
//            treeRegion.flip(false, true);
//            treesRendered = true;
//
//
//        }
//            batch.draw(treeRegion, 0, 0);


        //TODO : render crops
        for (Crop crop : App.getCurrentGame().getMap().getCrops()) {
            int x = crop.getCropTile().getCoordination().getX();
            int y = crop.getCropTile().getCoordination().getY();
            if (x >= startX && x < endX && y >= startY && y < endY) {
                float drawX = x * tileSize - cameraLeft;
                float drawY = y * tileSize - cameraBottom;

                int growth = crop.getCurrentState();
                TextureRegion cropTexture;
                String test = crop.getIconPath();
                if(!crop.getCropEnum().isForaging()) cropTexture = textures.get(crop.getStatePath());
                else cropTexture = textures.get(crop.getCropEnum().getIconPath());
                if (cropTexture != null) {
                    batch.setColor(1f, 1f, 1f, 1f);
                    batch.draw(cropTexture, drawX, drawY, tileSize, tileSize);
                }
            }
        }
        for (Tree tree : App.getCurrentGame().getMap().getTrees()) {
            tree.setCurrentState(5);
            int x = tree.getTile().getCoordination().getX();
            int y = tree.getTile().getCoordination().getY();
            if (x >= startX && x < endX && y >= startY && y < endY) {
                float drawX = x * tileSize - cameraLeft;
                float drawY = y * tileSize - cameraBottom;

                int growth = tree.getCurrentState();
                String test = tree.stagePath();
                TextureRegion treeTexture = textures.get(tree.stagePath());
                if (treeTexture != null) {
                    batch.setColor(1f, 1f, 1f, 1f);
                    float treeWidth = tileSize * 2f;
                    float treeHeight = tileSize * 3f;
                    float adjustedX = drawX - (treeWidth - tileSize) / 2f;
                    float adjustedY = drawY; // - (treeHeight - tileSize);

                    batch.draw(treeTexture, adjustedX, adjustedY, treeWidth, treeHeight);

                }
            }
        }

        batch.setColor(1f, 1f, 1f, 1f);
    }

    private void renderPlayer() {
        for (User player : game.getPlayers()) {
            Pair<Float, Float> pos = player.getCurrentPoint();
            moveDirection = player.getMovingDirection();

            stateTime += Gdx.graphics.getDeltaTime();

            Animation<TextureRegion> currentAnimation = playerAnimations.get(moveDirection);
            TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

            batch.draw(currentFrame, pos.first * Main.TILE_SIZE, pos.second * Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE * 2);
        }
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Texture getPixel() {
        return pixel;
    }

    // TODO: render inventory
//    private void renderInventory() {
//        Player player = game.getPlayer();
//        Map<ItemDescriptionId, Pair<Integer, Integer>> inventory = player.getInventory();
//        int selectedSlot = player.getSelectedSlot(); // Assuming you have this method
//
//        int screenWidth = Gdx.graphics.getWidth();
//        int slotSize = StardewMini.TILE_SIZE /2;
//        int numSlots = player.getMaxInventorySize();
//        int startX = (screenWidth - numSlots * slotSize) / 2;
//        int y = StardewMini.TILE_SIZE /2;
//
//        for (int i = 0; i < numSlots; i++) {
//            int x = startX + i * slotSize;
//
//            batch.draw(textures.get(TileDescriptionId.SLOT.name()), x, y, slotSize, slotSize);
//
//            String slotNum = String.valueOf(i + 1);
//            smallFont.draw(batch, slotNum, x + 2, y + slotSize - 2);
//        }
//
//        // Highlight selected slot
//        if (selectedSlot >= 0 && selectedSlot < numSlots) {
//            int highlightX = startX + selectedSlot * slotSize;
//            batch.draw(textures.get(TileDescriptionId.HIGHLIGHT.name()), highlightX, y, slotSize, slotSize);
//        }
//
//        for (Map.Entry<ItemDescriptionId, Pair<Integer, Integer>> entry : inventory.entrySet()) {
//            ItemDescriptionId id = entry.getKey();
//            int quantity = entry.getValue().first;
//            int index = entry.getValue().second;
//
//            if (index < 0 || index >= numSlots) continue;
//
//            TextureRegion itemTex = textures.get(id.name());
//            if (itemTex != null) {
//                int x = startX + index * slotSize;
//                batch.draw(itemTex, x, y, slotSize, slotSize);
//
//                // Draw item quantity at bottom-right corner
//                String count = String.valueOf(quantity);
//                layout.setText(smallFont, count);
//                smallFont.draw(batch, count, x + slotSize - layout.width - 2, y + layout.height + 2);
//            }
//        }
//    }
    private void renderCoordinates() {
        User playingUser = game.getPlayingUser(); // Assuming you have this method
        if (playingUser == null) return;

        Pair<Float, Float> pos = playingUser.getCurrentPoint();
        String coordText = String.format("x : %.1f y : %.1f\n%s", pos.first, pos.second,App.getCurrentGame().getPlayingUser().getCurrentTile().getTileType());

        // Calculate position (top right corner with some padding)
        float padding = 10f;
        float x = game.camera.viewportWidth - padding - 100;
        float y = game.camera.viewportHeight - padding;

        // Draw background for better readability
        batch.setColor(0, 0, 0, 0.5f); // Semi-transparent black
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            10, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1); // Reset color

        // Draw text
        smallFont.draw(batch, coordText, x, y);
    }


}
