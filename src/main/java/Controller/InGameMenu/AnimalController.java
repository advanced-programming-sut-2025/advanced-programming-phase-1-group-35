package Controller.InGameMenu;

import Controller.GameMenuController;
import Model.*;
import Model.Buildings.AnimalHouse;
import Model.FarmStuff.Farm;
import Model.animal.Animal;
import Model.animal.AnimalProduct;
import Model.animal.Fish;
import Model.enums.TileType;
import Model.enums.ToolTypes;
import Model.enums.WeatherCondition;
import Model.enums.animal.AnimalType;
import Model.enums.animal.FishType;

public class AnimalController {
    public Result buildAnimalHouse(String name, int x, int y) {
        if (!App.getCurrentGame().getMap().getTiles()[x][y].getContents().isEmpty()) {
            return new Result(false, "there are something else on this tile!");
        }
        Model.enums.Buildings.AnimalHouse animalHouseEnum = null;
        try {
             animalHouseEnum = Model.enums.Buildings.AnimalHouse.valueOf(name);
        }
        catch (Exception e) {

        }
        Tile[][] tiles = App.getCurrentGame().getMap().getTiles();
        for (int i = x; i < x + animalHouseEnum.width; i++) {
            for(int j = y; j < y + animalHouseEnum.height; j++) {
                if(!tiles[i][j].getTileType().equals(TileType.Soil) && !tiles[i][j].getTileType().equals(TileType.Grass)) {
                    return new Result(false, "there is something else on tile : " + "<" + i +  "," + j + ">");
                }
            }
        }
        AnimalHouse animalHouse = new AnimalHouse(animalHouseEnum.type, animalHouseEnum.level);
        App.getCurrentGame().getPlayingUser().getFarm().animalHouses.add(animalHouse);
        animalHouse.setFarm(App.getCurrentGame().getPlayingUser().getFarm());
        animalHouse.setFloorTiles(new Tile[animalHouseEnum.width][animalHouseEnum.height]);
        animalHouse.placeBuilding('ǂ', x, y, animalHouseEnum.width, animalHouseEnum.height, tiles);

        return new Result(true, "your " + name + " has been built!");
    }

