package core.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.Model.enums.Gender;
import core.Model.enums.SecurityQuestions;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class LocalTimeAdapter extends TypeAdapter<LocalTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.format(FORMATTER));
        }
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return LocalTime.parse(in.nextString(), FORMATTER);
    }
}

public class UserTypeAdapter extends TypeAdapter<User> {
    private final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
        .create();

    @Override
    public void write(JsonWriter out, User user) throws IOException {
        if (user == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("username").value(user.getUsername());
        out.name("password").value(user.getPassword());
        out.name("nickname").value(user.getNickname());
        out.name("email").value(user.getEmail());

        // Handle enum serialization
        out.name("gender").value(user.getGender() != null ? user.getGender().name() : null);

        // Handle security question
        SecurityQuestions question = user.getSecurityQuestion();
        if (question != null) {
            out.name("securityQuestion").value(question.name());
            out.name("securityQuestionText").value(question.question);
        } else {
            out.name("securityQuestion").nullValue();
            out.name("securityQuestionText").nullValue();
        }

        out.name("securityAnswer").value(user.getSecurityAnswer());
        out.name("highScore").value(user.getHighScore());
        out.name("gamesPlayed").value(user.getGamesPlayed());

        if (user.getCurrentGame() != null) {
            out.name("game");
            gson.toJson(user.getCurrentGame(), Game.class, out);
        }

        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        Game game = null;
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginObject();

        String username = null;
        String password = null;
        String nickname = null;
        String email = null;
        Gender gender = null;
        SecurityQuestions securityQuestion = null;
        String securityAnswer = null;
        int highScore = 0;
        int gamesPlayed = 0;

        while (in.hasNext()) {
            String fieldName = in.nextName();
            switch (fieldName) {
                case "username":
                    username = in.nextString();
                    break;
                case "password":
                    password = in.nextString();
                    break;
                case "nickname":
                    nickname = in.nextString();
                    break;
                case "email":
                    email = in.nextString();
                    break;
                case "gender":
                    String genderStr = in.nextString();
                    if (genderStr != null) {
                        gender = Gender.valueOf(genderStr);
                    }
                    break;
                case "securityQuestion":
                    String questionStr = in.nextString();
                    if (questionStr != null) {
                        securityQuestion = SecurityQuestions.valueOf(questionStr);
                    }
                    break;
                case "securityAnswer":
                    securityAnswer = in.nextString();
                    break;
                case "highScore":
                    highScore = in.nextInt();
                    break;
                case "gamesPlayed":
                    gamesPlayed = in.nextInt();
                    break;
                case "game":
                    game = gson.fromJson(in, Game.class);
                    break;
                default:
                    in.skipValue(); // Skip unknown fields
                    break;
            }
        }

        in.endObject();

        if (username == null || password == null || nickname == null || email == null ||
                gender == null || securityQuestion == null || securityAnswer == null) {
            throw new IOException("Required user fields are missing");
        }

        User user = new User(username, password, nickname, email, gender, securityQuestion, securityAnswer);
        user.setHighScore(highScore);
        user.setGamesPlayed(gamesPlayed);

        if (game != null) {
            user.setCurrentGame(game);
        }

        return user;
    }
}
