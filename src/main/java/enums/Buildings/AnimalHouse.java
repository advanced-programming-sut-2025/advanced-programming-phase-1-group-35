package enums.Buildings;

public enum AnimalHouse {
    Coop,
    BigCoop,
    DeluxeCoop,
    Barn,
    BigBarn,
    DeluxeBarn;

    public models.Buildings.AnimalHouse getAnimalHouse() {
        return new models.Buildings.AnimalHouse();
    }
}
