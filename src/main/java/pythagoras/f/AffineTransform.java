//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import pythagoras.util.NoninvertibleTransformException;

/**
 * Implements an affine (3x2 matrix) transform. The transformation matrix has the form:
 * <pre>{@code
 * [ m00, m10, 0 ]
 * [ m01, m11, 0 ]
 * [  tx,  ty, 1 ]
 * }</pre>
 */
public class AffineTransform extends AbstractTransform
{
    /** Identifies the affine transform in {@link #generality}. */
    public static final int GENERALITY = 4;

    /** The scale, rotation and shear components of this transform. */
    public float m00, m01, m10, m11;

    /** The translation components of this transform. */
    public float tx, ty;

    /** Creates an affine transform configured with the identity transform. */
    public AffineTransform () {
        this(1, 0, 0, 1, 0, 0);
    }

    /** Creates an affine transform from the supplied scale, rotation and translation. */
    public AffineTransform (float scale, float angle, float tx, float ty) {
        this(scale, scale, angle, tx, ty);
    }

    /** Creates an affine transform from the supplied scale, rotation and translation. */
    public AffineTransform (float scaleX, float scaleY, float angle, float tx, float ty) {
        float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
        this.m00 = cosa * scaleX; this.m01 = -sina * scaleX;
        this.m10 = sina * scaleY; this.m11 =  cosa * scaleY;
        this.tx  = tx;            this.ty  =  ty;
    }

    /** Creates an affine transform with the specified transform matrix. */
    public AffineTransform (float m00, float m01, float m10, float m11, float tx, float ty) {
        this.m00 = m00; this.m01 = m01;
        this.m10 = m10; this.m11 = m11;
        this.tx  = tx;  this.ty  = ty;
    }

    @Override // from Transform
    public float getUniformScale () {
        // the square root of the signed area of the parallelogram spanned by the axis vectors
        float cp = m00*m11 - m01*m10;
        return (cp < 0f) ? -FloatMath.sqrt(-cp) : FloatMath.sqrt(cp);
    }

    @Override // from Transform
    public float getScaleX () {
        return FloatMath.sqrt(m00*m00 + m01*m01);
    }

    @Override // from Transform
    public float getScaleY () {
        return FloatMath.sqrt(m10*m10 + m11*m11);
    }

    @Override // from Transform
    public float getRotation () {
        // use the iterative polar decomposition algorithm described by Ken Shoemake:
        // http://www.cs.wisc.edu/graphics/Courses/838-s2002/Papers/polar-decomp.pdf

        // start with the contents of the upper 2x2 portion of the matrix
        float n00 = m00, n10 = m10;
        float n01 = m01, n11 = m11;
        for (int ii = 0; ii < 10; ii++) {
            // store the results of the previous iteration
            float o00 = n00, o10 = n10;
            float o01 = n01, o11 = n11;

            // compute average of the matrix with its inverse transpose
            float det = o00*o11 - o10*o01;
            if (Math.abs(det) == 0f) {
                // determinant is zero; matrix is not invertible
                throw new NoninvertibleTransformException(this.toString());
            }
            float hrdet = 0.5f / det;
            n00 = +o11 * hrdet + o00*0.5f;
            n10 = -o01 * hrdet + o10*0.5f;

            n01 = -o10 * hrdet + o01*0.5f;
            n11 = +o00 * hrdet + o11*0.5f;

            // compute the difference; if it's small enough, we're done
            float d00 = n00 - o00, d10 = n10 - o10;
            float d01 = n01 - o01, d11 = n11 - o11;
            if (d00*d00 + d10*d10 + d01*d01 + d11*d11 < FloatMath.EPSILON) {
                break;
            }
        }
        // now that we have a nice orthogonal matrix, we can extract the rotation
        return FloatMath.atan2(n01, n00);
    }

    @Override // from Transform
    public float getTx () {
        return this.tx;
    }

    @Override // from Transform
    public float getTy () {
        return this.tx;
    }

    @Override // from Transform
    public Transform setUniformScale (float scale) {
        return setScale(scale, scale);
    }

    @Override // from Transform
    public Transform setScaleX (float scaleX) {
        // normalize the scale to 1, then re-apply
        float osx = getScaleX();
        m00 /= osx; m01 /= osx;
        m00 *= scaleX; m01 *= scaleX;
        return this;
    }

    @Override // from Transform
    public Transform setScaleY (float scaleY) {
        // normalize the scale to 1, then re-apply
        float osy = getScaleY();
        m10 /= osy; m11 /= osy;
        m10 *= scaleY; m11 *= scaleY;
        return this;
    }

