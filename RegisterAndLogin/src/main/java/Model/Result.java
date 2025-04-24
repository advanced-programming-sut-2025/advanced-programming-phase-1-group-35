package Model;

public class Result {
    private final boolean success;
    private final String message;

    public Result(boolean success , String message) {
        this.message = message;
        this.success = success;
    }

    @Override
    public String toString() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
