package common.Model;

import common.Model.enums.Gender;
import common.Model.enums.SecurityQuestions;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UserTypeAdapter extends TypeAdapter<User> {

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
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
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

        return user;
    }
}
