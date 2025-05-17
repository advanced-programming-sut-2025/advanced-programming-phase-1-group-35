package Controller.InGameMenu;

import Controller.GameMenuController;
import Model.*;
import Model.CropClasses.Crop;
import Model.CropClasses.Tree;
import Model.FarmStuff.Rock;
import Model.FarmStuff.Wood;
import Model.Tools.BackPack;
import Model.Tools.FishingPole;
import Model.Tools.Tool;
import Model.Tools.WateringCan;
import Model.animal.Animal;
import Model.enums.TileType;
import Model.enums.ToolTypes;
import Model.enums.animal.AnimalType;

import java.util.ArrayList;

public class ToolsController {
    public Result toolEquip(String toolName) {
        System.out.println("Tool equipped");
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
        for (ItemInterface item : player.backPack.items.keySet()) {
            if (item instanceof Tool tool && tool.getToolType().toString().equals(toolName)) {
                if (tool instanceof FishingPole) {
                    if (controller.findShopByTile(player.getCurrentTile()) != null &&
                            controller.findShopByTile(player.getCurrentTile()).getName().
                                    equalsIgnoreCase("FishShop")) {
                        return new Result(true, "you are not in Willy store!");
                    }
                } else {
                    if (controller.findShopByTile(player.getCurrentTile()) != null &&
                            controller.findShopByTile(player.getCurrentTile()).getName().
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
            if (item.getName().equals(itemName)) {
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
        if (!backPack.isToolInBackPack(tool.getToolType())) {
            return new Result(false, "you don't have a " + tool.getToolType() + " in your backpack!");
        }
        Tile currentTile = player.getCurrentTile();
        Tile destenationTile = game.getMap().getTileWithDirection(direction);
        if (destenationTile == null) {
            return new Result(false, "wrong direction!");
        }
        switch (tool.getToolType()) {
            case HOE -> {
                return useHoe(game, player, destenationTile);
            }
            case PICKAXE -> {
                return usePickaxe(game, player, destenationTile);
            }
            case AXE -> {
                return useAxe(game, player, destenationTile, direction);
            }
            case SHEARS -> {
                return useShears(game, player, destenationTile);
            }
            case MILK_PAIL -> {
                return useMilkPail(game, player, destenationTile);
            }
            case FISHING_ROD -> {
                return useFishingPole(game, player, destenationTile);
            }
            case WATERING_CAN -> {
                return useWateringCan(game, player, destenationTile);
            }
            case SCYTHE -> {
                return useScythe(game, player, destenationTile);
            }
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

    private Result useHoe(Game game, User player, Tile destenationTile) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        if (destenationTile.getTileType() == TileType.Soil) {
            destenationTile.setPlowed(true);
            destenationTile.setSymbol('ɍ');
            return new Result(true, "You used hoe and you can plant on that tile");
        } else {
            return new Result(false, "you cant use hoe on this tile");
        }
    }

    private Result usePickaxe(Game game, User player, Tile destenationTile) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        destenationTile.setPlowed(false);
        if (destenationTile.getTileType() == TileType.Rock) {
            destenationTile.setSymbol('.');
            destenationTile.setTileType(TileType.Soil);
            player.backPack.items.compute(Rock.mine(destenationTile), (k, v) -> v == null ? 1 : v + 1);
            return new Result(true, "You used pickaxe and destroyed a rock");
        } else {
            return new Result(false, "you cant use pickaxe on this tile");
        }
    }

    private Result useAxe(Game game, User player, Tile destenationTile, int direction) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        for (ItemInterface content : destenationTile.getContents()) {
            if (content instanceof Tree || content instanceof Wood) {
                ArrayList<ItemInterface> items = destenationTile.getContents();
                items.remove(content);
                destenationTile.setContents(items);
                destenationTile.setTileType(TileType.Soil);
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

    private Result useShears(Game game, User player, Tile destenationTile) {
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

    private Result useMilkPail(Game game, User player, Tile destenationTile) {
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

    private Result useFishingPole(Game game, User player, Tile destenationTile) {
        if (!energyCheck(player, 8)) {
            return new Result(false, "you don't have enough energy!");
        }
        if (destenationTile.getTileType() == TileType.Water) {
            for (ItemInterface item : player.backPack.items.keySet()) {
                if (item instanceof FishingPole pole) {
                    return new AnimalController().fishing(pole.getName());
                }
            }
        }
        return new Result(false, "you are not near to water!");
    }

    private Result useWateringCan(Game game, User player, Tile destenationTile) {
        if (!energyCheck(player, 5)) {
            return new Result(false, "you don't have enough energy!");
        }
        for (ItemInterface item : player.backPack.items.keySet()) {
            if (item instanceof WateringCan can) {
                if (destenationTile.getTileType() == TileType.Water) {
                    if (can.getCapacity() < 55) {
                        can.setCapacity(can.getCapacity() + 1);
                    }
                    return new Result(true, "You fill the can and its capacity now: " +
                            can.getCapacity());
                } else if (destenationTile.getTileType() == TileType.Grass ||
                        destenationTile.getTileType() == TileType.Soil) {
                    destenationTile.setWatered(true);
                    if (destenationTile.getPlanted() instanceof Crop crop) {
                        crop.setDaysSinceWatered(0);
                    } else if (destenationTile.getPlanted() instanceof Tree tree) {
                        tree.setDaysSinceWatered(0);
                    }
                    return new Result(true, "you watered this tile");
                }
            }
        }
        return new Result(false, "you cant use watering on this tile");
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
