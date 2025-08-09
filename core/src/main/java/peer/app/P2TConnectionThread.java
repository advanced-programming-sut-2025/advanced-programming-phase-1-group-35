package peer.app;

import common.models.ConnectionThread;
import common.models.Message;
import peer.P2TConnectionController;

import java.io.IOException;
import java.net.Socket;

import static peer.app.PeerApp.TIMEOUT_MILLIS;

public class P2TConnectionThread extends ConnectionThread {

    protected P2TConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public boolean initialHandshake() {
        try {
            socket.setSoTimeout(TIMEOUT_MILLIS);

            dataInputStream.readUTF();
            Message message1 = P2TConnectionController.status();
            sendMessage(message1);

            socket.setSoTimeout(0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected boolean handleMessage(Message message) {
        // FIX: Add a null check to prevent the crash from malformed messages.
        if (message == null) {
            // System.err.println("Received a null or malformed message from the stream. Ignoring.");
            return true; // Mark as handled to prevent further errors.
        }

        try {
            if (message.getType().equals(Message.Type.command)) {
                Message response = P2TConnectionController.handleCommand(message);
                if (response != null) {
                    sendMessage(response);
                }
                return true;
            } else if (message.getType().equals(Message.Type.requestResponse) || message.getType().equals(Message.Type.response)) {
                receivedMessagesQueue.put(message);
                return true;
            }
        } catch (Exception e) {
            // The null check above also prevents a crash here if the original message was null.
            System.err.println("Error processing message in P2TConnectionController. Message: " + message.body);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        super.run();
        PeerApp.endAll();
        System.exit(0);
    }
}
