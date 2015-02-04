//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * An exception thrown if an operation is performed on a {@link Path} that is in an illegal state
 * with respect to the particular operation being performed. For example, appending a segment to a
 * path without an initial moveto.
 */
public class IllegalPathStateException extends RuntimeException
{
    private static final long serialVersionUID = 5494939619370624441L;

    public IllegalPathStateException () {
    }

    public IllegalPathStateException (String s) {
        super(s);
    }
}
