package ru.ifmo.ctddev.isaev.rangesearch;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;


/**
 * @author iisaev
 */
public class NaiveRangeSearch extends RangeSearch {
    public NaiveRangeSearch(List<MyPoint> points) {
        super(points);
    }

    @Override
    public List<MyPoint> query(MyPoint point1, MyPoint point2, int topBias) {
        int fromX = min(point1.x, point2.x);
        int toX = max(point1.x, point2.x);
        int fromY = min(point1.y, point2.y);
        int toY = max(point1.y, point2.y);
        return points.stream()
                .filter(p -> p.x >= fromX && p.y >= fromY && p.x <= toX && p.y <= toY)
                .collect(Collectors.toList());
    }
}
