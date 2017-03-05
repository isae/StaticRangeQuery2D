package ru.ifmo.ctddev.isaev.util;

import ru.ifmo.ctddev.isaev.rangesearch.node.Point2D;

import java.util.Comparator;


/**
 * @author iisaev
 */
public class PointComparators {
    public static final Comparator<Point2D> BY_X = Comparator.comparingInt(Point2D::getX);

    public static final Comparator<Point2D> BY_Y = Comparator.comparingInt(Point2D::getY);
}
