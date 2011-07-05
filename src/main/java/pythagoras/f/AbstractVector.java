//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import pythagoras.util.Platform;

/**
 * Provides most of the implementation of {@link IVector}, obtaining only x and y from the derived
 * class.
 */
public abstract class AbstractVector implements IVector
{
    @Override // from interface IVector
    public float dot (IVector other) {
        return getX()*other.getX() + getY()*other.getY();
    }

    @Override // from interface IVector
    public Vector negate () {
        return negate(new Vector());
    }

    @Override // from interface IVector
    public Vector negate (Vector result) {
        return result.set(-getX(), -getY());
    }

    @Override // from interface IVector
    public Vector normalize () {
        return normalize(new Vector());
    }

    @Override // from interface IVector
    public Vector normalize (Vector result) {
        return mult(1f / length(), result);
    }

    @Override // from interface IVector
    public float angle (IVector other) {
        float cos = dot(other) / (length() * other.length());
        return cos >= 1f ? 0f : FloatMath.acos(cos);
    }

    @Override // from interface IVector
    public float direction (IVector other) {
        return FloatMath.atan2(other.getY() - getY(), other.getX() - getX());
    }

    @Override // from interface IVector
    public float length () {
        return FloatMath.sqrt(lengthSq());
    }

    @Override // from interface IVector
    public float lengthSq () {
        float x = getX(), y = getY();
        return (x*x + y*y);
    }

    @Override // from interface IVector
    public float distance (IVector other) {
        return FloatMath.sqrt(distanceSq(other));
    }

    @Override // from interface IVector
    public float distanceSq (IVector other) {
        float dx = getX() - other.getX(), dy = getY() - other.getY();
        return dx*dx + dy*dy;
    }

    @Override // from interface IVector
    public Vector mult (float v) {
        return mult(v, new Vector());
    }

    @Override // from interface IVector
    public Vector mult (float v, Vector result) {
        return result.set(getX()*v, getY()*v);
    }

    @Override // from interface IVector
    public Vector mult (IVector other) {
        return mult(other, new Vector());
    }

    @Override // from interface IVector
    public Vector mult (IVector other, Vector result) {
        return result.set(getX()*other.getX(), getY()*other.getY());
    }

    @Override // from interface IVector
    public Vector add (IVector other) {
        return add(other, new Vector());
    }

    @Override // from interface IVector
    public Vector add (IVector other, Vector result) {
        return add(other.getX(), other.getY(), result);
    }

    @Override // from interface IVector
    public Vector subtract (IVector other) {
        return subtract(other, new Vector());
    }

    @Override // from interface IVector
    public Vector subtract (IVector other, Vector result) {
        return add(-other.getX(), -other.getY(), result);
    }

    @Override // from interface IVector
    public Vector add (float x, float y) {
        return add(x, y, new Vector());
    }

    @Override // from interface IVector
    public Vector add (float x, float y, Vector result) {
        return result.set(getX() + x, getY() + y);
    }

    @Override // from interface IVector
    public Vector addScaled (IVector other, float v) {
        return addScaled(other, v, new Vector());
    }

    @Override // from interface IVector
    public Vector addScaled (IVector other, float v, Vector result) {
        return result.set(getX() + other.getX()*v, getY() + other.getY()*v);
    }

    @Override // from interface IVector
    public Vector rotate (float angle) {
        return rotate(angle, new Vector());
    }

    @Override // from interface IVector
    public Vector rotate (float angle, Vector result) {
        float x = getX(), y = getY();
        float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
        return result.set(x*cosa - y*sina, x*sina + y*cosa);
    }

    @Override // from interface IVector
    public Vector rotateAndAdd (float angle, IVector add, Vector result) {
        float x = getX(), y = getY();
        float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
        return result.set(x*cosa - y*sina + add.getX(), x*sina + y*cosa + add.getY());
    }

    @Override // from interface IVector
    public Vector rotateScaleAndAdd (float angle, float scale, IVector add, Vector result) {
        float x = getX(), y = getY();
        float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
        return result.set((x*cosa - y*sina)*scale + add.getX(),
                          (x*sina + y*cosa)*scale + add.getY());
    }

    @Override // from interface IVector
    public Vector lerp (IVector other, float t) {
        return lerp(other, t, new Vector());
    }

    @Override // from interface IVector
    public Vector lerp (IVector other, float t, Vector result) {
        float x = getX(), y = getY();
        float dx = other.getX() - x, dy = other.getY() - y;
        return result.set(x + t*dx, y + t*dy);
    }

    @Override // from interface IVector
    public Vector clone () {
        return new Vector(this);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractVector) {
            AbstractVector p = (AbstractVector)obj;
            return getX() == p.getX() && getY() == p.getY();
        }
        return false;
    }

    @Override
    public int hashCode () {
        return Platform.hashCode(getX()) ^ Platform.hashCode(getY());
    }

    @Override
    public String toString () {
        return Vectors.vectorToString(getX(), getY());
    }
}
