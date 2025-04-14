package Model.enums;

public enum SecurityQuestions {
    Question1("1- What is your greatest childhood trauma ?"),
    Question2("2- Which trauma or insecurity has led you to be a TA ?"),
    Question3("3- What is your wildest fantasy about an AP assignment ?"),
    Question4("4- on scale of 1 to 100000 how much do you enjoy writing vague docs about AP assignments?"),
    ;
    public final String question;
    SecurityQuestions(String question) {
        this.question = question;
    }
}
