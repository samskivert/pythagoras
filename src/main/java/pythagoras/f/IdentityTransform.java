//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Implements the identity transform.
 */
public class IdentityTransform extends AbstractTransform
{
    /** Identifies the identity transform in {@link #generality}. */
    public static final int GENERALITY = 0;

    @Override // from Transform
    public float getUniformScale () {
        return 1;
    }

    @Override // from Transform
    public float getScaleX () {
        return 1;
    }

    @Override // from Transform
    public float getScaleY () {
        return 1;
    }

    @Override // from Transform
    public float getRotation () {
        return 0;
    }

    @Override // from Transform
    public float getTx () {
        return 0;
    }

    @Override // from Transform
    public float getTy () {
        return 0;
    }

    @Override // from Transform
    public Transform invert () {
        return this;
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        return other;
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        return other;
    }

    @Override // from Transform
    public Transform lerp (Transform other, float t) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return into.set(p);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) {
        for (int ii = 0; ii < count; ii++) {
            transform(src[srcOff++], dst[dstOff++]);
        }
    }

    @Override // from Transform
    public void transform (float[] src, int srcOff, float[] dst, int dstOff, int count) {
        for (int ii = 0; ii < count; ii++) {
            dst[dstOff++] = src[srcOff++];
        }
    }

    @Override // from Transform
    public Point inverseTransform (IPoint p, Point into) {
        return into.set(p);
    }

    @Override // from Transform
    public Vector transform (IVector v, Vector into) {
        return into.set(v);
    }

    @Override // from Transform
    public Vector inverseTransform (IVector v, Vector into) {
        return into.set(v);
    }

    @Override // from Transform
    public Transform clone () {
        return this;
    }

    @Override // from Transform
    public int generality () {
        return GENERALITY;
    }

    @Override
    public String toString () {
        return "ident";
    }
}
