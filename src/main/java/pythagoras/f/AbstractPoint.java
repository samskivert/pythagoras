//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import pythagoras.util.Platform;

/**
 * Provides most of the implementation of {@link IPoint}, obtaining only the location from the
 * derived class.
 */
public abstract class AbstractPoint implements IPoint
{
    @Override // from IPoint
    public float distanceSq (float px, float py) {
        return Points.distanceSq(getX(), getY(), px, py);
    }

    @Override // from IPoint
    public float distanceSq (IPoint p) {
        return Points.distanceSq(getX(), getY(), p.getX(), p.getY());
    }

    @Override // from IPoint
    public float distance (float px, float py) {
        return Points.distance(getX(), getY(), px, py);
    }

    @Override // from IPoint
    public float distance (IPoint p) {
        return Points.distance(getX(), getY(), p.getX(), p.getY());
    }

    @Override // from IPoint
    public Point mult (float s) {
        return mult(s, new Point());
    }

    @Override // from IPoint
    public Point mult (float s, Point result) {
        return result.set(getX() * s, getY() * s);
    }

    @Override // from IPoint
    public Point add (float x, float y) {
        return new Point(getX() + x, getY() + y);
    }

    @Override // from IPoint
    public Point add (float x, float y, Point result) {
        return result.set(getX() + x, getY() + y);
    }

    @Override // from IPoint
    public Point rotate (float angle) {
        return rotate(angle, new Point());
    }

    @Override // from IPoint
    public Point rotate (float angle, Point result) {
        float x = getX(), y = getY();
        float sina = FloatMath.sin(angle), cosa = FloatMath.cos(angle);
        return result.set(x*cosa - y*sina, x*sina + y*cosa);
    }

    @Override // from IPoint
    public Point clone () {
        return new Point(this);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractPoint) {
            AbstractPoint p = (AbstractPoint)obj;
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
        return Points.pointToString(getX(), getY());
    }
}
