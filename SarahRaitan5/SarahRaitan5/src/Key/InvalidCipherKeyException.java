package Key;

/**
 * Created by hackeru on 3/21/2017.
 */
public class InvalidCipherKeyException extends NumberFormatException {
    public InvalidCipherKeyException(String message) {
        super("Your cipher key seems to be invalid: " + message);
    }
}
