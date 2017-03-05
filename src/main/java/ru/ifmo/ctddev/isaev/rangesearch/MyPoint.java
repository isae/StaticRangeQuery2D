package ru.ifmo.ctddev.isaev.rangesearch;

import ru.ifmo.ctddev.isaev.rangesearch.node.Point2D;

import java.util.Objects;


/**
 * @author iisaev
 */
public class MyPoint implements Point2D {
    public final int x;

    public final int y;

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyPoint myPoint = (MyPoint) o;
        return x == myPoint.x &&
                y == myPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + " " + y + ")";
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
