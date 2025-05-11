package View.InGameMenu;

import Controller.InGameMenu.ToolsController;
import Model.enums.Commands.ToolCommands;
import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ToolMenu extends AppMenu {
    ToolsController controller = new ToolsController();

    public void check(Scanner scanner) {
        String command = scanner.nextLine().trim();
        Matcher matcher;
        while (!command.equals("exit")) {
            if ((matcher = ToolCommands.toolEquip.getMatcher(command)) != null) {
                System.out.println(controller.toolEquip(matcher.group(1)));
            } else if ((matcher = ToolCommands.showCurrentTool.getMatcher(command)) != null) {
                System.out.println(controller.showCurrentTool());
            } else if ((matcher = ToolCommands.showAllTools.getMatcher(command)) != null) {
                System.out.println(controller.showTools());
            } else if ((matcher = ToolCommands.useTool.getMatcher(command)) != null) {
                controller.useTool(matcher.group(1));
            } else {
                System.out.println("Invalid command");
            }
        }
    }
}
