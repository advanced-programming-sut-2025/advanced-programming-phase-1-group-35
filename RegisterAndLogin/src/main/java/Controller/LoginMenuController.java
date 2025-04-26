package Controller;

import Model.App;
import Model.Result;
import Model.User;
import Model.enums.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {
    public void exitMenu() throws IOException {
        if (!App.isStayLoggedIn()) {
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
        }
        App.serializeApp();
        App.setCurrentMenu(Menu.ExitMenu);
    }

    public Result showCurrentMenu() {
        return new Result(true, "login menu");
    }

    public Result processRegistration(String username, String password,
                                      String confirmPassword, String email,
                                      String nickname, String gender,
                                      int securityQuestionNumber,
                                      String securityAnswer) {
        // Validate username
        if (getUser(username) != null) {
            return new Result(false, "Username is already in use");
        }
        if (Regexes.Username.getMatcher(username) == null) {
            return new Result(false, "Username is not valid");
        }

        // Validate email
        if (Regexes.Email.getMatcher(email) == null) {
            return new Result(false, "Email is not valid");
        }

        // Validate password
        Result passwordResult = validatePassword(password, confirmPassword);
        if (!passwordResult.isSuccess()) {
            return passwordResult;
        }

        // Validate gender
        Gender genderEnum;
        try {
            genderEnum = Gender.valueOf(gender.toLowerCase());
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid gender selection");
        }

        // Validate security question
        if (securityQuestionNumber < 1 || securityQuestionNumber > SecurityQuestions.values().length) {
            return new Result(false, "Invalid security question number");
        }

        SecurityQuestions question = SecurityQuestions.values()[securityQuestionNumber - 1];
        App.users.add(new User(username, passwordResult.toString(), nickname, email,
                genderEnum, question, securityAnswer));

        return new Result(true, "User successfully registered");
    }

    public Result confirmGeneratedPassword(String password) {
        return validatePassword(password, password);
    }

    public Result processLogin(String username, String password, boolean stayLoggedIn) {
        User user = getUser(username);
        if (user == null) {
            return new Result(false, "User not found");
        }
        if (!user.getPassword().equals(password)) {
            return new Result(false, "Wrong password");
        }

        App.setLoggedInUser(user);
        App.setStayLoggedIn(stayLoggedIn);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "User successfully logged in");
    }

    public Result processPasswordReset(String username, String answer,
                                       String newPassword, String confirmPassword) {
        User user = getUser(username);
        if (user == null) {
            return new Result(false, "User not found");
        }
        if (!answer.equalsIgnoreCase(user.getSecurityAnswer())) {
            return new Result(false, "Wrong answer");
        }

        Result passwordResult = validatePassword(newPassword, confirmPassword);
        if (!passwordResult.isSuccess()) {
            return passwordResult;
        }

        user.setPassword(passwordResult.toString());
        return new Result(true, "Password successfully changed");
    }

    public String generateRandomPassword() {
        List<Character> chars = getCharacters();
        Collections.shuffle(chars);
        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : chars) {
            shuffledPassword.append(c);
        }
        return shuffledPassword.toString();
    }

    public SecurityQuestions[] getSecurityQuestions() {
        return SecurityQuestions.values();
    }

    public Result validatePassword(String password, String confirmPassword) {
        if (Regexes.Password.getMatcher(password) == null) {
            return new Result(false, "Password is not valid");
        }
        if (!password.equals(confirmPassword)) {
            return new Result(false, "Password does not match the confirmation");
        }
        return checkPasswordStrength(password);
    }

    private Result checkPasswordStrength(String password) {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern number = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[" + Regexes.SpecialCharacters + "]");

        if (password.length() < 8) {
            return new Result(false, "Password must be at least 8 characters");
        }
        if (!uppercase.matcher(password).find()) {
            return new Result(false, "Password must contain at least one uppercase letter");
        }
        if (!lowercase.matcher(password).find()) {
            return new Result(false, "Password must contain at least one lowercase letter");
        }
        if (!number.matcher(password).find()) {
            return new Result(false, "Password must contain at least one number");
        }
        if (!special.matcher(password).find()) {
            return new Result(false, "Password must contain at least one special character");
        }
        return new Result(true, password);
    }

    public User getUser(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private List<Character> getCharacters() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 2 + random.nextInt(3); i++) {
            password.append(Regexes.LowerCase.getRegex().charAt(random.nextInt(Regexes.LowerCase.getRegex().length())));
            password.append(Regexes.UpperCase.getRegex().charAt(random.nextInt(Regexes.UpperCase.getRegex().length())));
            password.append(Regexes.Number.getRegex().charAt(random.nextInt(Regexes.Number.getRegex().length())));
            password.append(Regexes.SpecialCharacters.getRegex().charAt(random.nextInt(Regexes.SpecialCharacters.getRegex().length())));
        }
        List<Character> chars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            chars.add(c);
        }
        return chars;
    }
}