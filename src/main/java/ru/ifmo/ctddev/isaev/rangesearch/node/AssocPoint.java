package ru.ifmo.ctddev.isaev.rangesearch.node;

import java.awt.*;


/**
 * @author iisaev
 */
public class AssocPoint {
    private final Point point;

    private final Integer leftIndex;

    private final Integer rightIndex;

    public AssocPoint(Point point, Integer leftIndex, Integer rightIndex) {
        this.point = point;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    public Point getPoint() {
        return point;
    }

    public Integer getLeftIndex() {
        return leftIndex;
    }

    public Integer getRightIndex() {
        return rightIndex;
    }

    @Override
    public String toString() {
        return String.format("[%s/%s],%s,%s", point.x, point.y, leftIndex, rightIndex);
    }
}
