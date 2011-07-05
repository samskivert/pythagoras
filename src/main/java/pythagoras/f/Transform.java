//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Represents a geometric transform. Specialized implementations exist for identity, rigid body,
 * uniform, affine and general transforms.
 */
public interface Transform
{
    /** Sets the translation component of this transform.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    void setTranslation (float tx, float ty);

    /** Sets the x-component of this transform's translation.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    void setTx (float tx);

    /** Sets the y-component of this transform's translation.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    void setTy (float ty);

    /** Sets the rotation component of this transform.
     * @throws UnsupportedOperationException if the transform is not rigid body or greater. */
    void setRotation (float angle);

    /** Sets the uniform scale of this transform.
     * @throws UnsupportedOperationException if the transform is not uniform or greater. */
    void setScale (float scale);

    /** Sets the x and y scale of this transform.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    void setScale (float scaleX, float scaleY);

    /** Sets the x scale of this transform.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    void setScaleX (float scaleX);

    /** Sets the y scale of this transform.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    void setScaleY (float scaleY);

    /** Sets the affine transform matrix.
     * @throws UnsupportedOperationException if the transform is not affine or greater. */
    void setTransform (float m00, float m01, float m10, float m11,
                       float tx, float ty);

    /** Sets the general transform matrix.
     * @throws UnsupportedOperationException if the transform is not general. */
    void setTransform (float m00, float m01, float m02,
                       float m10, float m11, float m12,
                       float m20, float m21, float m22);

    /** Returns the x-coordinate of the translation component. */
    float getTx ();

    /** Returns the y-coordinate of the translation component. */
    float getTy ();

    /** Returns the rotation applied by this transform. Note that the rotation is extracted and
     * therefore approximate for affine and general transforms. */
    float getRotation (); // will be extracted from affine+

    /** Returns the uniform scale applied by this transform. Note that the uniform scale will be
     * approximated for non-uniform transforms (affine and general). */
    float getScale (); // will be extracted/approximated for affine+

    /** Returns the x-component of the scale applied by this transform. */
    float getScaleX (); // will be extracted from affine+

    /** Returns the y-component of the scale applied by this transform. */
    float getScaleY (); // will be extracted from affine+

    /** Returns the inverse of this transform.
     * @throws NoninvertibleTransformException if the transform is not invertible. */
    Transform invert ();

    /** Composes this transform with the supplied transform (i.e. {@code this x other}). */
    Transform compose (Transform other);

    /** Returns the linear interpolation between this transform and the specified other. */
    Transform lerp (Transform other, float t);

    /** Transforms the supplied point, writing the result into {@code into}, which may reference
     * the same object as {@code p}. */
    void transform (IPoint p, Point into);

    /** Inverse transforms the supplied point, writing the result into {@code into}, which may
     * reference the same object as {@code p}.
     * @throws NoninvertibleTransformException if the transform is not invertible. */
    void inverseTransform (IPoint p, Point into);

    /** Transforms the supplied vector, writing the result into {@code into}, which may reference
     * the same object as {@code v}. */
    void transform (IVector v, Vector into);

    /** Inverse transforms the supplied vector, writing the result into {@code into}, which may
     * reference the same object as {@code v}.
     * @throws NoninvertibleTransformException if the transform is not invertible. */
    void inverseTransform (IVector v, Vector into);
}
