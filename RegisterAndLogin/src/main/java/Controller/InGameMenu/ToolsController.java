package Controller.InGameMenu;

import Model.*;
import Model.CropClasses.Tree;
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
                if (tool.getToolName().equals(toolType)) {
                    playingUser.setCurrentTool(tool);
                    return new Result(true, tool.getToolName() + " is equipped!");
                }
            }

        }
        return new Result(false, toolType + " not found!");
    }

    public Result showCurrentTool() {
        if (App.getCurrentGame().getPlayingUser().getCurrentTool() == null) {
            return new Result(false, "there is no tool equipped!");
        } else {
            return new Result(true, App.getCurrentGame().getPlayingUser().getCurrentTool().getToolName().toString());
        }
    }

    public Result showTools() {
        BackPack backPack = App.getCurrentGame().getPlayingUser().backPack;
        StringBuilder output = new StringBuilder();
        for (ItemInterface item : backPack.items.keySet()) {
            if (item instanceof Tool tool) {
                output.append(tool.getToolName().toString());
                output.append("\n");
            }
        }
        output.append("end of tools!");
        return new Result(true, output.toString());
    }

    public Result upgradeTool(String toolName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        for (ItemInterface item : player.backPack.items.keySet()) {
            if (item instanceof Tool tool && tool.getToolName().toString().equals(toolName)) {
                if (tool instanceof FishingPole) {
                    // TODO : add if not in Willy store
                    return new Result(true, "you are not in Willy store!");
                } else {
                    // TODO : if not in blacksmith
                    return new Result(false, "you are not in the blacksmith");
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

    public Result useTool(int direction, String toolName) {
        ToolTypes toolType;
        try {
            toolType = ToolTypes.valueOf(toolName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid tool name!");
        }
        Game game = App.getCurrentGame();
        User player = App.getCurrentGame().getPlayingUser();
        BackPack backPack = player.backPack;
        if (!backPack.isToolInBackPack(toolType)) {
            return new Result(false, "you don't have a " + toolName + " in your backpack!");
        }
        Tile currentTile = player.getCurrentTile();
        Tile destenationTile = player.getMap().getTileWithDirection(direction);
        if (destenationTile == null) {
            return new Result(false, "wrong direction!");
        }
        // TODO : energy usage of each tool and connect to each method

    }

    public Result useHoe(Game game, User player, Tile destenationTile) {
        if (destenationTile.getTileType() == TileType.Soil) {
            destenationTile.setPlowed(true);
            return new Result(true, "You used hoe and you can plant on that tile");
        } else {
            return new Result(false, "you cant use hoe on this tile");
        }
    }

    public Result usePickaxe(Game game, User player, Tile destenationTile) {
        destenationTile.setPlowed(false);
        if (destenationTile.getTileType() == TileType.Rock) {
            destenationTile.setTileType(TileType.Soil);
            return new Result(true, "You used pickaxe and destroyed a rock");
        } else {
            return new Result(false, "you cant use pickaxe on this tile");
        }
    }

    public Result useAxe(Game game, User player, Tile destenationTile) {
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
                    return new Result(true, "You used axe and destroyed a tree");
                }
            }
        }
        return new Result(false, "you cant use axe on this tile");
    }

    public Result useShear(Game game, User player, Tile destenationTile) {
        for (ItemInterface content : destenationTile.getContents()) {
            if (content instanceof Animal animal) {
                if (animal.getAnimalType() == AnimalType.Sheep) {
                    return new AnimalController().collectProducts(animal.getName());
                }
            }
        }
        return new Result(false, "you is no sheep in this tile");
    }

    public Result useMilkPail(Game game, User player, Tile destenationTile) {
        for (ItemInterface content : destenationTile.getContents()) {
            if (content instanceof Animal animal) {
                if (animal.getAnimalType() == AnimalType.Cow) {
                    return new AnimalController().collectProducts(animal.getName());
                }
            }
        }
        return new Result(false, "you is no cow in this tile");
    }

    public Result useFishingPole(Game game, User player, Tile destenationTile) {
        if (destenationTile.getTileType() == TileType.Water) {
            for (ItemInterface item : player.backPack.items.keySet()) {
                if (item instanceof FishingPole pole) {
                    return new AnimalController().fishing(pole.getName());
                }
            }
        }
        return new Result(false, "you are not near to water!");
    }

    public Result useWateringCan(Game game, User player, Tile destenationTile) {
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
                    // TODO : watering this tile
                    return new Result(true, "you watered this tile");
                }
            }
        }
        return new Result(false, "you cant use watering on this tile");
    }

    public Result useScythe(Game game, User player, Tile destenationTile) {
        if (destenationTile.getTileType() == TileType.Grass ||
                destenationTile.getTileType() == TileType.Soil) {

            return new FarmingController(player.getMap().getTiles()).harvestCrop(destenationTile);
        }
        // TODO : cut the HARZ grasses
        return new Result(false, "you cant use scythe on this tile");
    }


}
