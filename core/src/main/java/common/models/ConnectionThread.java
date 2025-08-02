package common.models;

import common.utils.JSONUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

abstract public class ConnectionThread extends Thread {
	protected final DataInputStream dataInputStream;
	protected final DataOutputStream dataOutputStream;
	protected final BlockingQueue<Message> receivedMessagesQueue;
	protected String otherSideIP;
	protected int otherSidePort;
	protected Socket socket;
	protected AtomicBoolean end;
	protected boolean initialized = false;

	protected ConnectionThread(Socket socket) throws IOException {
		this.socket = socket;
		this.dataInputStream = new DataInputStream(socket.getInputStream());
		this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
		this.receivedMessagesQueue = new LinkedBlockingQueue<>();
		this.end = new AtomicBoolean(false);
	}

	public Message sendAndWaitForResponse(Message message, int timeoutMilli) {
		sendMessage(message);
		try {
			if (initialized) return receivedMessagesQueue.poll(timeoutMilli, TimeUnit.MILLISECONDS);
			socket.setSoTimeout(timeoutMilli);
			var result = JSONUtils.fromJson(dataInputStream.readUTF());
			socket.setSoTimeout(0);
			return result;
		} catch (Exception e) {
			System.err.println("Request Timed out.");
			e.printStackTrace();
			return null;
		}
	}

	abstract public boolean initialHandshake();

	abstract protected boolean handleMessage(Message message) throws Exception;

	public synchronized void sendMessage(Message message) {
		String JSONString = JSONUtils.toJson(message);

		try {
			dataOutputStream.writeUTF(JSONString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		initialized = false;
		if (!initialHandshake()) {
			System.err.println("Inital HandShake failed with remote device.");
			end();
			return;
		}

		initialized = true;
		while (!end.get()) {
			try {
				String receivedStr = dataInputStream.readUTF();
				Message message = JSONUtils.fromJson(receivedStr);
				boolean handled = handleMessage(message);
				if (!handled) try {
					receivedMessagesQueue.put(message);
				} catch (InterruptedException e) {}
			} catch (Exception e) {
				break;
			}
		}

		end();
	}

	public String getOtherSideIP() {
		return otherSideIP;
	}

	public void setOtherSideIP(String otherSideIP) {
		this.otherSideIP = otherSideIP;
	}

	public int getOtherSidePort() {
		return otherSidePort;
	}

	public void setOtherSidePort(int otherSidePort) {
		this.otherSidePort = otherSidePort;
	}

	public void end() {
		end.set(true);
		try {
			socket.close();
		} catch (IOException e) {}
	}

	public Message getReceivedMessage(int timeoutMilli) throws InterruptedException {
		return receivedMessagesQueue.poll(timeoutMilli, TimeUnit.MILLISECONDS);
	}

	public Socket getSocket() {
		return socket;
	}
}
