package tracker.controller;

import common.models.Message;
import core.Model.App;
import core.Model.User;
import tracker.app.PeerConnectionThread;
import tracker.app.TrackerApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackerConnectionController {
	public static Message handleCommand(Message message, PeerConnectionThread peerConnectionThread) throws IOException {
		String command = message.getFromBody("command");
        System.out.println("parsing command: " + command);
        if(command.equals("login")){
            return login(message, peerConnectionThread);
        }
        if(command.equals("onlineUsers")){
            return onlineUsers(message);
        }

        return null;
	}

    public static Message login(Message message, PeerConnectionThread peerConnectionThread) {
        for (User user : App.users) {
            if(user.getUsername().equals(message.getFromBody("username"))){
                App.onlineUsers.add(user);
                peerConnectionThread.user = user;
            }
        }

        HashMap<String , Object> body = new HashMap<>();
        body.put("command", "login");
        body.put("response", "success");
        Message response = new Message(body, Message.Type.response);
        return response;
    }
    public static Message onlineUsers(Message message) throws IOException {
        HashMap<String , Object> body = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        for (User onlineUser : App.onlineUsers) {
            names.add(onlineUser.getUsername());
        }
        body.put("command", "onlineUsers");
        body.put("onlineUsers", names);
        System.out.println(names);
        System.out.println("online users given to the peer");
        return new Message(body, Message.Type.requestResponse);
    }

    private static String resetConnections() throws IOException {
        ArrayList<PeerConnectionThread> connectionsCopy = new ArrayList<>(TrackerApp.getConnections());
        StringBuilder result = new StringBuilder();
        int failedResets = 0;

        for (PeerConnectionThread connection : connectionsCopy) {
            try {
                // Test if connection is responsive by refreshing status
                connection.refreshStatus();

            } catch (IOException e) {
                // Connection is unresponsive - close and remove it
                result.append("Connection to ")
                    .append(connection.getOtherSideIP())
                    .append(":")
                    .append(connection.getOtherSidePort())
                    .append(" was unresponsive and has been removed\n");
                if(App.onlineUsers.contains(connection.user)){
                    App.onlineUsers.remove(connection.user);
                }
                connection.end();
                TrackerApp.removePeerConnection(connection);
                failedResets++;
            }
        }
        for (PeerConnectionThread connection : TrackerApp.getConnections()) {
            System.out.println(connection.getOtherSideIP() + ": " + connection.getOtherSidePort());
        }
        return "";
    }
}
