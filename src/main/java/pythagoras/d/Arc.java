//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.io.Serializable;

/**
 * Represents an arc defined by a framing rectangle, start angle, angular extend, and closure type.
 */
public class Arc extends AbstractArc implements Serializable
{
    /** The x-coordinate of this arc's framing rectangle. */
    public double x;

    /** The y-coordinate of this arc's framing rectangle. */
    public double y;

    /** The width of this arc's framing rectangle. */
    public double width;

    /** The height of this arc's framing rectangle. */
    public double height;

    /** The starting angle of this arc. */
    public double start;

    /** The angular extent of this arc. */
    public double extent;

    /**
     * Creates an open arc with frame (0x0+0+0) and zero angles.
     */
    public Arc () {
        this(OPEN);
    }

    /**
     * Creates an arc of the specified type with frame (0x0+0+0) and zero angles.
     */
    public Arc (int type) {
        setArcType(type);
    }

    /**
     * Creates an arc of the specified type with the specified framing rectangle, starting angle
     * and angular extent.
     */
    public Arc (double x, double y, double width, double height,
                double start, double extent, int type) {
        setArc(x, y, width, height, start, extent, type);
    }

    /**
     * Creates an arc of the specified type with the supplied framing rectangle, starting angle and
     * angular extent.
     */
    public Arc (IRectangle bounds, double start, double extent, int type) {
        setArc(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(),
               start, extent, type);
    }

    @Override // from interface IArc
    public int getArcType () {
        return type;
    }

    @Override // from interface IArc
    public double getX () {
        return x;
    }

    @Override // from interface IArc
    public double getY () {
        return y;
    }

    @Override // from interface IArc
    public double getWidth () {
        return width;
    }

    @Override // from interface IArc
    public double getHeight () {
        return height;
    }

    @Override // from interface IArc
    public double getAngleStart () {
        return start;
    }

    @Override // from interface IArc
    public double getAngleExtent () {
        return extent;
    }

    /**
     * Sets the type of this arc to the specified value.
     */
    public void setArcType (int type) {
        if (type != OPEN && type != CHORD && type != PIE) {
            throw new IllegalArgumentException("Invalid Arc type: " + type);
        }
        this.type = type;
    }

    /**
     * Sets the starting angle of this arc to the specified value.
     */
    public void setAngleStart (double start) {
        this.start = start;
    }

    /**
     * Sets the angular extent of this arc to the specified value.
     */
    public void setAngleExtent (double extent) {
        this.extent = extent;
    }

    /**
     * Sets the location, size, angular extents, and closure type of this arc to the specified
     * values.
     */
    public void setArc (double x, double y, double width, double height,
                        double start, double extent, int type) {
        setArcType(type);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.start = start;
        this.extent = extent;
    }

    /**
     * Sets the location, size, angular extents, and closure type of this arc to the specified
     * values.
     */
    public void setArc (IPoint point, IDimension size, double start, double extent, int type) {
        setArc(point.getX(), point.getY(), size.getWidth(), size.getHeight(), start, extent, type);
    }

    /**
     * Sets the location, size, angular extents, and closure type of this arc to the specified
     * values.
     */
    public void setArc (IRectangle rect, double start, double extent, int type) {
        setArc(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), start, extent, type);
    }

    /**
     * Sets the location, size, angular extents, and closure type of this arc to the same values as
     * the supplied arc.
     */
    public void setArc (IArc arc) {
        setArc(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight(), arc.getAngleStart(),
               arc.getAngleExtent(), arc.getArcType());
    }

    /**
     * Sets the location, size, angular extents, and closure type of this arc based on the
     * specified values.
     */
    public void setArcByCenter (double x, double y, double radius,
                                double start, double extent, int type) {
        setArc(x - radius, y - radius, radius * 2f, radius * 2f, start, extent, type);
    }

    /**
     * Sets the location, size, angular extents, and closure type of this arc based on the
     * specified values.
     */
    public void setArcByTangent (IPoint p1, IPoint p2, IPoint p3, double radius) {
        // use simple geometric calculations of arc center, radius and angles by tangents
        double a1 = -Math.atan2(p1.getY() - p2.getY(), p1.getX() - p2.getX());
        double a2 = -Math.atan2(p3.getY() - p2.getY(), p3.getX() - p2.getX());
        double am = (a1 + a2) / 2f;
        double ah = a1 - am;
        double d = radius / Math.abs(Math.sin(ah));
        double x = p2.getX() + d * Math.cos(am);
        double y = p2.getY() - d * Math.sin(am);
        ah = ah >= 0f ? Math.PI * 1.5f - ah : Math.PI * 0.5f - ah;
        a1 = getNormAngle(Math.toDegrees(am - ah));
        a2 = getNormAngle(Math.toDegrees(am + ah));
        double delta = a2 - a1;
        if (delta <= 0f) {
            delta += 360f;
        }
        setArcByCenter(x, y, radius, a1, delta, type);
    }

    /**
     * Sets the starting angle of this arc to the angle defined by the supplied point relative to
     * the center of this arc.
     */
    public void setAngleStart (IPoint point) {
        double angle = Math.atan2(point.getY() - getCenterY(), point.getX() - getCenterX());
        setAngleStart(getNormAngle(-Math.toDegrees(angle)));
    }

    /**
     * Sets the starting angle and angular extent of this arc using two sets of coordinates. The
     * first set of coordinates is used to determine the angle of the starting point relative to
     * the arc's center. The second set of coordinates is used to determine the angle of the end
     * point relative to the arc's center. The arc will always be non-empty and extend
     * counterclockwise from the first point around to the second point.
     */
    public void setAngles (double x1, double y1, double x2, double y2) {
        double cx = getCenterX();
        double cy = getCenterY();
        double a1 = getNormAngle(-Math.toDegrees(Math.atan2(y1 - cy, x1 - cx)));
        double a2 = getNormAngle(-Math.toDegrees(Math.atan2(y2 - cy, x2 - cx)));
        a2 -= a1;
        if (a2 <= 0f) {
            a2 += 360f;
        }
        setAngleStart(a1);
        setAngleExtent(a2);
    }

    /**
     * Sets the starting angle and angular extent of this arc using two sets of coordinates. The
     * first set of coordinates is used to determine the angle of the starting point relative to
     * the arc's center. The second set of coordinates is used to determine the angle of the end
     * point relative to the arc's center. The arc will always be non-empty and extend
     * counterclockwise from the first point around to the second point.
     */
    public void setAngles (IPoint p1, IPoint p2) {
        setAngles(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @Override // from RectangularShape
    public void setFrame (double x, double y, double width, double height) {
        setArc(x, y, width, height, getAngleStart(), getAngleExtent(), type);
    }

    private int type;
}
