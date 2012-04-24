//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.nio.DoubleBuffer;

import pythagoras.util.Platform;
import pythagoras.util.SingularMatrixException;

/**
 * Provides most of the implementation of {@link IMatrix3}, obtaining only the components from the
 * derived class.
 */
public abstract class AbstractMatrix3 implements IMatrix3
{
    @Override // from IVector3
    public Matrix3 transpose () {
        return transpose(new Matrix3());
    }

    @Override // from IVector3
    public Matrix3 transpose (Matrix3 result) {
        return result.set(
            m00(), m01(), m02(),
            m10(), m11(), m12(),
            m20(), m21(), m22());
    }

    @Override // from IVector3
    public Matrix3 mult (IMatrix3 other) {
        return mult(other, new Matrix3());
    }

    @Override // from IVector3
    public Matrix3 mult (IMatrix3 other, Matrix3 result) {
        double m00 = m00(), m01 = m01(), m02 = m02();
        double m10 = m10(), m11 = m11(), m12 = m12();
        double m20 = m20(), m21 = m21(), m22 = m22();
        double om00 = other.m00(), om01 = other.m01(), om02 = other.m02();
        double om10 = other.m10(), om11 = other.m11(), om12 = other.m12();
        double om20 = other.m20(), om21 = other.m21(), om22 = other.m22();
        return result.set(
            m00*om00 + m10*om01 + m20*om02,
            m00*om10 + m10*om11 + m20*om12,
            m00*om20 + m10*om21 + m20*om22,

            m01*om00 + m11*om01 + m21*om02,
            m01*om10 + m11*om11 + m21*om12,
            m01*om20 + m11*om21 + m21*om22,

            m02*om00 + m12*om01 + m22*om02,
            m02*om10 + m12*om11 + m22*om12,
            m02*om20 + m12*om21 + m22*om22);
    }

    @Override // from IVector3
    public boolean isAffine () {
        return (m02() == 0f && m12() == 0f && m22() == 1f);
    }

    @Override // from IVector3
    public Matrix3 multAffine (IMatrix3 other) {
        return multAffine(other, new Matrix3());
    }

    @Override // from IVector3
    public Matrix3 multAffine (IMatrix3 other, Matrix3 result) {
        double m00 = m00(), m01 = m01(), m02 = m02();
        double m10 = m10(), m11 = m11(), m12 = m12();
        double m20 = m20(), m21 = m21(), m22 = m22();
        double om00 = other.m00(), om01 = other.m01(), om02 = other.m02();
        double om10 = other.m10(), om11 = other.m11(), om12 = other.m12();
        double om20 = other.m20(), om21 = other.m21(), om22 = other.m22();
        return result.set(
            m00*om00 + m10*om01,
            m00*om10 + m10*om11,
            m00*om20 + m10*om21 + m20,

            m01*om00 + m11*om01,
            m01*om10 + m11*om11,
            m01*om20 + m11*om21 + m21,

            0f, 0f, 1f);
    }

    @Override // from IVector3
    public Matrix3 invert () {
        return invert(new Matrix3());
    }

    /**
     * Inverts this matrix and places the result in the given object.  This code is based on the
     * examples in the <a href="http://www.j3d.org/matrix_faq/matrfaq_latest.html">Matrix and
     * Quaternion FAQ</a>.
     *
     * @return a reference to the result matrix, for chaining.
     */
    public Matrix3 invert (Matrix3 result) throws SingularMatrixException {
        double m00 = m00(), m01 = m01(), m02 = m02();
        double m10 = m10(), m11 = m11(), m12 = m12();
        double m20 = m20(), m21 = m21(), m22 = m22();
        // compute the determinant, storing the subdeterminants for later use
        double sd00 = m11*m22 - m21*m12;
        double sd10 = m01*m22 - m21*m02;
        double sd20 = m01*m12 - m11*m02;
        double det = m00*sd00 + m20*sd20 - m10*sd10;
        if (Math.abs(det) == 0f) {
            // determinant is zero; matrix is not invertible
            throw new SingularMatrixException(this.toString());
        }
        double rdet = 1f / det;
        return result.set(
            +sd00 * rdet,
            -(m10*m22 - m20*m12) * rdet,
            +(m10*m21 - m20*m11) * rdet,

            -sd10 * rdet,
            +(m00*m22 - m20*m02) * rdet,
            -(m00*m21 - m20*m01) * rdet,

            +sd20 * rdet,
            -(m00*m12 - m10*m02) * rdet,
            +(m00*m11 - m10*m01) * rdet);
    }

