package View;

import Controller.GameMenuController;
import Controller.InGameMenu.FarmingController;
import Controller.InGameMenu.NPCController;
import Controller.InGameMenu.ToolsController;
import Model.App;
import Model.Map;
import Model.enums.Commands.NPCCommands;
import Model.enums.Commands.ToolCommands;
import Model.enums.GameMenuCommands;
import Model.enums.Menu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends AppMenu {
    GameMenuController controller = new GameMenuController();
    ToolsController toolsController = new ToolsController();
    NPCController npcController = new NPCController();

    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scan();
        Matcher matcher;
        if ((matcher = GameMenuCommands.newGame.getMatcher(input)) != null) {
            System.out.println(controller.createNewGame(matcher.group("user1"), matcher.group("user2"),
                    matcher.group("user3")));
            FarmingController cont = new FarmingController(App.getCurrentGame().getMap().getTiles());
            cont.generateStartingPlants();
            cont.addForagingCrop();
        } else if (GameMenuCommands.showPlayerPosition.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.x
                    + " " + App.getCurrentGame().getPlayingUser().getCurrentTile().coordination.y);
        } else if ((matcher = GameMenuCommands.loadGame.getMatcher(input)) != null) {
            System.out.println(controller.loadGame());
        } else if ((matcher = GameMenuCommands.deleteGame.getMatcher(input)) != null) {
            System.out.println(controller.deleteCurrentGame());
        } else if ((matcher = GameMenuCommands.exitGame.getMatcher(input)) != null) {
            System.out.println(controller.exitGame());
        } else if ((matcher = GameMenuCommands.menuExit.getMatcher(input)) != null) {
            controller.exitMenu();
        } else if ((matcher = GameMenuCommands.nextTurn.getMatcher(input)) != null) {
            System.out.println(controller.goToNextTurn(null));
        } else if ((matcher = GameMenuCommands.walk.getMatcher(input)) != null) {
            System.out.println(controller.walk(App.getCurrentGame().getPlayingUser() , matcher.group("x"), matcher.group("y")));
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
        } else if ((matcher = GameMenuCommands.goToShopMenu.getMatcher(input)) != null) {
            System.out.println(controller.goToShopMenu());
        } else if ((matcher = ToolCommands.toolEquip.getMatcher(input)) != null) {
            System.out.println(toolsController.toolEquip(matcher.group("toolName")));
        } else if ((matcher = ToolCommands.showCurrentTool.getMatcher(input)) != null) {
            System.out.println(toolsController.showCurrentTool());
        } else if ((matcher = ToolCommands.showAllTools.getMatcher(input)) != null) {
            System.out.println(toolsController.showTools());
        } else if (GameMenuCommands.showWeather.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getWeather().getWeatherCondition());
        } else if (GameMenuCommands.weatherForecast.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getWeather().getTomorrowCondition());
        } else if ((matcher = GameMenuCommands.cheatWeatherSet.getMatcher(input)) != null) {
            System.out.println(App.getCurrentGame().getWeather().cheatWeatherSet(matcher.group("type")));
        } else if ((matcher = GameMenuCommands.cheatThor.getMatcher(input)) != null) {
            System.out.println(App.getCurrentGame().getWeather().hitTileWithThunder(Map.getTileWithCoordination(matcher.group("x"), matcher.group("y"))));
        } else if ((matcher = ToolCommands.upgradeTool.getMatcher(input)) != null) {
            System.out.println(toolsController.upgradeTool(matcher.group(1)));
        } else if ((matcher = ToolCommands.useTool.getMatcher(input)) != null) {
            System.out.println(toolsController.useTool(Integer.parseInt(matcher.group(1))));
        } else if (GameMenuCommands.goToAnimalMenu.getMatcher(input) != null) {
            App.setCurrentMenu(Menu.AnimalMenu);
        } else if (GameMenuCommands.goToCookingMenu.getMatcher(input) != null) {
            App.setCurrentMenu(Menu.CookingMenu);
        } else if ((matcher = GameMenuCommands.talkPlayer.getMatcher(input)) != null) {
            //TODO
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
        else if((matcher =GameMenuCommands.ChopTree.getMatcher(input) )!= null){
            System.out.println(controller.chopTree(matcher.group("direction")));
        }
        else if((matcher = GameMenuCommands.GoToNextDay.getMatcher(input) )!= null){
            App.getCurrentGame().getGameCalender().goToNextDay();
        }
        else if ((matcher = GameMenuCommands.talkPlayer.getMatcher(input)) != null) {
            System.out.println(controller.talk(matcher.group("username"), matcher.group("message")));
        } else if ((matcher = GameMenuCommands.talkHistory.getMatcher(input)) != null) {
            System.out.println(controller.talkHistory(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.giftPlayer.getMatcher(input)) != null) {
            System.out.println(controller.giftPlayer(matcher.group("username"), matcher.group("item"),
                    matcher.group("amount")));
        } else if ((matcher = GameMenuCommands.friendshipStatus.getMatcher(input)) != null) {
            System.out.println(controller.friendShipStatus(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.giftHistory.getMatcher(input)) != null) {
            System.out.println(controller.giftHistory(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.giftList.getMatcher(input)) != null) {
            System.out.println(controller.giftList());
        } else if ((matcher = GameMenuCommands.rateGift.getMatcher(input)) != null) {
            System.out.println(controller.rateGift(matcher.group("id"), matcher.group("rate")));
        } else if ((matcher = GameMenuCommands.hug.getMatcher(input)) != null) {
            System.out.println(controller.hug(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.flower.getMatcher(input)) != null) {
            System.out.println(controller.flower(matcher.group("username")));
        } else if ((matcher = NPCCommands.meetNPC.getMatcher(input)) != null) {
            System.out.println(npcController.meetNPC(matcher.group(1)));
        } else if ((matcher = NPCCommands.giftNPC.getMatcher(input)) != null) {
            System.out.println(npcController.sendGift(matcher.group(1), matcher.group(2)));
        } else if (NPCCommands.showAllFriendships.getMatcher(input) != null) {
            System.out.println(npcController.seeFriendshipWithNPCs());
        } else if ((matcher = NPCCommands.showQuestList.getMatcher(input)) != null) {
            System.out.println(npcController.seeQuestList(matcher.group(1)));
        } else if ((matcher = NPCCommands.showQuestList.getMatcher(input)) != null) {
            System.out.println(npcController.finishQuest(matcher.group(1), matcher.group(2)));
        } else if ((matcher = GameMenuCommands.askMarriage.getMatcher(input)) != null) {
            System.out.println(controller.askMarriage(matcher.group("username")));
        } else if ((matcher = GameMenuCommands.respondToMarriageRequest.getMatcher(input)) != null) {
            System.out.println(controller.respondToMarriageRequest());
        } else if (GameMenuCommands.getTime.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getGameCalender().getTime());
        } else if (GameMenuCommands.getDate.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getGameCalender().getDate());
        } else if (GameMenuCommands.getTimeAndDate.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getGameCalender().getDateTime());
        } else if (GameMenuCommands.getDayOfTheWeek.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getGameCalender().getDayOfTheWeek());
        } else if ((matcher = GameMenuCommands.cheatTime.getMatcher(input)) != null) {
            System.out.println(App.getCurrentGame().getGameCalender().cheatTime(Integer.parseInt(matcher.group(1))));
        } else if ((matcher = GameMenuCommands.cheatDate.getMatcher(input)) != null) {
            System.out.println(App.getCurrentGame().getGameCalender().cheatDate(Integer.parseInt(matcher.group(1))));
        }
        else if (GameMenuCommands.getSeason.getMatcher(input) != null) {
            System.out.println(App.getCurrentGame().getGameCalender().getSeason());
        }
        else if((matcher = GameMenuCommands.showInventory.getMatcher(input)) != null) {
            System.out.println(controller.showInventory());
        }
        else if((matcher = GameMenuCommands.ShowPlant.getMatcher(input)) != null) {
            System.out.println(controller.ShowCrop(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        }
        else if((matcher = GameMenuCommands.giveSeed.getMatcher(input) ) != null) {
            System.out.println(controller.giveSeed(matcher.group("seed")));
        }
        else if((matcher = GameMenuCommands.pickUpSeed.getMatcher(input)) != null) {
            System.out.println(controller.pickUpSeed(matcher.group("direction")));
        }
        else if((matcher = GameMenuCommands.pickItem.getMatcher(input)) != null) {
            System.out.println(controller.pickItem(matcher.group("itemName"), matcher.group("direction")));
        }
        else if((matcher = GameMenuCommands.showRecipes.getMatcher(input)) != null) {
            System.out.println(controller.ShowRecipes());
        }
        else if((matcher = GameMenuCommands.CraftItem.getMatcher(input)) != null) {
            System.out.println(controller.CraftItem(matcher.group("itemName")));
        }
        else if((matcher = GameMenuCommands.CheatGetItem.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddItemToBackPack(matcher.group("itemName"), matcher.group("amount")));
        }
        else if((matcher = GameMenuCommands.cheatPlaceCraft.getMatcher(input)) != null) {
            System.out.println(controller.cheatPlaceArtisan(matcher.group("itemName"), matcher.group("direction")));
        }

        else {
            System.out.println("Invalid input");
        }
    }
}
