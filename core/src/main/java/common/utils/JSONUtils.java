package common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.models.Message;

public class JSONUtils {
	private static final GsonBuilder gsonBuilder = new GsonBuilder();
	private static final Gson gson;

	static {
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
	}

	public synchronized static String toJson(Message message) {
		return gson.toJson(message);
	}

	public synchronized static Message fromJson(String json) {
		return gson.fromJson(json, Message.class);
	}
}
