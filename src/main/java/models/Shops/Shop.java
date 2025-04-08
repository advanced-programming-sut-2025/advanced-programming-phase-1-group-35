package models.Shops;

import models.Item;
import models.NPCs.NPC;

import java.util.HashMap;

public abstract class Shop {
    protected String name;
    protected String building;
    protected NPC owner;

    protected HashMap<Item, Integer> permanentStock = new HashMap<>();

    protected void showProducts(){}
}
