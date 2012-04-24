//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.nio.DoubleBuffer;

/**
 * A 3x3 column-major matrix.
 */
public class Matrix3 extends AbstractMatrix3
{
    /** The identity matrix. */
    public static final Matrix3 IDENTITY = new Matrix3();

    /** The values of the matrix. */
    public double m00, m10, m20;
    public double m01, m11, m21;
    public double m02, m12, m22;

    /**
     * Creates a matrix from its components.
     */
    public Matrix3 (double m00, double m10, double m20,
                    double m01, double m11, double m21,
                    double m02, double m12, double m22) {
        set(m00, m10, m20,
            m01, m11, m21,
            m02, m12, m22);
    }

    /**
     * Creates a matrix from an array of values.
     */
    public Matrix3 (double[] values) {
        set(values);
    }

    /**
     * Copy constructor.
     */
    public Matrix3 (Matrix3 other) {
        set(other);
    }

    /**
     * Creates an identity matrix.
     */
    public Matrix3 () {
        setToIdentity();
    }

    /**
     * Sets this matrix to the identity matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToIdentity () {
        return set(
            1f, 0f, 0f,
            0f, 1f, 0f,
            0f, 0f, 1f);
    }

    /**
     * Sets this to a rotation matrix that rotates one vector onto another.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToRotation (IVector3 from, IVector3 to) {
        double angle = from.angle(to);
        return (angle < 0.0001f) ?
            setToIdentity() : setToRotation(angle, from.cross(to).normalizeLocal());
    }

    /**
     * Sets this to a rotation matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToRotation (double angle, IVector3 axis) {
        return setToRotation(angle, axis.x(), axis.y(), axis.z());
    }

    /**
     * Sets this to a rotation matrix.  The formula comes from the OpenGL documentation for the
     * glRotatef function.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToRotation (double angle, double x, double y, double z) {
        double c = Math.cos(angle), s = Math.sin(angle), omc = 1f - c;
        double xs = x*s, ys = y*s, zs = z*s, xy = x*y, xz = x*z, yz = y*z;
        return set(
            x*x*omc + c, xy*omc - zs, xz*omc + ys,
            xy*omc + zs, y*y*omc + c, yz*omc - xs,
            xz*omc - ys, yz*omc + xs, z*z*omc + c);
    }

    // /**
    //  * Sets this to a rotation matrix.  The formula comes from the
    //  * <a href="http://www.j3d.org/matrix_faq/matrfaq_latest.html">Matrix and Quaternion FAQ</a>.
    //  *
    //  * @return a reference to this matrix, for chaining.
    //  */
    // public Matrix3 setToRotation (Quaternion quat) {
    //     double xx = quat.x*quat.x, yy = quat.y*quat.y, zz = quat.z*quat.z;
    //     double xy = quat.x*quat.y, xz = quat.x*quat.z, xw = quat.x*quat.w;
    //     double yz = quat.y*quat.z, yw = quat.y*quat.w, zw = quat.z*quat.w;
    //     return set(
    //         1f - 2f*(yy + zz), 2f*(xy - zw), 2f*(xz + yw),
    //         2f*(xy + zw), 1f - 2f*(xx + zz), 2f*(yz - xw),
    //         2f*(xz - yw), 2f*(yz + xw), 1f - 2f*(xx + yy));
    // }

