package Model;

import Model.enums.Crops.Minerals;

public class Mineral implements ItemInterface{
    private String name;
    private String description;
    private int price;
    private Tile tile;
    private char symbol = 'r';
    public Mineral(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Mineral(Minerals minerals, Tile tile) {
        this.name = minerals.getName();
        this.description = minerals.getDescription();
        this.price = minerals.getPrice();
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}
