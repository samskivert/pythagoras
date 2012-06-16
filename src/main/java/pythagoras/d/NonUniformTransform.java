//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Implements a uniform (translation, rotation, scaleX, scaleY) transform.
 */
public class NonUniformTransform extends AbstractTransform
{
    /** Identifies the uniform transform in {@link #generality}. */
    public static final int GENERALITY = 3;

    /** The scale components of this transform. */
    public double scaleX, scaleY;

    /** The rotation component of this transform (in radians). */
    public double rotation;

    /** The translation components of this transform. */
    public double tx, ty;

    /** Creates a uniform transform with zero translation and rotation, and unit scale. */
    public NonUniformTransform () {
        this.scaleX = this.scaleY = 1;
    }

    /** Creates a uniform transform with the specified translation, rotation and scale. */
    public NonUniformTransform (double scaleX, double scaleY, double rotation, double tx, double ty) {
        setScale(scaleX, scaleY);
        setRotation(rotation);
        setTranslation(tx, ty);
    }

    @Override // from Transform
    public double uniformScale () {
        return (scaleX + scaleY) / 2; // TODO: is this sane
    }

    @Override // from Transform
    public double scaleX () {
        return scaleX;
    }

    @Override // from Transform
    public double scaleY () {
        return scaleY;
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
        matrix[0] = cosa * scaleX;  matrix[1] = sina * scaleY;
        matrix[2] = -sina * scaleX; matrix[3] = cosa * scaleY;
        matrix[4] = tx;             matrix[5] = ty;
    }

    @Override // from Transform
    public Transform setUniformScale (double scale) {
        setScaleX(scale);
        setScaleY(scale);
        return this;
    }

    @Override // from Transform
    public Transform setScaleX (double scaleX) {
        if (scaleX == 0) throw new IllegalArgumentException("Scale (x) must not be zero.");
        this.scaleX = scaleX;
        return this;
    }

    @Override // from Transform
    public Transform setScaleY (double scaleY) {
        if (scaleY == 0) throw new IllegalArgumentException("Scale (y) must not be zero.");
        this.scaleY = scaleY;
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
        return scale(scale, scale);
    }

    @Override // from Transform
    public Transform scaleX (double scaleX) {
        if (scaleX == 0) throw new IllegalArgumentException("Scale (x) must not be zero.");
        this.tx *= scaleX;
        this.scaleX *= scaleX;
        return this;
    }

    @Override // from Transform
    public Transform scaleY (double scaleY) {
        if (scaleY == 0) throw new IllegalArgumentException("Scale (y) must not be zero.");
        this.ty *= scaleX;
        this.scaleY *= scaleY;
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
        Vector iscale = new Vector(1f / scaleX, 1f / scaleY);
        Vector t = new Vector(tx, ty).negateLocal().rotateLocal(-rotation).scaleLocal(iscale);
        return new NonUniformTransform(iscale.x, iscale.y, -rotation, t.x, t.y);
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.preConcatenate(this);
        }

        double otx = other.tx(), oty = other.ty();
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
        double ntx = (otx*cosa - oty*sina) * scaleX + tx();
        double nty = (otx*sina + oty*cosa) * scaleY + ty();

        double nrotation = MathUtil.normalizeAngle(rotation + other.rotation());
        double nscaleX = scaleX * other.scaleX();
        double nscaleY = scaleY * other.scaleY();
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.concatenate(this);
        }

        double tx = tx(), ty = ty();
        double sina = Math.sin(other.rotation()), cosa = Math.cos(other.rotation());
        double ntx = (tx*cosa - ty*sina) * other.scaleX() + other.tx();
        double nty = (tx*sina + ty*cosa) * other.scaleY() + other.ty();
        double nrotation = MathUtil.normalizeAngle(other.rotation() + rotation);
        double nscaleX = other.scaleX() * scaleX;
        double nscaleY = other.scaleY() * scaleY;
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Transform lerp (Transform other, double t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }

        double ntx = MathUtil.lerpa(tx, other.tx(), t);
        double nty = MathUtil.lerpa(ty, other.ty(), t);
        double nrotation = MathUtil.lerpa(rotation, other.rotation(), t);
        double nscaleX = MathUtil.lerp(scaleX, other.scaleX(), t);
        double nscaleY = MathUtil.lerp(scaleY, other.scaleY(), t);
        return new NonUniformTransform(nscaleX, nscaleY, nrotation, ntx, nty);
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        return Points.transform(p.x(), p.y(), scaleX, scaleY, rotation, tx, ty, into);
    }

    @Override // from Transform
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count) {
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
        for (int ii = 0; ii < count; ii++) {
            IPoint s = src[srcOff++];
            Points.transform(s.x(), s.y(), scaleX, scaleY, sina, cosa, tx, ty, dst[dstOff++]);
        }
    }

    @Override // from Transform
    public void transform (double[] src, int srcOff, double[] dst, int dstOff, int count) {
        Point p = new Point();
        double sina = Math.sin(rotation), cosa = Math.cos(rotation);
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
