package Model.enums.Crops;

public enum ForagingTrees {
    ACORNS("Acorns", "Special"),
    MAPLE_SEEDS("Maple Seeds", "Special"),
    PINE_CONES("Pine Cones", "Special"),
    MAHOGANY_SEEDS("Mahogany Seeds", "Special"),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seeds", "Special");

    private final String name;
    private final String category;

    ForagingTrees(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
