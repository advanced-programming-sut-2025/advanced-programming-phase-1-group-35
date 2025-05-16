package Model;

import Controller.GameMenuController;
import Controller.InGameMenu.FarmingController;
import Model.CropClasses.Crop;
import Model.CropClasses.Tree;
import Model.Shops.Shop;
import Model.Shops.ShopItem;
import Model.enums.Seasons;
import View.GameMenu;

import java.io.IOException;
import java.time.LocalDateTime;

public class GameCalender {
    private LocalDateTime gameDateTime;
    private Seasons season;

    public GameCalender() {
        this.gameDateTime = LocalDateTime.of(2025, 1, 1, 9, 0);
        this.season = Seasons.Spring;
    }

    public LocalDateTime getGameDateTime() {
        return gameDateTime;
    }

    public void setGameDateTime(LocalDateTime gameDateTime) {
        this.gameDateTime = gameDateTime;
    }

    public Seasons getSeason() {
        return season;
    }

    public void setSeason(Seasons season) {
        this.season = season;
    }

    public void updateTimeAndDateAndSeasonAfterTurns() throws IOException {
        gameDateTime = gameDateTime.plusHours(1);
        if (gameDateTime.getHour() == 22) {
            gameDateTime = gameDateTime.plusHours(11);
            goToNextDay();
        }
        if (gameDateTime.getDayOfMonth() == 29) {
            goToNextSeason();
            gameDateTime = LocalDateTime.of(2025, 1, 1, 9, 0);
        }
    }

    private void goToNextSeason() {
//        if (season == Seasons.Spring) {
//            season = Seasons.Summer;
//        } else if (season == Seasons.Summer) {
//            season = Seasons.Fall;
//        } else if (season == Seasons.Fall) {
//            season = Seasons.Winter;
//        } else if (season == Seasons.Winter) {
//            season = Seasons.Spring;
//        }
    season = season.findNextSeason(season);
    for(Crop crop : App.getCurrentGame().getMap().getCrops()) {
        if(!crop.getSeason().equals(season)) {
            crop.getCropTile().setPlanted(null);
            crop.getCropTile().getContents().remove(crop);
        }
    for(Tree tree : App.getCurrentGame().getMap().getTrees()) {
        if(!tree.getSeasons().contains(season)) {
            tree.getTile().setPlanted(null);
            tree.getTile().getContents().remove(crop);
        }
    }
    }
    }

    public void goToNextDay() throws IOException {
        Game game = App.getCurrentGame();
        game.getWeather().setWeatherCondition(game.getWeather().getTomorrowCondition());
        game.getWeather().setTomorrowCondition(game.getWeather().randomWeatherCondition(game.getGameCalender().getSeason()));
        gameDateTime = gameDateTime.plusDays(1).withHour(9).withMinute(0);
        for(Crop crop: App.getCurrentGame().getMap().getCrops()){
            crop.grow();
        }
        for(Tree tree : App.getCurrentGame().getMap().getTrees()) {
            tree.grow();
            if(tree.getTile().isFertilized()){
                tree.grow();
            }
        }
        if (gameDateTime.getDayOfMonth() == 29) {
            goToNextSeason();
            gameDateTime = LocalDateTime.of(2025, 1, 1, 9, 0);
        }
        FarmingController farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
        farmingController.addForagingCrop();
        farmingController.addForagingSeeds();
        farmingController.addForAgingTree();
        for (Shop shop : App.getCurrentGame().getMap().getShops()) {//restock the shops
            for (ShopItem product : shop.getProducts()) {
                product.setDailyBoughtCount(0);
            }
        }
        for (User player : App.getCurrentGame().getPlayers()) {
            player.setMoney(player.getIncome() + player.getMoney());
            player.setIncome(0);
            if(!player.getFarm().getCabin().isTileInBounds(player.getCurrentTile())){
                GameMenuController controller = new GameMenuController();
                GameMenu.print(controller.walk(String.format("%d",player.getFarm().getCabin().getBounds().x + 3),
                                                String.format("%d",player.getFarm().getCabin().getBounds().y + 3)).toString());
            }
        }
    }

//    int neededEnergyAmount = 10;
//        if(currentPlayer.energy.getEnergyAmount()<neededEnergyAmount){
//        TODO:currentPlayer.faint();
//    }
//    TODO(mhdsdg):currentPlayer.farm.shop.emptyShippingBin();


}
