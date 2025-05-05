package Model.TradeAndGift;

import Model.ItemInterface;
import Model.User;

public class Trade {
    User sender;
    User recipient;
    ItemInterface itemInterface;
    int amount;
    int price;
    ItemInterface priceItemInterface = null;
    int id;
    public static int idCounter;


}
