package helper;

public class ResponseSuccess {
    private String message;

    public ResponseSuccess(String message, String... args) {
        this.message = String.format(message, args);
    }

    public ResponseSuccess(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
