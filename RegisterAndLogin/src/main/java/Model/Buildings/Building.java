package Model.Buildings;

import Model.FarmStuff.Farm;
import Model.Tile;
import Model.enums.TileType;

import java.awt.*;

public class Building {
    protected Farm farm ;
    protected Tile[][] floorTiles;
    protected Tile doorTile;
    String name;
    protected Rectangle bounds = new Rectangle();
    int buildingID;
    protected int buildingIdCounter;

    public void placeBuilding(char symbol , int x , int y , int width , int height , Tile[][] tiles){
        bounds.setBounds(farm.getBounds().x + x , farm.getBounds().y + y , width , height);
        for (int i = bounds.x ; i < bounds.x + bounds.width ; i++) {
            for (int j = bounds.y ; j < bounds.y + bounds.height ; j++) {
                tiles[i][j].setSymbol(symbol);
                tiles[j][i].setWalkable(false);
                tiles[j][i].setTileType(TileType.BuildingTile);
                if(i != bounds.x && j != bounds.y && i != bounds.x + bounds.width - 1 &&
                        j != bounds.y + bounds.height - 1) {
                    floorTiles[i - bounds.x - 1][j - bounds.y - 1] = tiles[i][j];
                    tiles[i][j].setWalkable(true);
                }
            }
        }
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
