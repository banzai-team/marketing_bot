package banz.ai.marketing.bot.modelinterceptor.exception;

public class InvalidModelResponse extends RuntimeException {

    private final int statusCode;

    public InvalidModelResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public InvalidModelResponse(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public InvalidModelResponse(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public InvalidModelResponse(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public InvalidModelResponse(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statusCode = statusCode;
    }
}
