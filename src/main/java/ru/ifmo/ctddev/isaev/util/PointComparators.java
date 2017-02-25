package ru.ifmo.ctddev.isaev.util;

import ru.ifmo.ctddev.isaev.rangesearch.MyPoint;
import ru.ifmo.ctddev.isaev.rangesearch.node.AssocPoint;

import java.util.Comparator;


/**
 * @author iisaev
 */
public class PointComparators {
    public static final Comparator<MyPoint> BY_X = Comparator.comparingInt(p -> p.x);

    public static final Comparator<MyPoint> BY_Y = Comparator.comparingInt(p -> p.y);

    public static final Comparator<AssocPoint> ASSOC_BY_Y = Comparator.comparingInt(p -> p.getPoint().y);
}
