package models.TradeAndGift;

import models.Item;
import models.User;

public class Trade {
    User sender;
    User recipient;
    Item item;
    int amount;
    int price;
    Item priceItem = null;
    int id;
    public static int idCounter;


}
