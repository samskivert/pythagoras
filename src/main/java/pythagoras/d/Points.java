//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Point-related utility methods.
 */
public class Points
{
    /**
     * Returns the squared Euclidean distance between the specified two points.
     */
    public static double distanceSq (double x1, double y1, double x2, double y2) {
        x2 -= x1;
        y2 -= y1;
        return x2 * x2 + y2 * y2;
    }

    /**
     * Returns the Euclidean distance between the specified two points.
     */
    public static double distance (double x1, double y1, double x2, double y2) {
        return Math.sqrt(distanceSq(x1, y1, x2, y2));
    }

    /**
     * Returns a string describing the supplied point, of the form <code>+x+y</code>,
     * <code>+x-y</code>, <code>-x-y</code>, etc.
     */
    public static String pointToString (double x, double y) {
        StringBuilder buf = new StringBuilder();
        if (x >= 0) buf.append("+");
        buf.append(x);
        if (y >= 0) buf.append("+");
        buf.append(y);
        return buf.toString();
    }
}
