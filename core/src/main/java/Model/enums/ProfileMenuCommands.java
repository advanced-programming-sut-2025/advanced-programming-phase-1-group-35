package Model.enums;

public enum ProfileMenuCommands {
    changeUsername(""),
    changePassword(""),
    changeEmail(""),
    changeNickname(""),
    showUserInfo(""),
    ;


    private final String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }


}
