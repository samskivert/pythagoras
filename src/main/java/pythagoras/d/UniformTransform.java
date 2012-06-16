//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Implements a uniform (translation, rotation, scale) transform.
 */
public class UniformTransform extends AbstractTransform
{
    /** Identifies the uniform transform in {@link #generality}. */
    public static final int GENERALITY = 2;

    /** The uniform scale component of this transform. */
    public double scale;

    /** The rotation component of this transform (in radians). */
    public double rotation;

    /** The translation components of this transform. */
    public double tx, ty;

    /** Creates a uniform transform with zero translation and rotation, and unit scale. */
    public UniformTransform () {
        setUniformScale(1);
    }

    /** Creates a uniform transform with the specified translation, rotation and scale. */
    public UniformTransform (double scale, double rotation, double tx, double ty) {
        setUniformScale(scale);
        setRotation(rotation);
        setTranslation(tx, ty);
    }

    @Override // from Transform
    public double uniformScale () {
        return scale;
    }

    @Override // from Transform
    public double scaleX () {
        return scale;
    }

    @Override // from Transform
    public double scaleY () {
        return scale;
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
        matrix[0] = cosa * scale;  matrix[1] = sina * scale;
        matrix[2] = -sina * scale; matrix[3] = cosa * scale;
        matrix[4] = tx;            matrix[5] = ty;
    }

    @Override // from Transform
    public Transform setUniformScale (double scale) {
        if (scale == 0) throw new IllegalArgumentException("Scale must be non-zero.");
        this.scale = scale;
        return this;
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
    public Transform uniformScale (double scale) {
        if (scale == 0) throw new IllegalArgumentException("Scale must be non-zero.");
        this.tx *= scale;
        this.ty *= scale;
        this.scale *= scale;
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
        double nscale = 1f / scale, nrotation = -rotation;
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
        double nrotation = MathUtil.normalizeAngle(rotation + other.rotation());
        double nscale = scale * other.uniformScale();
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
        double nrotation = MathUtil.normalizeAngle(other.rotation() + rotation);
        double nscale = other.uniformScale() * scale;
        return new UniformTransform(nscale, nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Transform lerp (Transform other, double t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }

        Vector nt = translation().lerpLocal(other.translation(), t);
        double nrotation = MathUtil.lerpa(rotation, other.rotation(), t);
        double nscale = MathUtil.lerp(scale, other.uniformScale(), t);
        return new UniformTransform(nscale, nrotation, nt.x, nt.y);
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return Points.transform(p.x(), p.y(), scale, scale, rotation, tx, ty, into);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) {
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            IPoint p = src[srcOff++];
            Points.transform(p.x(), p.y(), scale, scale, sina, cosa, tx, ty, dst[dstOff++]);
        }
    }

    @Override // from Transform
    public void transform (double[] src, int srcOff, double[] dst, int dstOff, int count) {
        Point p = new Point();
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
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
