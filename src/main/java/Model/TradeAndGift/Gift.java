package Model.TradeAndGift;

import Model.ItemInterface;
import Model.TradeAndGift.Trade;

public class Gift {
    protected int senderID;
    protected int recipientID;
    protected ItemInterface itemInterface;
    protected int amount;
    protected int id;
    public static int idCounter = 1;
    private int rate = -1;

    public Gift(int senderID, int recipientID, ItemInterface itemInterface, int amount) {
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.itemInterface = itemInterface;
        this.amount = amount;
        this.id = idCounter++;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getRecipientID() {
        return recipientID;
    }

    public ItemInterface getItemInterface() {
        return itemInterface;
    }

    public int getAmount() {
        return amount;
    }

    public int getRate() {
        return rate;
    }

    public int getID() {
        return id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
