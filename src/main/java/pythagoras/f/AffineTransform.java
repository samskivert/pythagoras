//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import java.io.Serializable;

import pythagoras.util.NoninvertibleTransformException;
import pythagoras.util.Platform;

/**
 * Represents a 2D affine transform, which performs a linear mapping that preserves the
 * straightness and parallelness of lines.
 *
 * See http://download.oracle.com/javase/6/docs/api/java/awt/geom/AffineTransform.html
 */
public class AffineTransform implements Cloneable, Serializable
{
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_TRANSLATION = 1;
    public static final int TYPE_UNIFORM_SCALE = 2;
    public static final int TYPE_GENERAL_SCALE = 4;
    public static final int TYPE_QUADRANT_ROTATION = 8;
    public static final int TYPE_GENERAL_ROTATION = 16;
    public static final int TYPE_GENERAL_TRANSFORM = 32;
    public static final int TYPE_FLIP = 64;
    public static final int TYPE_MASK_SCALE = TYPE_UNIFORM_SCALE | TYPE_GENERAL_SCALE;
    public static final int TYPE_MASK_ROTATION = TYPE_QUADRANT_ROTATION | TYPE_GENERAL_ROTATION;

    /**
     * Constructs an identity transform.
     */
    public AffineTransform () {
        setToIdentity();
    }

    /**
     * Constructs a transform that is a copy of the supplied transform.
     */
    public AffineTransform (AffineTransform t) {
        setTransform(t);
    }

    /**
     * Constructs a transform with the specified transformation matrix.
     */
    public AffineTransform (float m00, float m10, float m01, float m11, float m02, float m12) {
        setTransform(m00, m10, m01, m11, m02, m12);
    }

    /**
     * Constructs a transform with the specified transformation matrix.
     *
     * @param matrix either {@code [m00, m10, m01, m11]} or {@code [m00, m10, m01, m11, m02, m12]}.
     */
    public AffineTransform (float[] matrix) {
        this.type = TYPE_UNKNOWN;
        m00 = matrix[0];
        m10 = matrix[1];
        m01 = matrix[2];
        m11 = matrix[3];
        if (matrix.length > 4) {
            m02 = matrix[4];
            m12 = matrix[5];
        }
    }

    /**
     * Returns the type of this affine transform, which is a bitwise-or of the type flags
     * ({@link #TYPE_TRANSLATION}, etc.).
     */
    public int getType () {
        if (type != TYPE_UNKNOWN) {
            return type;
        }

        int type = 0;

        if (m00 * m01 + m10 * m11 != 0) {
            type |= TYPE_GENERAL_TRANSFORM;
            return type;
        }

        if (m02 != 0 || m12 != 0) {
            type |= TYPE_TRANSLATION;
        } else if (m00 == 1f && m11 == 1f && m01 == 0 && m10 == 0) {
            type = TYPE_IDENTITY;
            return type;
        }

        if (m00 * m11 - m01 * m10 < 0) {
            type |= TYPE_FLIP;
        }

        float dx = m00 * m00 + m10 * m10;
        float dy = m01 * m01 + m11 * m11;
        if (dx != dy) {
            type |= TYPE_GENERAL_SCALE;
        } else if (dx != 1f) {
            type |= TYPE_UNIFORM_SCALE;
        }

        if ((m00 == 0 && m11 == 0) || (m10 == 0 && m01 == 0 && (m00 < 0 || m11 < 0))) {
            type |= TYPE_QUADRANT_ROTATION;
        } else if (m01 != 0 || m10 != 0) {
            type |= TYPE_GENERAL_ROTATION;
        }

        return type;
    }

    /**
     * Returns the x-component of the scale vector.
     */
    public float getScaleX () {
        return m00;
    }

    /**
     * Returns the y-component of the scale vector.
     */
    public float getScaleY () {
        return m11;
    }

    /**
     * Returns the x-component of the shear vector.
     */
    public float getShearX () {
        return m01;
    }

    /**
     * Returns the y-component of the shear vector.
     */
    public float getShearY () {
        return m10;
    }

    /**
     * Returns the x-component of the translation vector.
     */
    public float getTranslateX () {
        return m02;
    }

    /**
     * Returns the y-component of the translation vector.
     */
    public float getTranslateY () {
        return m12;
    }

    /**
     * Returns true if this transform is the identity.
     */
    public boolean isIdentity () {
        return getType() == TYPE_IDENTITY;
    }

    /**
     * Fills in the supplied matrix with this transform's values.
     *
     * @param matrix either a length-4 or length-6 array.
     */
    public void getMatrix (float[] matrix) {
        matrix[0] = m00;
        matrix[1] = m10;
        matrix[2] = m01;
        matrix[3] = m11;
        if (matrix.length > 4) {
            matrix[4] = m02;
            matrix[5] = m12;
        }
    }

