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

    public AffineTransform () {
        setToIdentity();
    }

    public AffineTransform (AffineTransform t) {
        setTransform(t);
    }

    public AffineTransform (float m00, float m10, float m01, float m11, float m02, float m12) {
        setTransform(m00, m10, m01, m11, m02, m12);
    }

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

    /*
     * Method returns type of affine transformation.
     *
     * Transform matrix is m00 m01 m02 m10 m11 m12
     *
     * According analytic geometry new basis vectors are (m00, m01) and (m10,
     * m11), translation vector is (m02, m12). Original basis vectors are (1, 0)
     * and (0, 1). Type transformations classification: TYPE_IDENTITY - new
     * basis equals original one and zero translation TYPE_TRANSLATION -
     * translation vector isn't zero TYPE_UNIFORM_SCALE - vectors length of new
     * basis equals TYPE_GENERAL_SCALE - vectors length of new basis doesn't
     * equal TYPE_FLIP - new basis vector orientation differ from original one
     * TYPE_QUADRANT_ROTATION - new basis is rotated by 90, 180, 270, or 360
     * degrees TYPE_GENERAL_ROTATION - new basis is rotated by arbitrary angle
     * TYPE_GENERAL_TRANSFORM - transformation can't be inversed
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

    public float getScaleX () {
        return m00;
    }

    public float getScaleY () {
        return m11;
    }

    public float getShearX () {
        return m01;
    }

    public float getShearY () {
        return m10;
    }

    public float getTranslateX () {
        return m02;
    }

    public float getTranslateY () {
        return m12;
    }

    public boolean isIdentity () {
        return getType() == TYPE_IDENTITY;
    }

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

    public float getDeterminant () {
        return m00 * m11 - m01 * m10;
    }

    public void setTransform (float m00, float m10, float m01, float m11, float m02, float m12) {
        this.type = TYPE_UNKNOWN;
        this.m00 = m00;
        this.m10 = m10;
        this.m01 = m01;
        this.m11 = m11;
        this.m02 = m02;
        this.m12 = m12;
    }

    public void setTransform (AffineTransform t) {
        setTransform(t.m00, t.m10, t.m01, t.m11, t.m02, t.m12);
        type = t.type;
    }

    public void setToIdentity () {
        type = TYPE_IDENTITY;
        m00 = m11 = 1f;
        m10 = m01 = m02 = m12 = 0;
    }

    public void setToTranslation (float mx, float my) {
        m00 = m11 = 1f;
        m01 = m10 = 0;
        m02 = mx;
        m12 = my;
        if (mx == 0 && my == 0) {
            type = TYPE_IDENTITY;
        } else {
            type = TYPE_TRANSLATION;
        }
    }

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

    public void setToRotation (float angle, float px, float py) {
        setToRotation(angle);
        m02 = px * (1f - m00) + py * m10;
        m12 = py * (1f - m00) - px * m10;
        type = TYPE_UNKNOWN;
    }

    public static AffineTransform getTranslateInstance (float mx, float my) {
        AffineTransform t = new AffineTransform();
        t.setToTranslation(mx, my);
        return t;
    }

    public static AffineTransform getScaleInstance (float scx, float scY) {
        AffineTransform t = new AffineTransform();
        t.setToScale(scx, scY);
        return t;
    }

    public static AffineTransform getShearInstance (float shx, float shy) {
        AffineTransform m = new AffineTransform();
        m.setToShear(shx, shy);
        return m;
    }

    public static AffineTransform getRotateInstance (float angle) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle);
        return t;
    }

    public static AffineTransform getRotateInstance (float angle, float x, float y) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle, x, y);
        return t;
    }

    public void translate (float mx, float my) {
        concatenate(getTranslateInstance(mx, my));
    }

    public void scale (float scx, float scy) {
        concatenate(getScaleInstance(scx, scy));
    }

    public void shear (float shx, float shy) {
        concatenate(getShearInstance(shx, shy));
    }

    public void rotate (float angle) {
        concatenate(getRotateInstance(angle));
    }

    public void rotate (float angle, float px, float py) {
        concatenate(getRotateInstance(angle, px, py));
    }

    public void concatenate (AffineTransform t) {
        multiply(t, this, this);
    }

    public void preConcatenate (AffineTransform t) {
        multiply(this, t, this);
    }

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

    public AffineTransform createInverse () throws NoninvertibleTransformException {
        return createInverse(new AffineTransform());
    }

    public Point transform (IPoint src, Point dst) {
        if (dst == null) {
            dst = new Point();
        }

        float x = src.getX(), y = src.getY();
        dst.setLocation(x * m00 + y * m01 + m02, x * m10 + y * m11 + m12);
        return dst;
    }

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

    public Point deltaTransform (IPoint src, Point dst) {
        if (dst == null) {
            dst = new Point();
        }
        float x = src.getX(), y = src.getY();
        dst.setLocation(x * m00 + y * m01, x * m10 + y * m11);
        return dst;
    }

    public void deltaTransform (float[] src, int srcOff, float[] dst, int dstOff, int length) {
        while (--length >= 0) {
            float x = src[srcOff++], y = src[srcOff++];
            dst[dstOff++] = x * m00 + y * m01;
            dst[dstOff++] = x * m10 + y * m11;
        }
    }

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
