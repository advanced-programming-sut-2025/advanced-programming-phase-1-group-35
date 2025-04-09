package Model.enums;

public enum ToolMaterial {
    Basic(1),
    Copper(2),
    Iron(3),
    Gold(4),
    Iridium(5);

    final int level;

    ToolMaterial(int level) {
        this.level = level;
    }
}
