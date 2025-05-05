package Model.Shops;

import Model.Buildings.Building;
import Model.NPCs.NPC;

import java.time.LocalTime;
import java.util.ArrayList;

public class Shop {
    private String name;
    private Building building;
    private NPC owner;
    private LocalTime OpeningTime;
    private LocalTime ClosingTime;
    private ArrayList<ShopItem> products = new ArrayList<>();





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public NPC getOwner() {
        return owner;
    }

    public void setOwner(NPC owner) {
        this.owner = owner;
    }

    public LocalTime getOpeningTime() {
        return OpeningTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        OpeningTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        ClosingTime = closingTime;
    }

    public ArrayList<ShopItem> getProducts() {
        return products;
    }
}
