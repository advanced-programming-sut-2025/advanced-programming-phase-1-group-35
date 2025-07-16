package Model.enums.Buildings;

import Model.Buildings.AnimalHouse;

public enum AnimalHouseEnum {
    Coop("coop", 1, 6, 12, "buildings/Coop.png"),
    Barn("barn", 1, 8, 14, "buildings/Barn.png");

    public final String type;
    public final int level;
    public final int height, width;
    public final String texturePath;

    AnimalHouseEnum(String type, int level, int height, int width, String texturePath) {
        this.type = type;
        this.level = level;
        this.height = height;
        this.width = width;
        this.texturePath = texturePath;
    }

    public AnimalHouse getAnimalHouse() {
        return new AnimalHouse(type, level);
    }
}