    /**
     * Sets this to a scale matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToScale (IVector3 scale) {
        return setToScale(scale.x(), scale.y(), scale.z());
    }

    /**
     * Sets this to a uniform scale matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToScale (double s) {
        return setToScale(s, s, s);
    }

    /**
     * Sets this to a scale matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToScale (double x, double y, double z) {
        return set(
            x,  0f, 0f,
            0f, y,  0f,
            0f, 0f, z);
    }

    /**
     * Sets this to a reflection across a plane intersecting the origin with the supplied normal.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToReflection (IVector3 normal) {
        return setToReflection(normal.x(), normal.y(), normal.z());
    }

    /**
     * Sets this to a reflection across a plane intersecting the origin with the supplied normal.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToReflection (double x, double y, double z) {
        double x2 = -2f*x, y2 = -2f*y, z2 = -2f*z;
        double xy2 = x2*y, xz2 = x2*z, yz2 = y2*z;
        return set(
            1f + x2*x, xy2, xz2,
            xy2, 1f + y2*y, yz2,
            xz2, yz2, 1f + z2*z);
    }

    /**
     * Sets this to a matrix that first rotates, then translates.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToTransform (IVector translation, double rotation) {
        return setToRotation(rotation).setTranslation(translation);
    }

    /**
     * Sets this to a matrix that first scales, then rotates, then translates.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToTransform (IVector translation, double rotation, double scale) {
        return setToRotation(rotation).set(
            m00 * scale, m10 * scale, translation.x(),
            m01 * scale, m11 * scale, translation.y(),
            0f, 0f, 1f);
    }

    /**
     * Sets this to a matrix that first scales, then rotates, then translates.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToTransform (IVector translation, double rotation, IVector scale) {
        double sx = scale.x(), sy = scale.y();
        return setToRotation(rotation).set(
            m00 * sx, m10 * sy, translation.x(),
            m01 * sx, m11 * sy, translation.y(),
            0f, 0f, 1f);
    }

    /**
     * Sets this to a translation matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToTranslation (IVector translation) {
        return setToTranslation(translation.x(), translation.y());
    }

    /**
     * Sets this to a translation matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToTranslation (double x, double y) {
        return set(
            1f, 0f, x,
            0f, 1f, y,
            0f, 0f, 1f);
    }

    /**
     * Sets the translation component of this matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setTranslation (IVector translation) {
        return setTranslation(translation.x(), translation.y());
    }

    /**
     * Sets the translation component of this matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setTranslation (double x, double y) {
        m20 = x;
        m21 = y;
        return this;
    }

    /**
     * Sets this to a rotation matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 setToRotation (double angle) {
        double sina = Math.sin(angle), cosa = Math.cos(angle);
        return set(
            cosa, -sina, 0f,
            sina, cosa, 0f,
            0f, 0f, 1f);
    }

    /**
     * Transposes this matrix in-place.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 transposeLocal () {
        return transpose(this);
    }

    /**
     * Multiplies this matrix in-place by another.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 multLocal (IMatrix3 other) {
        return mult(other, this);
    }

    /**
     * Multiplies this matrix in-place by another, treating the matricees as affine.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 multAffineLocal (IMatrix3 other) {
        return multAffine(other, this);
    }

    /**
     * Inverts this matrix in-place.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 invertLocal () {
        return invert(this);
    }

    /**
     * Inverts this matrix in-place as an affine matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 invertAffineLocal () {
        return invertAffine(this);
    }

    /**
     * Linearly interpolates between the this and the specified other matrix, placing the result in
     * this matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 lerpLocal (IMatrix3 other, double t) {
        return lerp(other, t, this);
    }

    /**
     * Linearly interpolates between this and the specified other matrix (treating the matrices as
     * affine), placing the result in this matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 lerpAffineLocal (IMatrix3 other, double t) {
        return lerpAffine(other, t, this);
    }

    /**
     * Copies the contents of another matrix.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 set (IMatrix3 other) {
        return set(
            other.m00(), other.m10(), other.m20(),
            other.m01(), other.m11(), other.m21(),
            other.m02(), other.m12(), other.m22());
    }

    /**
     * Copies the elements of an array.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 set (double[] values) {
        return set(
            values[0], values[1], values[2],
            values[3], values[4], values[5],
            values[6], values[7], values[8]);
    }

    /**
     * Sets all of the matrix's components at once.
     *
     * @return a reference to this matrix, for chaining.
     */
    public Matrix3 set (
        double m00, double m10, double m20,
        double m01, double m11, double m21,
        double m02, double m12, double m22) {
        this.m00 = m00; this.m01 = m01; this.m02 = m02;
        this.m10 = m10; this.m11 = m11; this.m12 = m12;
        this.m20 = m20; this.m21 = m21; this.m22 = m22;
        return this;
    }

    @Override // from AbstractMatrix3
    public double m00 () {
        return m00;
    }

    @Override // from AbstractMatrix3
    public double m10 () {
        return m10;
    }

    @Override // from AbstractMatrix3
    public double m20 () {
        return m20;
    }

    @Override // from AbstractMatrix3
    public double m01 () {
        return m01;
    }

    @Override // from AbstractMatrix3
    public double m11 () {
        return m11;
    }

    @Override // from AbstractMatrix3
    public double m21 () {
        return m21;
    }

    @Override // from AbstractMatrix3
    public double m02 () {
        return m02;
    }

    @Override // from AbstractMatrix3
    public double m12 () {
        return m12;
    }

    @Override // from AbstractMatrix3
    public double m22 () {
        return m22;
    }
}
