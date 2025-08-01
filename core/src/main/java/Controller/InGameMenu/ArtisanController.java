package Controller.InGameMenu;

import GraphicView.ArtisanUI;
import GraphicView.GameMenuUI;
import Model.*;
import Model.enums.machines.ArtisanProductDetails;
import Model.machines.BeeHouse;
import Model.machines.Cheese_Press;
import Model.machines.Keg;
import Model.machines.Machine;
import com.StardewValley.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

public class ArtisanController {
    private ArtisanUI ArtisanUI;
    private GameMenuUI GameMenuUI;
    private Machine machine;
//    public ArtisanController(ArtisanUI ArtisanUI) {
//        this.ArtisanUI = ArtisanUI;
////        this.machine = machine;
//    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
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
        for(Machine machine: App.getCurrentGame().getMap().getMachines()) {
            if (machine.isInUse()) {
                if (machine.getTimeInUse() < machine.getProductBeingBuilt().processingTime) {
                    machine.setTimeInUse(machine.getTimeInUse() + deltaTime);
                } else {
                    machine.setInUse(false);
                    machine.setFinished(true);
                }
            }
        }
    }
    public Result clickedMachine(int direction, GameMenuUI ui) {
        this.GameMenuUI = ui;
        this.setArtisanUI(new ArtisanUI());
        this.getArtisanUI().setController(this);
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
                getArtisanUI().getKegLabel().setText("Keg");
                break;
            case "Cheese_Press":
                getArtisanUI().getKegTable().setVisible(false);
                getArtisanUI().getBeeHouseTable().setVisible(false);
                getArtisanUI().getCheesePressTable().setVisible(true);
                getArtisanUI().getBeeHouseLabel().setText("Cheese_Press");
                break;
            case "BeeHouse":
                getArtisanUI().getKegTable().setVisible(false);
                getArtisanUI().getBeeHouseTable().setVisible(true);
                getArtisanUI().getCheesePressTable().setVisible(false);
                getArtisanUI().getCheesePressLabel().setText("BeeHouse");
        }
        this.getGameMenuUI().toggleArtisanUI(machine);

        return new Result(true,"artisan menu");
    }


    public List<Stack> getRecipes() {
        List<Stack> list = new ArrayList<>();
        List<Image> glowList = new ArrayList<>();

        Machine machine;
        if (ArtisanUI.getKegTable().isVisible()) machine = new Keg(this);
        else if (ArtisanUI.getBeeHouseTable().isVisible()) machine = new BeeHouse(this);
        else machine = new Cheese_Press(this);

        for (ArtisanProductDetails pr : machine.getProducts()) {
            Texture texture;
            try {
                texture = new Texture(pr.getPath());
            } catch (Exception e) {
                texture = new Texture("Debug.png");
            }

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

            Stack stack = new Stack();
            Image glow = AssetManager.glow();
            glow.setVisible(false);

            ImageButton button = new ImageButton(style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (Image g : glowList) g.setVisible(false);
                    glow.setVisible(true);
                    ArtisanUI.setSelectedItem(pr);
                    ArtisanUI.getSelectedItemLabel().setText(pr.getName());
                    ArtisanUI.getSelectedItemTexture().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pr.getPath()))));
//                    getArtisanUI().refreshSelectedItemDisplay(glow);
                }
            });

            stack.add(glow);
            stack.add(button);
            glowList.add(glow);
            list.add(stack);
        }
        return list;
    }
    public void renderButtons() {
        if(getArtisanUI().getCraft().isChecked()){
            getArtisanUI().getCraft().setChecked(false);
            machine.setProductBeingBuilt((ArtisanProductDetails) getArtisanUI().getSelectedItem());
            CreateItem(machine,(ArtisanProductDetails) getArtisanUI().getSelectedItem());
        }
        if(getArtisanUI().getBack().isChecked()){
            getArtisanUI().getBack().setChecked(false);
            Main.getGame().setScreen(getGameMenuUI());
        }
    }
    public void renderLabel(){
        if(machine.isInUse()){
            getArtisanUI().getCraftState().setText(machine.getProductBeingBuilt().getName() + " is " + (machine.getProductBeingBuilt().processingTime - machine.getTimeInUse())/100
             + "% done");
        }
        else if(machine.isFinished()){
            getArtisanUI().getCraftState().setText(machine.getProductBeingBuilt().getName() + " is finished");
        }
        else {
            getArtisanUI().getCraftState().setText("machine is in rest mode");
        }
    }


}
