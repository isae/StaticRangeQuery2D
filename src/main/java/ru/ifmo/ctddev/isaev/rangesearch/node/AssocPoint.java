package ru.ifmo.ctddev.isaev.rangesearch.node;

import ru.ifmo.ctddev.isaev.rangesearch.MyPoint;

import java.util.Objects;


/**
 * @author iisaev
 */
public class AssocPoint implements Point2D {
    private final MyPoint point;

    private final Integer leftIndex;

    private final Integer rightIndex;

    public AssocPoint(MyPoint point, Integer leftIndex, Integer rightIndex) {
        this.point = point;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    public MyPoint getPoint() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssocPoint that = (AssocPoint) o;
        return Objects.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }

    @Override
    public int getX() {
        return point.x;
    }

    @Override
    public int getY() {
        return point.y;
    }
}
