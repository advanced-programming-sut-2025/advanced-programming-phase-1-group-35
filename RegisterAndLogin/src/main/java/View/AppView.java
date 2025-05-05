package View;

import Model.App;
import Model.enums.Menu;

import java.io.IOException;
import java.util.Scanner;

public class AppView {
    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        do{
            if(App.getCurrentMenu().getMenu().getScanner() == null){
                App.getCurrentMenu().getMenu().setScanner(scanner);
            }
            App.getCurrentMenu().checkCommand(scanner);
        }while(App.getCurrentMenu() != Menu.ExitMenu);
    }
}
