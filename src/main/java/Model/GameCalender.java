package Model;

import Controller.GameMenuController;
import Controller.InGameMenu.FarmingController;
import Model.CropClasses.Crop;
import Model.CropClasses.Tree;
import Model.Shops.Shop;
import Model.Shops.ShopItem;
import Model.enums.Seasons;
import Model.enums.TileType;
import Model.enums.WeatherCondition;
import View.GameMenu;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Result getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = gameDateTime.format(formatter);
        return new Result(true, "time: " + formattedDate);
    }

    public Result getDate() {
        return new Result(true, "day: " + gameDateTime.getDayOfMonth() + " of " + season);
    }

    public Result getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd  HH:mm");
        String formattedDate = gameDateTime.format(formatter);
        return new Result(true, formattedDate + " (" + season + ")");
    }

    public Result getDayOfTheWeek() {
        DayOfWeek dayOfWeek = gameDateTime.getDayOfWeek();
        return new Result(true, "day: " + dayOfWeek);
    }

    public Result showSeason() {
        return new Result(true, "Season: " + season);
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
        if (season == Seasons.Spring) {
            season = Seasons.Summer;
        } else if (season == Seasons.Summer) {
            season = Seasons.Fall;
        } else if (season == Seasons.Fall) {
            season = Seasons.Winter;
        } else if (season == Seasons.Winter) {
            season = Seasons.Spring;
        }
        season = season.findNextSeason(season);
        for (Crop crop : App.getCurrentGame().getMap().getCrops()) {
            if (!crop.getSeasons().equals(season)) {
                crop.getCropTile().setPlanted(null);
                crop.getCropTile().getContents().remove(crop);
            }
            for (Tree tree : App.getCurrentGame().getMap().getTrees()) {
                if (!tree.getSeasons().contains(season)) {
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
        FarmingController farmingController = new FarmingController(App.getCurrentGame().getMap().getTiles());
        farmingController.crowAttack();
        Random rand = new Random();
        if (App.getCurrentGame().getWeather().getWeatherCondition().equals(WeatherCondition.storm)) {
            for(int i = 0; i<20 ; i++) {
                Tile[][] tile = App.getCurrentGame().getMap().getTiles();
                Tile temp = tile[rand.nextInt(300)][rand.nextInt(250)];
                if (temp.getTileType().equals(TileType.Soil)) {
                    Weather.hitTileWithThunder(temp);
                }
                else i--;
            }
        }
        ArrayList<Crop> crops = new ArrayList<>(App.getCurrentGame().getMap().getCrops());
        for (Crop crop : crops) {
            crop.getCropTile().setWatered(false);
            if (App.getCurrentGame().getWeather().getWeatherCondition().equals(WeatherCondition.rain)) {
                crop.setDaysSinceWatered(0);
                crop.getCropTile().setWatered(true);
            } else if (App.getCurrentGame().getWeather().getWeatherCondition().equals(WeatherCondition.storm)) {
                if (rand.nextInt(100) < 25) {
                    crop.getCropTile().setPlanted(null);
                    crop.getCropTile().getContents().remove(crop);
                    crop.getCropTile().setContentSymbol('.');
                    crop.getCropTile().setSymbol('.');
                    crop.getCropTile().changeTileContents(null);
                    App.getCurrentGame().getMap().getCrops().remove(crop);
                    App.getCurrentGame().getPlayingUser().getFarm().getCrops().remove(crop);
                }
            }
            boolean temp = crop.grow();
            if (crop.isFertilized()) {
                boolean fertilizer = false;
                if (crop.getFertilizer().getName().equals("Speed-Gro")) {
                    if (crop.getDaysSinceLastGrowth() == crop.getStages().get(crop.getCurrentState()) - 1) {
                        crop.setCurrentState(crop.getCurrentState() + 1);
                        crop.setDaysSinceLastGrowth(0);
                    }
                } else if (crop.getFertilizer().getName().equals("Deluxe Retaining Soil")) {
                    fertilizer = true;
                } else if (crop.getFertilizer().getName().equals("Basic Retaining Soil")) {
                    if (rand.nextInt(100) < 50) fertilizer = true;
                } else if (crop.getFertilizer().getName().equals("Quality Retaining Soil")) {
                    if (rand.nextInt(100) < 75) fertilizer = true;
                }

                if (crop.getDaysSinceWatered() > 1) {
                    if (!fertilizer) {
                        crop.getCropTile().setPlanted(null);
                        crop.getCropTile().getContents().remove(crop);
                    }
                }
            }
//            Crop crop1 : App.getCurrentGame().getMap().getCrops()
            for (int i=0 ; i<App.getCurrentGame().getMap().getCrops().size() ; i++) {
                App.getCurrentGame().getMap().getCrops().get(i).grow();
            }
            for (int i = 0; i < App.getCurrentGame().getMap().getTrees().size() ; i++) {
                Tree tree = App.getCurrentGame().getMap().getTrees().get(i);
                tree.getTile().setWatered(false);
                if (App.getCurrentGame().getWeather().getWeatherCondition().equals(WeatherCondition.rain)) {
                    tree.setDaysSinceWatered(0);
                    tree.getTile().setWatered(true);
                } else if (App.getCurrentGame().getWeather().getWeatherCondition().equals(WeatherCondition.storm)) {
                    if (rand.nextInt(100) < 25) {
                        tree.getTile().setPlanted(null);
                        tree.getTile().getContents().remove(tree);
                        tree.getTile().setContentSymbol('.');
                        tree.getTile().setSymbol('.');
                        tree.getTile().changeTileContents(null);
                        App.getCurrentGame().getMap().getTrees().remove(tree);
                        App.getCurrentGame().getPlayingUser().getFarm().getTrees().remove(tree);
                    }
                }
                tree.grow();
                if (tree.isFertilized()) {
                    boolean fertilizer = false;
                    if (tree.getFertilizer().getName().equals("Speed-Gro")) {
                        if (tree.getDaysSinceLastGrowth() == tree.getStages().get(tree.getCurrentState()) - 1) {
                            tree.setCurrentState(tree.getCurrentState() + 1);
                            tree.setDaysSinceLastGrowth(0);
                        }
                    } else if (tree.getFertilizer().getName().equals("Deluxe Retaining Soil")) {
                        fertilizer = true;
                    } else if (tree.getFertilizer().getName().equals("Basic Retaining Soil")) {
                        if (rand.nextInt(100) < 50) fertilizer = true;
                    } else if (tree.getFertilizer().getName().equals("Quality Retaining Soil")) {
                        if (rand.nextInt(100) < 75) fertilizer = true;
                    }
                    if (tree.getDaysSinceWatered() > 1) {
                        if (!fertilizer) {
                            tree.getTile().setPlanted(null);
                            tree.getTile().getContents().remove(tree);
                        }
                    }
                }
            }
            if (gameDateTime.getDayOfMonth() == 29) {
                goToNextSeason();
                gameDateTime = LocalDateTime.of(2025, 1, 1, 9, 0);
            }
            farmingController.addForagingCrop();
            farmingController.addForagingSeeds();
            farmingController.addForAgingTree();
            farmingController.crowAttack();
            for (Shop shop : App.getCurrentGame().getMap().getShops()) {//restock the shops
                for (ShopItem product : shop.getProducts()) {
                    product.setDailyBoughtCount(0);
                }
            }
            for (User player : App.getCurrentGame().getPlayers()) {
                player.setMoney(player.getIncome() + player.getMoney());
                player.setIncome(0);
                if (!player.getFarm().getCabin().isTileInBounds(player.getCurrentTile())) {
                    GameMenuController controller = new GameMenuController();
                    GameMenu.print(controller.walk(String.format("%d", player.getFarm().getCabin().getBounds().x + 3),
                            String.format("%d", player.getFarm().getCabin().getBounds().y + 3)).toString());
                }
            }
        }
    }

    public Result cheatTime(int hour) throws IOException {
        for (int i = 0; i < hour; i++) {
            updateTimeAndDateAndSeasonAfterTurns();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = gameDateTime.format(formatter);
        return new Result(true, "hour now is " + formattedDate);
    }

    public Result cheatDate(int day) throws IOException {
        for (int i = 0; i < day; i++) {
            goToNextDay();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd  HH:mm");
        String formattedDate = gameDateTime.format(formatter);
        return new Result(true, "it is " + formattedDate);
    }

//    int neededEnergyAmount = 10;
//        if(currentPlayer.energy.getEnergyAmount()<neededEnergyAmount){
//        TODO:currentPlayer.faint();
//    }
//    TODO(mhdsdg):currentPlayer.farm.shop.emptyShippingBin();


}
