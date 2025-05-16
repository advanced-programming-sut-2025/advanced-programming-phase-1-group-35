package Model;

import Model.CropClasses.Tree;
import Model.enums.Crops.Minerals;
import Model.enums.Crops.PlantAble;
import Model.enums.Seasons;
import Model.enums.WeatherCondition;

public class Weather {
    private WeatherCondition weatherCondition;
    private WeatherCondition tomorrowCondition;

//    public Result hitTileWithThunder(Tile tile){
//        if(tile == null){
//            return new Result(false, "coordination unavailable");
//        }
//    }

    public void applySnowWeather() {

    }

    public void applyStormWeather() {

    }

    public static Result hitTileWithThunder(Tile tile){
        tile.setGotHitWithThunder(true);
        if(tile.getPlanted().getClass() == Tree.class){
            App.getCurrentGame().getMap().getCrops().remove(tile.getPlanted());
            App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(tile.getPlanted());
            tile.getContents().remove(tile.getPlanted());
            tile.setPlanted(null);
            tile.getContents().add(Minerals.COAL);
            tile.setContentSymbol('~');
            tile.setSymbol('~');
        }
        return new Result(true, "tile with x=" + tile.coordination.x + " y=" + tile.coordination.y + " got hit thunder");
    }
    public Weather() {
        weatherCondition = randomWeatherCondition(Seasons.Spring);
        tomorrowCondition = randomWeatherCondition(Seasons.Spring);
    }

    public Result cheatWeatherSet(String weatherString){
        WeatherCondition weather;
        try {
            weather = WeatherCondition.valueOf(weatherString);
        }
        catch(IllegalArgumentException e){
            return new Result(false, "invalid weather string");
        }
        setTomorrowCondition(weather);
        return new Result(true, "cheater weather set tomorrow's weather : " + tomorrowCondition);
    }

    public WeatherCondition randomWeatherCondition(Seasons season) {
        Game currentGame = App.getCurrentGame();
        int randomNumber = (int)((Math.random()*4) + 1);
        for(WeatherCondition condition : WeatherCondition.values()){
            if(condition.getNumber() == randomNumber){
                return condition;
            }
        }
        return null;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    @Override
    public String toString() {
        return "Weather [weatherCondition=" + weatherCondition;
    }

    public WeatherCondition getTomorrowCondition() {
        return tomorrowCondition;
    }

    public void setTomorrowCondition(WeatherCondition tomorrowCondition) {
        this.tomorrowCondition = tomorrowCondition;
    }
}