    @Override // from IVector3
    public Matrix3 invertAffine () {
        return invertAffine(new Matrix3());
    }

    @Override // from IVector3
    public Matrix3 invertAffine (Matrix3 result) throws SingularMatrixException {
        double m00 = m00(), m01 = m01(), m02 = m02();
        double m10 = m10(), m11 = m11(), m12 = m12();
        double m20 = m20(), m21 = m21(), m22 = m22();
        // compute the determinant, storing the subdeterminants for later use
        double det = m00*m11 - m10*m01;
        if (Math.abs(det) == 0f) {
            // determinant is zero; matrix is not invertible
            throw new SingularMatrixException(this.toString());
        }
        double rdet = 1f / det;
        return result.set(
            +m11 * rdet,
            -m10 * rdet,
            +(m10*m21 - m20*m11) * rdet,

            -m01 * rdet,
            +m00 * rdet,
            -(m00*m21 - m20*m01) * rdet,

            0f, 0f, 1f);
    }

    @Override // from IVector3
    public Matrix3 lerp (IMatrix3 other, double t) {
        return lerp(other, t, new Matrix3());
    }

    @Override // from IVector3
    public Matrix3 lerp (IMatrix3 other, double t, Matrix3 result) {
        double m00 = m00(), m01 = m01(), m02 = m02();
        double m10 = m10(), m11 = m11(), m12 = m12();
        double m20 = m20(), m21 = m21(), m22 = m22();
        double om00 = other.m00(), om01 = other.m01(), om02 = other.m02();
        double om10 = other.m10(), om11 = other.m11(), om12 = other.m12();
        double om20 = other.m20(), om21 = other.m21(), om22 = other.m22();
        return result.set(
            m00 + t*(om00 - m00),
            m10 + t*(om10 - m10),
            m20 + t*(om20 - m20),

            m01 + t*(om01 - m01),
            m11 + t*(om11 - m11),
            m21 + t*(om21 - m21),

            m02 + t*(om02 - m02),
            m12 + t*(om12 - m12),
            m22 + t*(om22 - m22));
    }

    @Override // from IVector3
    public Matrix3 lerpAffine (IMatrix3 other, double t) {
        return lerpAffine(other, t, new Matrix3());
    }

    @Override // from IVector3
    public Matrix3 lerpAffine (IMatrix3 other, double t, Matrix3 result) {
        double m00 = m00(), m01 = m01(), m02 = m02();
        double m10 = m10(), m11 = m11(), m12 = m12();
        double m20 = m20(), m21 = m21(), m22 = m22();
        double om00 = other.m00(), om01 = other.m01(), om02 = other.m02();
        double om10 = other.m10(), om11 = other.m11(), om12 = other.m12();
        double om20 = other.m20(), om21 = other.m21(), om22 = other.m22();
        return result.set(
            m00 + t*(om00 - m00),
            m10 + t*(om10 - m10),
            m20 + t*(om20 - m20),

            m01 + t*(om01 - m01),
            m11 + t*(om11 - m11),
            m21 + t*(om21 - m21),

            0f, 0f, 1f);
    }

    @Override // from IVector3
    public DoubleBuffer get (DoubleBuffer buf) {
        buf.put(m00()).put(m01()).put(m02());
        buf.put(m10()).put(m11()).put(m12());
        buf.put(m20()).put(m21()).put(m22());
        return buf;
    }

    @Override // from IVector3
    public Vector3 transformLocal (Vector3 vector) {
        return transform(vector, vector);
    }

    @Override // from IVector3
    public Vector3 transform (IVector3 vector) {
        return transform(vector, new Vector3());
    }

    @Override // from IVector3
    public Vector3 transform (IVector3 vector, Vector3 result) {
        double vx = vector.x(), vy = vector.y(), vz = vector.z();
        return result.set(
            m00()*vx + m10()*vy + m20()*vz,
            m01()*vx + m11()*vy + m21()*vz,
            m02()*vx + m12()*vy + m22()*vz);
    }

