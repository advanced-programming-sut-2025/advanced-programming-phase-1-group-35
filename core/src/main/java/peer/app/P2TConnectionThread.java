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
    protected boolean handleMessage(Message message) throws Exception {
        if (message.getType().equals(Message.Type.command)) {
            sendMessage(P2TConnectionController.handleCommand(message));
            return true;
        }
        else if(message.getType().equals(Message.Type.requestResponse)){
            P2TConnectionController.handleCommand(message);
            return true;
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
