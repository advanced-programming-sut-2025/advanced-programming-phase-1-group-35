package GraphicView.Game;

import Controller.GameMenuController;
import Controller.InGameMenu.ArtisanController;
import Controller.InGameMenu.ToolsController;
import GraphicView.GameMenuUI;
import Model.Game;
import Model.Pair;
import Model.Result;
import Model.User;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.StardewValley.Main;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GameMenuInputAdapter extends InputAdapter {
    private final Game game;
    private final GameMenuController gameController;
    private final ToolsController toolsController;
    private ArtisanController artisanController;
    private final Set<Integer> keysHeld = new HashSet<>();
    public GameMenuUI gameMenuUI;

    public GameMenuInputAdapter(Game game, GameMenuController gameController, GameMenuUI gameMenuUI) {
        this.game = game;
        this.gameController = gameController;
        this.gameMenuUI = gameMenuUI;
        this.toolsController = new ToolsController();
        this.artisanController = new ArtisanController();
    }

    @Override
    public boolean keyDown(int keycode) {
        keysHeld.add(keycode);

        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            int selectedSlot = keycode - Input.Keys.NUM_1;
            game.getPlayingUser().setSelectedSlot(selectedSlot);
            return true;
        }

        if(keycode == Input.Keys.N){
            try {
                gameController.goToNextTurn(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(keycode == Input.Keys.P){
            gameMenuUI.goToShopMenu();
        }
        if(keycode == Input.Keys.I){
            gameMenuUI.goToFriendsMenu();
        }

        if (keycode == Input.Keys.C) {
            gameMenuUI.toggleCookMenu();
            return true;
        }

        if (keycode == Input.Keys.ESCAPE) {
            gameMenuUI.toggleInventoryMenu();
            return true;
        }

        if(keycode == Input.Keys.T){
            return true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keysHeld.remove(keycode);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        int current = game.getPlayingUser().getSelectedSlot();
        int size = game.getPlayingUser().getMaxInventorySize();
        int next = (current + (amountY > 0 ? 1 : -1) + size) % size;
        game.getPlayingUser().setSelectedSlot(next);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameMenuUI.buildingPlacementMode) {
            if (button == Input.Buttons.LEFT) {
                gameMenuUI.handleBuildingPlacement(screenX, screenY);
                return true;
            }
        }

        if (button == Input.Buttons.RIGHT) {
            handleRightClick(screenX, screenY);
            return true;
        }
        return false;
    }

    private void handleRightClick(int screenX, int screenY) {
        OrthographicCamera camera = game.camera;
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        Pair<Float, Float> playerPos = game.getPlayingUser().getCurrentPoint();

        int playerTileX = Math.round(playerPos.first);
        int playerTileY = Math.round(playerPos.second);

        int targetTileX = (int) (worldCoordinates.x / Main.TILE_SIZE);
        int targetTileY = (int) (worldCoordinates.y / Main.TILE_SIZE);

        int direction = calculateDirection(playerTileX, playerTileY, targetTileX, targetTileY);

        if (direction != 0) {
            Result result =  artisanController.clickedMachine(direction,gameMenuUI);
            if(!result.isSuccess()) result = toolsController.useTool(direction);

            if (result != null) {
                gameMenuUI.showDialog("Tool Used", result.toString());
            }
        }
    }

    private int calculateDirection(int startX, int startY, int endX, int endY) {
        int dx = endX - startX;
        int dy = endY - startY;

        if (Math.abs(dy) > Math.abs(dx) && dy > 0) return 8;
        if (Math.abs(dy) > Math.abs(dx) && dy < 0) return 2;
        if (Math.abs(dy) < Math.abs(dx) && dx > 0) return 6;
        if (Math.abs(dy) < Math.abs(dx) && dx < 0) return 4;

        return 0;
    }

    public void update(float delta) {
        User player = game.getPlayingUser();
        float vx = 0, vy = 0;
        int dir = 0;
        float sp = 1;

        if (keysHeld.contains(Input.Keys.W)) {
            vy += sp;
            dir = 3;
        }
        if (keysHeld.contains(Input.Keys.S)) {
            vy -= sp;
            dir = 1;
        }
        if (keysHeld.contains(Input.Keys.A)) {
            vx -= sp;
            dir = 4;
        }
        if (keysHeld.contains(Input.Keys.D)) {
            vx += sp;
            dir = 2;
        }

        float length = (float) Math.sqrt(vx * vx + vy * vy);
        if (length > 0) {
            vx /= length;
            vy /= length;
            player.setMovingDirection(dir);
        } else {
            player.setMovingDirection(0);
        }

        float speed = player.getSpeed();
        player.setVelocity(vx * speed, vy * speed);
        player.update(delta, game.getMap().getTiles());
    }
}
