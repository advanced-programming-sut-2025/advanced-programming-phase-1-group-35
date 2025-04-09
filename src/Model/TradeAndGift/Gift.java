package Model.TradeAndGift;

import Model.TradeAndGift.Trade;

public class Gift extends Trade {
    private static int idCounter;
    private int rate;

    public static int getIdCounter() {
        return idCounter;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
