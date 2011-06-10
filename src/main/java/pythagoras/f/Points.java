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
     * Returns the squared Euclidian distance between the specified two points.
     */
    public static float distanceSq (float x1, float y1, float x2, float y2) {
        x2 -= x1;
        y2 -= y1;
        return x2 * x2 + y2 * y2;
    }

    /**
     * Returns the Euclidian distance between the specified two points.
     */
    public static float distance (float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(distanceSq(x1, y1, x2, y2));
    }

    /**
     * Returns a string describing the supplied point, of the form <code>+x+y</code>,
     * <code>+x-y</code>, <code>-x-y</code>, etc.
     */
    public static String pointToString (float x, float y) {
        StringBuilder buf = new StringBuilder();
        if (x >= 0) buf.append("+");
        buf.append(x);
        if (y >= 0) buf.append("+");
        buf.append(y);
        return buf.toString();
    }
}
