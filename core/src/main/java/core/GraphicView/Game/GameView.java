package core.GraphicView.Game;

import com.badlogic.gdx.utils.ScreenUtils;
import core.Model.*;
import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Tree;
import core.Model.NPCs.NPC;
import core.Model.Point;
import core.Model.enums.Crops.CropEnum;
import core.Model.enums.Crops.TreeEnum;
import core.Model.enums.TileType;
import core.Model.User;
import core.Model.enums.machines.ArtisanProductDetails;
import core.Model.machines.Machine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Controller.Controller;
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
    private Animation<TextureRegion> CrowAnimation;
    private Texture activeEmoji;
    private float reactionTimer = 0f;
    private String activeText;

    public GameView(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        coordinateLabel = new Label();
        treeBatch = new SpriteBatch();
        loadTextures();
        loadFont();
    }

    public Animation<TextureRegion> getCrowAnimation() {
        return CrowAnimation;
    }

    private void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/stardew-valley.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        smallFont = generator.generateFont(parameter);
        generator.dispose();
    }

    private void loadTextures() {
        Array<TextureRegion> frames = new Array<>();
        for(int i =1; i<14; i++){
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("crowAnimation/frame"+i+".png"))));
        }
        CrowAnimation = new Animation<>(0.13f,frames);
        textures = new HashMap<>();
        textures.put("flooring/plowed_tile.png",new TextureRegion(new Texture(Gdx.files.internal("flooring/plowed_tile.png"))));
        for (TileType id : TileType.values()) {
            String path = id.getIconPath();
            textures.put(id.name(), new TextureRegion(new Texture(Gdx.files.internal(path))));
        }
        ArrayList<CropEnum> giants = new ArrayList<>();
        for (CropEnum cropEnum : CropEnum.values()) {
            if(cropEnum.canBecomeGiant()) giants.add(cropEnum);
            for (int i = 1; i <= cropEnum.getStages().size(); i++) { // or a fixed max stage
                String path = "crops/" + Controller.formatUpperSnakeCase(cropEnum.getName()) + "_Stage_" + i + ".png";
                if(cropEnum.isForaging()) {path = "crops/" + Controller.formatUpperSnakeCase(cropEnum.getName()) + ".png";
                    textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
                    break;}
                try {
                    textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
                }catch (Exception e) {
                    textures.put(path, new TextureRegion(new Texture(Gdx.files.internal("Debug.png"))));
                }
            }
        }
        for (CropEnum cropEnum : giants) {
            String path = "crops/Giant_" + Controller.formatUpperSnakeCase(cropEnum.getName()) + ".png";
            try {
                textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
            }catch (Exception e) {
                textures.put(path, new TextureRegion(new Texture(Gdx.files.internal("Debug.png"))));
            }
        }
        textures.put("trees/Stump.png", new TextureRegion(new Texture(Gdx.files.internal("trees/Stump.png"))));
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
                            textures.put(path, new TextureRegion(new Texture(Gdx.files.internal("Debug.png"))));
                        }
                    }
                }
                        try {
                textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
                        }catch(Exception e) {
                            textures.put(path, new TextureRegion(new Texture(Gdx.files.internal("Debug.png"))));
                        }
            }
        }
        for(ArtisanProductDetails details : ArtisanProductDetails.values()) {
            String path = "artisanGoods/" + Controller.formatUpperSnakeCase(details.getName()) + ".png";
            try{
                textures.put(path, new TextureRegion(new Texture(Gdx.files.internal(path))));
            } catch (Exception e) {
                textures.put(path, new TextureRegion(new Texture(Gdx.files.internal("Debug.png"))));
            }
        }
        textures.put("machines/Bee_House.png",new TextureRegion(new Texture(Gdx.files.internal("machines/Bee_House.png"))));
        textures.put("machines/Cheese_Press.png",new TextureRegion(new Texture(Gdx.files.internal("machines/Cheese_Press.png"))));
        textures.put("machines/Keg.png",new TextureRegion(new Texture(Gdx.files.internal("machines/keg.png"))));
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

    public void showReaction(Texture texture) {
        this.activeEmoji = texture;
        this.activeText = null;
        this.reactionTimer = 5.0f;
    }

    public void showReaction(String text) {
        this.activeText = text;
        this.activeEmoji = null;
        this.reactionTimer = 5.0f;
    }

    public void render(float delta) {
        if (reactionTimer > 0) {
            reactionTimer -= delta;
            if (reactionTimer <= 0) {
                activeEmoji = null;
                activeText = null;
            }
        }

        batch.setProjectionMatrix(game.camera.combined);
        batch.begin();
        renderTiles();
        renderPlayer();
        renderNPCs();
        renderCoordinates();
        renderDateTime();
        renderWeather();
        renderSeason();
        renderEnergyBar();

        if (activeEmoji != null) {
            User playingUser = game.getPlayingUser();
            if (playingUser != null && playingUser.getCurrentPoint() != null) {
                float tileX = playingUser.getCurrentPoint().first;
                float tileY = playingUser.getCurrentPoint().second;
                float playerX = tileX * Main.TILE_SIZE;
                float playerY = tileY * Main.TILE_SIZE;
                batch.draw(activeEmoji, playerX, playerY + (Main.TILE_SIZE * 5), Main.TILE_SIZE * 5, Main.TILE_SIZE * 5);
            }
        }
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
        ArrayList<Tile> plowedTiles = new ArrayList<>();

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
                    if(id.isPlowed()){
                        plowedTiles.add(id);
                    }
                    if(id.isWatered){
                        batch.setColor(0.6f, 0.6f, 0.9f, 1f);
                    }
                    if(id.isFertilized){
                        batch.setColor(0.8f, 0.8f, 0.5f, 1f);
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


        for(Machine machine : App.getCurrentGame().getMap().getMachines()){
        float x = machine.getX();
        float y = machine.getY();
            if (x >= startX && x < endX && y >= startY && y < endY) {
                float drawX = x * tileSize - cameraLeft;
                float drawY = y * tileSize - cameraBottom;

                TextureRegion machineTexture;
                machineTexture = textures.get("machines/"+Controller.formatUpperSnakeCase(machine.getName())+".png");

                if (machineTexture != null) {
                    batch.setColor(1f, 1f, 1f, 1f);
                    batch.draw(machineTexture, drawX, drawY, Main.TILE_SIZE, Main.TILE_SIZE);
                }
            }
        }

        for(Tile tile : plowedTiles){
            int x = tile.getCoordination().x;
            int y = tile.getCoordination().y;
            if (x >= startX && x < endX && y >= startY && y < endY) {
                float drawX = x * tileSize - cameraLeft;
                float drawY = y * tileSize - cameraBottom;

                TextureRegion cropTexture;
                cropTexture = textures.get("flooring/plowed_tile.png");

                if (cropTexture != null) {
                    batch.setColor(1f, 1f, 1f, 1f);
                    batch.draw(cropTexture, drawX, drawY, Main.TILE_SIZE, Main.TILE_SIZE);
                }
            }
        }

        //TODO : render crops
        int cropSize = Main.TILE_SIZE;
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
                if(crop.isGiant()){
                    cropTexture = textures.get("crops/Giant_" + Controller.formatUpperSnakeCase(crop.getName()) + ".png");
                    cropSize *= 2;
                }
                if (cropTexture != null) {
                    batch.setColor(1f, 1f, 1f, 1f);
                    batch.draw(cropTexture, drawX, drawY, cropSize, cropSize);
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
                    float treeWidth = tree.getCurrentState() > 2 ? tileSize * 2f : tileSize;
                    float treeHeight = tileSize * (float)tree.getCurrentState();
                    float adjustedX = drawX - (treeWidth - tileSize) / 2f;
                    float adjustedY = drawY; // - (treeHeight - tileSize);
                    if (tree.isChopped()) treeHeight /=3;
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

    private void renderNPCs() {
        for (NPC npc : game.getNpcs()) {
            if(npc.texture == null ) continue;
            Pair<Float , Float> pos = new Pair<>((float)npc.location.x , (float)npc.location.y);
            TextureRegion texture = new TextureRegion(npc.texture);
            batch.draw(texture, pos.first* Main.TILE_SIZE, pos.second*Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE * 2);
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
        float x = game.camera.viewportWidth - padding - 150;
        float y = game.camera.viewportHeight - padding;

        // Draw background for better readability
        batch.setColor(0, 0, 0, 0.5f); // Semi-transparent black
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            10, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1); // Reset color

        // Draw text
        smallFont.draw(batch, coordText, x, y);
    }
    private void renderDateTime() {
        String dateTimeText = game.getGameCalender().getGameDateTime().toString();
        // Assuming this returns a formatted string, adjust if needed

        // Calculate position (top right corner below coordinates)
        float padding = 10f;
        float x = game.camera.viewportWidth - padding - 150; // Wider for date/time
        float y = game.camera.viewportHeight - padding - smallFont.getLineHeight() - 5;

        // Draw background
        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            150, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1);

        // Draw text
        smallFont.draw(batch, dateTimeText, x, y);
    }

    private void renderWeather() {
        String weatherText = "Weather: " + game.getWeather().getWeatherCondition().toString();

        // Position below date/time
        float padding = 10f;
        float x = game.camera.viewportWidth - padding - 150;
        float y = game.camera.viewportHeight - padding - (smallFont.getLineHeight() + 5) * 2;

        // Draw background
        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            150, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1);

        // Draw text
        smallFont.draw(batch, weatherText, x, y);
    }

    private void renderSeason() {
        String seasonText = "Season: " + game.getGameCalender().getSeason().toString();

        // Position below weather
        float padding = 10f;
        float x = game.camera.viewportWidth - padding - 150;
        float y = game.camera.viewportHeight - padding - (smallFont.getLineHeight() + 5) * 3;

        // Draw background
        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            150, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1);

        // Draw text
        smallFont.draw(batch, seasonText, x, y);
    }
    private void renderEnergyBar() {
        User player = game.getPlayingUser();
        if (player == null) return;

        float energyRatio = (float) ((float) player.getEnergy().getEnergyAmount() / player.getEnergy().getEnergyCapacity());
        energyRatio = Math.max(0, Math.min(1, energyRatio)); // Clamp between 0 and 1

        // Dimensions and positioning
        float padding = 10f;
        float width = 150f;
        float height = 20f;
        float x = game.camera.viewportWidth - padding - width;
        float y = game.camera.viewportHeight - padding - (smallFont.getLineHeight() + 5) * 4 - height;

        // Draw background (empty energy)
        batch.setColor(0.2f, 0.2f, 0.2f, 0.7f);
        batch.draw(pixel, x, y, width, height);

        // Draw filled energy (color changes based on energy level)
        if (energyRatio > 0.6f) {
            batch.setColor(0.2f, 0.8f, 0.2f, 0.9f); // Green when high
        } else if (energyRatio > 0.3f) {
            batch.setColor(1f, 0.8f, 0.2f, 0.9f); // Yellow when medium
        } else {
            batch.setColor(0.8f, 0.2f, 0.2f, 0.9f); // Red when low
        }
        batch.draw(pixel, x, y, width * energyRatio, height);

        // Draw border
        batch.setColor(1f, 1f, 1f, 0.5f);
        batch.draw(pixel, x - 1, y - 1, width + 2, 1); // Top border
        batch.draw(pixel, x - 1, y + height, width + 2, 1); // Bottom border
        batch.draw(pixel, x - 1, y - 1, 1, height + 2); // Left border
        batch.draw(pixel, x + width, y - 1, 1, height + 2); // Right border

        // Draw energy text
        batch.setColor(1f, 1f, 1f, 1f);
        String energyText = String.format("%d/%d",
            (int)player.getEnergy().getEnergyAmount(),
            (int)player.getEnergy().getEnergyCapacity());

        // Center text in the bar
        GlyphLayout layout = new GlyphLayout(smallFont, energyText);
        float textX = x + (width - layout.width) / 2;
        float textY = y + (height + layout.height) / 2;
        smallFont.draw(batch, energyText, textX, textY);
    }
}