    @Override // from Transform
    public Transform setRotation (float angle) {
        // extract the scale, then reapply rotation and scale together
        float sx = getScaleX(), sy = getScaleY();
        float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
        m00 = cosa * sx; m01 = -sina * sx;
        m10 = sina * sy; m11 =  cosa * sy;
        return this;
    }

    @Override // from Transform
    public Transform setTranslation (float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
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
    public Transform setTransform (float m00, float m01, float m10, float m11, float tx, float ty) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
        this.tx = tx;
        this.ty = ty;
        return this;
    }

    @Override // from Transform
    public Transform uniformScale (float scale) {
        return scale(scale, scale);
    }

    @Override // from Transform
    public Transform scaleX (float scaleX) {
        m00 *= scaleX;
        m01 *= scaleX;
        tx  *= scaleX;
        return this;
    }

    @Override // from Transform
    public Transform scaleY (float scaleY) {
        m10 *= scaleY;
        m11 *= scaleY;
        ty  *= scaleY;
        return this;
    }

    @Override // from Transform
    public Transform rotate (float angle) {
        float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
        return Transforms.multiply(cosa, -sina, sina, cosa, 0, 0, this, this);
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
        // compute the determinant, storing the subdeterminants for later use
        float det = m00*m11 - m10*m01;
        if (Math.abs(det) == 0f) {
            // determinant is zero; matrix is not invertible
            throw new NoninvertibleTransformException(this.toString());
        }
        float rdet = 1f / det;
        return new AffineTransform(
            +m11 * rdet,              -m01 * rdet,
            -m10 * rdet,              +m00 * rdet,
            (m01*ty - m11*tx) * rdet, (m10*tx - m00*ty) * rdet);
    }

    @Override // from Transform
    public Transform concatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.preConcatenate(this);
        }
        if (other instanceof AffineTransform) {
            return Transforms.multiply(this, (AffineTransform)other, new AffineTransform());
        } else {
            AffineTransform oaff = new AffineTransform(other);
            return Transforms.multiply(this, oaff, oaff);
        }
    }

    @Override // from Transform
    public Transform preConcatenate (Transform other) {
        if (generality() < other.generality()) {
            return other.concatenate(this);
        }
        if (other instanceof AffineTransform) {
            return Transforms.multiply((AffineTransform)other, this, new AffineTransform());
        } else {
            AffineTransform oaff = new AffineTransform(other);
            return Transforms.multiply(oaff, this, oaff);
        }
    }

    @Override // from Transform
    public Transform lerp (Transform other, float t) {
        if (generality() < other.generality()) {
            return other.lerp(this, -t); // TODO: is this correct?
        }

        AffineTransform ot = (other instanceof AffineTransform) ?
            (AffineTransform)other : new AffineTransform(other);
        return new AffineTransform(
            m00 + t*(ot.m00 - m00), m01 + t*(ot.m01 - m01),
            m10 + t*(ot.m10 - m10), m11 + t*(ot.m11 - m11),
            tx  + t*(ot.tx  - tx ), ty  + t*(ot.ty  - ty ));
    }

    @Override // from Transform
    public Point transform (IPoint p, Point into) {
        float x = p.getX(), y = p.getY();
        return into.set(m00*x + m01*y + tx, m10*x + m11*y + ty);
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
            float x = src[srcOff++], y = src[srcOff++];
            dst[dstOff++] = m00*x + m01*y + tx;
            dst[dstOff++] = m10*x + m11*y + ty;
        }
    }

    @Override // from Transform
    public Point inverseTransform (IPoint p, Point into) {
        return invert().transform(p, into); // TODO
    }

    @Override // from Transform
    public Vector transform (IVector v, Vector into) {
        float x = v.getX(), y = v.getY();
        return into.set(m00*x + m01*y, m10*x + m11*y);
    }

    @Override // from Transform
    public Vector inverseTransform (IVector v, Vector into) {
        return invert().transform(v, into); // TODO
    }

    @Override // from Transform
    public Transform clone () {
        return new AffineTransform(m00, m01, m10, m11, tx, ty);
    }

    @Override // from Transform
    public int generality () {
        return GENERALITY;
    }

    @Override
    public String toString () {
        return "affine [" + FloatMath.toString(m00) + " " + FloatMath.toString(m01) + " " +
            FloatMath.toString(m10) + " " + FloatMath.toString(m11) + " " + getTranslation() + "]";
    }

    // we don't publicize this because it might encourage someone to do something stupid like
    // create a new AffineTransform from another AffineTransform using this instead of clone()
    protected AffineTransform (Transform other) {
        this(other.getScaleX(), other.getScaleY(), other.getRotation(),
             other.getTx(), other.getTy());
    }
}
