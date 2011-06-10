//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * An exception thrown by {@link AffineTransform} when a request for an inverse transform cannot be
 * satisfied.
 */
public class NoninvertibleTransformException extends java.lang.Exception
{
    public NoninvertibleTransformException (String s) {
        super(s);
    }
}
