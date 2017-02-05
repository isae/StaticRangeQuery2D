package ru.ifmo.ctddev.isaev.rangesearch;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author iisaev
 */
public class NaiveRangeSearch extends RangeSearch {
    public NaiveRangeSearch(List<Point> points) {
        super(points);
    }

    @Override
    public List<Point> query(Point point1, Point point2) {
        int fromX = Math.min(point1.x, point2.x);
        int toX = Math.max(point1.x, point2.x);
        int fromY = Math.min(point1.y, point2.y);
        int toY = Math.max(point1.y, point2.y);
        return points.stream()
                .filter(p -> p.x > fromX && p.y > fromY && p.x < toX && p.y < toY)
                .collect(Collectors.toList());
    }
}
