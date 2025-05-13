package View;

import Controller.GameMenuController;
import Controller.InGameMenu.FarmingController;
import Controller.InGameMenu.ToolsController;
import Model.App;
import Model.enums.Commands.AnimalCommands;
import Model.enums.Commands.ToolCommands;
import Model.enums.GameMenuCommands;
import Model.enums.Menu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends AppMenu {
    GameMenuController controller = new GameMenuController();
    ToolsController toolsController = new ToolsController();

    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scan();
        Matcher matcher;
        if ((matcher = GameMenuCommands.newGame.getMatcher(input)) != null) {
            System.out.println(controller.createNewGame(matcher.group("user1"), matcher.group("user2"),
                    matcher.group("user3")));
            FarmingController cont = new FarmingController(App.getCurrentGame().getMap().getTiles());
            cont.generateStartingPlants();
        } else if ((matcher = GameMenuCommands.loadGame.getMatcher(input)) != null) {
            System.out.println(controller.loadGame());
        } else if ((matcher = GameMenuCommands.deleteGame.getMatcher(input)) != null) {
            System.out.println(controller.deleteCurrentGame());
        } else if ((matcher = GameMenuCommands.exitGame.getMatcher(input)) != null) {
            System.out.println(controller.exitGame());
        } else if ((matcher = GameMenuCommands.menuExit.getMatcher(input)) != null) {
            controller.exitMenu();
        } else if ((matcher = GameMenuCommands.nextTurn.getMatcher(input)) != null) {
            System.out.println(controller.goToNextTurn());
        } else if ((matcher = GameMenuCommands.walk.getMatcher(input)) != null) {
            System.out.println(controller.walk(matcher.group("x"), matcher.group("y")));
        } else if ((matcher = GameMenuCommands.printMap.getMatcher(input)) != null) {
            System.out.println(controller.printMap(matcher.group("x"), matcher.group("y"), matcher.group("size")));
        } else if ((matcher = GameMenuCommands.helpReadingMap.getMatcher(input)) != null) {
            System.out.println(controller.helpReadingTheMap());
        } else if ((matcher = GameMenuCommands.showEnergy.getMatcher(input)) != null) {
            System.out.println(controller.showEnergy());
        } else if ((matcher = GameMenuCommands.cheatEnergySet.getMatcher(input)) != null) {
            System.out.println(controller.cheatEnergySet(matcher.group("value")));
        } else if ((matcher = GameMenuCommands.cheatEnergyUnlimited.getMatcher(input)) != null) {
            System.out.println(controller.cheatEnergyUnlimited());
        }
        else if((matcher = GameMenuCommands.goToShopMenu.getMatcher(input) )!= null){
            System.out.println(controller.goToShopMenu());
        }
        else if ((matcher = ToolCommands.toolEquip.getMatcher(input)) != null) {
            System.out.println(toolsController.toolEquip(matcher.group("toolName")));
        } else if ((matcher = ToolCommands.showCurrentTool.getMatcher(input)) != null) {
            System.out.println(toolsController.showCurrentTool());
        } else if ((matcher = ToolCommands.showAllTools.getMatcher(input)) != null) {
            System.out.println(toolsController.showTools());
        } else if ((matcher = ToolCommands.upgradeTool.getMatcher(input)) != null) {
            System.out.println(toolsController.upgradeTool(matcher.group(1)));
        } else if ((matcher = ToolCommands.useTool.getMatcher(input)) != null) {
            System.out.println(toolsController.useTool(Integer.parseInt(matcher.group(1))));
        }
        else if (GameMenuCommands.goToAnimalMenu.getMatcher(input)!= null) {
            App.setCurrentMenu(Menu.AnimalMenu);
        }
        else if (GameMenuCommands.goToCookingMenu.getMatcher(input)!= null) {
            App.setCurrentMenu(Menu.CookingMenu);
        }
        else if((matcher = GameMenuCommands.showCropInfo.getMatcher(input) )!= null){
            System.out.println(controller.showCropInfo(matcher.group("cropName")));
        }
        else if((matcher = GameMenuCommands.plantSeed.getMatcher(input) )!= null){
            System.out.println(controller.plantSeed(matcher.group("seed"), matcher.group("direction")));
        }
        else if((matcher = GameMenuCommands.fertilize.getMatcher(input) )!= null){
            System.out.println(controller.fertilize(matcher.group("fertilizer") , matcher.group("direction")));
        }
        else if((matcher = GameMenuCommands.harvest.getMatcher(input) )!= null){
            System.out.println(controller.harvest(matcher.group("direction")));
        }
        else if((matcher = GameMenuCommands.GoToNextDay.getMatcher(input) )!= null){
            App.getCurrentGame().getGameCalender().goToNextDay();
        }
        else {
            System.out.println("Invalid input");
        }
    }
}
