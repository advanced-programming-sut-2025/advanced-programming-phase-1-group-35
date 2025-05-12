package Model;

public class Mineral implements ItemInterface{
    private String name;
    private String description;
    private int price;

    public Mineral(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
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
