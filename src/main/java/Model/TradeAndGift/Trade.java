package Model.TradeAndGift;

import Controller.GameMenuController;
import Model.ItemInterface;
import Model.User;

public class Trade extends Gift{
    private int price;
    private ItemInterface targetItem = null;
    private int targetAmount;
    private boolean isAnswered = false;
    private boolean accepted = false;
    private int id;
    public static int idCounter;

    public Trade(int senderID, int recipientID, ItemInterface itemInterface, int amount,
                 int price, ItemInterface targetItem, int targetAmount) {
        super(senderID, recipientID, itemInterface, amount);
        this.price = price;
        this.targetItem = targetItem;
        this.targetAmount = targetAmount;
        this.id = idCounter++;
    }

    public String toString() {
        GameMenuController controller = new GameMenuController();
        String m = "sender : " + controller.getUserByID(senderID) + "\nrecipient : " + controller.getUserByID(recipientID) +
        "\nitem: " + itemInterface + "\namount: " + amount + "is answered: " + isAnswered;
        if(isAnswered){
            m += accepted ? "   accepted" : "   rejected";
        }
        if(price == 0){
            m += "\ntarget item: " + targetItem + "\ntarget amount: " + targetAmount;
        }
        else{
            m += "\nprice: " + price ;
        }
        return m;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemInterface getTargetItem() {
        return targetItem;
    }

    public void setTargetItem(ItemInterface targetItem) {
        this.targetItem = targetItem;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
