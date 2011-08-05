//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * {@link Transform} related utility methods.
 */
public class Transforms
{
    /**
     * Creates and returns a new shape that is the supplied shape transformed by this transform's
     * matrix.
     */
    public static IShape createTransformedShape (Transform t, IShape src) {
        if (src == null) {
            return null;
        }
        if (src instanceof Path) {
            return ((Path)src).createTransformedShape(t);
        }
        PathIterator path = src.getPathIterator(t);
        Path dst = new Path(path.getWindingRule());
        dst.append(path, false);
        return dst;
    }

    /**
     * Multiplies the supplied two affine transforms, storing the result in {@code into}. {@code
     * into} may refer to the same instance as {@code a} or {@code b}.
     * @return {@code into} for chaining.
     */
    public static AffineTransform multiply (
        AffineTransform a, AffineTransform b, AffineTransform into) {
        return multiply(a.m00, a.m01, a.m10, a.m11, a.tx, a.ty,
                        b.m00, b.m01, b.m10, b.m11, b.tx, b.ty, into);
    }

    /**
     * Multiplies the supplied two affine transforms, storing the result in {@code into}. {@code
     * into} may refer to the same instance as {@code a}.
     * @return {@code into} for chaining.
     */
    public static AffineTransform multiply (
        AffineTransform a, float m00, float m01, float m10, float m11, float tx, float ty,
        AffineTransform into) {
        return multiply(a.m00, a.m01, a.m10, a.m11, a.tx, a.ty,
                        m00, m01, m10, m11, tx, ty, into);
    }

    /**
     * Multiplies the supplied two affine transforms, storing the result in {@code into}. {@code
     * into} may refer to the same instance as {@code b}.
     * @return {@code into} for chaining.
     */
    public static AffineTransform multiply (
        float m00, float m01, float m10, float m11, float tx, float ty,
        AffineTransform b, AffineTransform into) {
        return multiply(m00, m01, m10, m11, tx, ty,
                        b.m00, b.m01, b.m10, b.m11, b.tx, b.ty, into);
    }

    /**
     * Multiplies the supplied two affine transforms, storing the result in {@code into}.
     * @return {@code into} for chaining.
     */
    public static AffineTransform multiply (
        float am00, float am01, float am10, float am11, float atx, float aty,
        float bm00, float bm01, float bm10, float bm11, float btx, float bty,
        AffineTransform into) {
        into.m00 = am00 * bm00 + am10 * bm01;
        into.m01 = am01 * bm00 + am11 * bm01;
        into.m10 = am00 * bm10 + am10 * bm11;
        into.m11 = am01 * bm10 + am11 * bm11;
        into.tx  = am00 *  btx + am10 *  bty + atx;
        into.ty  = am01 *  btx + am11 *  bty + aty;
        return into;
    }
}
