package Controller.InGameMenu;

import Model.*;
import Model.Tools.BackPack;
import Model.Tools.Tool;
import Model.enums.ToolTypes;

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
        for (Tool tool : backPack.tools) {
            if (tool.getToolName().equals(toolType)) {
                playingUser.setCurrentTool(tool);
                return new Result(true, tool.getToolName() + " is equipped!");
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
        for (Tool tool : backPack.tools) {
            output.append(tool.getToolName().toString());
            output.append("\n");
        }
        output.append("end of tools!");
        return new Result(true, output.toString());
    }

    // to do after map is complete
    public void useTool(String direction) {

    }

}
