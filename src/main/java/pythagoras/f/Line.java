//
// $Id$

package pythagoras.f;

import java.io.Serializable;

/**
 * Represents a line segment.
 */
public class Line extends AbstractLine implements Serializable
{
    public float x1;
    public float y1;
    public float x2;
    public float y2;

    /**
     * Creates a line from (0,0) to (0,0).
     */
    public Line () {
    }

    /**
     * Creates a line from (x1,y1), to (x2,y2).
     */
    public Line (float x1, float y1, float x2, float y2) {
        setLine(x1, y1, x2, y2);
    }

    /**
     * Creates a line from p1 to p2.
     */
    public Line (IPoint p1, IPoint p2) {
        setLine(p1, p2);
    }

    /**
     * Sets the start and end point of this line to the specified values.
     */
    public void setLine (float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Sets the start and end of this line to the specified points.
     */
    public void setLine (IPoint p1, IPoint p2) {
        setLine(p1.getX(), p1.getY(), p2.getY(), p2.getY());
    }

    @Override // from interface ILine
    public float getX1 () {
        return x1;
    }

    @Override // from interface ILine
    public float getY1 () {
        return y1;
    }

    @Override // from interface ILine
    public float getX2 () {
        return x2;
    }

    @Override // from interface ILine
    public float getY2 () {
        return y2;
    }
}
