package core.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.Model.enums.Gender;
import core.Model.enums.SecurityQuestions;
import core.Model.enums.Seasons;
import core.Model.enums.WeatherCondition;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Adapts LocalTime for Gson serialization/deserialization.
class LocalTimeAdapter extends TypeAdapter<LocalTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    // Writes a LocalTime object as a string.
    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.format(FORMATTER));
        }
    }

    // Reads a LocalTime object from a string.
    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return LocalTime.parse(in.nextString(), FORMATTER);
    }
}

// Adapts User objects for Gson serialization/deserialization.
public class UserTypeAdapter extends TypeAdapter<User> {
    // Gson instance for nested object serialization (like SerializableGame).
    private static final Gson innerGson = new GsonBuilder()
        .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
        .create();

    // Writes a User object to JSON.
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
        out.name("gender").value(user.getGender() != null ? user.getGender().name() : null);

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

        // Serializes the game state as a SerializableGame DTO.
        if (user.getCurrentGame() != null) {
            out.name("currentSerializableGame");
            SerializableGame serializableGame = new SerializableGame(user.getCurrentGame());
            innerGson.toJson(serializableGame, SerializableGame.class, out);
        } else {
            out.name("currentSerializableGame").nullValue();
        }

        out.endObject();
    }

    // Reads a User object from JSON.
    @Override
    public User read(JsonReader in) throws IOException {
        SerializableGame serializableGame = null;
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
                // Deserializes the SerializableGame DTO.
                case "currentSerializableGame":
                    serializableGame = innerGson.fromJson(in, SerializableGame.class);
                    break;
                default:
                    in.skipValue();
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

        // Reconstructs the live Game object from the SerializableGame DTO.
        if (serializableGame != null) {
            ArrayList<User> gamePlayers = new ArrayList<>();
            for (Integer playerID : serializableGame.playerIDs) {
                User gamePlayer = App.findUserByID(playerID);
                if (gamePlayer != null) {
                    gamePlayers.add(gamePlayer);
                }
            }
            User playingUser = App.findUserByID(serializableGame.playingUserID);

            if (playingUser != null && !gamePlayers.isEmpty()) {
                Game reconstructedGame = new Game(gamePlayers, playingUser);
                reconstructedGame.setGameID(serializableGame.gameID);

                if (serializableGame.map != null) {
                    reconstructedGame.setMap(new Map());
                    reconstructedGame.getMap().reconstructFromSerializableMap(serializableGame.map);
                }

                GameCalender reconstructedCalender = new GameCalender();
//                reconstructedCalender.setGameDateTime(serializableGame.gameDateTime);
                reconstructedCalender.setSeason(Seasons.valueOf(serializableGame.currentSeasonName));
                reconstructedCalender.dayPassedFromSeason = serializableGame.dayPassedFromSeason;

                reconstructedGame.setGameCalender(reconstructedCalender);

                Weather reconstructedWeather = new Weather();
                reconstructedWeather.setWeatherCondition(WeatherCondition.valueOf(serializableGame.weatherConditionName));
                reconstructedWeather.setTomorrowCondition(WeatherCondition.valueOf(serializableGame.tomorrowWeatherConditionName));
                reconstructedGame.setWeather(reconstructedWeather);

                for(User p : gamePlayers) {
                    p.setCurrentGame(reconstructedGame);
                }

                user.setCurrentGame(reconstructedGame);
            }
        }

        return user;
    }
}
