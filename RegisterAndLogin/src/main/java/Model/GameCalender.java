package Model;

import Model.enums.Seasons;

import java.time.LocalDateTime;

public class GameCalender {
    private LocalDateTime gameDateTime;
    private final int minutesPerTurn = 60;
    private final int daysPerSeason = 28;
    private Seasons season;

    public LocalDateTime getGameDateTime() {
        return gameDateTime;
    }

    public void setGameDateTime(LocalDateTime gameDateTime) {
        this.gameDateTime = gameDateTime;
    }

    public int getMinutesPerTurn() {
        return minutesPerTurn;
    }

    public Seasons getSeason() {
        return season;
    }

    public void setSeason(Seasons season) {
        this.season = season;
    }

    public void updateTimeAndDateAndSeasonAfterTurns() {

    }

    public void goToNextDay() {
    //TODO: where is the clock?
        //if(clock.hour==22)
        //clock.hour.setHour(9)
        User currentPlayer = null;
        int neededEnergyAmount = 10;
        if(currentPlayer.energy.getEnergyAmount()<neededEnergyAmount){
            //TODO:currentPlayer.faint();
        }
        //currentPlayer.farm.growCrops();
        //currentPlayer.farm.generateRandomForaging();
        //TODO(mhdsdg):currentPlayer.farm.shop.emptyShippingBin();
    }


}
