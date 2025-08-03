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
        // FIX: Wrapped the message handling in a try-catch block.
        // This prevents an exception in the controller from crashing the entire connection thread.
        try {
            if (message.getType().equals(Message.Type.command)) {
                // P2TConnectionController.handleCommand may return null for broadcasts.
                // Only send a response if one is provided.
                Message response = P2TConnectionController.handleCommand(message);
                if (response != null) {
                    sendMessage(response);
                }
                return true;
            } else if (message.getType().equals(Message.Type.requestResponse)) {
                // This handles responses to requests this client has sent.
                // It should put the message in the queue for the waiting thread.
                receivedMessagesQueue.put(message);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error processing message in P2TConnectionController. Message: " + message.body);
            e.printStackTrace();
        }
        // Return false if the message was not of a known type or if an error occurred.
        return false;
    }

    @Override
    public void run() {
        super.run();
        PeerApp.endAll();
        System.exit(0);
    }
}
