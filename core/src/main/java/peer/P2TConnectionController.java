package peer;

import common.models.Message;
import peer.app.PeerApp;

import java.util.HashMap;

public class P2TConnectionController {
    public static Message handleCommand(Message message) {
        return null;
    }
    public static Message status() {
        HashMap<String , Object> map = new HashMap<>();
        map.put("command", "status");
        map.put("response", "ok");
        map.put("peer", PeerApp.getPeerIP());
        map.put("listen_port", PeerApp.getPeerPort());
        return new Message(map, Message.Type.response);
    }
}
