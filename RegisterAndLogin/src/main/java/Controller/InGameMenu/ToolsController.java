package Controller.InGameMenu;

import Model.*;
import Model.CropClasses.Tree;
import Model.FarmStuff.Wood;
import Model.Tools.BackPack;
import Model.Tools.Tool;
import Model.enums.TileType;
import Model.enums.ToolTypes;

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
        // TODO : if not in blacksmith
        return new Result(false, "you are not in the blacksmith");
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
        } else if (player.getEnergy().getEnergyAmount() < 5) {
            return new Result(false, "You do not have enough energy!");
        }
        Tile currentTile = player.getCurrentTile();
        Tile destenationTile = player.getMap().getTileWithDirection(direction);
        if (destenationTile == null) {
            return new Result(false, "wrong direction!");
        }
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



}
