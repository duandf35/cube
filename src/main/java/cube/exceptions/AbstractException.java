package cube.exceptions;

/**
 * @author wenyu
 * @since 10/22/15
 */
public abstract class AbstractException extends Exception {

    public String message;

    public AbstractException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
