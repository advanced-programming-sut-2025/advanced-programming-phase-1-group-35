package core.Controller.InGameMenu;

import core.GameUpdater;
import core.Model.*;
import core.Controller.GameMenuController;
import core.Model.*;
import core.Model.CropClasses.Crop;
import core.Model.CropClasses.Tree;
import core.Model.FarmStuff.Rock;
import core.Model.FarmStuff.Wood;
import core.Model.Tools.BackPack;
import core.Model.Tools.FishingPole;
import core.Model.Tools.Tool;
import core.Model.Tools.WateringCan;
import core.Model.animal.Animal;
import core.Model.enums.TileType;
import core.Model.enums.ToolTypes;
import core.Model.enums.animal.AnimalType;
import core.Model.enums.animal.FishType;

import java.util.ArrayList;

public class ToolsController {

    public Result toolEquip(String toolName) {
        User playingUser = App.getCurrentGame().getPlayingUser();
        BackPack backPack = playingUser.backPack;
        ToolTypes toolType;
        try {
            toolType = ToolTypes.valueOf(toolName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid tool name!");
        }
        for (ItemInterface item : backPack.items.keySet()) {
            if (item instanceof Tool tool) {
                if (tool.getToolType().equals(toolType)) {
                    playingUser.setCurrentTool(tool);
                    return new Result(true, tool.getToolType() + " is equipped!");
                }
            }

        }
        return new Result(false, toolType + " not found!");
    }

    public Result showCurrentTool() {
        if (App.getCurrentGame().getPlayingUser().getCurrentTool() == null) {
            return new Result(false, "there is no tool equipped!");
        } else {
            return new Result(true, App.getCurrentGame().getPlayingUser().getCurrentTool().getToolType().toString());
        }
    }

    public Result showTools() {
        BackPack backPack = App.getCurrentGame().getPlayingUser().backPack;
        StringBuilder output = new StringBuilder();
        for (ItemInterface item : backPack.items.keySet()) {
            if (item instanceof Tool tool) {
                output.append(tool.getToolType().toString());
                output.append("\n");
            }
        }
        output.append("end of tools!");
        return new Result(true, output.toString());
    }

    public Result upgradeTool(String toolName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        ShopMenuController controller = new ShopMenuController();
        if (toolName.equalsIgnoreCase("backpack")) {
            return player.backPack.upgradeBackPack();
        }
        for (ItemInterface item : player.backPack.items.keySet()) {
            if (item instanceof Tool tool && tool.getToolType().toString().equalsIgnoreCase(toolName)) {
                if (tool instanceof FishingPole) {
                    if (controller.findShopByTile(player.getCurrentTile()) != null &&
                        !controller.findShopByTile(player.getCurrentTile()).getName().
                            equalsIgnoreCase("FishShop")) {
                        return new Result(false, "you are not in Willy store!");
                    }
                } else {
                    if (controller.findShopByTile(player.getCurrentTile()) == null ||
                        !controller.findShopByTile(player.getCurrentTile()).getName().
                            equalsIgnoreCase("Blacksmith")) {
                        return new Result(false, "you are not in the blacksmith");
                    }
                }
            }
        }
        return new Result(false, toolName + " not found!");
    }

    public Result useTrashCan(String itemName) {
        for (ItemInterface item : App.getCurrentGame().getPlayingUser().backPack.items.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                App.getCurrentGame().getPlayingUser().backPack.items.remove(item);

                return new Result(true, "You have used the trash can and removed: " + itemName);
            }
        }
        return new Result(false, "item not found!");
    }

    public Result showInventory() {
        StringBuilder output = new StringBuilder();
        for (ItemInterface item : App.getCurrentGame().getPlayingUser().backPack.items.keySet()) {
            output.append(item.getName());
            output.append("\n");
        }
        return new Result(true, output.toString());
    }

    public Result useTool(int direction) {
        Game game = App.getCurrentGame();
        User player = App.getCurrentGame().getPlayingUser();
        BackPack backPack = player.backPack;
        Tool tool = player.getCurrentTool();
        if (tool == null) {
            return new Result(false, "No tool is equipped!");
        }
        if (!backPack.isToolInBackPack(tool.getToolType())) {
            return new Result(false, "you don't have a " + tool.getToolType() + " in your backpack!");
        }
        Tile destenationTile = game.getMap().getTileWithDirection(direction);
        if (destenationTile == null) {
            return new Result(false, "wrong direction!");
        }
        switch (tool.getToolType()) {
            case HOE:
                return useHoe(player, destenationTile);
            case PICKAXE:
                return usePickaxe(player, destenationTile);
            case AXE:
                return useAxe(player, destenationTile, direction);
            case SHEARS:
                return useShears(player, destenationTile);
            case MILK_PAIL:
                return useMilkPail(player, destenationTile);
            case FISHING_ROD:
                return useFishingPole(player, destenationTile);
            case WATERING_CAN:
                return useWateringCan(player, destenationTile);
            case SCYTHE:
                return useScythe(game, player, destenationTile);
        }
        return null;
    }