    public Result buyAnimal(String animal, String name) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        AnimalType type;
        AnimalHouse house = null;
        try {
            type = AnimalType.valueOf(animal);
        } catch (IllegalArgumentException e) {
            return new Result(false, "invalid animal type");
        }
        house = type.getAnimalHouse(player, type);
        if (farm.isAnimalNameExist(name)) {
            return new Result(false, "animal name has already been used!");
        } else if (house == null) {
            return new Result(false, "no animal house!");
        }
        int x = 0,y = 0;
        house.thisHouseAnimals.add(type.createAnimal(name));
        farm.animals.add(type.createAnimal(name));
        return new Result(true, "animal " + name + " has been bought!");
    }

    public Result nazTheAnimal(String animalName) {
        AnimalHouse house = null;
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        if (!farm.isAnimalNameExist(animalName)) {
            return new Result(false, "there is no animal with that name!");
        }
        Animal animal = farm.findAnimal(animalName);
        if (Math.abs(animal.location.x - game.getPlayingUser().getCurrentTile().getCoordination().x) > 1 ||
                Math.abs(animal.location.y - game.getPlayingUser().getCurrentTile().getCoordination().y) > 1) {
            return new Result(false, "You are not close to the animal");
        }
        animal.setFriendship(animal.getFriendship() + 15);
        animal.setNazToday(true);
        return new Result(true, "you naz " + animalName + " successfully!");
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
        App.getCurrentGame().getMap().changeTileSymbol(App.getCurrentGame().getMap().getTiles()[x][y], '.', animal.getName().charAt(0));
        animal.location = new Point(x, y);
        return new Result(true, animal.getName() + " has ate grass!");
    }

    public Result feedByHay(String animalName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        Animal animal = farm.findAnimal(animalName);
        if (animal == null) {
            return new Result(false, "there is no animal with that name!");
        } else if (animal.isFeedToday()) {
            return new Result(false, "you ate today!");
        }
        animal.setFeedToday(true);
        animal.setCanProduceTomorrow(true);
        return new Result(true, animalName + " has ate hay!");
    }

    public Result produces() {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        StringBuilder output = new StringBuilder();
        for (Animal animal : farm.animals) {
            output.append(animal.getName()).append(":  ");
            for (AnimalProduct product : animal.getProducts()) {
                output.append(product.getName()).append("  ");
            }
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
            return new Result(false, animalName + " can not yield today! try to be nicer with him");
        } else if (animal.isCollectedToday()) {
            return new Result(false, "you already collect its products");
        }
        player.backPack.items.put(animal.getProducts()[0],
                player.backPack.items.getOrDefault(animal.getProducts()[0], 0) + 1);
        animal.setDaysPastLastProduction(0);
        animal.setCollectedToday(true);
        return new Result(true, animal.getName() + " has collected its products! it was " +
                animal.getProductionRate());
    }

    public Result sellAnimal(String animalName) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        Animal animal = farm.findAnimal(animalName);
        AnimalHouse house = null;
        if (animal == null) {
            return new Result(false, "there is no animal with that name!");
        }
        house = animal.getAnimalType().getAnimalHouse(player, animal.getAnimalType());
        if (house == null) {
            return new Result(false, "animal house not found!");
        }
        int price = animal.getSellingPrice();
        farm.animals.remove(animal);
        house.thisHouseAnimals.remove(animal);
        player.setMoney(player.getMoney() + price);
        return new Result(true, "you sold " + animalName + "! price: " + price);
    }

    public Result fishing(String poleName) {
        if (!new GameMenuController().isCloseToSea()) {
            return new Result(false, "you are not near to a sea!");
        } else if (!App.getCurrentGame().getPlayingUser().getCurrentTool().getToolType().equals(ToolTypes.FISHING_ROD)) {
            return new Result(false, "you are not equipped by a fishing pole!");
        }
        FishType randomFish = FishType.getRandomFish();
        int fishCount = 0;
        double m = switch (App.getCurrentGame().getWeather().getWeatherCondition().name()) {
            case "sunny" -> 1.5;
            case "rain" -> 1.2;
            case "storm" -> 0.5;
            default -> 1;
        };
        double fishingSkill = App.getCurrentGame().getPlayingUser().getFishingSkill().getCurrentLevel();
        fishCount = (int) Math.ceil((Math.random()) * m * (fishingSkill + 2));
        fishCount = Math.max(fishCount, 6);

        double poleCoefficient = 1;
        double fishQuality = (Math.random() * (fishingSkill + 2) * poleCoefficient) / (7 - m);
        double price = 0.0;
        if (fishQuality >= 0.5 && fishQuality < 0.7) {
            price = randomFish.getBasePrice() * 1.25;
        } else if (fishQuality >= 0.7 && fishQuality < 0.9) {
            price = randomFish.getBasePrice() * 1.5;
        } else if (fishQuality >= 0.9) {
            price = randomFish.getBasePrice() * 2;
        }

        Fish fish = new Fish(randomFish.getName(), (int) price, randomFish.getSeason(), "normal");
        App.getCurrentGame().getPlayingUser().backPack.items.put(fish,
                App.getCurrentGame().getPlayingUser().backPack.items.getOrDefault(fish, 0) + fishCount);
        return new Result(true, fishCount + " of " + fish.getName() + " has been caught!");
    }

    public Result cheatFriendshipAnimal(String animalName, int amount) {
        Game game = App.getCurrentGame();
        User player = game.getPlayingUser();
        Farm farm = player.getFarm();
        Animal animal = farm.findAnimal(animalName);
        if (animal == null) {
            return new Result(false, "there is no animal with that name!");
        }
        animal.setFriendship(amount);
        return new Result(true, "friendship with " + animalName + " is now " + animal.getFriendship());
    }

}
