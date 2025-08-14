package tracker.app;

import common.models.ConnectionThread;
import common.models.Message;
import core.Model.User;
import tracker.controller.TrackerConnectionController;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static tracker.app.TrackerApp.TIMEOUT_MILLIS;

public class PeerConnectionThread extends ConnectionThread {
    public User user = null;

    public PeerConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        try {
            socket.setSoTimeout(TIMEOUT_MILLIS);

            refreshStatus();
            TrackerApp.addPeerConnection(this);

            socket.setSoTimeout(0);
            return true;
        } catch (Exception e) {
            System.err.println("Error during simplified handshake: " + e.getMessage());
            return false;
        }
    }

    public void refreshStatus() throws IOException {
        try {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("command", "status");
            Message message1 = new Message(map1, Message.Type.command);

            // This call will throw an exception on timeout or if the peer closed the socket
            Message message2 = sendAndWaitForResponse(message1, TIMEOUT_MILLIS);

            otherSideIP = message2.getFromBody("peer");
            otherSidePort = message2.getIntFromBody("listen_port");
        }
        catch (Exception e) {
            // This block now triggers the cleanup for unresponsive peers
            System.err.println("Peer " + (otherSideIP != null ? otherSideIP : "UNKNOWN") + " failed status check: " + e.getMessage());
            this.end.set(true); // Signal the thread to stop its run loop
            TrackerApp.removePeerConnection(this); // Use our centralized cleanup method

            // Re-throw exception to notify the caller (like the health check loop) that it failed
            throw new IOException("Peer is unresponsive. Connection terminated.", e);
        }
    }

    @Override
    protected boolean handleMessage(Message message) throws IOException {
        if(message == null){
            return true;
        }
        if (message.getType().equals(Message.Type.command)) {
            sendMessage(TrackerConnectionController.handleCommand(message, this));
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        super.run();
        TrackerApp.removePeerConnection(this);
    }
}
