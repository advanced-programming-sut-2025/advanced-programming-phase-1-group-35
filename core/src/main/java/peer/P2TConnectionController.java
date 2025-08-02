package peer;

import common.models.Message;
import core.Model.App;
import core.Model.User;
import peer.app.PeerApp;

import java.util.ArrayList;
import java.util.HashMap;

public class P2TConnectionController {
    public static Message handleCommand(Message message) {
        String command = message.getFromBody("command");
        if(command.equals("status")){
            return status();
        }
        else if(command.equals("onlineUsers")){
            return updateOnlineUsers(message);
        }
        System.out.println("Unknown command: " + command);
        return null;
    }

    private static Message updateOnlineUsers(Message message) {
        System.out.println("Updating online users");
        App.onlineUsers.clear();
        ArrayList<String> names = message.getFromBody("onlineUsers");
        for(String name : names){
            for(User user : App.users){
                if(user.getUsername().equals(name)){
                    App.onlineUsers.add(user);
                }
            }
        }
        System.out.println("online users updated");
        return new Message(new HashMap<>() , Message.Type.response);
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
