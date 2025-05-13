package Model.TradeAndGift;

import Model.ItemInterface;
import Model.User;

public class Trade extends Gift{

    int price;
    ItemInterface priceItemInterface = null;
    int id;
    public static int idCounter;

    public Trade(int senderID, int recipientID, ItemInterface itemInterface, int amount,
                 int price, ItemInterface priceItemInterface) {
        super(senderID, recipientID, itemInterface, amount);
        this.price = price;
        this.priceItemInterface = priceItemInterface;
    }

}
