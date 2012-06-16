//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Implements a rigid body (translation, rotation) transform.
 */
public class RigidTransform extends AbstractTransform
{
    /** Identifies the rigid body transform in {@link #generality}. */
    public static final int GENERALITY = 1;

    /** The rotation component of this transform (in radians). */
    public double rotation;

    /** The translation components of this transform. */
    public double tx, ty;

    /** Creates a rigid body transform with zero translation and rotation. */
    public RigidTransform () {
    }

    /** Creates a rigid body transform with the specified translation and rotation. */
    public RigidTransform (double rotation, double tx, double ty) {
        setRotation(rotation);
        setTranslation(tx, ty);
    }

    @Override // from Transform
    public double uniformScale () {
        return 1;
    }

    @Override // from Transform
    public double scaleX () {
        return 1;
    }

    @Override // from Transform
    public double scaleY () {
        return 1;
    }

    @Override // from Transform
    public double rotation () {
        return rotation;
    }

    @Override // from Transform
    public double tx () {
        return tx;
    }

    @Override // from Transform
    public double ty () {
        return ty;
    }

    @Override // from Transform
    public void get (double[] matrix) {
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
        matrix[0] = cosa;  matrix[1] = sina;
        matrix[2] = -sina; matrix[3] = cosa;
        matrix[4] = tx;    matrix[5] = ty;
    }

    @Override // from Transform
    public Transform setRotation (double angle) {
        this.rotation = angle;
        return this;
    }

    @Override // from Transform
    public Transform setTx (double tx) {
        this.tx = tx;
        return this;
    }

    @Override // from Transform
    public Transform setTy (double ty) {
        this.ty = ty;
        return this;
    }

    @Override // from Transform
    public Transform rotate (double angle) {
        double otx = this.tx, oty = this.ty;
        if (otx != 0 || oty != 0) {
            double sina = Math.sin(angle), cosa = Math.cos(angle);
            this.tx = otx*cosa - oty*sina;
            this.ty = otx*sina + oty*cosa;
        }
        this.rotation += angle;
        return this;
    }

    @Override // from Transform
    public Transform translateX (double tx) {
        this.tx += tx;
        return this;
    }

    @Override // from Transform
    public Transform translateY (double ty) {
        this.ty += ty;
        return this;
    }

    @Override // from Transform
    public Transform invert () {
        Vector t = translation().negateLocal().rotateLocal(-rotation);
        return new RigidTransform(-rotation, t.x, t.y);
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.preConcatenate(this);
        }

        Vector nt = other.translation();
        nt.rotateAndAdd(rotation, translation(), nt);
        double nrotation = MathUtil.normalizeAngle(rotation + other.rotation());
        return new RigidTransform(nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.concatenate(this);
        }

        Vector nt = translation();
        nt.rotateAndAdd(other.rotation(), other.translation(), nt);
        double nrotation = MathUtil.normalizeAngle(other.rotation() + rotation);
        return new RigidTransform(nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Transform lerp (Transform other, double t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }
        Vector nt = translation().lerpLocal(other.translation(), t);
        return new RigidTransform(MathUtil.lerpa(rotation, other.rotation(), t), nt.x, nt.y);
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return Points.transform(p.x(), p.y(), 1, 1, rotation, tx, ty, into);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) {
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            IPoint s = src[srcOff++];
            Points.transform(s.x(), s.y(), 1, 1, sina, cosa, tx, ty, dst[dstOff++]);
        }
    }

    @Override // from Transform
    public void transform (double[] src, int srcOff, double[] dst, int dstOff, int count) {
        Point p = new Point();
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            Points.transform(src[srcOff++], src[srcOff++], 1, 1, sina, cosa, tx, ty, p);
            dst[dstOff++] = p.x;
            dst[dstOff++] = p.y;
        }
    }

    @Override // from Transform
    public Point inverseTransform (IPoint p, Point into) {
        return Points.inverseTransform(p.x(), p.y(), 1, 1, rotation, tx, ty, into);
    }

    @Override // from Transform
    public Vector transformPoint (IVector v, Vector into) {
        return Vectors.transform(v.x(), v.y(), 1, 1, rotation, tx, ty, into);
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
        return "rigid [rot=" + rotation + ", trans=" + translation() + "]";
    }
}
