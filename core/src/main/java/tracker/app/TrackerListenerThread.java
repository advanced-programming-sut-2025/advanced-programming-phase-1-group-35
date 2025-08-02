package tracker.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TrackerListenerThread extends Thread {
    private final ServerSocket serverSocket;

    public TrackerListenerThread(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void handleConnection(Socket socket) {
        if (socket == null) return;
        try {
            new PeerConnectionThread(socket).start();
        } catch (IOException e) {
            try {socket.close();} catch (IOException ex) {}
        }
    }

    @Override
    public void run() {
        while (!TrackerApp.isEnded()) {
            try {
                Socket socket = serverSocket.accept();
                handleConnection(socket);
            } catch (Exception e) {
                break;
            }
        }

        try {serverSocket.close();} catch (Exception ignored) {}
    }
}
