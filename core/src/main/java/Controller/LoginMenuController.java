package Controller;

import GraphicView.ForgotPasswordUI;
import GraphicView.LoginUI;
import GraphicView.MainMenuUI;
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
        if(!managePasswordResult.isSuccess()) {
            view.getAdvanceButton().setChecked(false);
            return managePasswordResult;
        }
        password = view.getPasswordField().getText();
        Gender genderEnum = switch (view.getGenderBox().getSelected().toString().toLowerCase()) {
            case "male" -> Gender.male;
            case "female" -> Gender.female;
            default -> null;
        };
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
        App.serializeApp();
        return new Result(true , "user successfully registered , now you can log in");
    }

    public Result login(String username, String password, boolean stayLoggedIn) throws IOException {
        User user = getUser(username);
        if(user == null) {
            return new Result(false, "User not found");
        }
        if(!user.getPassword().equals(SHA256.hashString(password))) {
            return new Result(false, "Wrong password");
        }
        App.setLoggedInUser(user);
        App.setStayLoggedIn(stayLoggedIn);
        App.setCurrentMenu(Menu.MainMenu);
        Main.getGame().getScreen().dispose();
        Main.getGame().setScreen(new MainMenuUI(new MainMenuController()));
        App.serializeApp();
        return new Result(true, "user successfully logged in");
    }

    public Result forgotPassword(String username) throws IOException {
        if(!LoginView.getForgotPasswordButton().isChecked())return null;
        User user = getUser(username);
        if(user == null) {
            showErrorDialog(Dialogues.ErrorUserDoesNotExist.title, Dialogues.ErrorUserDoesNotExist.message);
            LoginView.getForgotPasswordButton().setChecked(false);
            return new Result(false, "User not found");
        }
        Main.getGame().getScreen().dispose();
        Main.getGame().setScreen(new ForgotPasswordUI(new ForgotPasswordMenuController(),user));
        return null;
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
        boolean Strong = false;
        if(password.length() < 8) {
            showErrorDialog(Dialogues.ErrorPasswordNotLongEnough.title, Dialogues.ErrorPasswordNotLongEnough.message);
            return new Result(false, "Password too short");
        }
        if(!uppercase.matcher(password).find()) {
            showErrorDialog(Dialogues.ErrorNoUpperCase.title, Dialogues.ErrorNoUpperCase.message);
            return new Result(false, "Password is too short");
        }
        if(!lowercase.matcher(password).find()) {
            showErrorDialog(Dialogues.ErrorNoLowerCase.title, Dialogues.ErrorNoLowerCase.message);
            return new Result(false, "Password is too short");
        }
        if(!number.matcher(password).find()) {
            showErrorDialog(Dialogues.ErrorNoNumber.title, Dialogues.ErrorNoNumber.message);
            return new Result(false, "Password is too short");
        }
        if(!special.matcher(password).find()) {
            showErrorDialog(Dialogues.ErrorNoSpecialCharacter.title, Dialogues.ErrorNoSpecialCharacter.message);
            return new Result(false, "Password is too short");
        }
        return new Result(true, "password is strong enough");
    }
    public Result checkRandom() throws IOException {
        if(view.getGenerateRandomPasswordButton().isChecked()){
            Result result = generateRandomPassword();
            view.getGenerateRandomPasswordButton().setChecked(false);
            return result;
        }
        return null;
    }
    public Result managePassword(String password , String confirmPassword) throws IOException {
        if(Regexes.Password.getMatcher(password) == null) {
            showErrorDialog(Dialogues.ErrorInvalidPassword.title, Dialogues.ErrorInvalidPassword.message);
            return new Result(false, "Password is not valid");
        }
        Result passCheckResult = checkPasswordStrength(password);
        if(!passCheckResult.isSuccess()) return passCheckResult;
        if(!password.equals(confirmPassword)) {
            showErrorDialog(Dialogues.ErrorConfirmPasswordFailed.title, Dialogues.ErrorConfirmPasswordFailed.message);
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
        view.getPasswordField().setText(shuffledPassword.toString());
        return new Result(true, shuffledPassword.toString());
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
