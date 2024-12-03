/** Exception for trying to splice a list into itself */
public class SelfInsertException extends RuntimeException {
  /** default constructor */
  public SelfInsertException() {
    super("A node cannot be inserted after itself.");
  }
}