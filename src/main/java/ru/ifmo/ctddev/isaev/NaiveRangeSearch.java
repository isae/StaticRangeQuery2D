package ru.ifmo.ctddev.isaev;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author iisaev
 */
public class NaiveRangeSearch extends RangeSearch {
    protected NaiveRangeSearch(List<Point> points) {
        super(points);
    }

    @Override
    public List<Point> query(Point topLeft, Point bottomRight) {
        return points.stream()
                .filter(p -> p.x > topLeft.x && p.y > topLeft.y && p.x < bottomRight.x && p.y < bottomRight.y)
                .collect(Collectors.toList());
    }
}