    @Override // from IVector3
    public Vector transformPointLocal (Vector point) {
        return transformPoint(point, point);
    }

    @Override // from IVector3
    public Vector transformPoint (IVector point) {
        return transformPoint(point, new Vector());
    }

    @Override // from IVector3
    public Vector transformPoint (IVector point, Vector result) {
        double px = point.x(), py = point.y();
        return result.set(m00()*px + m10()*py + m20(), m01()*px + m11()*py + m21());
    }

    @Override // from IVector3
    public Vector transformVectorLocal (Vector vector) {
        return transformVector(vector, vector);
    }

    @Override // from IVector3
    public Vector transformVector (IVector vector) {
        return transformVector(vector, new Vector());
    }

    @Override // from IVector3
    public Vector transformVector (IVector vector, Vector result) {
        double vx = vector.x(), vy = vector.y();
        return result.set(m00()*vx + m10()*vy, m01()*vx + m11()*vy);
    }

    @Override // from IVector3
    public double extractRotation () {
        // start with the contents of the upper 2x2 portion of the matrix
        double n00 = m00(), n10 = m10();
        double n01 = m01(), n11 = m11();
        for (int ii = 0; ii < 10; ii++) {
            // store the results of the previous iteration
            double o00 = n00, o10 = n10;
            double o01 = n01, o11 = n11;

            // compute average of the matrix with its inverse transpose
            double det = o00*o11 - o10*o01;
            if (Math.abs(det) == 0f) {
                // determinant is zero; matrix is not invertible
                throw new SingularMatrixException(this.toString());
            }
            double hrdet = 0.5f / det;
            n00 = +o11 * hrdet + o00*0.5f;
            n10 = -o01 * hrdet + o10*0.5f;

            n01 = -o10 * hrdet + o01*0.5f;
            n11 = +o00 * hrdet + o11*0.5f;

            // compute the difference; if it's small enough, we're done
            double d00 = n00 - o00, d10 = n10 - o10;
            double d01 = n01 - o01, d11 = n11 - o11;
            if (d00*d00 + d10*d10 + d01*d01 + d11*d11 < MathUtil.EPSILON) {
                break;
            }
        }
        // now that we have a nice orthogonal matrix, we can extract the rotation
        return Math.atan2(n01, n00);
    }

    @Override // from IVector3
    public Vector extractScale () {
        return extractScale(new Vector());
    }

    @Override // from IVector3
    public Vector extractScale (Vector result) {
        double m00 = m00(), m01 = m01(), m10 = m10(), m11 = m11();
        return result.set(
            Math.sqrt(m00*m00 + m01*m01),
            Math.sqrt(m10*m10 + m11*m11));
    }

    @Override // from IVector3
    public double approximateUniformScale () {
        double cp = m00()*m11() - m01()*m10();
        return (cp < 0f) ? -Math.sqrt(-cp) : Math.sqrt(cp);
    }

    @Override
    public String toString () {
        return "[[" + m00() + ", " + m10() + ", " + m20() + "], " +
            "[" + m01() + ", " + m11() + ", " + m21() + "], " +
            "[" + m02() + ", " + m12() + ", " + m22() + "]]";
    }

    @Override
    public int hashCode () {
        return Platform.hashCode(m00()) ^ Platform.hashCode(m10()) ^ Platform.hashCode(m20()) ^
            Platform.hashCode(m01()) ^ Platform.hashCode(m11()) ^ Platform.hashCode(m21()) ^
            Platform.hashCode(m02()) ^ Platform.hashCode(m12()) ^ Platform.hashCode(m22());
    }

    @Override
    public boolean equals (Object other) {
        if (!(other instanceof AbstractMatrix3)) {
            return false;
        }
        AbstractMatrix3 omat = (AbstractMatrix3)other;
        return
            m00() == omat.m00() && m10() == omat.m10() && m20() == omat.m20() &&
            m01() == omat.m01() && m11() == omat.m11() && m21() == omat.m21() &&
            m02() == omat.m02() && m12() == omat.m12() && m22() == omat.m22();
    }
}
