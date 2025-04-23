package Model.enums.Buildings;

public enum AnimalHouse {
    Coop,
    BigCoop,
    DeluxeCoop,
    Barn,
    BigBarn,
    DeluxeBarn;

    public Model.Buildings.AnimalHouse getAnimalHouse() {
        return new Model.Buildings.AnimalHouse();
    }
}
