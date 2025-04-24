package Model.enums;

import Model.Weather;

import java.util.List;

public enum Seasons {
    Spring(new WeatherCondition[]{
            WeatherCondition.sunny,
            WeatherCondition.rain,
            WeatherCondition.storm},
            1),
    Summer(new WeatherCondition[]{
            WeatherCondition.rain,
            WeatherCondition.sunny,
            WeatherCondition.storm},
            2),
    Fall(new WeatherCondition[]{
            WeatherCondition.rain,
            WeatherCondition.sunny,
            WeatherCondition.storm
    },
            3),
    Winter(new WeatherCondition[]{
            WeatherCondition.sunny,
            WeatherCondition.snow,
    },
            4);

    WeatherCondition[] conditions;
    int numberInorder;
    Seasons(WeatherCondition[] conditions, int number) {
        this.conditions = conditions;
        this.numberInorder = number;
    }
    public Seasons findNextSeason(Seasons currentSeason){
        for(Seasons season : Seasons.values()){
            if(season.numberInorder == currentSeason.numberInorder+1 ||
                    season.numberInorder == currentSeason.numberInorder-3){
                return season;
            }
        }
        return null;
    }
    public WeatherCondition[] getWeathers(Seasons season) {
        return season.conditions;
    }
}