    /**
     * Returns the <a href="http://en.wikipedia.org/wiki/Determinant">determinant</a> of this
     * matrix.
     */
    public float getDeterminant () {
        return m00 * m11 - m01 * m10;
    }

    /**
     * Sets this transform's values.
     */
    public void setTransform (float m00, float m10, float m01, float m11, float m02, float m12) {
        this.type = TYPE_UNKNOWN;
        this.m00 = m00;
        this.m10 = m10;
        this.m01 = m01;
        this.m11 = m11;
        this.m02 = m02;
        this.m12 = m12;
    }

    /**
     * Sets this transform's values to be equal to those of the supplied transform.
     */
    public void setTransform (AffineTransform t) {
        setTransform(t.m00, t.m10, t.m01, t.m11, t.m02, t.m12);
        type = t.type;
    }

    /**
     * Sets this transform to the identity transform. Any existing transform values are
     * overwritten.
     */
    public void setToIdentity () {
        type = TYPE_IDENTITY;
        m00 = m11 = 1f;
        m10 = m01 = m02 = m12 = 0;
    }

    /**
     * Sets this transform to a simple translation using the supplied values. Any existing
     * transform values are overwritten.
     */
    public void setToTranslation (float tx, float ty) {
        m00 = m11 = 1f;
        m01 = m10 = 0;
        m02 = tx;
        m12 = ty;
        if (tx == 0 && ty == 0) {
            type = TYPE_IDENTITY;
        } else {
            type = TYPE_TRANSLATION;
        }
    }

    /**
     * Sets this transform to a simple scale using the supplied values. Any existing transform
     * values are overwritten.
     */
    public void setToScale (float scx, float scy) {
        m00 = scx;
        m11 = scy;
        m10 = m01 = m02 = m12 = 0;
        if (scx != 1f || scy != 1f) {
            type = TYPE_UNKNOWN;
        } else {
            type = TYPE_IDENTITY;
        }
    }

    /**
     * Sets this transform to a simple shear using the supplied values. Any existing transform
     * values are overwritten.
     */
    public void setToShear (float shx, float shy) {
        m00 = m11 = 1f;
        m02 = m12 = 0;
        m01 = shx;
        m10 = shy;
        if (shx != 0 || shy != 0) {
            type = TYPE_UNKNOWN;
        } else {
            type = TYPE_IDENTITY;
        }
    }

    /**
     * Sets this transform to a simple rotation using the supplied values. Any existing transform
     * values are overwritten.
     */
    public void setToRotation (float angle) {
        float sin = (float)Math.sin(angle);
        float cos = (float)Math.cos(angle);
        if (Math.abs(cos) < ZERO) {
            cos = 0;
            sin = sin > 0 ? 1f : -1f;
        } else if (Math.abs(sin) < ZERO) {
            sin = 0;
            cos = cos > 0 ? 1f : -1f;
        }
        m00 = m11 = cos;
        m01 = -sin;
        m10 = sin;
        m02 = m12 = 0;
        type = TYPE_UNKNOWN;
    }

    /**
     * Sets this transform to a simple rotation using the supplied values. Any existing transform
     * values are overwritten.
     */
    public void setToRotation (float angle, float px, float py) {
        setToRotation(angle);
        m02 = px * (1f - m00) + py * m10;
        m12 = py * (1f - m00) - px * m10;
        type = TYPE_UNKNOWN;
    }

    /**
     * Returns a transform that performs the specified translation.
     */
    public static AffineTransform getTranslateInstance (float tx, float ty) {
        AffineTransform t = new AffineTransform();
        t.setToTranslation(tx, ty);
        return t;
    }

    /**
     * Returns a transform that performs the specified scale.
     */
    public static AffineTransform getScaleInstance (float scx, float scY) {
        AffineTransform t = new AffineTransform();
        t.setToScale(scx, scY);
        return t;
    }

    /**
     * Returns a transform that performs the specified shear.
     */
    public static AffineTransform getShearInstance (float shx, float shy) {
        AffineTransform m = new AffineTransform();
        m.setToShear(shx, shy);
        return m;
    }

