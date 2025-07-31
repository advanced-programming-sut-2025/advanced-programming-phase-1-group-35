package View.InGameMenu;

import tracker.Controller.InGameMenu.TradeMenuController;
import common.Model.App;
import common.Model.enums.Commands.TradeMenuCommands;
import View.AppMenu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu extends AppMenu {
    TradeMenuController controller = new TradeMenuController();
    public void check(Scanner scanner) throws IOException {
        controller.setUser(App.getCurrentGame().getPlayingUser());
        String input = TradeMenu.scan();
        Matcher matcher ;
        if((matcher = TradeMenuCommands.requestTrade.getMatcher(input)) != null){
            System.out.println(controller.requestTrade(matcher.group("username"), matcher.group("type"),
                    matcher.group("item"), matcher.group("amount"), matcher.group("price"),
                    matcher.group("tItem"), matcher.group("tAmount")));
        }
        else if((matcher = TradeMenuCommands.listTradeRequests.getMatcher(input)) != null){
            System.out.println(controller.listTradeRequests());
        }
        else if((matcher = TradeMenuCommands.listUnAnsweredTradeRequests.getMatcher(input)) != null){
            System.out.println(controller.listUnAnsweredTradeRequests());
        }
        else if((matcher = TradeMenuCommands.respondToTrade.getMatcher(input)) != null){
            System.out.println(controller.respondToTrade(matcher.group("answer"), matcher.group("id")));
        }
        else if((matcher = TradeMenuCommands.tradeHistory.getMatcher(input)) != null){
            System.out.println(controller.tradeHistory(matcher.group("username")));
        }
        else{
            System.out.println("invalid input");
        }
    }
}
