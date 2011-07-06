//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Implements a rigid body (translation, rotation) transform.
 */
public class RigidTransform extends AbstractTransform
{
    /** Identifies the rigid body transform in {@link #generality}. */
    public static final int GENERALITY = 1;

    /** The rotation component of this transform (in radians). */
    public float rotation;

    /** The translation components of this transform. */
    public float tx, ty;

    /** Creates a rigid body transform with zero translation and rotation. */
    public RigidTransform () {
    }

    /** Creates a rigid body transform with the specified translation and rotation. */
    public RigidTransform (float rotation, float tx, float ty) {
        setRotation(rotation);
        setTranslation(tx, ty);
    }

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
        return rotation;
    }

    @Override // from Transform
    public float getTx () {
        return tx;
    }

    @Override // from Transform
    public float getTy () {
        return ty;
    }

    @Override // from Transform
    public Transform setRotation (float angle) {
        this.rotation = angle;
        return this;
    }

    @Override // from Transform
    public Transform setTx (float tx) {
        this.tx = tx;
        return this;
    }

    @Override // from Transform
    public Transform setTy (float ty) {
        this.ty = ty;
        return this;
    }

    @Override // from Transform
    public Transform invert () {
        Vector t = getTranslation().negateLocal().rotateLocal(-rotation);
        return new RigidTransform(-rotation, t.x, t.y);
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.preConcatenate(this);
        }

        Vector nt = other.getTranslation();
        nt.rotateAndAdd(rotation, getTranslation(), nt);
        float nrotation = FloatMath.normalizeAngle(rotation + other.getRotation());
        return new RigidTransform(nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.concatenate(this);
        }

        Vector nt = getTranslation();
        nt.rotateAndAdd(other.getRotation(), other.getTranslation(), nt);
        float nrotation = FloatMath.normalizeAngle(other.getRotation() + rotation);
        return new RigidTransform(nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Transform lerp (Transform other, float t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }
        Vector nt = getTranslation().lerpLocal(other.getTranslation(), t);
        return new RigidTransform(FloatMath.lerpa(rotation, other.getRotation(), t), nt.x, nt.y);
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return Points.transform(p.getX(), p.getY(), 1, 1, rotation, tx, ty, into);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) { 
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            IPoint s = src[srcOff++];
            Points.transform(s.getX(), s.getY(), 1, 1, sina, cosa, tx, ty, dst[dstOff++]);
        }
    }

    @Override // from Transform
    public void transform (float[] src, int srcOff, float[] dst, int dstOff, int count) {
        Point p = new Point();
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            Points.transform(src[srcOff++], src[srcOff++], 1, 1, sina, cosa, tx, ty, p);
            dst[dstOff++] = p.x;
            dst[dstOff++] = p.y;
        }
    }

    @Override // from Transform
    public Point inverseTransform (IPoint p, Point into) {
        return Points.inverseTransform(p.getX(), p.getY(), 1, 1, rotation, tx, ty, into);
    }

    @Override // from Transform
    public Vector transform (IVector v, Vector into) {
        return v.rotate(rotation, into);
    }

    @Override // from Transform
    public Vector inverseTransform (IVector v, Vector into) {
        return v.rotate(-rotation, into);
    }

    @Override // from Transform
    public Transform clone () {
        return new RigidTransform(rotation, tx, ty);
    }

    @Override // from Transform
    public int generality () {
        return GENERALITY;
    }

    @Override
    public String toString () {
        return "rigid [rot=" + rotation + ", trans=" + getTranslation() + "]";
    }
}
