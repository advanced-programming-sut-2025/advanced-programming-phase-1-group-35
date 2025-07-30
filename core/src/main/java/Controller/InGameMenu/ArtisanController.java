package Controller.InGameMenu;

import GraphicView.ArtisanUI;
import GraphicView.GameMenuUI;
import Model.*;
import Model.enums.machines.ArtisanProductDetails;
import Model.machines.BeeHouse;
import Model.machines.Cheese_Press;
import Model.machines.Keg;
import Model.machines.Machine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

public class ArtisanController {
    private ArtisanUI ArtisanUI;
    private GameMenuUI GameMenuUI;
    public ArtisanController(ArtisanUI ArtisanUI) {
        this.ArtisanUI = ArtisanUI;
    }


    public ArtisanUI getArtisanUI() {
        return ArtisanUI;
    }

    public void setArtisanUI(ArtisanUI artisanUI) {
        ArtisanUI = artisanUI;
    }

    public GameMenuUI getGameMenuUI() {
        return GameMenuUI;
    }

    public void setGameMenuUI(GameMenuUI gameMenuUI) {
        GameMenuUI = gameMenuUI;
    }

    public Result CraftMachine(int selectedMachine) { //1 for keg,2 for cheese press and 3 for bee house
        Tile[][] map = App.getCurrentGame().getMap().getTiles();
        int x = App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getX();
        int y = App.getCurrentGame().getPlayingUser().getCurrentTile().getCoordination().getY();
        for(int i =-1; i<= 1; i++){
            for(int j =0; j<= 1; j++){
                if(map[i][j].getPlanted() !=null || !map[i][j].getContents().isEmpty() ){
                    return new Result(false,"you can't place the machine here");
                }
            }
            Machine machine;
            switch (selectedMachine){
                case 1:
                    machine = new Keg(this);
                break;
                case 2:
                    machine = new Cheese_Press(this);
                    break;
                case 3:
                   machine = new BeeHouse(this);
                   break;
                default:
                    machine = null;
            }
            try {
                    App.getCurrentGame().getMap().getMachines().add(machine);
                    map[x][y].getContents().add(machine);
            } catch (NullPointerException e) {}
        }
        return new Result(true,"machine crafted");
    }
        public Result CreateItem(Machine machine2, ArtisanProductDetails product) {
        Machine machine = null;
        if(machine2 instanceof Keg){
            machine = (Keg) machine2;
        }
        else if(machine2 instanceof Cheese_Press){
            machine = (Cheese_Press) machine2;
        }
        else if(machine2 instanceof BeeHouse){
            machine = (BeeHouse) machine2;
        }
        if(!machine.getProducts().contains(product)){
            return new Result(false,"product doesn't exist");
        }
        machine.setInUse(true);
        machine.setProductBeingBuilt(product);
        return new Result(true,"product being created");
    }
    public void renderTimer(float deltaTime) {
        for(Machine machine: App.getCurrentGame().getMap().getMachines()){
            if(machine.getTimeInUse()<machine.getProductBeingBuilt().processingTime) {
                machine.setTimeInUse(machine.getTimeInUse()+deltaTime);
            }
            else {
                machine.setInUse(false);
                machine.setFinished(true);
            }
        }
    }
    public Result clickedMachine(int direction) {
        Tile tile = App.getCurrentGame().getMap().getTileWithDirection(direction);
        Machine machine = null;
        for(ItemInterface item: tile.getContents()){
            if(item instanceof Machine){
                machine = (Machine) item;
                break;
            }
        }
        if(machine==null){
            return new Result(false,"no machines on this tile!");
        }
        switch(machine.getName()){
            case "Keg":
                getArtisanUI().getKegTable().setVisible(true);
                getArtisanUI().getBeeHouseTable().setVisible(false);
                getArtisanUI().getCheesePressTable().setVisible(false);
                getArtisanUI().getLabel().setText("Keg");
                break;
            case "Cheese_Press":
                getArtisanUI().getKegTable().setVisible(false);
                getArtisanUI().getBeeHouseTable().setVisible(false);
                getArtisanUI().getCheesePressTable().setVisible(true);
                getArtisanUI().getLabel().setText("Cheese_Press");
                break;
            case "BeeHouse":
                getArtisanUI().getKegTable().setVisible(false);
                getArtisanUI().getBeeHouseTable().setVisible(true);
                getArtisanUI().getCheesePressTable().setVisible(false);
                getArtisanUI().getLabel().setText("BeeHouse");
        }
        getGameMenuUI().toggleArtisanUI();
        return new Result(true,"artisan menu");
    }

    public List<Stack> getRecipes() {
        List<Stack> list = new ArrayList<>();
        List<Image> glowList = new ArrayList<>();

        Keg keg = new Keg(this);
        BeeHouse beeHouse = new BeeHouse(this);
        Cheese_Press cheesePress = new Cheese_Press(this);

        if(getArtisanUI().getKegTable().isVisible()){
        for (ArtisanProductDetails pr : keg.getProducts()) {
            Texture texture = new Texture(pr.getPath()); // Or use cached version
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

            Stack stack = new Stack();
            Image glow = AssetManager.glow();
            glow.setVisible(false);

            ImageButton button = new ImageButton(style);
            stack.add(glow);
            stack.add(button);

            glowList.add(glow);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (Image g : glowList) g.setVisible(false);
                    glow.setVisible(true);
                }
            });
            list.add(stack);
        }
        }

        else if(getArtisanUI().getBeeHouseTable().isVisible()){
            for (ArtisanProductDetails pr : beeHouse.getProducts()) {
                Texture texture = new Texture(pr.getPath()); // Or use cached version
                ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
                style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

                Stack stack = new Stack();
                Image glow = AssetManager.glow();
                glow.setVisible(false);

                ImageButton button = new ImageButton(style);
                stack.add(glow);
                stack.add(button);

                glowList.add(glow);

                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for (Image g : glowList) g.setVisible(false);
                        glow.setVisible(true);
                    }
                });
                list.add(stack);
            }
        }
        else if(getArtisanUI().getCheesePressTable().isVisible()){
            for (ArtisanProductDetails pr : cheesePress.getProducts()) {
                Texture texture = new Texture(pr.getPath()); // Or use cached version
                ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
                style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

                Stack stack = new Stack();
                Image glow = AssetManager.glow();
                glow.setVisible(false);

                ImageButton button = new ImageButton(style);
                stack.add(glow);
                stack.add(button);

                glowList.add(glow);

                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for (Image g : glowList) g.setVisible(false);
                        glow.setVisible(true);
                    }
                });
                list.add(stack);
            }
        }
        return list;
    }
}
