package banz.ai.marketing.bot.modelbehavior.behavoir.exception;

public class InvalidModelBehaviorException extends RuntimeException {
    public InvalidModelBehaviorException() {
    }

    public InvalidModelBehaviorException(String message) {
        super(message);
    }

    public InvalidModelBehaviorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidModelBehaviorException(Throwable cause) {
        super(cause);
    }

    public InvalidModelBehaviorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
