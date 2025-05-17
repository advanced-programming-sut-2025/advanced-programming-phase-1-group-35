package Controller.InGameMenu;

import Controller.GameMenuController;
import Model.App;
import Model.ItemInterface;
import Model.Result;
import Model.TradeAndGift.Trade;
import Model.User;
import Model.enums.ItemConstant;

import java.io.IOException;
import java.util.Map;

public class TradeMenuController {
    private User user;
    GameMenuController gameMenuController = new GameMenuController();

    public void setUser(User user) {
        this.user = user;
    }
    public Result listPlayers() {
        StringBuilder m = new StringBuilder();
        for (User player : App.getCurrentGame().getPlayers()) {
            if(player.equals(user))continue;
            m.append(player.toString());
        }
        return new Result(true , m.toString());
    }
    public Result requestTrade(String username, String type, String itemName, String amountString,
                               String priceString, String targetItemName, String targetAmountString ) throws IOException {
        User receiver = gameMenuController.getUserBYName(username);
        if(receiver == null){
            return new Result(false, "User not found");
        }
        if(receiver.equals(user)){
            return new Result(false, "you should see a therapist for your bipolar shit");
        }
        if(priceString != null && targetItemName != null){
            return new Result(false , "stop being indecisive");
        }
        int amount = Integer.parseInt(amountString);
        Map.Entry<ItemInterface, Integer> item = gameMenuController.getItemFromBackPack(itemName);
        if(item == null){
            return new Result(false, "Item not found");
        }
        Result result = null;
        result = switch (type) {
            case "money" -> tradeWithMoney(receiver ,item, amount, priceString);
            case "item" -> tradeWithItem(receiver, item, amount, targetItemName, targetAmountString);
            default -> null;
        };
        gameMenuController.goToNextTurn(receiver);
        return result;
    }

    private Result tradeWithItem(User receiver, Map.Entry<ItemInterface, Integer> item, int amount, String targetItemName, String targetAmountString) throws IOException {
        if(item.getValue() < amount){
            return new Result(false, "you do not have enough of this item");
        }
        ItemConstant targetItemConst = gameMenuController.getItemConstantByName(targetItemName);
        if(targetItemConst == null){
            return new Result(false, "target Item not found");
        }
        ItemInterface targetItem = targetItemConst.getItem();
        int targetAmount = Integer.parseInt(targetAmountString);
        Trade trade = new Trade(user.getID(), receiver.getID(), item.getKey(), amount,
                0, targetItem, targetAmount);
        user.getTrades().add(trade);
        receiver.getTrades().add(trade);
        receiver.setHasNewTradeRequest(true);
        gameMenuController.removeFromBackPack(item, user.backPack, amount);
        return new Result(true, "trade request sent");
    }

    private Result tradeWithMoney(User receiver, Map.Entry<ItemInterface, Integer> item, int amount, String priceString) {
        if(item.getValue() < amount){
            return new Result(false, "you do not have enough of this item");
        }
        int price = Integer.parseInt(priceString);
        Trade trade = new Trade(user.getID(), receiver.getID(), item.getKey(),item.getValue(),
                price, null, 0);
        user.getTrades().add(trade);
        receiver.getTrades().add(trade);
        receiver.setHasNewTradeRequest(true);
        gameMenuController.removeFromBackPack(item, user.backPack, amount);
        return new Result(true, "trade request sent");
    }

    public Result listTradeRequests(){
        StringBuilder m = new StringBuilder();
        m.append("all trade requests: \n═════════════════════════════════\n");
        for (Trade trade : user.getTrades()) {
            m.append(trade.toString());
            m.append("\n═════════════════════════════════\n");
        }
        return new Result(true , m.toString());
    }
    public Result listUnAnsweredTradeRequests(){
        StringBuilder m = new StringBuilder();
        m.append("unanswered trade requests: \n");
        for (Trade trade : user.getTrades()) {
            if(trade.isAnswered())continue;
            m.append(trade.toString());
            m.append("\n═════════════════════════════════\n");
        }
        return new Result(true , m.toString());
    }
    public Result respondToTrade(String response, String tradeIDString){
        int tradeID = Integer.parseInt(tradeIDString);
        Trade trade = user.getTrades().get(tradeID);
        if(trade == null){
            return new Result(false, "Trade not found");
        }
        User receiver = gameMenuController.getUserByID(user.getID());
        if(response.equalsIgnoreCase("-accept")){
            if(trade.getPrice() > 0 ){
                if(user.getMoney() < trade.getPrice())
                    return new Result(false, "you do not have enough money");
                else {
                    user.setMoney(user.getMoney() - trade.getPrice());
                    receiver.setMoney(receiver.getMoney() + trade.getPrice());
                }
            }
            else{
                int amountInBackPack = user.backPack.items.get(trade.getTargetItem());
                if(amountInBackPack < trade.getTargetAmount()){
                    return new Result(false, "you do not have enough of the target item");
                }
                else {
                    user.backPack.items.compute(trade.getTargetItem(),
                            (k,v) -> v - trade.getAmount());
                    if(user.backPack.items.get(trade.getTargetItem()) == 0){
                        user.backPack.items.remove(trade.getTargetItem());
                    }
                    receiver.backPack.items.compute(trade.getTargetItem(), (k,v) -> v + trade.getTargetAmount());
                }
            }

            receiver.backPack.items.compute(trade.getItemInterface(), (k,v) -> v == null ?  trade.getAmount() : v + trade.getAmount());
            trade.setAnswered(true);
            trade.setAccepted(true);
            gameMenuController.increaseMutualXP(user, receiver, 50);
            return new Result(true, "trade accepted");
        }
        else {
            trade.setAnswered(true);
            trade.setAccepted(false);
            user.backPack.items.compute(trade.getItemInterface(), (k,v) -> v == null ? trade.getAmount() : trade.getAmount() + v);
            gameMenuController.increaseMutualXP(user,receiver,-30);
            return new Result(true, "trade rejected");
        }
    }
    public Result tradeHistory(String username){
        User them = gameMenuController.getUserBYName(username);
        if(them == null){
            return new Result(false, "User not found");
        }
        StringBuilder m = new StringBuilder();
        m.append("trade history: \n");
        for (Trade trade : user.getTrades()) {
            if(trade.getSenderID() == them.getID() ||
                trade.getRecipientID() == them.getID()){
                m.append(trade.toString());
            }
            m.append("\n═════════════════════════════════\n");
        }
        return new Result(true , m.toString());
    }
    public Trade getTradeByID(int tradeID){
        for (Trade trade : user.getTrades()) {
            if(trade.getID() == tradeID){
                return trade;
            }
        }
        return null;
    }
}
