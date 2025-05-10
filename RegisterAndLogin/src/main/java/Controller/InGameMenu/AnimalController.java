package Controller.InGameMenu;

import Model.App;
import Model.Buildings.AnimalHouse;
import Model.FarmStuff.Farm;
import Model.Game;
import Model.Result;
import Model.User;
import Model.animal.Animal;
import Model.enums.WeatherCondition;
import Model.enums.animal.AnimalType;

import java.util.Arrays;

public class AnimalController {
    public Result buildAnimalHouse(String name, int x, int y) {
        if (false) {
            // TODO : if tile is not empty or not in player's farm
            return new Result(false, "there are something else on this tile!");
        }
        try {
            Model.enums.Buildings.AnimalHouse.valueOf(name);
        } catch (IllegalArgumentException e) {
            return new Result(false, "false type!");
        }

        if (false) {
            // TODO : if dont have enough money or resource
            return new Result(false, "not enough resources!");
        }

        // TODO : build a new bulding and place it in map
        return new Result(true, "your " + name + " has been built!");
    }

    public Result buyAnimal(String animal, String name) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        AnimalType type;
        AnimalHouse house = null; // TODO : find the animal house in the map and set it to this object
        try {
            type = AnimalType.valueOf(animal);
        } catch (IllegalArgumentException e) {
            return new Result(false, "invalid animal type");
        }
        if (farm.isAnimalNameExist(name)) {
            return new Result(false, "animal name has already been used!");
        } else if (false) {
            // TODO
            return new Result(false, "not enough money!");
        } else if (house == null) {
            return new Result(false, "no animal house!");
        }

        house.thisHouseAnimals.add(type.createAnimal(name));
        farm.animals.add(type.createAnimal(name));
        return new Result(true, "animal " + name + " has been bought!");
    }

    public Result nazTheAnimal(String animalName) {
        // TODO : things that effect friendship with animals like spending night out
        AnimalHouse house = null; // TODO : find the animal house in the map and set it to this object
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        if (!farm.isAnimalNameExist(animalName)) {
            return new Result(false, "there is no animal with that name!");
        }
        Animal animal = farm.findAnimal(animalName);
        if (false) {
            // TODO : if not around that animal
            return new Result(false, "you are close to " + animalName + "!");
        }
        animal.setFriendship(animal.getFriendship() + 15);
        return new Result(true, "you naz " + animalName + "successfully!");
    }

    public Result seeAnimalsCondition() {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        StringBuilder output = new StringBuilder();
        for (Animal animal : farm.animals) {
            output.append(animal.getName()).append("   friendship: ").append(animal.getFriendship());
            output.append("  naz: ").append(animal.isNazToday());
            output.append("  feed: ").append(animal.isFeedToday());
            output.append("\n");
        }
        return new Result(true, output.toString());
    }

    public Result shepherdAnimal(String animalName, int x, int y) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        if (!farm.isAnimalNameExist(animalName)) {
            return new Result(false, "there is no animal with that name!");
        } else if (game.getWeather().getWeatherCondition() == WeatherCondition.snow ||
                game.getWeather().getWeatherCondition() == WeatherCondition.rain ||
                game.getWeather().getWeatherCondition() == WeatherCondition.storm) {
            return new Result(false, "weather condition is not good for outside shepherd!");
        }
        Animal animal = farm.findAnimal(animalName);
        animal.setFeedToday(true);
        animal.setCanProduceTomorrow(true);
        return new Result(true, animal.getName() + "has ate grass!");
    }

    public Result feedByHay(String animalName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        Animal animal;
        // TODO : update hay resource
        try {
            animal = farm.findAnimal(animalName);
        } catch (NullPointerException e) {
            return new Result(false, "there is no animal with that name!");
        }
        animal.setFeedToday(true);
        animal.setCanProduceTomorrow(true);
        return new Result(true, animalName + "has ate hay!");
    }

    public Result produces() {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        StringBuilder output = new StringBuilder();
        for (Animal animal : farm.animals) {
            output.append(animal.getName()).append(Arrays.toString(animal.getProducts()));
            output.append("\n");
        }
        return new Result(true, output.toString());
    }

    public Result collectProducts(String animalName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        Animal animal = farm.findAnimal(animalName);
        if (animal == null) {
            return new Result(false, "there is no animal with that name!");
        } else if (!animal.isCanProduceTomorrow()) {
            return new Result(false, animalName + "can not yield today! try to be nicer with him");
        } else if (animal.getDaysPastLastProduction() == 0) {
            return new Result(false, animalName + "you already collect its products");
        }
        player.backPack.animalProducts.put(animal.getProducts()[0].getProductDetails(), animal.getProductionRate());
        animal.setDaysPastLastProduction(0);
        return new Result(true, animal.getName() + "has collected its products! it was " +
                animal.getProductionRate() + " units of " + animal.getProducts()[0].getProductDetails().name);
    }

    public Result sellAnimal(String animalName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        Animal animal = farm.findAnimal(animalName);
        AnimalHouse house = null; // TODO : find the animal house in the map and set it to this object
        if (animal == null) {
            return new Result(false, "there is no animal with that name!");
        }
        int price = animal.getSellingPrice();
        farm.animals.remove(animal);
        house.thisHouseAnimals.remove(animal);
        player.setMoney(player.getMoney() + price);
        return new Result(true, "you sold " + animalName + "! price: " + price);
    }

}
