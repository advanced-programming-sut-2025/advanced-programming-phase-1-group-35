package Controller.InGameMenu;

import Model.*;
import Model.Tools.BackPack;
import Model.TradeAndGift.Gift;
import Model.enums.Gender;

import java.util.Map;

public class FriendshipMenuController {
    private Result acceptMarriageRequest(User user) {
        Map.Entry<ItemInterface, Integer> ring = getItemFromBackPack("WEDDING_RING", user.getAskedMarriage().backPack);
        if (ring == null) {
            return new Result(false, "that stupid boy forgot the ring");
        }
        user.setSpouse(user.getAskedMarriage());
        user.getSpouse().setSpouse(user);
        user.setAskedMarriage(null);
        removeFromBackPack(ring, user.getSpouse().backPack, 1);
        addToBackPack(ring, user.backPack, 1);
        return new Result(true, "You have successfully accepted the marriage");
    }
    private Result rejectMarriageRequest(User user) {
        int xp = user.getFriendshipXPs().get(user.getAskedMarriage().getID());
        increaseMutualXP(user, user.getAskedMarriage(), -xp);
        user.getAskedMarriage().getEnergy().setEnergyCapacity(user.getAskedMarriage().getEnergy().getEnergyCapacity() / 2);
        user.setAskedMarriage(null);
        return new Result(true, "damn , so we breaking hearts now ?");
    }
    public Result talk(String username, String message) {
        User sender = App.getCurrentGame().getPlayingUser();
        User receiver = getUserBYName(username);
        if (receiver == null) {
            return new Result(false, "user not found");
        }
        if (notCloseEnough(sender, receiver)) {
            System.out.println("sender pos : " + sender.getCurrentTile().coordination.x + " " + sender.getCurrentTile().coordination.y);
            System.out.println("receiver pos : " + receiver.getCurrentTile().coordination.x + " " + receiver.getCurrentTile().coordination.y);
            return new Result(false, "you are not close enough, somehow you don't have a cell phone either");
        }
        Message m = new Message(sender.getID(), message, receiver.getID());
        sender.getMessages().add(m);
        receiver.getMessages().add(m);
        receiver.setHasNewMessages(true);
        increaseMutualXP(sender, receiver, 20);
        return new Result(true, "your message was sent");
    }
    public boolean notCloseEnough(User sender, User receiver) {
        return (Math.abs(receiver.getCurrentTile().coordination.x - sender.getCurrentTile().coordination.x) > 2 ||
            Math.abs(receiver.getCurrentTile().coordination.y - sender.getCurrentTile().coordination.y) > 2);
    }

    public void increaseMutualXP(User sender, User receiver, int i) {
        if (sender.getSpouse().equals(receiver)) {
            sender.getEnergy().setEnergyAmount(sender.getEnergy().getEnergyAmount() + 50);
            receiver.getEnergy().setEnergyAmount(receiver.getEnergy().getEnergyAmount() + 50);
        }
        sender.getFriendshipXPs().put(receiver.getID(), sender.getFriendshipXPs().getOrDefault(receiver.getID(), 100) + i);
        receiver.getFriendshipXPs().put(sender.getID(), receiver.getFriendshipXPs().getOrDefault(sender.getID(), 100) + i);
    }

    public Result talkHistory(String username) {
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        StringBuilder m = new StringBuilder();
        m.append("talk history with ").append(username).append(": \n═════════════════════════════════\n");
        for (Message message : me.getMessages()) {
            if (message.getSenderID() == friend.getID()) {
                m.append("received: \n").append(message.getMessage()).append("\n═════════════════════════════════\n");
            } else if (message.getReceiverID() == friend.getID()) {
                m.append("sent: \n").append(message.getMessage()).append("\n═════════════════════════════════\n");
            }
        }
        return new Result(true, m.toString());
    }

    public Result friendShipStatus(String username) {
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        int xp = me.getFriendshipXPs().getOrDefault(friend.getID(), 100);
        int level = xp / 100 - 1;
        return new Result(true, "friendship status for " + username +
            "\nfriendship level: " + level + "\nfriendship xp: " + xp);
    }

    public Result giftPlayer(String username, String ItemName, String amountString) {
        User user = App.getCurrentGame().getPlayingUser();
        User receiver = getUserBYName(username);
        int amount = Integer.parseInt(amountString);
        Map.Entry<ItemInterface, Integer> item = getItemFromBackPack(ItemName);
        if (receiver == null) {
            return new Result(false, "user not found");
        }
        if (item == null) {
            return new Result(false, "item not found");
        }
        if (item.getValue() < amount) {
            return new Result(false, "you don't have enough of this item");
        }
        if (!receiver.backPack.doesBackPackHasSpace()) {
            return new Result(false, "this player doesn't have enough space");
        }
        if (user.getFriendshipXPs().getOrDefault(receiver.getID(), 100) / 100 - 1 < 1) {
            return new Result(false, "you should be at least level one friends");
        }
        Gift gift = new Gift(user.getID(), receiver.getID(), item.getKey(), amount);
        user.getGifts().add(gift);
        receiver.getGifts().add(gift);
        addToBackPack(item, receiver.backPack, amount);
        removeFromBackPack(item, user.backPack, amount);
        return new Result(true, "gift has been sent");
    }

