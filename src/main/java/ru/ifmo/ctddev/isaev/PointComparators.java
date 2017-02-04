package ru.ifmo.ctddev.isaev;

import java.awt.*;
import java.util.Comparator;


/**
 * @author iisaev
 */
public class PointComparators {
    public static final Comparator<Point> BY_X = Comparator.comparingInt(p -> p.x);

    public static final Comparator<Point> BY_Y = Comparator.comparingInt(p -> p.y);

    public static final Comparator<AssocPoint> ASSOC_BY_Y = Comparator.comparingInt(p -> p.getPoint().y);
}
