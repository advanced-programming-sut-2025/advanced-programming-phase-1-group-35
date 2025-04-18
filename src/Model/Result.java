package Model;

public class Result {
    private final String message;
    private final boolean isSuccess;
    public Result(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }
    @Override
    public String toString() {
        return message;
    }
}
