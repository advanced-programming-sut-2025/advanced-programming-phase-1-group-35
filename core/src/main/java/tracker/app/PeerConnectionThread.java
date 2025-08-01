package tracker.app;

import common.models.ConnectionThread;
import common.models.Message;
import tracker.controller.TrackerConnectionController;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static tracker.app.TrackerApp.TIMEOUT_MILLIS;

public class PeerConnectionThread extends ConnectionThread {
    private HashMap<String, String> fileAndHashes;

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
            Message message2 = sendAndWaitForResponse(message1, TIMEOUT_MILLIS);
            otherSideIP = message2.getFromBody("peer");
            otherSidePort = message2.getIntFromBody("listen_port");
        }
        catch (Exception e) {
            socket.close();
            TrackerApp.removePeerConnection(this);
            this.end.set(true);
        }
    }

    @Override
    protected boolean handleMessage(Message message) {
        if (message.getType().equals(Message.Type.file_request)) {
            sendMessage(TrackerConnectionController.handleCommand(message));
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        super.run();
        TrackerApp.removePeerConnection(this);
    }

    public Map<String, String> getFileAndHashes() {
        return Map.copyOf(fileAndHashes);
    }
}
