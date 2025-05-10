package Model.Tools;

import Model.*;
import Model.animal.Fish;
import Model.enums.CookingIngredient;
import Model.enums.animal.AnimalProductDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPack extends Tool {
    public ArrayList<Tool> tools;
    public ArrayList<Food> foods;
    public HashMap<CookingIngredient, Integer> ingredients;
    public HashMap<AnimalProductDetails, Integer> animalProducts;
    public HashMap<Fish, Integer> fishes;
    private int capacity = 12;

    public BackPack(String name) {
        super(name);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void removeItem(Item item) {

    }

    public Food getFood(String foodName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        for (Food food : foods) {
            if (food.recipe.getDisplayName().equals(foodName)) {
                return food;
            }
        }
        return null;
    }

    @Override
    public void reduceEnergy() {

    }
}
