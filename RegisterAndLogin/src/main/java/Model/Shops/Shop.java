package Model.Shops;

import Model.Item;
import Model.NPCs.NPC;

import java.util.HashMap;

public abstract class Shop {
    protected String name;
    protected String building;
    protected NPC owner;

    protected HashMap<Item, Integer> permanentStock = new HashMap<>();

    protected void showProducts(){}
}
