package peer;

import core.Model.User;

/**
 * An interface for UI/Game components to listen for real-time in-game state changes
 * for other players.
 */
public interface GameStateUpdateListener {

    /**
     * Called when another player's money has been updated.
     * @param user The user whose state was updated.
     * @param newMoney The new money value.
     */
    void onPlayerMoneyUpdated(User user, int newMoney);

    /**
     * Called when another player's energy has been updated.
     * @param user The user whose state was updated.
     * @param newEnergy The new energy value.
     */
    void onPlayerEnergyUpdated(User user, int newEnergy);

    /**
     * Called when another player's position has been updated.
     * @param user The user whose state was updated.
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    void onPlayerPositionUpdated(User user, float x, float y);

}
