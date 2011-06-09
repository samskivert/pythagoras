//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Contains geometry routines that don't fit more nicely into specialized classes.
 */
public class Geometry
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
     * Returns true if the specified two line segments intersect.
     */
    public static boolean linesIntersect (float x1, float y1, float x2, float y2,
                                          float x3, float y3, float x4, float y4) {
        // A = (x2-x1, y2-y1)
        // B = (x3-x1, y3-y1)
        // C = (x4-x1, y4-y1)
        // D = (x4-x3, y4-y3) = C-B
        // E = (x1-x3, y1-y3) = -B
        // F = (x2-x3, y2-y3) = A-B
        //
        // Result is ((AxB) * (AxC) <= 0) and ((DxE) * (DxF) <= 0)
        // 
        // DxE = (C-B)x(-B) = BxB-CxB = BxC
        // DxF = (C-B)x(A-B) = CxA-CxB-BxA+BxB = AxB+BxC-AxC
        x2 -= x1; // A
        y2 -= y1;
        x3 -= x1; // B
        y3 -= y1;
        x4 -= x1; // C
        y4 -= y1;

        float AvB = x2 * y3 - x3 * y2;
        float AvC = x2 * y4 - x4 * y2;

        // online
        if (AvB == 0 && AvC == 0) {
            if (x2 != 0) {
                return (x4 * x3 <= 0) ||
                    ((x3 * x2 >= 0) && (x2 > 0 ? x3 <= x2 || x4 <= x2 : x3 >= x2 || x4 >= x2));
            }
            if (y2 != 0) {
                return (y4 * y3 <= 0) ||
                    ((y3 * y2 >= 0) && (y2 > 0 ? y3 <= y2 || y4 <= y2 : y3 >= y2 || y4 >= y2));
            }
            return false;
        }

        float BvC = x3 * y4 - x4 * y3;
        return (AvB * AvC <= 0) && (BvC * (AvB + BvC - AvC) <= 0);
    }

    /**
     * Returns true if the specified line segment intersects the specified rectangle.
     */
    public static boolean lineIntersectsRect (float x1, float y1, float x2, float y2,
                                              float rx, float ry, float rw, float rh) {
        float rr = rx + rw, rb = ry + rh;
        return (rx <= x1 && x1 <= rr && ry <= y1 && y1 <= rb)
            || (rx <= x2 && x2 <= rr && ry <= y2 && y2 <= rb)
            || linesIntersect(rx, ry, rr, rb, x1, y1, x2, y2)
            || linesIntersect(rr, ry, rx, rb, x1, y1, x2, y2);
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

    /**
     * Returns a string describing the supplied dimension, of the form <code>widthxheight</code>.
     */
    public static String dimenToString (float width, float height) {
        return width + "x" + height;
    }
}
