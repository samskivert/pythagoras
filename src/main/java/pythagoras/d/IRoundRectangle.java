//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Provides read-only access to a {@link RoundRectangle}.
 */
public interface IRoundRectangle extends IRectangularShape, Cloneable
{
    /** Returns the width of the corner arc. */
    double getArcWidth ();

    /** Returns the height of the corner arc. */
    double getArcHeight ();

    /** Returns a mutable copy of this round rectangle. */
    RoundRectangle clone ();
}
