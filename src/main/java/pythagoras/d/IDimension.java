//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Provides read-only access to a {@link Dimension}.
 */
public interface IDimension extends Cloneable
{
    /**
     * Returns the magnitude in the x-dimension.
     */
    double getWidth ();

    /**
     * Returns the magnitude in the y-dimension.
     */
    double getHeight ();

    /**
     * Returns a mutable copy of this dimension.
     */
    Dimension clone ();
}
