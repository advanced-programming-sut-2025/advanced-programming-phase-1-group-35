package core.GraphicView.Game;

import com.badlogic.gdx.graphics.GL20;
import core.Model.*;
import core.Model.*;
import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Tree;
import core.Model.NPCs.NPC;
import core.Model.Point;
import core.Model.enums.Crops.CropEnum;
import core.Model.enums.Crops.TreeEnum;
import core.Model.enums.TileType;
import core.Model.*;
import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Tree;
import core.Model.NPCs.NPC;
import core.Model.Point;
import core.Model.enums.Crops.CropEnum;
import core.Model.enums.Crops.TreeEnum;
import core.Model.User;
import core.Model.enums.TileType;
import core.Model.enums.machines.ArtisanProductDetails;
import core.Model.machines.ArtisanProduct;
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
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.StardewValley.Main;
import peer.GameStateUpdateListener;
import peer.P2TConnectionController;

public class GameView implements GameStateUpdateListener {
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

    private static class Reaction {
        Texture emoji;
        String text;
        float timer;

        Reaction(Texture emoji) {
            this.emoji = emoji;
            this.text = null;
            this.timer = 5.0f;
        }

        Reaction(String text) {
            this.emoji = null;
            this.text = text;
            this.timer = 5.0f;
        }
    }
    private final Map<User, Reaction> activeReactions = new ConcurrentHashMap<>();

