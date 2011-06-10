//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * Provides most of the implementation of {@link IPoint}, obtaining only the location from the
 * derived class.
 */
public abstract class AbstractPoint implements IPoint
{
    @Override // from interface IPoint
    public double distanceSq (double px, double py) {
        return Points.distanceSq(getX(), getY(), px, py);
    }

    @Override // from interface IPoint
    public double distanceSq (IPoint p) {
        return Points.distanceSq(getX(), getY(), p.getX(), p.getY());
    }

    @Override // from interface IPoint
    public double distance (double px, double py) {
        return Points.distance(getX(), getY(), px, py);
    }

    @Override // from interface IPoint
    public double distance (IPoint p) {
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
        long bits = Double.doubleToLongBits(getX());
        bits += Double.doubleToLongBits(getY()) * 37;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public String toString () {
        return Points.pointToString(getX(), getY());
    }
}
