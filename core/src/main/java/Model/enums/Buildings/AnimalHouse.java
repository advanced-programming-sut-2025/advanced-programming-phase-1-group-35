package Model.enums.Buildings;

public enum AnimalHouse {
    Coop("coop", 1, 6, 12, "buildings/Coop.png"),
    Barn("barn", 1, 8, 14, "buildings/Barn.png");

    public final String type;
    public final int level;
    public final int height, width;
    public final String texturePath;

    AnimalHouse(String type, int level, int height, int width, String texturePath) {
        this.type = type;
        this.level = level;
        this.height = height;
        this.width = width;
        this.texturePath = texturePath;
    }

    public Model.Buildings.AnimalHouse getAnimalHouse() {
        return new Model.Buildings.AnimalHouse(type, level);
    }
}
