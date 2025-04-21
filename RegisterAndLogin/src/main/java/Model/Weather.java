package Model;

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
    }
    public WeatherCondition randomWeatherCondition(Seasons season) {
        Game currentGame = new Game();
        int randomNumber = (int)((Math.random()*4) + 1);
        for(WeatherCondition condition : WeatherCondition.values()){
            if(condition.getNumber() == randomNumber){
                return condition;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "Weather [weatherCondition=" + weatherCondition;
    }
}
