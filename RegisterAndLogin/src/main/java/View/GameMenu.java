package View;

import Controller.GameMenuController;
import Model.enums.GameMenuCommands;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends AppMenu {
    GameMenuController controller = new GameMenuController();
    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scan();
        Matcher matcher ;
        if((matcher = GameMenuCommands.newGame.getMatcher(input) )!= null){
            System.out.println(controller.createNewGame(matcher.group("user1") , matcher.group("user2") ,
                    matcher.group("user3")));
        }
    }
}
