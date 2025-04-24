package Controller.InGameMenu;

import Model.*;
import Model.Tools.BackPack;
import Model.Tools.Tool;
import Model.enums.ToolTypes;

public class ToolsController {
    public Result toolEquip(String toolName) {
        User playingUser = Game.playingUser;
        Inventory inventory = playingUser.inventory;
        BackPack backPack = inventory.backPack;
        ToolTypes toolType;
        try {
            toolType = ToolTypes.valueOf(toolName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new Result(false,"Invalid tool name!");
        }
        for (Tool tool : backPack.tools) {
            if (tool.getToolName().equals(toolType)) {
                playingUser.setCurrentTool(tool);
                return new Result(true,tool.getToolName() + " is equipped!");
            }
        }
        return new Result(false,toolType + " not found!");
    }

    public Result showCurrentTool() {
        if (Game.playingUser.getCurrentTool() == null) {
            return new Result(false,"there is no tool equipped!");
        } else {
            return new Result(true,Game.playingUser.getCurrentTool().getToolName().toString());
        }
    }

    public Result showTools() {
        BackPack backPack = Game.playingUser.inventory.backPack;
        for (Tool tool : backPack.tools) {
            System.out.println(tool.getToolName().toString());
        }
        return new Result(true,"end of tools!");
    }

    // to do after map is complete
    public void useTool(String direction) {

    }

}
