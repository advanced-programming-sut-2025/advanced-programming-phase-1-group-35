package Controller.InGameMenu;

import Model.*;

import Model.Shops.Shop;

public class ShopMenuController {
    public Shop shop;
    public ShopMenuController() {
        if(App.getCurrentGame() == null)return;
        if(App.getCurrentGame().getPlayingUser() == null)return;
        Tile tile = App.getCurrentGame().getPlayingUser().getCurrentTile();
        shop = findShopByTile(tile);
    }

    public Shop findShopByTile(Tile tile) {
        for (Shop s : App.getCurrentGame().getMap().shops) {
            for (int i = 0; i < s.getBuilding().getFloorTiles().length; i++) {
                for (int j = 0; j < s.getBuilding().getFloorTiles().length; j++) {
                    if(s.getBuilding().getFloorTiles()[i][j].equals(tile)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

    public void showAllProducts() {

    }
    public void showAvailableProducts() {

    }
    public Result purchase(){
        return null;
    }


    public Result CheatAdd(){
        return null;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
