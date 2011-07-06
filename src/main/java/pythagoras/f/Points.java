//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Point-related utility methods.
 */
public class Points
{
    /**
     * Returns the squared Euclidean distance between the specified two points.
     */
    public static float distanceSq (float x1, float y1, float x2, float y2) {
        x2 -= x1;
        y2 -= y1;
        return x2 * x2 + y2 * y2;
    }

    /**
     * Returns the Euclidean distance between the specified two points.
     */
    public static float distance (float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(distanceSq(x1, y1, x2, y2));
    }

    /** Transforms a point as specified, storing the result in the point provided.
     * @return a reference to the result point, for chaining. */
    public static Point transform (float x, float y, float sx, float sy, float rotation,
                                   float tx, float ty, Point result) {
        return transform(x, y, sx, sy, FloatMath.sin(rotation), FloatMath.cos(rotation), tx, ty,
                         result);
    }

    /** Transforms a point as specified, storing the result in the point provided.
     * @return a reference to the result point, for chaining. */
    public static Point transform (float x, float y, float sx, float sy, float sina, float cosa,
                                   float tx, float ty, Point result) {
        return result.set((x*cosa - y*sina) * sx + tx, (x*sina + y*cosa) * sy + ty);
    }

    /** Inverse transforms a point as specified, storing the result in the point provided.
     * @return a reference to the result point, for chaining. */
    public static Point inverseTransform (float x, float y, float sx, float sy, float rotation,
                                          float tx, float ty, Point result) {
        x -= tx; y -= ty; // untranslate
        float sinnega = FloatMath.sin(-rotation), cosnega = FloatMath.cos(-rotation);
        float nx = (x * cosnega - y * sinnega); // unrotate
        float ny = (x * sinnega + y * cosnega);
        return result.set(nx / sx, ny / sy); // unscale
    }

    /**
     * Returns a string describing the supplied point, of the form <code>+x+y</code>,
     * <code>+x-y</code>, <code>-x-y</code>, etc.
     */
    public static String pointToString (float x, float y) {
        StringBuilder buf = new StringBuilder();
        if (x >= 0) buf.append("+");
        buf.append(FloatMath.toString(x));
        if (y >= 0) buf.append("+");
        buf.append(FloatMath.toString(y));
        return buf.toString();
    }
}
