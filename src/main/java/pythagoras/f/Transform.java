//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Represents a geometric transform. Specialized implementations exist for identity, rigid body,
 * uniform, non-uniform, and affine transforms.
 */
public interface Transform
{
    /** Returns the uniform scale applied by this transform. The uniform scale will be approximated
     * for non-uniform transforms. */
    float getUniformScale ();

    /** Returns the scale vector for this transform. */
    Vector getScale ();

    /** Returns the x-component of the scale applied by this transform. Note that this will be
     * extracted and therefore approximate for affine transforms. */
    float getScaleX ();

    /** Returns the y-component of the scale applied by this transform. Note that this will be
     * extracted and therefore approximate for affine transforms. */
    float getScaleY ();

    /** Returns the rotation applied by this transform. Note that the rotation is extracted and
     * therefore approximate for affine transforms.
     * @throws NoninvertibleTransformException if the transform is not invertible. */
    float getRotation ();

    /** Returns the translation vector for this transform. */
    Vector getTranslation ();

    /** Returns the x-coordinate of the translation component. */
    float getTx ();

    /** Returns the y-coordinate of the translation component. */
    float getTy ();

    /** Sets the uniform scale of this transform.
     * @return this instance, for chaining.
     * @throws IllegalArgumentException if the supplied scale is zero.
     * @throws UnsupportedOperationException if the transform is not uniform or greater. */
    Transform setUniformScale (float scale);

    /** Sets the x and y scale of this transform.
     * @return this instance, for chaining.
     * @throws IllegalArgumentException if either supplied scale is zero.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    Transform setScale (float scaleX, float scaleY);

    /** Sets the x scale of this transform.
     * @return this instance, for chaining.
     * @throws IllegalArgumentException if the supplied scale is zero.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    Transform setScaleX (float scaleX);

    /** Sets the y scale of this transform.
     * @return this instance, for chaining.
     * @throws IllegalArgumentException if the supplied scale is zero.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    Transform setScaleY (float scaleY);

    /** Sets the rotation component of this transform.
     * @return this instance, for chaining.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    Transform setRotation (float angle);

    /** Sets the translation component of this transform.
     * @return this instance, for chaining.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    Transform setTranslation (float tx, float ty);

    /** Sets the x-component of this transform's translation.
     * @return this instance, for chaining.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    Transform setTx (float tx);

    /** Sets the y-component of this transform's translation.
     * @return this instance, for chaining.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    Transform setTy (float ty);

    /** Sets the affine transform matrix.
     * @return this instance, for chaining.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    Transform setTransform (float m00, float m01, float m10, float m11,
                            float tx, float ty);

    /** Returns a new transform that represents the inverse of this transform.
     * @throws NoninvertibleTransformException if the transform is not invertible. */
    Transform invert ();

    /** Returns a new transform comprised of the concatenation of {@code other} to this transform
     * (i.e. {@code this x other}). */
    Transform concatenate (Transform other);

    /** Returns a new transform comprised of the concatenation of this transform to {@code other}
     * (i.e. {@code other x this}). */
    Transform preConcatenate (Transform other);

    /** Returns a new transform comprised of the linear interpolation between this transform and
     * the specified other. */
    Transform lerp (Transform other, float t);

    /** Transforms the supplied point, writing the result into {@code into}.
     * @param into a point into which to store the result, may be the same object as {@code p}.
     * @return {@code into} for chaining. */
    Point transform (IPoint p, Point into);

    /** Transforms the supplied points.
     * @param src the points to be transformed.
     * @param srcOff the offset into the {@code src} array at which to start.
     * @param dst the points into which to store the transformed points. May be {@code src}.
     * @param dstOff the offset into the {@code dst} array at which to start.
     * @param count the number of points to transform. */
    void transform (IPoint[] src, int srcOff, Point[] dst, int dstOff, int count);

    /** Transforms the supplied points.
     * @param src the points to be transformed (as {@code [x, y, x, y, ...]}).
     * @param srcOff the offset into the {@code src} array at which to start.
     * @param dst the points into which to store the transformed points. May be {@code src}.
     * @param dstOff the offset into the {@code dst} array at which to start.
     * @param count the number of points to transform. */
    void transform (float[] src, int srcOff, float[] dst, int dstOff, int count);

    /** Inverse transforms the supplied point, writing the result into {@code into}.
     * @param into a point into which to store the result, may be the same object as {@code p}.
     * @return {@code into}, for chaining.
     * @throws NoninvertibleTransformException if the transform is not invertible. */
    Point inverseTransform (IPoint p, Point into);

    /** Transforms the supplied vector, writing the result into {@code into}.
     * @param into a vector into which to store the result, may be the same object as {@code v}.
     * @return {@code into}, for chaining. */
    Vector transform (IVector v, Vector into);

    /** Inverse transforms the supplied vector, writing the result into {@code into}.
     * @param into a vector into which to store the result, may be the same object as {@code v}.
     * @return {@code into}, for chaining.
     * @throws NoninvertibleTransformException if the transform is not invertible. */
    Vector inverseTransform (IVector v, Vector into);

    /** Returns a clone of this transform. */
    Transform clone ();

    /** Returns an integer that increases monotonically with the generality of the transform
     * implementation. Used internally when combining transforms. */
    int generality ();
}
