package View;

import java.io.IOException;
import java.util.Scanner;

public abstract class AppMenu {
    private static Scanner scanner;
    public abstract void check(Scanner scanner) throws IOException;

    public static String scan() throws IOException {
        String input = scanner.nextLine().trim();
        if(input.equals("exit")) {//exit without save
            System.exit(0);
        }
        return input;
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
