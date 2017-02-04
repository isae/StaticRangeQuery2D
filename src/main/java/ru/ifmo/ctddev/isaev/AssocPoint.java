package ru.ifmo.ctddev.isaev;

import java.awt.Point;


/**
 * @author iisaev
 */
public class AssocPoint {
    private final Point point;
    private final int subListIndex;

    public AssocPoint(Point point, int subListIndex) {
        this.point = point;
        this.subListIndex = subListIndex;
    }

    public Point getPoint() {
        return point;
    }

    public int getSubListIndex() {
        return subListIndex;
    }
}
