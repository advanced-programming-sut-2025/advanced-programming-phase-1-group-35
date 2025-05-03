package Model;

import Model.enums.Seasons;

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

    public void updateTimeAndDateAndSeasonAfterTurns() {
        gameDateTime = gameDateTime.plusHours(1);
        if (gameDateTime.getHour() == 22) {
            gameDateTime = gameDateTime.plusHours(11);
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
    }

    public void goToNextDay() {
        gameDateTime = gameDateTime.plusDays(1).withHour(9).withMinute(0);

        if (gameDateTime.getDayOfMonth() == 29) {
            goToNextSeason();
            gameDateTime = LocalDateTime.of(2025, 1, 1, 9, 0);
        }
    }

//    int neededEnergyAmount = 10;
//        if(currentPlayer.energy.getEnergyAmount()<neededEnergyAmount){
//        TODO:currentPlayer.faint();
//    }
//    currentPlayer.farm.growCrops();
//    currentPlayer.farm.generateRandomForaging();
//    TODO(mhdsdg):currentPlayer.farm.shop.emptyShippingBin();


}
