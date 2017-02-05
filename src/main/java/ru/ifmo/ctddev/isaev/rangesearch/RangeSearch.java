package ru.ifmo.ctddev.isaev.rangesearch;

import java.awt.*;
import java.util.List;


/**
 * @author iisaev
 */
public abstract class RangeSearch {
    protected final java.util.List<Point> points;

    protected RangeSearch(List<Point> points) {
        this.points = points;
    }

    public abstract List<Point> query(Point topLeft, Point bottomRight, int pointSize);
}
