package Model;

import Model.CropClasses.Tree;
import Model.enums.Crops.Minerals;
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

    public static void hitTileWithThunder(Tile tile){
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
}
