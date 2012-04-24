//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import java.nio.FloatBuffer;

import pythagoras.util.SingularMatrixException;

/**
 * Provides read-only access to a {@link Matrix3}.
 */
interface IMatrix3
{
    /** Returns the (0,0)th component of the matrix. */
    float m00 ();

    /** Returns the (1,0)th component of the matrix. */
    float m10 ();

    /** Returns the (2,0)th component of the matrix. */
    float m20 ();

    /** Returns the (0,1)th component of the matrix. */
    float m01 ();

    /** Returns the (1,1)th component of the matrix. */
    float m11 ();

    /** Returns the (2,1)th component of the matrix. */
    float m21 ();

    /** Returns the (0,2)th component of the matrix. */
    float m02 ();

    /** Returns the (1,2)th component of the matrix. */
    float m12 ();

    /** Returns the (2,2)th component of the matrix. */
    float m22 ();

    /**
     * Transposes this matrix.
     *
     * @return a new matrix containing the result.
     */
    Matrix3 transpose ();

    /**
     * Transposes this matrix, storing the result in the provided object.
     *
     * @return the result matrix, for chaining.
     */
    Matrix3 transpose (Matrix3 result);

    /**
     * Multiplies this matrix by another.
     *
     * @return a new matrix containing the result.
     */
    Matrix3 mult (IMatrix3 other);

    /**
     * Multiplies this matrix by another and stores the result in the object provided.
     *
     * @return a reference to the result matrix, for chaining.
     */
    Matrix3 mult (IMatrix3 other, Matrix3 result);

    /**
     * Determines whether this matrix represents an affine transformation.
     */
    boolean isAffine ();

    /**
     * Multiplies this matrix by another, treating the matrices as affine.
     *
     * @return a new matrix containing the result.
     */
    Matrix3 multAffine (IMatrix3 other);

    /**
     * Multiplies this matrix by another, treating the matrices as affine, and stores the result
     * in the object provided.
     *
     * @return a reference to the result matrix, for chaining.
     */
    Matrix3 multAffine (IMatrix3 other, Matrix3 result);

    /**
     * Inverts this matrix.
     *
     * @return a new matrix containing the result.
     */
    Matrix3 invert ();

    /**
     * Inverts this matrix and places the result in the given object.  This code is based on the
     * examples in the <a href="http://www.j3d.org/matrix_faq/matrfaq_latest.html">Matrix and
     * Quaternion FAQ</a>.
     *
     * @return a reference to the result matrix, for chaining.
     */
    Matrix3 invert (Matrix3 result) throws SingularMatrixException;

    /**
     * Inverts this matrix as an affine matrix.
     *
     * @return a new matrix containing the result.
     */
    Matrix3 invertAffine ();

    /**
     * Inverts this matrix as an affine matrix and places the result in the given object.
     *
     * @return a reference to the result matrix, for chaining.
     */
    Matrix3 invertAffine (Matrix3 result) throws SingularMatrixException;

    /**
     * Linearly interpolates between this and the specified other matrix.
     *
     * @return a new matrix containing the result.
     */
    Matrix3 lerp (IMatrix3 other, float t);

    /**
     * Linearly interpolates between this and the specified other matrix, placing the result in
     * the object provided.
     *
     * @return a reference to the result object, for chaining.
     */
    Matrix3 lerp (IMatrix3 other, float t, Matrix3 result);

    /**
     * Linearly interpolates between this and the specified other matrix, treating the matrices as
     * affine.
     *
     * @return a new matrix containing the result.
     */
    Matrix3 lerpAffine (IMatrix3 other, float t);

    /**
     * Linearly interpolates between this and the specified other matrix (treating the matrices as
     * affine), placing the result in the object provided.
     *
     * @return a reference to the result object, for chaining.
     */
    Matrix3 lerpAffine (IMatrix3 other, float t, Matrix3 result);

    /**
     * Places the contents of this matrix into the given buffer in the standard OpenGL order.
     *
     * @return a reference to the buffer, for chaining.
     */
    FloatBuffer get (FloatBuffer buf);

    /**
     * Transforms a vector in-place by the inner 3x3 part of this matrix.
     *
     * @return a reference to the vector, for chaining.
     */
    Vector3 transformLocal (Vector3 vector);

    /**
     * Transforms a vector by this matrix.
     *
     * @return a new vector containing the result.
     */
    Vector3 transform (IVector3 vector);

    /**
     * Transforms a vector by this matrix and places the result in the object provided.
     *
     * @return a reference to the result, for chaining.
     */
    Vector3 transform (IVector3 vector, Vector3 result);

    /**
     * Transforms a point in-place by this matrix.
     *
     * @return a reference to the point, for chaining.
     */
    Vector transformPointLocal (Vector point);

    /**
     * Transforms a point by this matrix.
     *
     * @return a new vector containing the result.
     */
    Vector transformPoint (IVector point);

    /**
     * Transforms a point by this matrix and places the result in the object provided.
     *
     * @return a reference to the result, for chaining.
     */
    Vector transformPoint (IVector point, Vector result);

    /**
     * Transforms a vector in-place by the inner 2x2 part of this matrix.
     *
     * @return a reference to the vector, for chaining.
     */
    Vector transformVectorLocal (Vector vector);

    /**
     * Transforms a vector by this inner 2x2 part of this matrix.
     *
     * @return a new vector containing the result.
     */
    Vector transformVector (IVector vector);

    /**
     * Transforms a vector by the inner 2x2 part of this matrix and places the result in the object
     * provided.
     *
     * @return a reference to the result, for chaining.
     */
    Vector transformVector (IVector vector, Vector result);

    /**
     * Extracts the rotation component of the matrix.  This uses the iterative polar decomposition
     * algorithm described by
     * <a href="http://www.cs.wisc.edu/graphics/Courses/838-s2002/Papers/polar-decomp.pdf">Ken
     * Shoemake</a>.
     */
    float extractRotation ();

    /**
     * Extracts the scale component of the matrix.
     *
     * @return a new vector containing the result.
     */
    Vector extractScale ();

    /**
     * Extracts the scale component of the matrix and places it in the provided result vector.
     *
     * @return a reference to the result vector, for chaining.
     */
    Vector extractScale (Vector result);

    /**
     * Returns an approximation of the uniform scale for this matrix (the square root of the
     * signed area of the parallelogram spanned by the axis vectors).
     */
    float approximateUniformScale ();
}
