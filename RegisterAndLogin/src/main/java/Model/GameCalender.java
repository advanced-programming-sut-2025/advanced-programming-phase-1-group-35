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

    }


}
