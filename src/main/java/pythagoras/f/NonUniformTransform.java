//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Implements a uniform (translation, rotation, scaleX, scaleY) transform.
 */
public class NonUniformTransform extends AbstractTransform
{
    /** Identifies the uniform transform in {@link #generality}. */
    public static final int GENERALITY = 3;

    /** The scale components of this transform. */
    public float scaleX, scaleY;

    /** The rotation component of this transform (in radians). */
    public float rotation;

    /** The translation components of this transform. */
    public float tx, ty;

    /** Creates a uniform transform with zero translation and rotation, and unit scale. */
    public NonUniformTransform () {
        this.scaleX = this.scaleY = 1;
    }

    /** Creates a uniform transform with the specified translation, rotation and scale. */
    public NonUniformTransform (float scaleX, float scaleY, float rotation, float tx, float ty) {
        setScale(scaleX, scaleY);
        setRotation(rotation);
        setTranslation(tx, ty);
    }

    @Override // from Transform
    public float uniformScale () {
        return (scaleX + scaleY) / 2; // TODO: is this sane
    }

    @Override // from Transform
    public float scaleX () {
        return scaleX;
    }

    @Override // from Transform
    public float scaleY () {
        return scaleY;
    }

    @Override // from Transform
    public float rotation () {
        return rotation;
    }

    @Override // from Transform
    public float tx () {
        return tx;
    }

    @Override // from Transform
    public float ty () {
        return ty;
    }

    @Override // from Transform
    public void get (float[] matrix) {
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        matrix[0] = cosa * scaleX;  matrix[1] = sina * scaleY;
        matrix[2] = -sina * scaleX; matrix[3] = cosa * scaleY;
        matrix[4] = tx;             matrix[5] = ty;
    }

    @Override // from Transform
    public Transform setUniformScale (float scale) {
        setScaleX(scale);
        setScaleY(scale);
        return this;
    }

    @Override // from Transform
    public Transform setScaleX (float scaleX) {
        if (scaleX == 0) throw new IllegalArgumentException("Scale (x) must not be zero.");
        this.scaleX = scaleX;
        return this;
    }

    @Override // from Transform
    public Transform setScaleY (float scaleY) {
        if (scaleY == 0) throw new IllegalArgumentException("Scale (y) must not be zero.");
        this.scaleY = scaleY;
        return this;
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
    public Transform uniformScale (float scale) {
        return scale(scale, scale);
    }

    @Override // from Transform
    public Transform scaleX (float scaleX) {
        if (scaleX == 0) throw new IllegalArgumentException("Scale (x) must not be zero.");
        this.tx *= scaleX;
        this.scaleX *= scaleX;
        return this;
    }

    @Override // from Transform
    public Transform scaleY (float scaleY) {
        if (scaleY == 0) throw new IllegalArgumentException("Scale (y) must not be zero.");
        this.ty *= scaleX;
        this.scaleY *= scaleY;
        return this;
    }

    @Override // from Transform
    public Transform rotate (float angle) {
        float otx = this.tx, oty = this.ty;
        if (otx != 0 || oty != 0) {
            float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
            this.tx = otx*cosa - oty*sina;
            this.ty = otx*sina + oty*cosa;
        }
        this.rotation += angle;
        return this;
    }

    @Override // from Transform
    public Transform translateX (float tx) {
        this.tx += tx;
        return this;
    }

    @Override // from Transform
    public Transform translateY (float ty) {
        this.ty += ty;
        return this;
    }

    @Override // from Transform
    public Transform invert () {
        Vector iscale = new Vector(1f / scaleX, 1f / scaleY);
        Vector t = new Vector(tx, ty).negateLocal().rotateLocal(-rotation).scaleLocal(iscale);
        return new NonUniformTransform(iscale.x, iscale.y, -rotation, t.x, t.y);
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.preConcatenate(this);
        }

        float otx = other.tx(), oty = other.ty();
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        float ntx = (otx*cosa - oty*sina) * scaleX + tx();
        float nty = (otx*sina + oty*cosa) * scaleY + ty();

        float nrotation = MathUtil.normalizeAngle(rotation + other.rotation());
        float nscaleX = scaleX * other.scaleX();
        float nscaleY = scaleY * other.scaleY();
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.concatenate(this);
        }

        float tx = tx(), ty = ty();
        float sina = FloatMath.sin(other.rotation()), cosa = FloatMath.cos(other.rotation());
        float ntx = (tx*cosa - ty*sina) * other.scaleX() + other.tx();
        float nty = (tx*sina + ty*cosa) * other.scaleY() + other.ty();
        float nrotation = MathUtil.normalizeAngle(other.rotation() + rotation);
        float nscaleX = other.scaleX() * scaleX;
        float nscaleY = other.scaleY() * scaleY;
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Transform lerp (Transform other, float t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }

        float ntx = MathUtil.lerpa(tx, other.tx(), t);
        float nty = MathUtil.lerpa(ty, other.ty(), t);
        float nrotation = MathUtil.lerpa(rotation, other.rotation(), t);
        float nscaleX = MathUtil.lerp(scaleX, other.scaleX(), t);
        float nscaleY = MathUtil.lerp(scaleY, other.scaleY(), t);
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return Points.transform(p.x(), p.y(), scaleX, scaleY, rotation, tx, ty, into);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) {
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            IPoint s = src[srcOff++];
            Points.transform(s.x(), s.y(), scaleX, scaleY, sina, cosa, tx, ty, dst[dstOff++]);
        }
    }

    @Override // from Transform
    public void transform (float[] src, int srcOff, float[] dst, int dstOff, int count) {
        Point p = new Point();
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            Points.transform(src[srcOff++], src[srcOff++], scaleX, scaleY, sina, cosa, tx, ty, p);
            dst[dstOff++] = p.x;
            dst[dstOff++] = p.y;
        }
    }

    @Override // from Transform
    public Point inverseTransform (IPoint p, Point into) {
        return Points.inverseTransform(p.x(), p.y(), scaleX, scaleY, rotation, tx, ty, into);
    }

    @Override // from Transform
    public Vector transformPoint (IVector v, Vector into) {
        return Vectors.transform(v.x(), v.y(), scaleX, scaleY, rotation, tx, ty, into);
    }

    @Override // from Transform
    public Vector transform (IVector v, Vector into) {
        return Vectors.transform(v.x(), v.y(), scaleX, scaleY, rotation, into);
    }

    @Override // from Transform
    public Vector inverseTransform (IVector v, Vector into) {
        return Vectors.inverseTransform(v.x(), v.y(), scaleX, scaleY, rotation, into);
    }

    @Override // from Transform
    public Transform clone () {
        return new NonUniformTransform(scaleX, scaleY, rotation, tx, ty);
    }

    @Override // from Transform
    public int generality () {
        return GENERALITY;
    }

    @Override
    public String toString () {
        return "nonunif [scale=" + scale() + ", rot=" + rotation +
            ", trans=" + translation() + "]";
    }
}
