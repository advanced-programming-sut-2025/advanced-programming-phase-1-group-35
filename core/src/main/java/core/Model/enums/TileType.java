package core.Model.enums;

public enum TileType {
    Soil("flooring/dirt.png"),
    Rock("rock/boulder.png"),
    Grass("flooring/grass.png"),
    Water("flooring/water.png"),
    OutSideFarm("flooring/grass.png"),
    BuildingTile("flooring/dirt.png"),
    BuildingWall("flooring/dirt.png"),
    Pathway("flooring/pathway.png"),
    ShippingBin("flooring/dirt.png"),
    ;

    String iconPath ;
    TileType(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }
}
