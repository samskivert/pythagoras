//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

/**
 * Implements some code shared by the various {@link Transform} implementations.
 */
public abstract class AbstractTransform implements Transform
{
    @Override // from Transform
    public Vector getScale () {
        return new Vector(getScaleX(), getScaleY());
    }

    @Override // from Transform
    public Vector getTranslation () {
        return new Vector(getTx(), getTy());
    }

    @Override // from Transform
    public Transform setUniformScale (float scale) {
        throw new UnsupportedOperationException();
    }

    @Override // from Transform
    public Transform setScale (float scaleX, float scaleY) {
        setScaleX(scaleX);
        setScaleY(scaleY);
        return this;
    }

    @Override // from Transform
    public Transform setScaleX (float scaleX) {
        throw new UnsupportedOperationException();
    }

    @Override // from Transform
    public Transform setScaleY (float scaleY) {
        throw new UnsupportedOperationException();
    }

    @Override // from Transform
    public Transform setRotation (float angle) {
        throw new UnsupportedOperationException();
    }

    @Override // from Transform
    public Transform setTranslation (float tx, float ty) {
        setTx(tx);
        setTy(ty);
        return this;
    }

    @Override // from Transform
    public Transform setTx (float tx) {
        throw new UnsupportedOperationException();
    }

    @Override // from Transform
    public Transform setTy (float ty) {
        throw new UnsupportedOperationException();
    }

    @Override // from Transform
    public Transform setTransform (float m00, float m01, float m10, float m11, float tx, float ty) {
        throw new UnsupportedOperationException();
    }

    @Override // from Transform
    public abstract Transform clone ();
}
