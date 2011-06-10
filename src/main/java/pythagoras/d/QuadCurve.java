//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.io.Serializable;

/**
 * Represents a quadratic curve.
 */
public class QuadCurve extends AbstractQuadCurve implements Serializable
{
    /** The x-coordinate of the start of this curve. */
    public double x1;

    /** The y-coordinate of the start of this curve. */
    public double y1;

    /** The x-coordinate of the control point. */
    public double ctrlx;

    /** The y-coordinate of the control point. */
    public double ctrly;

    /** The x-coordinate of the end of this curve. */
    public double x2;

    /** The y-coordinate of the end of this curve. */
    public double y2;

    /**
     * Creates a quad curve with all points at (0,0).
     */
    public QuadCurve () {
    }

    /**
     * Creates a quad curve with the specified start, control, and end points.
     */
    public QuadCurve (double x1, double y1, double ctrlx, double ctrly, double x2, double y2) {
        setCurve(x1, y1, ctrlx, ctrly, x2, y2);
    }

    /**
     * Configures the start, control, and end points for this curve.
     */
    public void setCurve (double x1, double y1, double ctrlx, double ctrly, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.ctrlx = ctrlx;
        this.ctrly = ctrly;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Configures the start, control, and end points for this curve.
     */
    public void setCurve (IPoint p1, IPoint cp, IPoint p2) {
        setCurve(p1.getX(), p1.getY(), cp.getX(), cp.getY(), p2.getX(), p2.getY());
    }

    /**
     * Configures the start, control, and end points for this curve, using the values at the
     * specified offset in the {@link coords} array.
     */
    public void setCurve (double[] coords, int offset) {
        setCurve(coords[offset + 0], coords[offset + 1],
                 coords[offset + 2], coords[offset + 3],
                 coords[offset + 4], coords[offset + 5]);
    }

    /**
     * Configures the start, control, and end points for this curve, using the values at the
     * specified offset in the {@link points} array.
     */
    public void setCurve (IPoint[] points, int offset) {
        setCurve(points[offset + 0].getX(), points[offset + 0].getY(),
                 points[offset + 1].getX(), points[offset + 1].getY(),
                 points[offset + 2].getX(), points[offset + 2].getY());
    }

    /**
     * Configures the start, control, and end points for this curve to be the same as the supplied
     * curve.
     */
    public void setCurve (IQuadCurve curve) {
        setCurve(curve.getX1(), curve.getY1(), curve.getCtrlX(), curve.getCtrlY(),
                 curve.getX2(), curve.getY2());
    }

    @Override // from interface IQuadCurve
    public double getX1 () {
        return x1;
    }

    @Override // from interface IQuadCurve
    public double getY1 () {
        return y1;
    }

    @Override // from interface IQuadCurve
    public double getCtrlX () {
        return ctrlx;
    }

    @Override // from interface IQuadCurve
    public double getCtrlY () {
        return ctrly;
    }

    @Override // from interface IQuadCurve
    public double getX2 () {
        return x2;
    }

    @Override // from interface IQuadCurve
    public double getY2 () {
        return y2;
    }
}
