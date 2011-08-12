//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Rectangle-related utility methods.
 */
public class Rectangles
{
    /**
     * Intersects the supplied two rectangles, writing the result into {@code dst}.
     */
    public static void intersect (IRectangle src1, IRectangle src2, Rectangle dst) {
        double x1 = Math.max(src1.minX(), src2.minX());
        double y1 = Math.max(src1.minY(), src2.minY());
        double x2 = Math.min(src1.maxX(), src2.maxX());
        double y2 = Math.min(src1.maxY(), src2.maxY());
        dst.setBounds(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Unions the supplied two rectangles, writing the result into {@code dst}.
     */
    public static void union (IRectangle src1, IRectangle src2, Rectangle dst) {
        double x1 = Math.min(src1.minX(), src2.minX());
        double y1 = Math.min(src1.minY(), src2.minY());
        double x2 = Math.max(src1.maxX(), src2.maxX());
        double y2 = Math.max(src1.maxY(), src2.maxY());
        dst.setBounds(x1, y1, x2 - x1, y2 - y1);
    }
}
