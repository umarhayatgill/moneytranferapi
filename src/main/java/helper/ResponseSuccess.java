package helper;

public class ResponseSuccess {
    private String message;

    public ResponseSuccess(String message, String... args) {
        this.message = String.format(message, args);
    }

    public ResponseSuccess(Exception e) {
        this.message = e.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
