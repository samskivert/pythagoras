//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

/**
 * Provides most of the implementation of {@link IPoint}, obtaining only the location from the
 * derived class.
 */
public abstract class AbstractPoint implements IPoint
{
    @Override // from interface IPoint
    public int distanceSq (int px, int py) {
        return Points.distanceSq(getX(), getY(), px, py);
    }

    @Override // from interface IPoint
    public int distanceSq (IPoint p) {
        return Points.distanceSq(getX(), getY(), p.getX(), p.getY());
    }

    @Override // from interface IPoint
    public int distance (int px, int py) {
        return Points.distance(getX(), getY(), px, py);
    }

    @Override // from interface IPoint
    public int distance (IPoint p) {
        return Points.distance(getX(), getY(), p.getX(), p.getY());
    }

    @Override // from interface IPoint
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
        return getX() ^ getY();
    }

    @Override
    public String toString () {
        return Points.pointToString(getX(), getY());
    }
}
