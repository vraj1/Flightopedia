package exception;

/**
 * Exception class if no such flight exists.
 */
public class NoSuchFlightException extends Exception {

  private static final long serialVersionUID = 6087721090539288166L;

  /**
   * Creates a new <code>NoSuchFlightException</code> with the given message.
   * 
   * @param message the message for this <code>NoSuchFlightException</code>
   */
  public NoSuchFlightException(String message) {
    super(message);
  }
}
