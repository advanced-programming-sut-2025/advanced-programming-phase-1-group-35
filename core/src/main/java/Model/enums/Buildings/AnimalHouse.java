package Model.enums.Buildings;

public enum AnimalHouse {
    Coop("coop", 1, 6 , 3),
    BigCoop("coop",2, 6, 3),
    DeluxeCoop("coop",3, 6, 3),
    Barn("barn", 1, 7, 4),
    BigBarn("barn", 2,7, 4),
    DeluxeBarn("barn", 3,7,4);

    public final String type;
    public final int level;
    public final int height , width;

    AnimalHouse(String type, int level, int height, int width) {
        this.type = type;
        this.level = level;
        this.height = height;
        this.width = width;
    }

    public Model.Buildings.AnimalHouse getAnimalHouse() {
        return new Model.Buildings.AnimalHouse(type, level);
    }
}
