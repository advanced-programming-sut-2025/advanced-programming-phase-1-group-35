package Model.enums;

public enum WeatherCondition {
    sunny(1),
    rain(2),
    storm(3),
    snow(4);

    int number;
    WeatherCondition(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
}
