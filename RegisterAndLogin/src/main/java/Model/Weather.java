package Model;

import Model.CropClasses.Tree;
import Model.enums.Crops.PlantAble;
import Model.enums.Seasons;
import Model.enums.WeatherCondition;

public class Weather {
    private WeatherCondition weatherCondition;

    public void applySunnyWeather() {

    }

    public void applyRainWeather() {

    }

    public void applySnowWeather() {

    }

    public void applyStormWeather() {

    }

    public void hitTileWithThunder(Tile tile){
        tile.setGotHitWithThunder(true);
        if(tile.getPlanted().getClass() == Tree.class){
            //TODO:replace tree with coal
        }
    }
    public WeatherCondition randomWeatherCondition(Seasons season) {
        Game currentGame;//TODO: = new Game();
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
}
