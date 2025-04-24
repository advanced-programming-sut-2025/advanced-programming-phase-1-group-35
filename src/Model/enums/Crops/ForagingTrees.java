package Model.enums.Crops;

public enum ForagingTrees implements PlantAble {
    ACORNS("Acorns"),
    MAPLE_SEEDS("Maple Seeds"),
    PINE_CONES("Pine Cones"),
    MAHOGANY_SEEDS("Mahogany Seeds"),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seeds");

    private final String name;

    ForagingTrees(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