    public GameView(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        coordinateLabel = new Label();
        treeBatch = new SpriteBatch();
        loadTextures();
        loadFont();
        P2TConnectionController.addGameStateUpdateListener(this);
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

    public void render(float delta) {
        for (Iterator<Map.Entry<User, Reaction>> it = activeReactions.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<User, Reaction> entry = it.next();
            Reaction reaction = entry.getValue();
            reaction.timer -= delta;
            if (reaction.timer <= 0) {
                if (reaction.emoji != null) {
                    reaction.emoji.dispose();
                }
                it.remove();
            }
        }
        batch.setProjectionMatrix(game.camera.combined);
        batch.begin();
        renderTiles();
        renderPlayer();
        renderNPCs();
        renderReactions();
        renderNightOverlay();
        batch.end();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.begin();
        renderCoordinates();
        renderDateTime();
        renderWeather();
        renderSeason();
        renderEnergyBar();
        batch.end();
    }

    private void renderReactions() {
        for (Map.Entry<User, Reaction> entry : activeReactions.entrySet()) {
            User user = entry.getKey();
            Reaction reaction = entry.getValue();
            Pair<Float, Float> pos = user.getCurrentPoint();

            if (pos != null) {
                float playerX = pos.first * Main.TILE_SIZE;
                float playerY = pos.second * Main.TILE_SIZE;

                if (reaction.emoji != null) {
                    batch.draw(reaction.emoji, playerX, playerY + (Main.TILE_SIZE * 2), Main.TILE_SIZE, Main.TILE_SIZE);
                } else if (reaction.text != null) {
                    GlyphLayout layout = new GlyphLayout(smallFont, reaction.text);
                    float textX = playerX + (Main.TILE_SIZE - layout.width) / 2;
                    float textY = playerY + (Main.TILE_SIZE * 2) + Main.TILE_SIZE / 2f + layout.height;

                    batch.setColor(0, 0, 0, 0.5f);
                    batch.draw(pixel, textX - 5, textY - layout.height - 5, layout.width + 10, layout.height + 10);
                    batch.setColor(1, 1, 1, 1);

                    smallFont.draw(batch, reaction.text, textX, textY);
                }
            }
        }
    }

    /**
     * Renders a transparent overlay that darkens based on the time of day.
     */
    private void renderNightOverlay() {
        // Assuming game.getGameCalender().getGameDateTime() has a getHour() method.
        int hour = game.getGameCalender().getGameDateTime().getHour();
        float alpha = 0f;

        // The sky starts getting dark at 6 PM (18:00)
        if (hour >= 18 && hour < 19) {
            // Stage 1 of darkness
            alpha = 0.2f;
        } else if (hour >= 19 && hour < 20) {
            // Stage 2 of darkness
            alpha = 0.4f;
        } else if (hour >= 20 || hour < 6) {
            // Maximum darkness from 8 PM (20:00) until 6 AM
            alpha = 0.6f;
        }

        // If it's dark enough to render the overlay
        if (alpha > 0) {
            // Enable blending to allow for transparency
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            // Set the color of the overlay (dark blueish tint for night)
            batch.setColor(0, 0, 0.1f, alpha);

            // Get camera position to draw the overlay over the entire viewport
            float camX = game.camera.position.x;
            float camY = game.camera.position.y;
            float viewportWidth = game.camera.viewportWidth;
            float viewportHeight = game.camera.viewportHeight;
            float startX = camX - viewportWidth / 2;
            float startY = camY - viewportHeight / 2;

            // Draw a 1x1 pixel texture stretched to cover the screen
            batch.draw(pixel, startX, startY, viewportWidth, viewportHeight);

            // Reset the batch color to white to not affect subsequent draw calls
            batch.setColor(1, 1, 1, 1);

            // It's good practice to disable blending when done, though SpriteBatch manages this.
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }


    private void renderTiles() {
        Tile[][] tiles = game.getMap().getTiles();

        float camX = game.camera.position.x;
        float camY = game.camera.position.y;
        float viewportWidth = game.camera.viewportWidth;
        float viewportHeight = game.camera.viewportHeight;

        int tileSize = Main.TILE_SIZE;

        // Culling: Calculate the visible tile range
        int startX = Math.max(0, (int) ((camX - viewportWidth / 2) / tileSize));
        int startY = Math.max(0, (int) ((camY - viewportHeight / 2) / tileSize));
        int endX = Math.min(tiles.length, (int) ((camX + viewportWidth / 2) / tileSize) + 2);
        int endY = Math.min(tiles[0].length, (int) ((camY + viewportHeight / 2) / tileSize) + 2);

        // Render base tiles
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                if (x >= tiles.length || y >= tiles[0].length) continue;
                Tile id = tiles[x][y];
                if (id != null) {
                    // *** FIX: Draw at world coordinates ***
                    float drawX = x * tileSize;
                    float drawY = y * tileSize;

                    if(id.isWatered()){
                        batch.setColor(0.6f, 0.6f, 0.9f, 1f);
                    } else if(id.isFertilized){
                        batch.setColor(0.8f, 0.8f, 0.5f, 1f);
                    } else {
                        batch.setColor(1f, 1f, 1f, 1f);
                    }

                    TextureRegion texture = textures.get(id.getTileType().name());
                    if (texture != null) {
                        batch.draw(texture, drawX, drawY, tileSize, tileSize);
                        TextureRegion layer1 = id.getTexture();
                        if (layer1 != null) {
                            batch.draw(layer1, drawX, drawY, tileSize, tileSize);
                        }
                    }

                    if (id.isPlowed()) {
                        TextureRegion plowedTexture = textures.get("flooring/plowed_tile.png");
                        if (plowedTexture != null) {
                            batch.setColor(1f, 1f, 1f, 1f);
                            batch.draw(plowedTexture, drawX, drawY, tileSize, tileSize);
                        }
                    }
                }
            }
        }
        batch.setColor(1f, 1f, 1f, 1f); // Reset color

        // Render machines
        for(Machine machine : App.getCurrentGame().getMap().getMachines()){
            int x = (int)machine.getX();
            int y = (int)machine.getY();
            if (x >= startX && x < endX && y >= startY && y < endY) {
                // *** FIX: Draw at world coordinates ***
                float drawX = x * tileSize;
                float drawY = y * tileSize;
                TextureRegion machineTexture = textures.get("machines/"+Controller.formatUpperSnakeCase(machine.getName())+".png");
                if (machineTexture != null) {
                    batch.draw(machineTexture, drawX, drawY, Main.TILE_SIZE, Main.TILE_SIZE);
                }
            }
        }

        // Render crops
        for (Crop crop : App.getCurrentGame().getMap().getCrops()) {
            int x = crop.getCropTile().getCoordination().getX();
            int y = crop.getCropTile().getCoordination().getY();
            if (x >= startX && x < endX && y >= startY && y < endY) {
                // *** FIX: Draw at world coordinates ***
                float drawX = x * tileSize;
                float drawY = y * tileSize;

                TextureRegion cropTexture;
                if(!crop.getCropEnum().isForaging()) cropTexture = textures.get(crop.getStatePath());
                else cropTexture = textures.get(crop.getCropEnum().getIconPath());

                float cropSize = Main.TILE_SIZE;
                if(crop.isGiant()){
                    cropTexture = textures.get("crops/Giant_" + Controller.formatUpperSnakeCase(crop.getName()) + ".png");
                    cropSize *= 2;
                }
                if (cropTexture != null) {
                    batch.draw(cropTexture, drawX, drawY, cropSize, cropSize);
                }
            }
        }

        // Render trees
        for (Tree tree : App.getCurrentGame().getMap().getTrees()) {
            int x = tree.getTile().getCoordination().getX();
            int y = tree.getTile().getCoordination().getY();
            if (x >= startX && x < endX && y >= startY && y < endY) {
                // *** FIX: Draw at world coordinates ***
                float drawX = x * tileSize;
                float drawY = y * tileSize;

                TextureRegion treeTexture = textures.get(tree.stagePath());
                if (treeTexture != null) {
                    float treeWidth = tree.getCurrentState() > 2 ? tileSize * 2f : tileSize;
                    float treeHeight = tileSize * (float)tree.getCurrentState();
                    float adjustedX = drawX - (treeWidth - tileSize) / 2f;
                    float adjustedY = drawY;
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

    private void renderCoordinates() {
        User playingUser = game.getPlayingUser();
        if (playingUser == null) return;

        Pair<Float, Float> pos = playingUser.getCurrentPoint();
        String coordText = String.format("x : %.1f y : %.1f\n%s", pos.first, pos.second,App.getCurrentGame().getPlayingUser().getCurrentTile().getTileType());

        float padding = 10f;
        float x = Gdx.graphics.getWidth() - padding - 150;
        float y = Gdx.graphics.getHeight() - padding;

        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight()*2 - 5,
            160, smallFont.getLineHeight()*2 + 10);
        batch.setColor(1, 1, 1, 1);

        smallFont.draw(batch, coordText, x, y);
    }
    private void renderDateTime() {
        String dateTimeText = game.getGameCalender().getGameDateTime().toString();

        float padding = 10f;
        float x = Gdx.graphics.getWidth() - padding - 150;
        float y = Gdx.graphics.getHeight() - padding - (smallFont.getLineHeight() + 5) * 2;

        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            150, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1);

        smallFont.draw(batch, dateTimeText, x, y);
    }

    private void renderWeather() {
        String weatherText = "Weather: " + game.getWeather().getWeatherCondition().toString();

        float padding = 10f;
        float x = Gdx.graphics.getWidth() - padding - 150;
        float y = Gdx.graphics.getHeight() - padding - (smallFont.getLineHeight() + 5) * 3;

        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            150, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1);

        smallFont.draw(batch, weatherText, x, y);
    }

    private void renderSeason() {
        String seasonText = "Season: " + game.getGameCalender().getSeason().toString();

        float padding = 10f;
        float x = Gdx.graphics.getWidth() - padding - 150;
        float y = Gdx.graphics.getHeight() - padding - (smallFont.getLineHeight() + 5) * 4;

        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, x - 5, y - smallFont.getLineHeight() - 5,
            150, smallFont.getLineHeight() + 10);
        batch.setColor(1, 1, 1, 1);

        smallFont.draw(batch, seasonText, x, y);
    }
    private void renderEnergyBar() {
        User player = game.getPlayingUser();
        if (player == null) return;

        float energyRatio = (float) ((float) player.getEnergy().getEnergyAmount() / player.getEnergy().getEnergyCapacity());
        energyRatio = Math.max(0, Math.min(1, energyRatio));

        float padding = 10f;
        float width = 150f;
        float height = 20f;
        float x = Gdx.graphics.getWidth() - padding - width;
        float y = Gdx.graphics.getHeight() - padding - (smallFont.getLineHeight() + 5) * 5 - height;

        batch.setColor(0.2f, 0.2f, 0.2f, 0.7f);
        batch.draw(pixel, x, y, width, height);

        if (energyRatio > 0.6f) {
            batch.setColor(0.2f, 0.8f, 0.2f, 0.9f);
        } else if (energyRatio > 0.3f) {
            batch.setColor(1f, 0.8f, 0.2f, 0.9f);
        } else {
            batch.setColor(0.8f, 0.2f, 0.2f, 0.9f);
        }
        batch.draw(pixel, x, y, width * energyRatio, height);

        batch.setColor(1f, 1f, 1f, 0.5f);
        batch.draw(pixel, x - 1, y - 1, width + 2, 1);
        batch.draw(pixel, x - 1, y + height, width + 2, 1);
        batch.draw(pixel, x - 1, y - 1, 1, height + 2);
        batch.draw(pixel, x + width, y - 1, 1, height + 2);

        batch.setColor(1f, 1f, 1f, 1f);
        String energyText = String.format("%d/%d",
            (int)player.getEnergy().getEnergyAmount(),
            (int)player.getEnergy().getEnergyCapacity());

        GlyphLayout layout = new GlyphLayout(smallFont, energyText);
        float textX = x + (width - layout.width) / 2;
        float textY = y + (height + layout.height) / 2;
        smallFont.draw(batch, energyText, textX, textY);
    }

    public void dispose() {
        P2TConnectionController.removeGameStateUpdateListener(this);
        batch.dispose();
        smallFont.dispose();
        pixel.dispose();
        playerAtlas.dispose();
        for (TextureRegion region : textures.values()) {
            region.getTexture().dispose();
        }
        for(Reaction reaction : activeReactions.values()){
            if(reaction.emoji != null){
                reaction.emoji.dispose();
            }
        }
    }

    @Override
    public void onPlayerMoneyUpdated(User user, int newMoney) {}

    @Override
    public void onPlayerEnergyUpdated(User user, int newEnergy) {}

    @Override
    public void onPlayerPositionUpdated(User user, float x, float y) {}

    @Override
    public void onPlayerReaction(User user, String type, String content) {
        if ("emoji".equals(type)) {
            Texture emojiTexture = new Texture(Gdx.files.internal(content));
            activeReactions.put(user, new Reaction(emojiTexture));
        } else if ("text".equals(type)) {
            activeReactions.put(user, new Reaction(content));
        }
    }
}
