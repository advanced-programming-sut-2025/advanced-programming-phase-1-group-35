package tracker.controller;

import common.models.Message;
import tracker.app.PeerConnectionThread;
import tracker.app.TrackerApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackerConnectionController {
	public static Message handleCommand(Message message) {
		HashMap<String , Object> map = new HashMap<>();
		// 1. Validate message type and content
		String fileName = message.getFromBody("name");
		// 2. Find peers having the requested file
		ArrayList<PeerConnectionThread> peers = new ArrayList<>();
		String MD5Hash = null;
		for (PeerConnectionThread peer : TrackerApp.getConnections()) {
			String hash = peer.getFileAndHashes().get(fileName);
			if(hash == null) continue;
			// 3. Check for hash consistency
			if(MD5Hash == null) {
				MD5Hash = hash;
				peers.add(peer);
			}
			else if(hash.equals(MD5Hash)) peers.add(peer);
			else {
				map.put("response", "error");
				map.put("error", "multiple_hash");
				return new Message(map, Message.Type.response);
			}
		}
		// 4. Return peer information or error
		if(!peers.isEmpty()) {
			map.put("response", "peer_found");
			map.put("md5", MD5Hash);
			map.put("peer_have", peers.get(0).getOtherSideIP());
			map.put("peer_port", peers.get(0).getOtherSidePort());
			return new Message(map, Message.Type.response);
		}
		map.put("response", "error");
		map.put("error", "not_found");
		return new Message(map, Message.Type.response);
	}

	public static Map<String, List<String>> getSends(PeerConnectionThread connection) {
		// 1. Build command message
		HashMap<String, Object> map = new HashMap<>();
		map.put("command", "get_sends");
		// 2. Send message and wait for response
		Message response = connection.sendAndWaitForResponse(new Message(map, Message.Type.command), TrackerApp.TIMEOUT_MILLIS);
		// 3. Parse and return sent files map
		Map<String, List<String>> rawMap = response.getFromBody("sent_files");

		return rawMap;
	}

	public static Map<String, List<String>> getReceives(PeerConnectionThread connection) {
		// 1. Build command message
		HashMap<String, Object> map = new HashMap<>();
		map.put("command", "get_receives");
		// 2. Send message and wait for response
		Message response = connection.sendAndWaitForResponse(new Message(map, Message.Type.command), TrackerApp.TIMEOUT_MILLIS);
		// 3. Parse and return sent files map
		Map<String, List<String>> rawMap = response.getFromBody("received_files");

		return rawMap;
	}
}
