package peer;

import core.Model.Lobby;
import java.util.List;

/**
 * An interface for UI components to listen for real-time updates about lobbies
 * from the tracker. This allows the networking layer to signal the UI to refresh.
 */
public interface LobbyUpdateListener {

    /**
     * Called when the entire list of available lobbies has changed.
     * (e.g., a new lobby was created or an empty one was removed).
     * @param lobbies The new, complete list of lobbies.
     */
    void onLobbyListUpdated(List<Lobby> lobbies);

    /**
     * Called when the state of a specific lobby has changed.
     * (e.g., a player joined or left).
     * @param lobby The updated Lobby object.
     */
    void onLobbyStateUpdated(Lobby lobby);
}
