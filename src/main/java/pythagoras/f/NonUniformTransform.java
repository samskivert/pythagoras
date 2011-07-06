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
    public float getUniformScale () {
        return (scaleX + scaleY) / 2; // TODO: is this sane
    }

    @Override // from Transform
    public float getScaleX () {
        return scaleX;
    }

    @Override // from Transform
    public float getScaleY () {
        return scaleY;
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
    public Transform invert () {
        Vector iscale = new Vector(1f / scaleX, 1f / scaleY);
        Vector t = new Vector(tx, ty).negateLocal().rotateLocal(-rotation).multLocal(iscale);
        return new NonUniformTransform(iscale.x, iscale.y, -rotation, t.x, t.y);
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.preConcatenate(this);
        }

        float otx = other.getTx(), oty = other.getTy();
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        float ntx = (otx*cosa - oty*sina) * scaleX + getTx();
        float nty = (otx*sina + oty*cosa) * scaleY + getTy();

        float nrotation = FloatMath.normalizeAngle(rotation + other.getRotation());
        float nscaleX = scaleX * other.getScaleX();
        float nscaleY = scaleY * other.getScaleY();
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.concatenate(this);
        }

        float tx = getTx(), ty = getTy();
        float sina = FloatMath.sin(other.getRotation()), cosa = FloatMath.cos(other.getRotation());
        float ntx = (tx*cosa - ty*sina) * other.getScaleX() + other.getTx();
        float nty = (tx*sina + ty*cosa) * other.getScaleY() + other.getTy();
        float nrotation = FloatMath.normalizeAngle(other.getRotation() + rotation);
        float nscaleX = other.getScaleX() * scaleX;
        float nscaleY = other.getScaleY() * scaleY;
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Transform lerp (Transform other, float t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }

        Vector nt = getTranslation().lerpLocal(other.getTranslation(), t);
        float nrotation = FloatMath.lerpa(rotation, other.getRotation(), t);
        float nscaleX = FloatMath.lerp(scaleX, other.getScaleX(), t);
        float nscaleY = FloatMath.lerp(scaleY, other.getScaleY(), t);
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return Points.transform(p.getX(), p.getY(), scaleX, scaleY, rotation, tx, ty, into);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) { 
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            IPoint s = src[srcOff++];
            Points.transform(s.getX(), s.getY(), scaleX, scaleY, sina, cosa, tx, ty, dst[dstOff++]);
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
        return Points.inverseTransform(p.getX(), p.getY(), scaleX, scaleY, rotation, tx, ty, into);
    }

    @Override // from Transform
    public Vector transform (IVector v, Vector into) {
        return Vectors.transform(v.getX(), v.getY(), scaleX, scaleY, rotation, into);
    }

    @Override // from Transform
    public Vector inverseTransform (IVector v, Vector into) {
        return Vectors.inverseTransform(v.getX(), v.getY(), scaleX, scaleY, rotation, into);
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
        return "nonunif [scale=" + getScale() + ", rot=" + rotation +
            ", trans=" + getTranslation() + "]";
    }
}