    /**
     * Returns a transform that performs the specified rotation.
     */
    public static AffineTransform getRotateInstance (float angle) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle);
        return t;
    }

    /**
     * Returns a transform that performs the specified rotation.
     */
    public static AffineTransform getRotateInstance (float angle, float x, float y) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle, x, y);
        return t;
    }

    /**
     * Concatenates the specified translation to this transform.
     */
    public void translate (float tx, float ty) {
        concatenate(getTranslateInstance(tx, ty));
    }

    /**
     * Concatenates the specified scale to this transform.
     */
    public void scale (float scx, float scy) {
        concatenate(getScaleInstance(scx, scy));
    }

    /**
     * Concatenates the specified shear to this transform.
     */
    public void shear (float shx, float shy) {
        concatenate(getShearInstance(shx, shy));
    }

    /**
     * Concatenates the specified rotation to this transform.
     */
    public void rotate (float angle) {
        concatenate(getRotateInstance(angle));
    }

    /**
     * Concatenates the specified rotation to this transform.
     */
    public void rotate (float angle, float px, float py) {
        concatenate(getRotateInstance(angle, px, py));
    }

    /**
     * Concatenates the specified transform to this transform.
     */
    public void concatenate (AffineTransform t) {
        multiply(t, this, this);
    }

    /**
     * Pre-concatenates the specified transform to this transform.
     */
    public void preConcatenate (AffineTransform t) {
        multiply(this, t, this);
    }

    /**
     * Computes the inverse of this transform and stores it in the supplied target.
     *
     * @return the supplied target.
     * @throws NoninvertibleTransformException if this transform cannot be inverted.
     */
    public AffineTransform createInverse (AffineTransform target)
        throws NoninvertibleTransformException {
        float det = getDeterminant();
        if (Math.abs(det) < ZERO) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }
        target.setTransform(m11 / det,  // m00
                            -m10 / det, // m10
                            -m01 / det, // m01
                            m00 / det,  // m11
                            (m01 * m12 - m11 * m02) / det,  // m02
                            (m10 * m02 - m00 * m12) / det); // m12
        return target;
    }

    /**
     * Computes and returns the inverse of this transform.
     *
     * @return the supplied target.
     * @throws NoninvertibleTransformException if this transform cannot be inverted.
     */
    public AffineTransform createInverse () throws NoninvertibleTransformException {
        return createInverse(new AffineTransform());
    }

    /**
     * Transforms the supplied point using this transform's matrix.
     *
     * @param src the point to be transformed.
     * @param dst the point in which to store the transformed values, if null a new instance will
     * be created. May be {@code src}.
     * @return the supplied (or created) destination point.
     */
    public Point transform (IPoint src, Point dst) {
        if (dst == null) {
            dst = new Point();
        }

        float x = src.getX(), y = src.getY();
        dst.setLocation(x * m00 + y * m01 + m02, x * m10 + y * m11 + m12);
        return dst;
    }

    /**
     * Transforms the supplied points using this transform's matrix.
     *
     * @param src the points to be transformed.
     * @param srcOff the offset into the {@code src} array at which to start.
     * @param dst the points into which to store the transformed points. May be {@code src}.
     * @param dstOff the offset into the {@code dst} array at which to start.
     * @param length the number of points to transform.
     */
    public void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int length) {
        while (--length >= 0) {
            IPoint srcPoint = src[srcOff++];
            float x = srcPoint.getX();
            float y = srcPoint.getY();
            Point dstPoint = dst[dstOff];
            if (dstPoint == null) {
                dstPoint = new Point();
            }
            dstPoint.setLocation(x * m00 + y * m01 + m02, x * m10 + y * m11 + m12);
            dst[dstOff++] = dstPoint;
        }
    }

    /**
     * Transforms the supplied points using this transform's matrix.
     *
     * @param src the points to be transformed (as {@code [x, y, x, y, ...]}).
     * @param srcOff the offset into the {@code src} array at which to start.
     * @param dst the points into which to store the transformed points. May be {@code src}.
     * @param dstOff the offset into the {@code dst} array at which to start.
     * @param length the number of points to transform.
     */
    public void transform (float[] src, int srcOff, float[] dst, int dstOff, int length) {
        int step = 2;
        if (src == dst && srcOff < dstOff && dstOff < srcOff + length * 2) {
            srcOff = srcOff + length * 2 - 2;
            dstOff = dstOff + length * 2 - 2;
            step = -2;
        }
        while (--length >= 0) {
            float x = src[srcOff + 0];
            float y = src[srcOff + 1];
            dst[dstOff + 0] = (x * m00 + y * m01 + m02);
            dst[dstOff + 1] = (x * m10 + y * m11 + m12);
            srcOff += step;
            dstOff += step;
        }
    }

    /**
     * Transforms the supplied relative distance vector (ignores the translation component).
     *
     * @param src the point to be transformed.
     * @param dst the point in which to store the transformed values, if null a new instance will
     * be created. May be {@code src}.
     * @return the supplied (or created) destination point.
     */
    public Point deltaTransform (IPoint src, Point dst) {
        if (dst == null) {
            dst = new Point();
        }
        float x = src.getX(), y = src.getY();
        dst.setLocation(x * m00 + y * m01, x * m10 + y * m11);
        return dst;
    }

    /**
     * Transforms the supplied relative distance vectors using this transform's matrix (ignores the
     * translation component).
     *
     * @param src the points to be transformed (as {@code [x, y, x, y, ...]}).
     * @param srcOff the offset into the {@code src} array at which to start.
     * @param dst the points into which to store the transformed points. May be {@code src}.
     * @param dstOff the offset into the {@code dst} array at which to start.
     * @param length the number of points to transform.
     */
    public void deltaTransform (float[] src, int srcOff, float[] dst, int dstOff, int length) {
        while (--length >= 0) {
            float x = src[srcOff++], y = src[srcOff++];
            dst[dstOff++] = x * m00 + y * m01;
            dst[dstOff++] = x * m10 + y * m11;
        }
    }

    /**
     * Transforms the supplied point using the inverse of this transform's matrix.
     *
     * @param src the point to be transformed.
     * @param dst the point in which to store the transformed values, if null a new instance will
     * be created. May be {@code src}.
     * @return the supplied (or created) destination point.
     */
    public Point inverseTransform (IPoint src, Point dst) throws NoninvertibleTransformException {
        float det = getDeterminant();
        if (Math.abs(det) < ZERO) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }
        if (dst == null) {
            dst = new Point();
        }
        float x = src.getX() - m02, y = src.getY() - m12;
        dst.setLocation((x * m11 - y * m01) / det, (y * m00 - x * m10) / det);
        return dst;
    }

    /**
     * Transforms the supplied points using the inverse of this transform's matrix.
     *
     * @param src the points to be transformed (as {@code [x, y, x, y, ...]}).
     * @param srcOff the offset into the {@code src} array at which to start.
     * @param dst the points into which to store the transformed points. May be {@code src}.
     * @param dstOff the offset into the {@code dst} array at which to start.
     * @param length the number of points to transform.
     */
    public void inverseTransform (float[] src, int srcOff, float[] dst, int dstOff, int length)
          throws NoninvertibleTransformException {
        float det = getDeterminant();
        if (Math.abs(det) < ZERO) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }
        while (--length >= 0) {
            float x = src[srcOff++] - m02, y = src[srcOff++] - m12;
            dst[dstOff++] = (x * m11 - y * m01) / det;
            dst[dstOff++] = (y * m00 - x * m10) / det;
        }
    }

    /**
     * Creates and returns a new shape that is the supplied shape transformed by this transform's
     * matrix.
     */
    public IShape createTransformedShape (IShape src) {
        if (src == null) {
            return null;
        }
        if (src instanceof Path) {
            return ((Path)src).createTransformedShape(this);
        }
        PathIterator path = src.getPathIterator(this);
        Path dst = new Path(path.getWindingRule());
        dst.append(path, false);
        return dst;
    }

    @Override
    public String toString () {
        return getClass().getName() +
            "[[" + m00 + ", " + m01 + ", " + m02 + "], [" + m10 + ", " + m11 + ", " + m12 + "]]";
    }

    // @Override // can't declare @Override due to GWT
    public AffineTransform clone () {
        AffineTransform xform = new AffineTransform(m00, m01, m02, m10, m11, m12);
        xform.type = this.type;
        return xform;
    }

    @Override
    public int hashCode () {
        return Platform.hashCode(m00) ^ Platform.hashCode(m01) ^ Platform.hashCode(m02) ^
            Platform.hashCode(m10) ^ Platform.hashCode(m11) ^ Platform.hashCode(m12);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AffineTransform) {
            AffineTransform t = (AffineTransform)obj;
            return m00 == t.m00 && m01 == t.m01 && m02 == t.m02 &&
                m10 == t.m10 && m11 == t.m11 && m12 == t.m12;
        }
        return false;
    }

    /**
     * Multiplies two transforms, storing the result in the target transform.
     * @return the supplied target transform.
     */
    protected AffineTransform multiply (AffineTransform t1, AffineTransform t2,
                                        AffineTransform into) {
        into.setTransform(t1.m00 * t2.m00 + t1.m10 * t2.m01, // m00
                          t1.m00 * t2.m10 + t1.m10 * t2.m11, // m01
                          t1.m01 * t2.m00 + t1.m11 * t2.m01, // m10
                          t1.m01 * t2.m10 + t1.m11 * t2.m11, // m11
                          t1.m02 * t2.m00 + t1.m12 * t2.m01 + t2.m02,  // m02
                          t1.m02 * t2.m10 + t1.m12 * t2.m11 + t2.m12); // m12
        return into;
    }

    // the values of transformation matrix
    private float m00;
    private float m10;
    private float m01;
    private float m11;
    private float m02;
    private float m12;

    /** The transformation {@code type}. */
    private transient int type;

    /** An initial type value. */
    private static final int TYPE_UNKNOWN = -1;

    /** The min value equivalent to zero. An absolute value < ZERO is considered to be zero. */
    private static final float ZERO = 1E-10f;
}
