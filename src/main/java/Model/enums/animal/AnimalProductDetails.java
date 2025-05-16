package Model.enums.animal;

import Model.ItemInterface;
import Model.ItemInterface;

import Model.animal.Animal;
import Model.enums.ItemConstant;

public enum AnimalProductDetails implements ItemConstant,ItemInterface {
    Egg(50 , "egg"),
    BigEgg(95 , "big egg"),
    DuckEgg(95 , "duck egg"),
    DuckFeather(250 , "duck feather"),
    Wool(340 , "wool"),
    RabbitFoot(565 , "rabbit foot"),
    DinoEgg(350 , "dinosaur egg"),
    Milk(125 , "milk"),
    BigMilk(190 , "big milk"),
    GoatMilk(225 , "goat milk"),
    BigGoatMilk(345 , "big goat milk") ,
    Truffle(625 , "truffle"),;

    public final String name;
    public final int price ;
    AnimalProductDetails(int price , String name) {
        this.price = price;
        this.name = name;
    }
    public Model.animal.AnimalProduct getProduct() {
        return new Model.animal.AnimalProduct(this.name , this.price);
    }

    @Override
    public ItemInterface getItem() {
        return getProduct();
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
