package ru.ifmo.ctddev.isaev.rangesearch.node;

import java.awt.*;


/**
 * @author iisaev
 */
public class Rect {
    private final Point point1;

    private final Point point2;

    private final int fromX;

    private final int fromY;

    private final int toX;

    private final int toY;

    private final int width;

    private final int height;

    public Rect(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
        fromX = Math.min(point1.x, point2.x);
        toX = Math.max(point1.x, point2.x);
        fromY = Math.min(point1.y, point2.y);
        toY = Math.max(point1.y, point2.y);
        width = toX - fromX;
        height = toY - fromY;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
