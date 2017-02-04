package ru.ifmo.ctddev.isaev;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static ru.ifmo.ctddev.isaev.PointComparators.*;


/**
 * @author iisaev
 */
public class RangeTreeSearch extends RangeSearch {
    private Node tree;

    protected RangeTreeSearch(List<Point> points) {
        super(points);
        points.sort(BY_X);
        tree = buildNode(points);
        boolean f = false;
    }

    private Node buildNode(List<Point> points) {
        if (points.size() > 2) {
            return buildComplexNode(points);
        } else if (points.size() == 2) {
            Point childPoint = points.get(0);
            Point parentPoint = points.get(1);
            Leaf child = new Leaf(childPoint);
            return new Node(child, null, parentPoint.x,
                    singletonList(new AssocPoint(parentPoint, 0, -1))
            );
        } else {
            return new Leaf(points.get(0));
        }
    }

    private Node buildComplexNode(List<Point> points) {
        List<Point> sortedByY = new ArrayList<>(points);
        sortedByY.sort(BY_Y);

        int medianIndex = points.size() / 2;
        Point median = points.get(medianIndex);
        List<Point> leftList = points.subList(0, medianIndex);
        List<Point> leftSortedByY = new ArrayList<>(leftList);
        leftSortedByY.sort(BY_Y);
        Node left = buildNode(leftList);

        List<Point> rightList = points.subList(medianIndex + 1, points.size());
        List<Point> rightSortedByY = new ArrayList<>(rightList);
        rightSortedByY.sort(BY_Y);
        Node right = buildNode(rightList);

        List<AssocPoint> assoc = new ArrayList<>();
        sortedByY.forEach(point -> {
            Integer leftIndex = getAssocIndex(leftSortedByY, point);
            Integer rightIndex = getAssocIndex(rightSortedByY, point);
            assoc.add(new AssocPoint(point, leftIndex, rightIndex));
        });

        assoc.sort(ASSOC_BY_Y);
        return new Node(left, right, median.x, assoc);
    }

    private Integer getAssocIndex(List<Point> leftSortedByY, Point point) {
        int pos = Collections.binarySearch(leftSortedByY, point, BY_Y);
        int res = pos >= 0 ? pos : -pos - 1;
        return res == leftSortedByY.size() ? null : res;
    }

    @Override
    public List<Point> query(Point topLeft, Point bottomRight) {
        int fromX = topLeft.x;
        int toX = bottomRight.y;
        int fromY = topLeft.y;
        int toY = bottomRight.y;
        throw new UnsupportedOperationException("Method is not implemented");
    }
}
