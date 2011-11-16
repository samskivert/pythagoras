//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

/**
 * Math utility methods.
 */
public class MathUtil
{
    /**
     * Clamps the supplied {@code value} to between {@code low} and {@code high} (both inclusive).
     */
    public static int clamp (int value, int low, int high) {
        if (value < low) return low;
        if (value > high) return high;
        return value;
    }
}
