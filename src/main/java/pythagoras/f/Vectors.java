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
     * Returns the magnitude of the specified vector.
     */
    public static final float length (float x, float y) {
        return FloatMath.sqrt(lengthSq(x, y));
    }

    /**
     * Returns the square of the magnitude of the specified vector.
     */
    public static final float lengthSq (float x, float y) {
        return (x*x + y*y);
    }

    /**
     * Transforms a point as specified, storing the result in the point provided.
     * @return a reference to the result vector, for chaining.
     */
    public static Vector transform (float x, float y, float sx, float sy, float rotation,
                                   Vector result) {
        return transform(x, y, sx, sy, FloatMath.sin(rotation), FloatMath.cos(rotation), result);
    }

    /**
     * Transforms a vector as specified, storing the result in the vector provided.
     * @return a reference to the result vector, for chaining.
     */
    public static Vector transform (float x, float y, float sx, float sy, float sina, float cosa,
                                    Vector result) {
        return result.set((x*cosa - y*sina) * sx, (x*sina + y*cosa) * sy);
    }

    /**
     * Inverse transforms a point as specified, storing the result in the point provided.
     * @return a reference to the result vector, for chaining.
     */
    public static Vector inverseTransform (float x, float y, float sx, float sy, float rotation,
                                           Vector result) {
        float sinnega = FloatMath.sin(-rotation), cosnega = FloatMath.cos(-rotation);
        float nx = (x * cosnega - y * sinnega); // unrotate
        float ny = (x * sinnega + y * cosnega);
        return result.set(nx / sx, ny / sy); // unscale
    }

    /**
     * Returns a string describing the supplied vector, of the form <code>+x+y</code>,
     * <code>+x-y</code>, <code>-x-y</code>, etc.
     */
    public static String vectorToString (float x, float y) {
        return FloatMath.toString(x) + FloatMath.toString(y);
    }
}
