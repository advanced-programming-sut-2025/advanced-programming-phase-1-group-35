package common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.models.Message;
import core.Model.User;
import core.Model.UserTypeAdapter;

public class JSONUtils {
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static final Gson gson;

    static {
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(User.class, new UserTypeAdapter());
        gson = gsonBuilder.create();
    }

    public synchronized static String toJson(Object object) {
        return gson.toJson(object);
    }

    public synchronized static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public synchronized static Message fromJson(String json) {
        return gson.fromJson(json, Message.class);
    }
}
