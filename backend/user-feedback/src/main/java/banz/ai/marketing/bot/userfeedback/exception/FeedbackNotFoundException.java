package banz.ai.marketing.bot.userfeedback.exception;

public class FeedbackNotFoundException extends RuntimeException {

  private final long feedbackId;

  public FeedbackNotFoundException(long feedbackId) {
    this.feedbackId = feedbackId;
  }

  public FeedbackNotFoundException(String message, long feedbackId) {
    super(message);
    this.feedbackId = feedbackId;
  }

  public FeedbackNotFoundException(String message, Throwable cause, long feedbackId) {
    super(message, cause);
    this.feedbackId = feedbackId;
  }

  public FeedbackNotFoundException(Throwable cause, long feedbackId) {
    super(cause);
    this.feedbackId = feedbackId;
  }

  public FeedbackNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long feedbackId) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.feedbackId = feedbackId;
  }
}
