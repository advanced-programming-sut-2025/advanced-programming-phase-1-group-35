package Model.enums;

public enum Dialogues {
    ErrorUserExists("Error","Username is already in use"),
    ErrorInvalidUserName("Error", "Username is not valid"),
    ErrorInvalidEmail("Error", "Email is not valid"),
    ErrorPasswordNotLongEnough("Error", "Password is too short"),
    ErrorNoUpperCase("Error", "Password must contain at least one uppercase letter"),
    ErrorNoLowerCase("Error", "Password must contain at least one lowercase letter"),
    ErrorNoNumber("Error", "Password must contain at least one number"),
    ErrorNoSpecialCharacter("Error", "Password must contain at least one special"),
    ErrorSecurityAnswerEmpty("Error", "Security answer is too short"),
    ErrorConfirmPasswordFailed("Error", "Confirm password is incorrect"),
    ErrorWrongAnswer("Error", "Your answer is incorrect"),
    ErrorInvalidPassword("Error","Your password format is invalid" ),
    ErrorPasswordIncorrect("Error", "Password is incorrect"),
    ErrorUserDoesNotExist("Error", "Username does not exist"),

    ;
    public String title, message;
    Dialogues(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