    private boolean energyCheck(User player, int energyCost) {
        if (player.getEnergy().getEnergyAmount() < energyCost) {
            return false;
        }
        player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() - energyCost);
        return true;
    }

    private Result useHoe(User player, Tile destenationTile) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        if (destenationTile.getTileType() == TileType.Soil) {
            destenationTile.setPlowed(true);
            destenationTile.setSymbol('ɍ');
            GameUpdater.sendTileUpdate(destenationTile);
            return new Result(true, "You used hoe and you can plant on that tile");
        } else {
            return new Result(false, "you cant use hoe on this tile");
        }
    }

    private Result usePickaxe(User player, Tile destenationTile) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        destenationTile.setPlowed(false);
        if (destenationTile.getTileType() == TileType.Rock) {
            destenationTile.setSymbol('.');
            destenationTile.setTileType(TileType.Soil);
            GameUpdater.sendTileUpdate(destenationTile);
            player.backPack.items.compute(Rock.mine(destenationTile), (k, v) -> v == null ? 1 : v + 1);
            return new Result(true, "You used pickaxe and destroyed a rock");
        } else {
            return new Result(false, "you cant use pickaxe on this tile");
        }
    }

    private Result useAxe(User player, Tile destenationTile, int direction) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        for (ItemInterface content : destenationTile.getContents()) {
            if (content instanceof Tree || content instanceof Wood) {
                ArrayList<ItemInterface> items = destenationTile.getContents();
                items.remove(content);
                destenationTile.setContents(items);
                destenationTile.setTileType(TileType.Soil);
                GameUpdater.sendTileUpdate(destenationTile);
                if (content instanceof Wood) {
                    return new Result(true, "You used axe and destroyed a wood stick");
                } else {
                    player.backPack.items.put(new Wood(), 1);
                    switch (direction) {
                        case 8:
                            new GameMenuController().chopTree("up");
                            break;
                        case 6:
                            new GameMenuController().chopTree("right");
                            break;
                        case 2:
                            new GameMenuController().chopTree("down");
                            break;
                        case 4:
                            new GameMenuController().chopTree("left");
                            break;
                    }
                    return new Result(true, "You used axe and destroyed a tree");
                }
            }
        }
        return new Result(false, "you cant use axe on this tile");
    }

    private Result useShears(User player, Tile destenationTile) {
        if (!energyCheck(player, 4)) {
            return new Result(false, "you don't have enough energy!");
        }
        for (ItemInterface content : destenationTile.getContents()) {
            if (content instanceof Animal animal) {
                if (animal.getAnimalType() == AnimalType.Sheep) {
                    return new AnimalController().collectProducts(animal.getName());
                }
            }
        }
        return new Result(false, "you is no sheep in this tile");
    }

    private Result useMilkPail(User player, Tile destenationTile) {
        if (!energyCheck(player, 4)) {
            return new Result(false, "you don't have enough energy!");
        }
        for (ItemInterface content : destenationTile.getContents()) {
            if (content instanceof Animal animal) {
                if (animal.getAnimalType() == AnimalType.Cow) {
                    return new AnimalController().collectProducts(animal.getName());
                }
            }
        }
        return new Result(false, "you is no cow in this tile");
    }

    private Result useFishingPole(User player, Tile destenationTile) {
        if (!energyCheck(player, 8)) {
            return new Result(false, "you don't have enough energy!");
        }
        return new AnimalController().fishing(FishType.getRandomFish(), false);
    }

    private Result useWateringCan(User player, Tile destenationTile) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        for (ItemInterface item : player.backPack.items.keySet()) {
            if (item instanceof WateringCan can) {
                try {
                    if (destenationTile.getTileType() == TileType.Water) {
                        if (can.getCapacity() < 55) {
                            can.setCapacity(can.getCapacity() + 1);
                        }
                        return new Result(true, "You fill the can and its capacity now: " +
                            can.getCapacity());
                    } else if (!destenationTile.isWatered) {
                        destenationTile.setWatered(true);
                        if (destenationTile.getPlanted() instanceof Crop crop) {
                            crop.setDaysSinceWatered(0);
                        } else if (destenationTile.getPlanted() instanceof Tree tree) {
                            tree.setDaysSinceWatered(0);
                        }
                        GameUpdater.sendTileUpdate(destenationTile);
                        return new Result(true, "you watered this tile");
                    }
                }catch (Exception e){
                    return new Result(false, "you can't water this tile");
                }
            }
            }

            return new Result(false, "you cant use watering can on this tile");

    }
    private Result useScythe(Game game, User player, Tile destenationTile) {
        if (!energyCheck(player, 2)) {
            return new Result(false, "you don't have enough energy!");
        }
        if (destenationTile.getTileType() == TileType.Grass ||
            destenationTile.getTileType() == TileType.Soil) {

            return new FarmingController(game.getMap().getTiles()).harvestCrop(destenationTile);
        }
        return new Result(false, "you cant use scythe on this tile");
    }

}
