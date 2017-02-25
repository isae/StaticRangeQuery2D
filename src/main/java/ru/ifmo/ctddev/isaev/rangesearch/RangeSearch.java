package ru.ifmo.ctddev.isaev.rangesearch;

import java.util.List;


/**
 * @author iisaev
 */
public abstract class RangeSearch {
    protected final List<MyPoint> points;

    protected RangeSearch(List<MyPoint> points) {
        this.points = points;
    }

    public abstract List<MyPoint> query(MyPoint topLeft, MyPoint bottomRight, int topBias);
}
