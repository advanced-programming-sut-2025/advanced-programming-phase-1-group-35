package View;

import Controller.LoginMenuController;
import Model.Result;
import Model.User;
import Model.enums.LoginMenuCommands;
import Model.enums.SecurityQuestions;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if ((matcher = LoginMenuCommands.register.getMatcher(input)) != null) {
            handleRegistration(scanner, matcher);
        } else if ((matcher = LoginMenuCommands.login.getMatcher(input)) != null) {
            handleLogin(matcher);
        } else if ((matcher = LoginMenuCommands.forgotPassword.getMatcher(input)) != null) {
            handlePasswordReset(scanner, matcher);
        } else if (LoginMenuCommands.menuExit.getMatcher(input) != null) {
            controller.exitMenu();
        } else if (LoginMenuCommands.showCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else {
            System.out.println("Invalid command");
        }
    }

    private void handleRegistration(Scanner scanner, Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        String email = matcher.group("email");
        String nickname = matcher.group("nickname");
        String gender = matcher.group("gender");

        // Handle random password case
        if (password.equalsIgnoreCase("random")) {
            password = handleRandomPassword(scanner);
            if (password == null) return; // User cancelled
        }

        // Password confirmation loop
        while (true) {
            System.out.println("Confirm your password:");
            String confirmPassword = scanner.nextLine().trim();

            Result validation = controller.validatePassword(password, confirmPassword);
            if (validation.isSuccess()) {
                password = validation.toString();
                break;
            }
            System.out.println(validation);
            System.out.println("Please try again or enter 'back' to cancel:");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("back")) {
                System.out.println("Registration cancelled");
                return;
            }
        }

        // Security question handling
        SecurityQuestions question = handleSecurityQuestion(scanner);
        if (question == null) return; // User cancelled

        // Security answer confirmation loop
        String securityAnswer;
        while (true) {
            System.out.println("Enter your answer:");
            securityAnswer = scanner.nextLine().trim();
            System.out.println("Confirm your answer:");
            String answerConfirm = scanner.nextLine().trim();

            if (securityAnswer.equals(answerConfirm)) {
                break;
            }
            System.out.println("Answers don't match. Try again or enter 'back' to cancel:");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("back")) {
                System.out.println("Registration cancelled");
                return;
            }
        }

        Result result = controller.processRegistration(username, password, password,
                email, nickname, gender, question.ordinal() + 1, securityAnswer);
        System.out.println(result);
    }

    private void handleLogin(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        boolean stayLoggedIn = matcher.group("stay") != null;

        Result result = controller.processLogin(username, password, stayLoggedIn);
        System.out.println(result);
    }

    private void handlePasswordReset(Scanner scanner, Matcher matcher) {
        String username = matcher.group("username");
        User user = controller.getUser(username);
        if (user == null) {
            System.out.println(new Result(false, "User not found"));
            return;
        }
    }

    private String handleRandomPassword(Scanner scanner) {
        while (true) {
            String generatedPassword = controller.generateRandomPassword();
            System.out.println("Generated password: " + generatedPassword);
            System.out.println("Do you accept this password? (y/n/regenerate)");

            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "y":
                    return generatedPassword;
                case "n":
                    System.out.println("Password generation cancelled");
                    return null;
                case "regenerate":
                    continue;
                default:
                    System.out.println("Invalid choice. Please enter y, n, or regenerate");
            }
        }
    }

    private SecurityQuestions handleSecurityQuestion(Scanner scanner) {
        System.out.println("Security questions:");
        SecurityQuestions[] questions = controller.getSecurityQuestions();
        for (int i = 0; i < questions.length; i++) {
            System.out.println((i + 1) + ". " + questions[i].question);
        }

        while (true) {
            System.out.println("Select a security question (1-" + questions.length + "):");
            String input = scanner.nextLine().trim();

            try {
                int questionNumber = Integer.parseInt(input);
                if (questionNumber >= 1 && questionNumber <= questions.length) {
                    return questions[questionNumber - 1];
                }
                System.out.println("Invalid question number");
            } catch (NumberFormatException e) {
                if (input.equalsIgnoreCase("back")) {
                    System.out.println("Question selection cancelled");
                    return null;
                }
                System.out.println("Please enter a valid number");
            }
        }
    }
}