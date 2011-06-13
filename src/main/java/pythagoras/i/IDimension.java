//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

/**
 * Provides read-only access to a {@link Dimension}.
 */
public interface IDimension extends Cloneable
{
    /**
     * Returns the magnitude in the x-dimension.
     */
    int getWidth ();

    /**
     * Returns the magnitude in the y-dimension.
     */
    int getHeight ();

    /**
     * Returns a mutable copy of this dimension.
     */
    Dimension clone ();
}
