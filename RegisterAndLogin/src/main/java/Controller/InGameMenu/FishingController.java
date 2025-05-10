package Controller.InGameMenu;

import Model.App;
import Model.Result;
import Model.animal.Fish;
import Model.enums.animal.FishType;

import java.util.ArrayList;

public class FishingController {
    public Result fishing(String poleName) {
        if (false) {
            // TODO
            return new Result(false, "you are not near to a sea!");
        } else if (false) {
            // TODO : search the backpack for this pole
            return new Result(false, "fish pole not found!");
        }
        FishType randomFish = FishType.getRandomFish();
        int fishCount = 0;
        double m = switch (App.getCurrentGame().getWeather().getWeatherCondition().name()) {
            case "sunny" -> 1.5;
            case "rain" -> 1.2;
            case "storm" -> 0.5;
            default -> 1;
        };
        double fishingSkill = 1; //TODO
        fishCount = (int) Math.ceil((Math.random()) * m * (fishingSkill + 2));
        fishCount = Math.max(fishCount, 6);

        // TODO : impact of skill and weather on price and quality

        Fish fish = new Fish(randomFish.getName(), randomFish.getBasePrice(), randomFish.getSeason(), "normal");
        App.getCurrentGame().getPlayingUser().backPack.fishes.put(fish,
                App.getCurrentGame().getPlayingUser().backPack.fishes.getOrDefault(fish, 0) + fishCount);
        return new Result(true, fishCount + " of " + fish.getName() + "has been caught!");
    }
}
