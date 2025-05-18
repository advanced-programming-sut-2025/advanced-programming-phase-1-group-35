package Controller;

import Model.App;
import Model.Result;
import Model.User;
import Model.enums.Regexes;

import java.io.IOException;

public class ProfileMenuController {
    LoginMenuController loginMenuController = new LoginMenuController();
    public Result showUserInfo() {
        User user = App.getLoggedInUser();
        return new Result(true , "Username : " + user.getUsername() +
                "\nNickname : " + user.getNickname() +
                "\nHighScore : " + user.getHighScore() +
                "\nGames Played : " + user.getGamesPlayed());
    }

    public Result changeUsername(String username) {
        if(username.equals(App.getLoggedInUser().getUsername())) {
            return new Result(false , "now that wouldn't be a change would it ?");
        }
        if(Regexes.Username.getMatcher(username) == null){
            return new Result(false , "new username is invalid");
        }
        if(loginMenuController.getUser(username) != null){
            return new Result(false , "username is already taken");
        }
        App.getLoggedInUser().setUsername(username);
        return new Result(true , "username has been changed");
    }

    public Result changePassword(String oldPassword, String newPassword) throws IOException {
        if(oldPassword.equals(newPassword)){
            return new Result(false , "now that wouldn't be a change would it ?");
        }
        Result managePasswordResult = loginMenuController.managePassword(newPassword , newPassword);
        if(!managePasswordResult.isSuccess()) return managePasswordResult;
        else newPassword = managePasswordResult.toString();
        App.getLoggedInUser().setPassword(newPassword);
        return new Result(true , "password has been changed");
    }

    public Result changeNickname(String nickname) {
        if(nickname.equals(App.getLoggedInUser().getNickname())){
            return new Result(false , "now that wouldn't be a change would it ?");
        }
        App.getLoggedInUser().setNickname(nickname);
        return new Result(true , "nickname has been changed");
    }

    public Result changeEmail(String email) {
        if(email.equals(App.getLoggedInUser().getEmail())){
            return new Result(false , "now that wouldn't be a change would it ?");
        }
        if(Regexes.Email.getMatcher(email) == null){
            return new Result(false , "new email is invalid");
        }
        App.getLoggedInUser().setEmail(email);
        return new Result(true , "email has been changed");
    }
}
