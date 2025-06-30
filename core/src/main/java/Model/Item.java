package Model;

public class Item implements ItemInterface{
    private int price;
    private final String name;

    public Item(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override

    public int getPrice(){
        return price;
    }
}
