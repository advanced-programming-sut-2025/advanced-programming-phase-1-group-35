package test;

import Controller.GameMenuController;
import Model.Game;
import Model.User;
import Model.enums.Gender;
import Model.enums.SecurityQuestions;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TimeDateWeatherTest {
    ArrayList<User> users;
    Game game;
    @Test
    public void test() {
        users = new ArrayList<User>();
        users.add(new User("name", "password", "nickname", "email", Gender.female,
                SecurityQuestions.Question1, "answer"));
        game = new Game(users ,users.get(0) );
        System.out.println(";alksdjf;al");
    }

    @Test
    public void testTime() {
        users = new ArrayList<User>();
        users.add(new User("name", "password", "nickname", "email", Gender.female,
                SecurityQuestions.Question1, "answer"));
        game = new Game(users ,users.get(0) );
        assertEquals(game.getGameCalender().getTime().toString(), "time: 09:00");
        assertEquals(game.getGameCalender().getDate().toString(), "day: 1 of Spring");
        assertEquals(game.getGameCalender().getDateTime().toString(), "01  09:00 (Spring)");
    }

}
