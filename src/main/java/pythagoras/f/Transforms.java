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
}
