package Controller;

import GraphicView.LoginUI;
import GraphicView.SignUpUI;
import Model.App;
import Model.Result;
import Model.SHA256;
import Model.User;
import Model.enums.*;
import View.LoginMenu;
import com.StardewValley.Main;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController extends Controller {
    private SignUpUI view ;
    private LoginUI LoginView;

    public void exitMenu() throws IOException {
        if(!App.isStayLoggedIn()) {
            App.setLoggedInUser(null);
            App.setCurrentMenu(Menu.LoginMenu);
        }
        App.serializeApp();
        App.setCurrentMenu(Menu.ExitMenu);
    }
    public Result showCurrentMenu() {
        return new Result(true , "login menu");
    }
    public Result registerUser() throws IOException {
        if(!view.getAdvanceButton().isChecked())return null;
        String password;
        if(getUser(view.getUsernameField().getText()) != null) {
            showErrorDialog(Dialogues.ErrorUserExists.title, Dialogues.ErrorUserExists.message);
            view.getAdvanceButton().setChecked(false);
        }
        if(Regexes.Username.getMatcher(view.getUsernameField().getText()) == null) {
            showErrorDialog(Dialogues.ErrorInvalidUserName.title, Dialogues.ErrorInvalidUserName.message);
            view.getAdvanceButton().setChecked(false);
        }
        if(Regexes.Email.getMatcher(view.getEmailField().getText()) == null) {
            showErrorDialog(Dialogues.ErrorInvalidEmail.title, Dialogues.ErrorInvalidEmail.message);
            view.getAdvanceButton().setChecked(false);
        }
        Result managePasswordResult = managePassword(view.getPasswordField().getText() , view.getConfirmPasswordField().getText());
        if(!managePasswordResult.isSuccess()) return managePasswordResult;
        else password = managePasswordResult.toString();
        Gender genderEnum = switch (view.getGenderBox().getSelected().toString().toLowerCase()) {
            case "male" -> Gender.male;
            case "female" -> Gender.female;
            default -> null;
        };
        if(genderEnum == null) {
            return new Result(false, "I'm a CE major , I believe in binary");
        }
        for (SecurityQuestions question : SecurityQuestions.values()) {
            System.out.println(question.question);
        }
        SecurityQuestions question = null;
        String answer = view.getSecurityAnswerField().getText();

        if(answer.length() < 3) {
            showErrorDialog(Dialogues.ErrorSecurityAnswerEmpty.title, Dialogues.ErrorSecurityAnswerEmpty.message);
            view.getAdvanceButton().setChecked(false);
        }
        question = switch (view.getSecurityQuestionBox().getSelectedIndex() + 1) {
            case 1 -> SecurityQuestions.Question1;
            case 2 -> SecurityQuestions.Question2;
            case 3 -> SecurityQuestions.Question3;
            case 4 -> SecurityQuestions.Question4;
            default -> null;
        };
        App.users.add(new User(view.getUsernameField().getText() , SHA256.hashString(password) ,
            view.getUsernameField().getText(), view.getEmailField().getText() , genderEnum , question , answer));
        Main.getGame().getScreen().dispose();
        Main.getGame().setScreen(new LoginUI(new LoginMenuController()));
        return new Result(true , "user successfully registered , now you can log in");
    }

    public Result login(String username, String password, String stayLoggedIn) {
        User user = getUser(username);
        if(user == null) {
            return new Result(false, "User not found");
        }
        if(!user.getPassword().equals(SHA256.hashString(password))) {
            return new Result(false, "Wrong password");
        }
        App.setLoggedInUser(user);
        boolean stayLoggedInUser = stayLoggedIn == null ? false : true;
        App.setStayLoggedIn(stayLoggedInUser);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "user successfully logged in");
    }

    public Result forgotPassword(String username , Scanner scanner) throws IOException {
        User user = getUser(username);
        if(user == null) {
            return new Result(false, "User not found");
        }
        String input = scanner.nextLine().trim();
        Matcher matcher = LoginMenuCommands.answerQuestion.getMatcher(input);
        if(matcher == null) return new Result(false, "Invalid answer question");
        if(!matcher.group("answer").equalsIgnoreCase(user.getSecurityAnswer())) {
            return new Result(false, "Wrong answer");
        }
        LoginMenu.print("choose a new password . (random for random generated password)");
        String newPassword = scanner.nextLine().trim();
        Result managePasswordResult = managePassword(newPassword , newPassword);
        if(!managePasswordResult.isSuccess()) return managePasswordResult;
        else newPassword = managePasswordResult.toString();
        user.setPassword(newPassword);
        return new Result(true, "password successfully changed");
    }



    public User getUser(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public Result checkPasswordStrength(String password) {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern number = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[" + Regexes.SpecialCharacters + "]");
        if(password.length() < 8) return new Result(false, "");
        if(!uppercase.matcher(password).find())
            return new Result(false, "");
        if(!lowercase.matcher(password).find())
            return new Result(false, "");
        if(!number.matcher(password).find())
            return new Result(false, "");
        if(!special.matcher(password).find())
            return new Result(false, "");
        return new Result(true, "password is strong enough");
    }
    public Result managePassword(String password , String confirmPassword) throws IOException {
        if(password.equalsIgnoreCase("random")){
            Result result = generateRandomPassword();
            if(result.isSuccess()) {
                password = result.toString();
                confirmPassword = result.toString();
                System.out.println("random password was chosen");
            }
            else return result;
        }
        if(Regexes.Password.getMatcher(password) == null) {
            return new Result(false, "Password is not valid");
        }
        Result passCheckResult = checkPasswordStrength(password);
        if(!passCheckResult.isSuccess()) return passCheckResult;
        if(!password.equals(confirmPassword)) {
            return new Result(false, "Password does not match the confirmation");
        }
        return new Result(true, password);
    }
    public Result generateRandomPassword() throws IOException {
        List<Character> chars = getCharacters();
        Collections.shuffle(chars);
        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : chars) {
            shuffledPassword.append(c);
        }
        LoginMenu.print("random generated password: " + shuffledPassword);
        LoginMenu.print("do you want to keep the password ? (y/n)\n" +
                           "n will take you back to login menu");
        while(true) {
            String answer = LoginMenu.scan();
            if (answer.equals("y")) {
                return new Result(true, shuffledPassword.toString());
            } else if (answer.equals("n")) {
                return new Result(false, "Redirecting to login menu ...");
            }
            else LoginMenu.print("invalid input");
        }
    }

    private static List<Character> getCharacters() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0 ; i < 2 + random.nextInt(3); i++) {
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

    public void setView(SignUpUI signUpUI) {
        this.view = signUpUI;
    }
    public void setView(LoginUI loginUI) {
        this.LoginView = loginUI;
    }
}
