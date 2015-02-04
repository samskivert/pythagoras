//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.util;

/**
 * Thrown when inversion is attempted on a singular (non-invertible) matrix.
 */
public class SingularMatrixException extends RuntimeException
{
    private static final long serialVersionUID = -4744745375693073952L;

    /**
     * Creates a new exception.
     */
    public SingularMatrixException () {
    }

    /**
     * Creates a new exception with the provided message.
     */
    public SingularMatrixException (String message) {
        super(message);
    }
}
