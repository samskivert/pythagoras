//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Implements a uniform (translation, rotation, scale) transform.
 */
public class UniformTransform extends AbstractTransform
{
    /** Identifies the uniform transform in {@link #generality}. */
    public static final int GENERALITY = 2;

    /** The uniform scale component of this transform. */
    public float scale;

    /** The rotation component of this transform (in radians). */
    public float rotation;

    /** The translation components of this transform. */
    public float tx, ty;

    /** Creates a uniform transform with zero translation and rotation, and unit scale. */
    public UniformTransform () {
        setUniformScale(1);
    }

    /** Creates a uniform transform with the specified translation, rotation and scale. */
    public UniformTransform (float scale, float rotation, float tx, float ty) {
        setUniformScale(scale);
        setRotation(rotation);
        setTranslation(tx, ty);
    }

    @Override // from Transform
    public float uniformScale () {
        return scale;
    }

    @Override // from Transform
    public float scaleX () {
        return scale;
    }

    @Override // from Transform
    public float scaleY () {
        return scale;
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
        matrix[0] = cosa * scale;  matrix[1] = sina * scale;
        matrix[2] = -sina * scale; matrix[3] = cosa * scale;
        matrix[4] = tx;            matrix[5] = ty;
    }

    @Override // from Transform
    public Transform setUniformScale (float scale) {
        if (scale == 0) throw new IllegalArgumentException("Scale must be non-zero.");
        this.scale = scale;
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
        if (scale == 0) throw new IllegalArgumentException("Scale must be non-zero.");
        this.tx *= scale;
        this.ty *= scale;
        this.scale *= scale;
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
        float nscale = 1f / scale, nrotation = -rotation;
        Vector t = translation().negateLocal().rotateLocal(nrotation).scaleLocal(nscale);
        return new UniformTransform(nscale, nrotation, t.x, t.y);
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.preConcatenate(this);
        }

        Vector nt = other.translation();
        nt.rotateScaleAndAdd(rotation, scale, translation(), nt);
        float nrotation = MathUtil.normalizeAngle(rotation + other.rotation());
        float nscale = scale * other.uniformScale();
        return new UniformTransform(nscale, nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.concatenate(this);
        }

        Vector nt = translation();
        nt.rotateScaleAndAdd(other.rotation(), other.uniformScale(),
                             other.translation(), nt);
        float nrotation = MathUtil.normalizeAngle(other.rotation() + rotation);
        float nscale = other.uniformScale() * scale;
        return new UniformTransform(nscale, nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Transform lerp (Transform other, float t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }

        Vector nt = translation().lerpLocal(other.translation(), t);
        float nrotation = MathUtil.lerpa(rotation, other.rotation(), t);
        float nscale = MathUtil.lerp(scale, other.uniformScale(), t);
        return new UniformTransform(nscale, nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return Points.transform(p.x(), p.y(), scale, scale, rotation, tx, ty, into);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) {
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            IPoint p = src[srcOff++];
            Points.transform(p.x(), p.y(), scale, scale, sina, cosa, tx, ty, dst[dstOff++]);
        }
    }

    @Override // from Transform
    public void transform (float[] src, int srcOff, float[] dst, int dstOff, int count) {
        Point p = new Point();
        float sina = FloatMath.sin(rotation), cosa = FloatMath.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            Points.transform(src[srcOff++], src[srcOff++], scale, scale, sina, cosa, tx, ty, p);
            dst[dstOff++] = p.x;
            dst[dstOff++] = p.y;
        }
    }

    @Override // from Transform
    public Point inverseTransform (IPoint p, Point into) {
        return Points.inverseTransform(p.x(), p.y(), scale, scale, rotation, tx, ty, into);
    }

    @Override // from Transform
    public Vector transformPoint (IVector p, Vector into) {
        return Vectors.transform(p.x(), p.y(), scale, scale, rotation, tx, ty, into);
    }

    @Override // from Transform
    public Vector transform (IVector v, Vector into) {
        return Vectors.transform(v.x(), v.y(), scale, scale, rotation, into);
    }

    @Override // from Transform
    public Vector inverseTransform (IVector v, Vector into) {
        return Vectors.inverseTransform(v.x(), v.y(), scale, scale, rotation, into);
    }

    @Override // from Transform
    public Transform clone () {
        return new UniformTransform(scale, rotation, tx, ty);
    }

    @Override // from Transform
    public int generality () {
        return GENERALITY;
    }

    @Override
    public String toString () {
        return "uniform [scale=" + scale + ", rot=" + rotation +
            ", trans=" + translation() + "]";
    }
}
