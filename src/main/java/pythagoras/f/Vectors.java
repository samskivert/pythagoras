//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Vector-related utility methods.
 */
public class Vectors
{
    /** A unit vector in the X+ direction. */
    public static final IVector UNIT_X = new Vector(1f, 0f);

    /** A unit vector in the Y+ direction. */
    public static final IVector UNIT_Y = new Vector(0f, 1f);

    /** The zero vector. */
    public static final IVector ZERO = new Vector(0f, 0f);

    /** A vector containing the minimum floating point value for all components
     * (note: the components are -{@link Float#MAX_VALUE}, not {@link Float#MIN_VALUE}). */
    public static final IVector MIN_VALUE = new Vector(-Float.MAX_VALUE, -Float.MAX_VALUE);

    /** A vector containing the maximum floating point value for all components. */
    public static final IVector MAX_VALUE = new Vector(Float.MAX_VALUE, Float.MAX_VALUE);

    /**
     * Returns a string describing the supplied vector, of the form <code>+x+y</code>,
     * <code>+x-y</code>, <code>-x-y</code>, etc.
     */
    public static String vectorToString (float x, float y) {
        StringBuilder buf = new StringBuilder();
        if (x >= 0) buf.append("+");
        buf.append(x);
        if (y >= 0) buf.append("+");
        buf.append(y);
        return buf.toString();
    }
}
