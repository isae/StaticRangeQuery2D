package ru.ifmo.ctddev.isaev;

import java.awt.*;
import java.util.List;


/**
 * @author iisaev
 */
public class RangeTreeSearch extends RangeSearch {
    protected RangeTreeSearch(List<Point> points) {
        super(points);
    }

    @Override
    public List<Point> query(Point topLeft, Point bottomRight) {
        throw new UnsupportedOperationException("Method is not implemented");
    }
}
