package Controller.InGameMenu;

import Model.*;

import Model.Shops.Shop;
import Model.Shops.ShopItem;
import Model.enums.Menu;

import java.io.IOException;

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
                for (int j = 0; j < s.getBuilding().getFloorTiles()[i].length; j++) {
                    if(s.getBuilding().getFloorTiles()[i][j].equals(tile)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

    public Result showAllProducts() {
        StringBuilder output = new StringBuilder();
        for (ShopItem product : shop.getProducts()) {
            output.append("name: ").append(product.getName()).append("\n").append("price: ").append(product.getPrice()).append("\n")
                    .append("description: ").append(product.getDescription()).append("\n").append("daily limit: ")
                    .append(product.getDailyLimit()).append("\n").append("════════════════════════════════\n");
        }
        return new Result(true, "products: \n════════════════════════════════\n" + output.toString());
    }
    public Result showAvailableProducts() {
        StringBuilder output = new StringBuilder();
        for (ShopItem product : shop.getProducts()) {
            if((product.getSeason() != null &&
                    !product.getSeason().equals(App.getCurrentGame().getGameCalender().getSeason())) ||
                    (product.getDailyBoughtCount() >= product.getDailyLimit())) continue;
            output.append("name: ").append(product.getName()).append("\n").append("price: ").append(product.getPrice()).append("\n")
                    .append("description: ").append(product.getDescription()).append("\n").append("daily limit: ")
                    .append(product.getDailyLimit()).append("\n").append("════════════════════════════════\n");
        }
        return new Result(true, "products: \n════════════════════════════════\n" + output.toString());
    }
    public Result purchase(String name , String countString) throws IOException {
        int count = Integer.parseInt(countString);
        ShopItem item = findShopItemByName(name);
        User user = App.getCurrentGame().getPlayingUser();
        if(item == null) {
            return new Result(false, "No such item");
        }
        if(count > item.getDailyLimit() - item.getDailyBoughtCount()){
            return new Result(false, "daily limit exceeded");
        }
        if(item.getPrice() > user.getMoney()){
            return new Result(false, "You don't have enough money");
        }
        Object product = item.makeInstance() ;
        if(product == null){
            return new Result(false, "purchase failed");
        }
        if(product instanceof Result){
            return (Result) product;
        }
        user.getBackPack().items.compute((ItemInterface) product , (k, v) -> v == null ? count : v + count );
        item.setDailyBoughtCount(item.getDailyBoughtCount() + count);
        return new Result(true, "you successfully purchased " + count + " of " + item.getName());
    }
    public ShopItem findShopItemByName(String name) {
        for (ShopItem product : shop.getProducts()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public Result cheatAddMoney(String amountString){
        int amount = Integer.parseInt(amountString);
        App.getCurrentGame().getPlayingUser().setMoney(amount + App.getCurrentGame().getPlayingUser().getMoney());
        return new Result(true, "added " + amount + " to your account");
    }

    public Result goBack(){
        App.setCurrentMenu(Menu.GameMenu);
        return new Result(true, "going back to game menu");
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