    public Result giftList() {
        User user = App.getCurrentGame().getPlayingUser();
        StringBuilder m = new StringBuilder();
        String rating;
        m.append("gift list:\n═════════════════════════════════\n");
        for (Gift gift : user.getGifts()) {
            if (gift.getSenderID() == user.getID()) {
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d", gift.getRate());
                m.append("you sent :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                    .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            } else {
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d", gift.getRate());
                m.append("you received :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                    .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
        }
        return new Result(true, m.toString());
    }

    public Result giftHistory(String username) {
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        StringBuilder m = new StringBuilder();
        String rating;
        m.append("gift history with ").append(username).append(": \n═════════════════════════════════\n");
        for (Gift gift : me.getGifts()) {
            if (gift.getRecipientID() == me.getID()) {
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d", gift.getRate());
                m.append("you received :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                    .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            } else if (gift.getSenderID() == me.getID()) {
                rating = gift.getRate() == -1 ? "not rated yet" : String.format("%d", gift.getRate());
                m.append("you sent :").append(gift.getAmount()).append(" of ").append(gift.getItemInterface().getName())
                    .append("\nrating: ").append(rating).append("\nID: ").append(gift.getID()).append("\n═════════════════════════════════\n");
            }
        }
        friend.setHasNewGift(true);
        return new Result(true, m.toString());
    }

    public Result rateGift(String giftIDString, String ratingString) {
        Gift gift = getGiftByID(Integer.parseInt(giftIDString));
        if (gift == null) {
            return new Result(false, "gift not found");
        }
        int rate = Integer.parseInt(ratingString);
        User sender = getUserByID(gift.getSenderID());
        User receiver = getUserByID(gift.getRecipientID());
        if (App.getCurrentGame().getPlayingUser().getID() != receiver.getID()) {
            return new Result(false, "you are not the one who got the gift");
        }
        if (rate < 1 || rate > 5) {
            return new Result(false, "rating should be between 1 and 5");
        }
        gift.setRate(rate);
        int xp = (rate - 3) * 10 + 15;
        increaseMutualXP(sender, receiver, xp);
        return new Result(true, "rating has been set");
    }

    public User getUserByID(int senderID) {
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getID() == senderID) {
                return player;
            }
        }
        return null;
    }
    public Result hug(String username) {
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        if (friend == null) {
            return new Result(false, "user not found");
        }
        if (notCloseEnough(me, friend)) {
            return new Result(false, "you are not close enough");
        }
        if (me.getFriendshipXPs().getOrDefault(friend.getID(), 100) / 100 - 1 < 2) {
            return new Result(false, "you should be at least level two friends");
        }
        increaseMutualXP(friend, me, 60);
        return new Result(true, "awww that's totally platonic");
    }

    public Result flower(String username) {
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        if (friend == null) {
            return new Result(false, "user not found");
        }
        if (notCloseEnough(me, friend)) {
            return new Result(false, "you are not close enough");
        }
        if (me.getFriendshipXPs().getOrDefault(friend.getID(), 100) / 100 - 1 < 3) {
            return new Result(false, "you should finish level two friendship");
        }
        Map.Entry<ItemInterface, Integer> item = getItemFromBackPack("BOUQUET");
        if (item == null) {
            return new Result(false, "you are not a magician you can't summon flowers");
        }
        if (!friend.backPack.doesBackPackHasSpace()) {
            return new Result(false, "your friend's backpack is full");
        }
        me.getLvl3FriendsID().add(friend.getID());
        friend.getLvl3FriendsID().add(me.getID());
        addToBackPack(item, friend.backPack, 1);
        removeFromBackPack(item, me.backPack, 1);
        return new Result(true, "wow you are really making a move don't you ?");
    }

    public Result askMarriage(String username) {
        User me = App.getCurrentGame().getPlayingUser();
        User friend = getUserBYName(username);
        if (friend == null) {
            return new Result(false, "user not found");
        }
        if (notCloseEnough(me, friend)) {
            return new Result(false, "you are not close enough");
        }
        Map.Entry<ItemInterface, Integer> ring = getItemFromBackPack("WEDDING_RING");
        if (ring == null) {
            return new Result(false, "you don't have a wedding ring");
        }
        if (!me.getGender().equals(Gender.male)) {
            return new Result(false, "you are not a male");
        }
        if (!friend.getGender().equals(Gender.female)) {
            return new Result(false, "that's fucking gay");
        }
        if (me.getFriendshipXPs().getOrDefault(friend.getID(), 100) < 400) {
            return new Result(false, "you are not intimate enough");
        }
        friend.setAskedMarriage(me);
        return new Result(true, "we are all rooting for you");
    }
    public void addToBackPack(Map.Entry<ItemInterface, Integer> item, BackPack backPack, int amount) {
        backPack.items.compute(item.getKey(), (k, v) -> v == null ? amount : v + amount);
    }

    public void removeFromBackPack(Map.Entry<ItemInterface, Integer> item, BackPack backPack, int amount) {
        backPack.items.compute(item.getKey(), (k, v) -> v - amount);
        if (item.getValue() < 0) {
            backPack.items.remove(item.getKey());
        }
    }
    public Gift getGiftByID(int giftID) {
        for (Gift gift : App.getCurrentGame().getPlayingUser().getGifts()) {
            if (giftID == gift.getID()) {
                return gift;
            }
        }
        return null;
    }
    public User getUserBYName(String userName) {
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.getUsername().equals(userName)) {
                return player;
            }
        }
        return null;
    }
    public Map.Entry<ItemInterface, Integer> getItemFromBackPack(String productName) {
        for (Map.Entry<ItemInterface, Integer> e : App.getCurrentGame().getPlayingUser().getBackPack().items.entrySet()) {
            if (e.getKey().getName().equalsIgnoreCase(productName)) {
                return e;
            }
        }
        return null;
    }
    public Map.Entry<ItemInterface, Integer> getItemFromBackPack(String productName, BackPack backPack) {
        for (Map.Entry<ItemInterface, Integer> e : backPack.items.entrySet()) {
            if (e.getKey().getName().equalsIgnoreCase(productName)) {
                return e;
            }
        }
        return null;
    }
}
