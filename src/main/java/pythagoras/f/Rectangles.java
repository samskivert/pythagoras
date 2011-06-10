//
// $Id$

package pythagoras.f;

/**
 * Rectangle-related utility methods.
 */
public class Rectangles
{
    /**
     * Intersects the supplied two rectangles, writing the result into {@link dst}.
     */
    public static void intersect (IRectangle src1, IRectangle src2, Rectangle dst) {
        float x1 = Math.max(src1.getMinX(), src2.getMinX());
        float y1 = Math.max(src1.getMinY(), src2.getMinY());
        float x2 = Math.min(src1.getMaxX(), src2.getMaxX());
        float y2 = Math.min(src1.getMaxY(), src2.getMaxY());
        dst.setBounds(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Unions the supplied two rectangles, writing the result into {@link dst}.
     */
    public static void union (IRectangle src1, IRectangle src2, Rectangle dst) {
        float x1 = Math.min(src1.getMinX(), src2.getMinX());
        float y1 = Math.min(src1.getMinY(), src2.getMinY());
        float x2 = Math.max(src1.getMaxX(), src2.getMaxX());
        float y2 = Math.max(src1.getMaxY(), src2.getMaxY());
        dst.setBounds(x1, y1, x2 - x1, y2 - y1);
    }
}
