//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Dimension-related utility methods.
 */
public class Dimensions
{
    /**
     * Returns a string describing the supplied dimension, of the form <code>widthxheight</code>.
     */
    public static String dimenToString (double width, double height) {
        return width + "x" + height;
    }
}
