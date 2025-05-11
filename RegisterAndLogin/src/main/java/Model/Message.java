package Model;

public class Message {
    private int senderID;
    private String message;
    private int receiverID;
    private boolean isAnswered = false;

    public Message(int senderIP, String message, int receiverIP) {
        this.senderID = senderIP;
        this.message = message;
        this.receiverID = receiverIP;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
