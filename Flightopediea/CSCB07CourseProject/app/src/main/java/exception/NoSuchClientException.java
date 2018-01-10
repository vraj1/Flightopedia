package exception;
/**
 * Exception class if no such client exists.
 */
public class NoSuchClientException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new <code>NoSuchClientException</code> with the given message.
   * @param message the message for this <code>NoSuchFlightException</code>
   */
  public NoSuchClientException(String message) {
    super(message);
  }
}
