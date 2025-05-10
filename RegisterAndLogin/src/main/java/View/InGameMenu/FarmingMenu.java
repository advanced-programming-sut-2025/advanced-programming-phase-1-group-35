package View.InGameMenu;

import Controller.InGameMenu.CropController;
import Controller.InGameMenu.FarmingController;
import Model.App;
import Model.enums.Commands.FarmingCommands;
import View.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class FarmingMenu extends AppMenu {
    FarmingController controller = new FarmingController(App.getCurrentGame().getMap().getTiles());
    CropController cropController = new CropController();
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        while (!command.equals("exit")) {
            if ((matcher = FarmingCommands.Plant.getMatcher(command)) != null) {
                controller.plantSeed(matcher.group(1), matcher.group(2));
            }
            else if((matcher = FarmingCommands.CraftInfo.getMatcher(command)) != null) {
                System.out.println(cropController.getCropInfo(matcher.group(1)));
            }
            else if((matcher = FarmingCommands.ShowPlant.getMatcher(command)) != null) {
            System.out.println(controller.ShowCrop(Integer.parseInt(matcher.group(1)),Integer.parseInt(matcher.group(2))));
            }
            else if((matcher = FarmingCommands.Fertilize.getMatcher(command)) != null) {
                System.out.println(controller.Fertilize(matcher.group(1), matcher.group(2)));
            }

        }
    }
}