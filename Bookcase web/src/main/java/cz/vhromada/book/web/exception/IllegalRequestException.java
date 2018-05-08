package cz.vhromada.book.web.exception;

/**
 * A class represents exception caused by illegal request.
 *
 * @author Vladimir Hromada
 */
public class IllegalRequestException extends RuntimeException {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of IllegalRequestException.
     *
     * @param message message
     */
    public IllegalRequestException(final String message) {
        super(message);
    }

}
