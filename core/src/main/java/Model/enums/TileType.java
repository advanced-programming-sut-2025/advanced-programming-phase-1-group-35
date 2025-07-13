package Model.enums;

public enum TileType {
    Soil("flooring/dirt.png"),
    Rock("rock/boulder.png"),
    Grass("flooring/grass.png"),
    Water("flooring/water.png"),
    OutSideFarm("flooring/grass.png"),
    BuildingTile("flooring/water.png"),
    BuildingWall("flooring/water.png"),
    Pathway("flooring/pathway.png"),
    ShippingBin("flooring/water.png"),
    ;

    String iconPath ;
    TileType(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }
}
