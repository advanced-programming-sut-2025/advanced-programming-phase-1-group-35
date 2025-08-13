package core;

import com.google.gson.Gson;
import common.models.Message;
import core.Model.App;
import core.Model.Serializables.SerializableTile;
import core.Model.Shops.Shop;
import core.Model.Shops.ShopItem;
import core.Model.Tile;
import core.Model.Tools.BackPack;
import core.Model.Tools.SkillLevel;
import core.Model.User;
import peer.app.PeerApp;

import java.util.HashMap;

/**
 * A utility class for sending in-game state updates to the tracker.
 * This sends small, specific messages rather than entire objects to be efficient.
 */
public class GameUpdater {

    /**
     * Sends an update for a specific field of the current user.
     * @param field The name of the field that changed (e.g., "money", "energy").
     * @param value The new value of the field.
     */
    public static void sendUpdate(String field, Object value) {
        if (PeerApp.getP2TConnection() == null || !PeerApp.getP2TConnection().isAlive()) {
            System.err.println("Cannot send game update, not connected to tracker.");
            return;
        }

        // The payload contains the specific field and its new value.
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("field", field);
        payload.put("value", value);

        // This is the main message body sent to the server.
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "game_state_update");
        body.put("otherUser", -1);
        body.put("payload", payload);

        Message request = new Message(body, Message.Type.command);
        PeerApp.getP2TConnection().sendMessage(request);
    }

    public static void sendOtherUserUpdate(String field, Object value, User user) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("field", field);
        payload.put("value", value);

        // This is the main message body sent to the server.
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "game_state_update");
        body.put("otherUser", user.getID());
        body.put("payload", payload);

        Message request = new Message(body, Message.Type.command);
        PeerApp.getP2TConnection().sendMessage(request);
    }

    public static void sendInventoryUpdate(BackPack backPack, User user) {
        Gson gson = new Gson();
        sendOtherUserUpdate("backpack", gson.toJson(backPack), user);
    }

    public static void sendTileUpdate(Tile tile) {
        Gson gson = new Gson();
        sendUpdate("tile", gson.toJson(new SerializableTile(tile)));
    }

    // --- Public Methods for Specific Updates ---

    public static void sendMoneyUpdate(int newMoney) {
        sendUpdate("money", newMoney);
    }

    public static void sendEnergyUpdate(int newEnergy) {
        sendUpdate("energy", newEnergy);
    }

    public static void sendPositionUpdate(float x, float y) {
        HashMap<String, Float> position = new HashMap<>();
        position.put("x", x);
        position.put("y", y);
        sendUpdate("position", position);
    }

    // --- FIX: Added new methods for other User fields ---

    /**
     * Call this when the player's selected inventory slot changes.
     * @param newSlotIndex The new index of the selected slot.
     */
    public static void sendSelectedSlotUpdate(int newSlotIndex) {
        sendUpdate("selectedSlot", newSlotIndex);
    }

    /**
     * Call this when a player's skill level changes.
     * @param skillName The name of the skill (e.g., "farming", "mining").
     * @param skillLevel The updated SkillLevel object.
     */
    public static void sendSkillUpdate(String skillName, SkillLevel skillLevel) {
        // We send a map containing the level and XP for the specific skill.
        HashMap<String, Object> skillData = new HashMap<>();
        skillData.put("level", skillLevel.getCurrentLevel());
        skillData.put("xp", skillLevel.getCurrentLevel());

        // The field name is dynamic (e.g., "skill_farming", "skill_mining")
        sendUpdate("skill_" + skillName, skillData);
    }

    /**
     * Sends a reaction update to other players.
     * @param type The type of reaction ("emoji" or "text").
     * @param content The content of the reaction (emoji path or message).
     */
    public static void sendReactionUpdate(String type, String content) {
        HashMap<String, String> reactionData = new HashMap<>();
        reactionData.put("type", type);
        reactionData.put("content", content);
        sendUpdate("reaction", reactionData);
    }

    public static void sendShopUpdate(Shop shop, ShopItem item) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("field", "shopUpdate");
        HashMap<String, Object> value = new HashMap<>();
        System.out.println("shop: " + shop.getName() + " item: " + item.getName());
        value.put("shop", shop.getName());
        value.put("item", item.getName());
        value.put("stock", item.getDailyBoughtCount());
        String serial = new Gson().toJson(value);
        System.out.println(serial);
        payload.put("value", serial);

        // This is the main message body sent to the server.
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "game_state_update");
        body.put("otherUser", -1);
        body.put("payload", payload);

        Message request = new Message(body, Message.Type.command);
        PeerApp.getP2TConnection().sendMessage(request);
    }

    public static void passTime() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("field", "passTime");
        payload.put("value", "passTime");

        // This is the main message body sent to the server.
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "game_state_update");
        body.put("payload", payload);

        Message request = new Message(body, Message.Type.command);
        PeerApp.getP2TConnection().sendMessage(request);
    }

    public static void increaseFriendXP(User sender, User receiver, int i) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("field", "increaseFriendXP");
        HashMap<String, Object> value = new HashMap<>();
        value.put("sender", sender.getID());
        value.put("receiver", receiver.getID());
        value.put("xp", i);
        payload.put("value", value);

        // This is the main message body sent to the server.
        HashMap<String, Object> body = new HashMap<>();
        body.put("command", "game_state_update");
        body.put("payload", payload);

        Message request = new Message(body, Message.Type.command);
        PeerApp.getP2TConnection().sendMessage(request);
    }
}
